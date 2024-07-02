package me.air_bottle.arenagame2.monster;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public interface MonsterBuilder {
    void createMonsters(MythicMob mob, Location location, int level);
    void remainingMonsters(MythicMobDeathEvent event);
    void removeMonsters();
    MythicMob spade = null;
    MythicMob heart = null;
}
