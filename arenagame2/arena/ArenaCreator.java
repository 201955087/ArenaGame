package me.air_bottle.arenagame2.arena;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.HashSet;
import java.util.Set;

public class ArenaCreator implements ArenaMenu, Listener {
    private final int arenaSize = 30;
    private static final Set<Location> arenaLocations = new HashSet<>();
    public Location cornerLocation;
    public Location centerLocation;
    public Location halfLocation;
    @Override
    public void createArena(Player player) {
        Location location = player.getLocation();

        int startX = location.getBlockX() - arenaSize / 2;
        int startZ = location.getBlockZ() - arenaSize / 2;
        int startY = location.getBlockY() + 6;

        for(int x = startX; x < startX + 30; x++) {
            for(int z = startZ; z < startZ + 30; z++) {
                for(int i = 0; i < 6; i++) {
                    if(!location.getWorld().getBlockAt(x, startY + i, z).getType().equals(Material.AIR)) {
                        player.sendMessage("아레나 생성 불가");
                        return;
                    }
                }
                Block block = location.getWorld().getBlockAt(x, startY, z);
                block.setType(Material.DRIED_KELP_BLOCK);
                arenaLocations.add(block.getLocation().clone());
            }
        }
        int centerX = startX + arenaSize / 2;
        int centerZ = startZ + arenaSize / 2;
        int halfX = startX + arenaSize / 5;
        int halfZ = startZ + arenaSize / 5;
        halfLocation = new Location(location.getWorld(), halfX, startY + 1, halfZ);
        centerLocation = new Location(location.getWorld(), centerX, startY + 1, centerZ);
        createBorder(centerLocation);
        player.teleport(centerLocation);
        player.sendMessage("아레나 생성");

        cornerLocation = new Location(location.getWorld(), startX + 1, startY +1, startZ + 1);
    }

    @Override
    public void createBorder(Location location) {
        World world = location.getWorld();
        WorldBorder worldBorder = world.getWorldBorder();

        worldBorder.setCenter(location);
        worldBorder.setSize(arenaSize);
    }

    @Override
    public void removeArena(Player player) {
        if(arenaLocations.isEmpty()) {
            player.sendMessage("아레나가 존재하지 않음");
            return;
        }
        for(Location location : arenaLocations) {
            Block block = location.getBlock();
            if(block.getType() == Material.DRIED_KELP_BLOCK) {
                block.setType(Material.AIR);
            }
        }
        arenaLocations.clear();
        player.sendMessage("아레나 제거");
    }

    @Override
    public void removeBorder(Location location) {
        World world = location.getWorld();
        WorldBorder worldBorder = world.getWorldBorder();

        worldBorder.reset();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.DRIED_KELP_BLOCK) {
            event.setCancelled(true);
        }
    }
}
