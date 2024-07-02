package me.air_bottle.arenagame2.reward.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface SpecialItemsBuilder {
    ItemStack armorSpecial();
    ItemStack weaponSpecial();
    ItemStack buffSpecial();
    void usingSpecial(PlayerInteractEvent event);
}
