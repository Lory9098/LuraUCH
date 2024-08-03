package me.nettychannell.lurauhc.crafting;

import me.nettychannell.lurauhc.LuraUHC;
import me.nettychannell.lurauhc.utils.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.Bukkit;

public class CustomCraftings {

    public static void registerCraftings() {
        LuraUHC plugin = LuraUHC.getInstance();

        RecipeChoice.MaterialChoice anyWood = new RecipeChoice.MaterialChoice(
                Material.OAK_PLANKS,
                Material.SPRUCE_PLANKS,
                Material.BIRCH_PLANKS,
                Material.JUNGLE_PLANKS,
                Material.ACACIA_PLANKS,
                Material.DARK_OAK_PLANKS,
                Material.CRIMSON_PLANKS,
                Material.WARPED_PLANKS
        );

        ShapedRecipe woodPickaxe = new ShapedRecipe(
                new NamespacedKey(plugin, "advanced_wood_pickaxe"),
                new ItemBuilder(Material.STONE_PICKAXE)
                        .addEnchant(Enchantment.DIG_SPEED, 1)
                        .addEnchant(Enchantment.DURABILITY, 3)
                        .build()
        );
        woodPickaxe.shape("WWW", " S ", " S ");
        woodPickaxe.setIngredient('W', anyWood);
        woodPickaxe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(woodPickaxe);

        ShapedRecipe stonePickaxe = new ShapedRecipe(
                new NamespacedKey(plugin, "advanced_stone_pickaxe"),
                new ItemBuilder(Material.IRON_PICKAXE)
                        .addEnchant(Enchantment.DIG_SPEED, 1)
                        .addEnchant(Enchantment.DURABILITY, 3)
                        .build()
        );
        stonePickaxe.shape("SSS", " S ", " S ");
        stonePickaxe.setIngredient('S', Material.COBBLESTONE);
        Bukkit.addRecipe(stonePickaxe);

        ShapedRecipe woodAxe = new ShapedRecipe(
                new NamespacedKey(plugin, "advanced_wood_axe"),
                new ItemBuilder(Material.STONE_AXE)
                        .addEnchant(Enchantment.DIG_SPEED, 1)
                        .addEnchant(Enchantment.DURABILITY, 3)
                        .build()
        );
        woodAxe.shape("WW ", "WS ", " S ");
        woodAxe.setIngredient('W', anyWood);
        woodAxe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(woodAxe);

        ShapedRecipe stoneAxe = new ShapedRecipe(
                new NamespacedKey(plugin, "advanced_stone_axe"),
                new ItemBuilder(Material.IRON_AXE)
                        .addEnchant(Enchantment.DIG_SPEED, 1)
                        .addEnchant(Enchantment.DURABILITY, 3)
                        .build()
        );
        stoneAxe.shape("II ", "IS ", " S ");
        stoneAxe.setIngredient('I', Material.COBBLESTONE);
        stoneAxe.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(stoneAxe);

        ShapedRecipe woodShovel = new ShapedRecipe(
                new NamespacedKey(plugin, "advanced_wood_shovel"),
                new ItemBuilder(Material.STONE_SHOVEL)
                        .addEnchant(Enchantment.DIG_SPEED, 1)
                        .addEnchant(Enchantment.DURABILITY, 3)
                        .build()
        );
        woodShovel.shape(" W ", " S ", " S ");
        woodShovel.setIngredient('W', anyWood);
        woodShovel.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(woodShovel);

        ShapedRecipe stoneShovel = new ShapedRecipe(
                new NamespacedKey(plugin, "advanced_stone_shovel"),
                new ItemBuilder(Material.IRON_SHOVEL)
                        .addEnchant(Enchantment.DIG_SPEED, 1)
                        .addEnchant(Enchantment.DURABILITY, 3)
                        .build()
        );
        stoneShovel.shape(" I ", " S ", " S ");
        stoneShovel.setIngredient('I', Material.COBBLESTONE);
        stoneShovel.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(stoneShovel);

        ShapedRecipe woodSword = new ShapedRecipe(
                new NamespacedKey(plugin, "advanced_wood_sword"),
                new ItemBuilder(Material.STONE_SWORD)
                        .build()
        );
        woodSword.shape(" W ", " W ", " S ");
        woodSword.setIngredient('W', anyWood);
        woodSword.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(woodSword);

        ShapedRecipe stoneSword = new ShapedRecipe(
                new NamespacedKey(plugin, "advanced_stone_sword"),
                new ItemBuilder(Material.IRON_SWORD)
                        .build()
        );
        stoneSword.shape(" I ", " I ", " S ");
        stoneSword.setIngredient('I', Material.COBBLESTONE);
        stoneSword.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(stoneSword);
    }
}
