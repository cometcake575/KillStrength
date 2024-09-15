package com.starshooterstudios.killstrength;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.starshooterstudios.killstrength.systems.*;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.intellij.lang.annotations.Subst;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static List<System> systems = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        emptyKey = new NamespacedKey(this, "empty");
        systems.add(new SpeedSystem());
        systems.add(new StrengthSystem());
        systems.add(new JumpSystem());
        systems.add(new HasteSystem());
        systems.add(new HeroSystem());
        systems.add(new RegenSystem());
        for (System system : systems) Bukkit.getPluginManager().registerEvents(system, this);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new CustomItems(this), this);

        systemKey = new NamespacedKey(this, "system-type");
    }

    public static NamespacedKey systemKey;

    private static NamespacedKey emptyKey;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().getPersistentDataContainer().has(systemKey)) {
            openSystemChooser(event.getPlayer());
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer().getPersistentDataContainer().has(systemKey)) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> openSystemChooser((Player) event.getPlayer()), 20);
    }

    public static void openSystemChooser(Player player) {
        player.getPersistentDataContainer().remove(systemKey);
        Inventory inventory = Bukkit.createInventory(null, InventoryType.DISPENSER, Component.text("Choose your power"));
        player.openInventory(inventory);
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = empty.getItemMeta();
        meta.setHideTooltip(true);
        meta.displayName(Component.empty());
        meta.getPersistentDataContainer().set(emptyKey, PersistentDataType.BOOLEAN, true);
        empty.setItemMeta(meta);

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, empty);
        }

        inventory.setItem(0, generateSystemItem("strength", Material.IRON_SWORD, "Strength"));
        inventory.setItem(1, generateSystemItem("speed", Material.FEATHER, "Speed"));
        inventory.setItem(2, generateSystemItem("haste", Material.GOLDEN_PICKAXE, "Haste"));
        inventory.setItem(6, generateSystemItem("jump", Material.SLIME_BALL, "Jump Boost"));
        inventory.setItem(7, generateSystemItem("regen", Material.DIAMOND, "Regeneration"));
        inventory.setItem(8, generateSystemItem("hero", Material.EMERALD, "Hero of the Village"));
    }

    public static ItemStack generateSystemItem(String systemID, Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(name).decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(systemKey, PersistentDataType.STRING, systemID);
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void onServerTickEnd(ServerTickEndEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            @Subst("killstrength:empty") String formatted = "killstrength:%s".formatted(player.getPersistentDataContainer().getOrDefault(systemKey, PersistentDataType.STRING, "empty"));
            player.sendActionBar(Component.text("\uE000").font(Key.key(formatted)));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;
        if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(emptyKey)) event.setCancelled(true);
        if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(systemKey)) {
            event.setCancelled(true);
            event.getWhoClicked().getPersistentDataContainer().set(systemKey, PersistentDataType.STRING, event.getCurrentItem().getItemMeta().getPersistentDataContainer().getOrDefault(systemKey, PersistentDataType.STRING, ""));
            event.getWhoClicked().closeInventory();
        }
    }
}