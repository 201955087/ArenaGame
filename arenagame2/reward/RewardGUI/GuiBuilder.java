package me.air_bottle.arenagame2.reward.RewardGUI;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface GuiBuilder {
    void openRewardGUI(Player player);
    void onInventoryClick(InventoryClickEvent event);

}
