package me.air_bottle.arenagame2.reward.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class SpecialItems implements SpecialItemsBuilder, Listener {
    private final JavaPlugin plugin;

    public SpecialItems(JavaPlugin plugin) {
        this.plugin = plugin;
        if(this.plugin == null) {
            throw new IllegalArgumentException("plugin cannot be null");
        }
    }
    @Override
    public ItemStack armorSpecial() {
        ItemStack armorSpecial = new ItemStack(Material.BONE);
        ItemMeta meta = armorSpecial.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "소환자");
        List<String> lore = new ArrayList<>();
        lore.add("사용 시 미끼용 주민 3마리 소환");
        lore.add("쿨타임 50초");
        meta.setLore(lore);
        armorSpecial.setItemMeta(meta);
        return armorSpecial;
    }
    @Override
    public ItemStack weaponSpecial() {
        ItemStack weaponSpecial = new ItemStack(Material.STICK);
        ItemMeta meta = weaponSpecial.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "한방자");
        List<String> lore = new ArrayList<>();
        lore.add("사용 시 10초동안 힘 최대버프 부여");
        lore.add("쿨타임 45초");
        meta.setLore(lore);
        weaponSpecial.setItemMeta(meta);
        return weaponSpecial;
    }
    @Override
    public ItemStack buffSpecial() {
        ItemStack buffSpecial = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = buffSpecial.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "수호자");
        List<String> lore = new ArrayList<>();
        lore.add("사용 시 10초간 무적(저항 버프 5단계)");
        lore.add("쿨타임 30초");
        meta.setLore(lore);
        buffSpecial.setItemMeta(meta);
        return buffSpecial;
    }
    @EventHandler
    public void usingSpecial(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.NETHER_STAR
                && item.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "수호자")) {
            int cooldown = player.getCooldown(Material.NETHER_STAR);

            if (cooldown > 0) {
                int remainingCooldown = cooldown / 20;
                player.sendMessage("재사용까지 " + remainingCooldown + "초");
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 4));
            player.sendMessage("수호자 아이템 사용");
            player.setCooldown(Material.NETHER_STAR, 600);
        }
        if (item != null && item.getType() == Material.STICK
                && item.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "한방자")) {
            int cooldown = player.getCooldown(Material.STICK);

            if (cooldown > 0) {
                int remainingCooldown = cooldown / 20;
                player.sendMessage("재사용까지 " + remainingCooldown + "초");
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, Integer.MAX_VALUE));
            player.sendMessage("힘버프 부여");
            player.setCooldown(Material.STICK, 850);
        }
        if (item != null && item.getType() == Material.BONE
                && item.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "소환자")) {
            int cooldown = player.getCooldown(Material.BONE);
            if(cooldown > 0) {
                int remainingCooldown = cooldown / 20;
                player.sendMessage("재사용까지 " + remainingCooldown + "초");
                return;
            }
            for(int i = 0; i < 3; i++) {
                Villager villager = player.getWorld().spawn(player.getLocation(), Villager.class);
                villager.setCustomName(ChatColor.GOLD + "미끼");
            }
            player.sendMessage("전용개 4마리 소환");
            player.setCooldown(Material.BONE, 1000);
        }
    }
}
