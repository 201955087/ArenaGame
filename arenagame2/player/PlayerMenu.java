package me.air_bottle.arenagame2.player;

import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public interface PlayerMenu {
    void onPlayerDeath(PlayerDeathEvent event);
    void onFoodLevel(FoodLevelChangeEvent event);
}
