package me.air_bottle.arenagame2.reward;

import org.bukkit.entity.Player;

public interface RewardBuilder {
    void defaultItems(Player player);
    void startingItems(Player player);
    void upgradeArmor(Player player);
    void upgradeWeapon(Player player);
    void buffUpgrade(Player player);
    void resetReward(Player player);

}
