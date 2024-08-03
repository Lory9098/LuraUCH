package me.nettychannell.lurauhc.utils;

import org.bukkit.Material;

public class IngotUtils {
    public static Material getIngot(Material type) {
        if (type == Material.IRON_ORE) {
            return Material.IRON_INGOT;
        } else if (type == Material.GOLD_ORE) {
            return Material.GOLD_INGOT;
        } else if (type == Material.DIAMOND_ORE) {
            return Material.DIAMOND;
        } else if (type == Material.EMERALD_ORE) {
            return Material.EMERALD;
        } else if (type == Material.REDSTONE_ORE) {
            return Material.REDSTONE;
        } else if (type == Material.LAPIS_ORE) {
            return Material.LAPIS_LAZULI;
        }
        return null;
    }
}
