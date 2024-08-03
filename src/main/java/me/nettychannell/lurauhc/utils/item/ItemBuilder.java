package me.nettychannell.lurauhc.utils.item;

import me.nettychannell.lurauhc.utils.ChatUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.*;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        meta = item.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, 1);
    }

    public ItemBuilder(ItemStack items) {
        item = items;
        meta = item.getItemMeta();
    }

    public ItemBuilder(ItemStack items, int amount) {
        item = items;
        if (amount > 64 || amount < 0) amount = 64;
        item.setAmount(amount);
        meta = item.getItemMeta();
    }

    public ItemBuilder name(String name) {
        meta.displayName(Component.text(ChatUtil.color(name)));
        return this;
    }

    public String getName() {
        return PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(meta.displayName()));
    }

    public ItemBuilder setDurability(int damage) {
        Damageable damageable = (Damageable) meta;
        damageable.setDamage(damage);
        meta = (ItemMeta) damageable;
        return this;
    }

    public ItemBuilder setByte(int data) {
        item = new ItemStack(item.getType(), item.getAmount(), (short) data);
        item.setItemMeta(meta);
        return this;
    }

    public byte getByte() {
        return item.getData().getData();
    }

    public List<String> getLore() {
        return meta.getLore();
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(ChatUtil.color(Arrays.asList(lore)));
    }

    public ItemBuilder setLore(List<String> lore) {
        meta.setLore(ChatUtil.color(lore));
        return this;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return new HashMap<>(meta.getEnchants());
    }

    public ItemBuilder addLore(List<String> lores) {
        List<String> newLore = meta.getLore();
        newLore.addAll(ChatUtil.color(lores));

        meta.setLore(newLore);

        return this;
    }

    public ItemBuilder setCustomModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    public int getCustomModelData() {
        return meta.getCustomModelData();
    }

    public ItemBuilder setFlags(ItemFlag... flags) {
        for (ItemFlag flag : flags)
            meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        meta.addEnchant(ench, level, true);
        return this;
    }

    public ItemBuilder addLoreLines(List<String> lines) {
        if (meta.hasLore()) {
            List<String> lore = new ArrayList<>(meta.getLore());
            lore.addAll(lines);
            meta.setLore(lore);
        } else {
            meta.setLore(lines);
        }
        return this;
    }

    public ItemBuilder addLoreLines(String... lines) {
        if (meta.hasLore()) {
            List<String> lore = new ArrayList<>(meta.getLore());
            lore.addAll(ChatUtil.color(Arrays.asList(lines)));
            meta.setLore(lore);
        } else {
            meta.setLore(ChatUtil.color(Arrays.asList(lines)));
        }
        return this;
    }

    public ItemBuilder setMeta(ItemMeta meta) {
        this.meta = meta;
        return this;
    }

    public ItemBuilder spawner(EntityType entityType) {
        BlockStateMeta blockMeta = (BlockStateMeta) meta;
        BlockState blockState = blockMeta.getBlockState();
        CreatureSpawner spawner = (CreatureSpawner) blockState;

        spawner.setSpawnedType(entityType);
        blockMeta.setBlockState(spawner);

        item.setItemMeta(blockMeta);
        meta = item.getItemMeta();
        return this;
    }

    public ItemBuilder setLeatherColor(int red, int green, int blue) {
        LeatherArmorMeta im = (LeatherArmorMeta) meta;
        im.setColor(Color.fromRGB(red, green, blue));
        return this;
    }

    public ItemBuilder setGlowing(boolean glowing) {
        if (glowing) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
            meta.removeEnchant(Enchantment.DURABILITY);
            meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemBuilder setPotionType(PotionType type, int level) {
        Potion potion = new Potion(type, level);
        item = potion.toItemStack(item.getAmount());
        return this;
    }

    public ItemBuilder addPotionEffect(PotionEffectType type, int duration, int amplifier) {
        if (meta instanceof PotionMeta) {
            PotionMeta potionMeta = (PotionMeta) meta;
            potionMeta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);
            this.meta = potionMeta;
        }
        return this;
    }

    public ItemBuilder setSplashable(boolean splashable) {
        if (item.getType() == Material.POTION) {
            Potion potion = Potion.fromItemStack(item);
            potion.setSplash(splashable);
            item = potion.toItemStack(item.getAmount());
        }
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public ItemBuilder clone() {
        try {
            return (ItemBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
