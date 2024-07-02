package me.air_bottle.arenagame2.round;

import org.bukkit.entity.Player;

public interface RoundMenu {
    void startGame(Player player);
    void startRound(Player player, String difficulty);
    void clearRound(Player player);
    void endGame(Player player);
    void roundCountdown(Player player, int delay);
    int getRound();
}
