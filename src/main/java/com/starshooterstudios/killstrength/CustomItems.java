package com.starshooterstudios.killstrength;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomItems implements Listener {
    public static ItemStack increaser;

    public CustomItems(JavaPlugin plugin) {
        neutraliserKey = new NamespacedKey(plugin, "neutraliser");
        switcherKey = new NamespacedKey(plugin, "switcher");
        increaserKey = new NamespacedKey(plugin, "increaser");

        increaser = new ItemStack(Material.POTION);
        ItemMeta iMeta = increaser.getItemMeta();
        iMeta.getPersistentDataContainer().set(increaserKey, PersistentDataType.BOOLEAN, true);
        increaser.setItemMeta(iMeta);

        ItemStack neutraliser = new ItemStack(Material.POTION);
        ItemMeta nMeta = neutraliser.getItemMeta();
        nMeta.getPersistentDataContainer().set(neutraliserKey, PersistentDataType.BOOLEAN, true);
        neutraliser.setItemMeta(nMeta);
        ShapedRecipe neutraliserRecipe = new ShapedRecipe(neutraliserKey, neutraliser);
        neutraliserRecipe.shape("DSD", "NUN", "DTD");
        neutraliserRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        neutraliserRecipe.setIngredient('S', Material.NETHER_STAR);
        neutraliserRecipe.setIngredient('N', Material.NETHERITE_INGOT);
        neutraliserRecipe.setIngredient('U', Material.TOTEM_OF_UNDYING);
        neutraliserRecipe.setIngredient('T', Material.TRIDENT);

        Bukkit.removeRecipe(neutraliserRecipe.getKey());
        Bukkit.addRecipe(neutraliserRecipe);

        ItemStack switcher = new ItemStack(Material.NETHER_STAR);
        ItemMeta sMeta = switcher.getItemMeta();
        sMeta.getPersistentDataContainer().set(switcherKey, PersistentDataType.BOOLEAN, true);
        switcher.setItemMeta(sMeta);
        ShapedRecipe switcherRecipe = new ShapedRecipe(switcherKey, switcher);
        switcherRecipe.shape("DAD", "NBN", "DAD");
        switcherRecipe.setIngredient('D', Material.DIAMOND_BLOCK);
        switcherRecipe.setIngredient('A', Material.GOLDEN_APPLE);
        switcherRecipe.setIngredient('N', Material.NETHERITE_INGOT);
        switcherRecipe.setIngredient('B', Material.NETHERITE_BLOCK);

        Bukkit.removeRecipe(switcherRecipe.getKey());
        Bukkit.addRecipe(switcherRecipe);
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getItemMeta() == null) return;
        for (System system : Main.systems) {
            if (!event.getPlayer().getPersistentDataContainer().getOrDefault(Main.systemKey, PersistentDataType.STRING, "").equals(system.getSystemName())) continue;
            if (event.getItem().getItemMeta().getPersistentDataContainer().has(neutraliserKey)) {
                system.reset(event.getPlayer());
            } else if (event.getItem().getItemMeta().getPersistentDataContainer().has(increaserKey)) {
                system.increment(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getItem().getItemMeta() == null) return;
        if (!event.getAction().isRightClick()) return;
        if (event.getItem().getItemMeta().getPersistentDataContainer().has(switcherKey)) {
            event.getItem().setAmount(event.getItem().getAmount() - 1);
            Main.openSystemChooser(event.getPlayer());
        }
    }

    private final NamespacedKey neutraliserKey;
    private final NamespacedKey increaserKey;
    private final NamespacedKey switcherKey;
}
