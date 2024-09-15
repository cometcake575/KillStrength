package com.starshooterstudios.killstrength;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class System implements Listener {
    @EventHandler
    public void onPlayerPostRespawn(PlayerPostRespawnEvent event) {
        applyStrength(event.getPlayer());
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> applyStrength(event.getPlayer()), 2);
    }

    public void increment(Player player) {
        player.getPersistentDataContainer().set(getDataKey(), PersistentDataType.INTEGER, getNext(player));
    }

    public void decrement(Player player) {
        player.getPersistentDataContainer().set(getDataKey(), PersistentDataType.INTEGER, getPrevious(player));
    }

    public int getNext(Player player) {
        int i = player.getPersistentDataContainer().getOrDefault(getDataKey(), PersistentDataType.INTEGER, 0) + 1;
        while (i >= 1) {
            i -= 1;
            incrementStrength(player);
        }
        return i;
    }

    public int getPrevious(Player player) {
        int i = player.getPersistentDataContainer().getOrDefault(getDataKey(), PersistentDataType.INTEGER, 0) - 1;
        while (i <= -1) {
            i += 1;
            decrementStrength(player);
        }
        return i;
    }

    public void incrementStrength(Player player) {
        player.getPersistentDataContainer().set(getEffectKey(), PersistentDataType.INTEGER, Math.min(2, getStrength(player) + 1));
        applyStrength(player);
    }

    public void decrementStrength(Player player) {
        player.getPersistentDataContainer().set(getEffectKey(), PersistentDataType.INTEGER, Math.max(-2, getStrength(player) - 1));
        applyStrength(player);
    }

    public void reset(Player player) {
        player.getPersistentDataContainer().set(getEffectKey(), PersistentDataType.INTEGER, Math.max(0, getStrength(player)));
        player.getPersistentDataContainer().set(getDataKey(), PersistentDataType.INTEGER, Math.max(0, player.getPersistentDataContainer().getOrDefault(getDataKey(), PersistentDataType.INTEGER, 0)));
        applyStrength(player);
    }

    public int getStrength(Player player) {
        return player.getPersistentDataContainer().getOrDefault(getEffectKey(), PersistentDataType.INTEGER, 0);
    }

    public void removeStrength(Player player) {
        PotionEffect effect = player.getPotionEffect(getStrongEffectType());
        if (effect != null && effect.isInfinite()) player.removePotionEffect(effect.getType());
    }

    public void removeWeakness(Player player) {
        PotionEffect effect = player.getPotionEffect(getWeakEffectType());
        if (effect != null && effect.isInfinite()) player.removePotionEffect(effect.getType());
    }

    public void applyStrength(Player player) {
        int strength = getStrength(player);
        if (strength == 0 || !player.getPersistentDataContainer().getOrDefault(Main.systemKey, PersistentDataType.STRING, "").equals(getSystemName())) {
            removeStrength(player);
            removeWeakness(player);
        } else if (strength > 0) {
            removeWeakness(player);
            player.addPotionEffect(new PotionEffect(getStrongEffectType(), PotionEffect.INFINITE_DURATION, getStrength(player) - 1));
        } else {
            removeStrength(player);
            player.addPotionEffect(new PotionEffect(getWeakEffectType(), PotionEffect.INFINITE_DURATION, Math.abs(getStrength(player)) - 1));
        }
    }

    public abstract NamespacedKey getDataKey();

    public abstract NamespacedKey getEffectKey();

    public abstract PotionEffectType getStrongEffectType();

    public abstract PotionEffectType getWeakEffectType();

    public abstract String getSystemName();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getPlayer().getKiller() != event.getPlayer() && event.getPlayer().getKiller() != null) {
            if (event.getPlayer().getKiller().getPersistentDataContainer().getOrDefault(Main.systemKey, PersistentDataType.STRING, "").equals(getSystemName())) {
                increment(event.getPlayer().getKiller());
            }
        } else event.getDrops().add(CustomItems.increaser);
        if (event.getPlayer() == event.getPlayer().getKiller()) return;
        if (event.getPlayer().getPersistentDataContainer().getOrDefault(Main.systemKey, PersistentDataType.STRING, "").equals(getSystemName())) {
            decrement(event.getPlayer());
        }
    }
}
