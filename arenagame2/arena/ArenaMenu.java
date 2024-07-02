package me.air_bottle.arenagame2.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public interface ArenaMenu {
    void createArena(Player player);
    void createBorder(Location location);
    void removeArena(Player player);
    void removeBorder(Location location);
    void onBlockBreak(BlockBreakEvent event);
}
