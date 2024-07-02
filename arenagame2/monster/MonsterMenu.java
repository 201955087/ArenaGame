package me.air_bottle.arenagame2.monster;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent;
import io.lumine.mythic.core.mobs.ActiveMob;
import me.air_bottle.arenagame2.round.RoundController;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MonsterMenu implements MonsterBuilder, Listener {
    private final RoundController roundcontroller;
    private final JavaPlugin plugin;
    public int monsterCount = 0;
    private final List<ActiveMob> activeMobs = new ArrayList<>();
    private final BukkitAPIHelper mythicMobsHelper = MythicBukkit.inst().getAPIHelper();

    public MonsterMenu(RoundController roundcontroller, JavaPlugin plugin) {
        this.roundcontroller = roundcontroller;
        this.plugin = plugin;
    }

    @Override
    public void createMonsters(MythicMob mob, Location location, int level) {
        if(location == null) {
            System.err.println("spawnLocation is null");
            return;
        }
        if (mob != null) {
            for (int i = 0; i < level; i++) {
                ActiveMob activeMob = mob.spawn(BukkitAdapter.adapt(location), level);
                activeMobs.add(activeMob);
                monsterCount++;
            }
        }
    }

    @EventHandler
    public void remainingMonsters(MythicMobDeathEvent event) {
        Player player = (Player) event.getKiller();
        if(event.getMob().getMobType().equals(spade.getInternalName()) || event.getMob().getMobType().equals(heart.getInternalName())) {
            monsterCount--;
            player.sendMessage("남은 몬스터: " + monsterCount);
        }
        if(roundcontroller.inRound && monsterCount <= 0) {
            roundcontroller.clearRound(player);
        }
    }
    @Override
    public void removeMonsters() {
        for(ActiveMob activeMob : activeMobs) {
            activeMob.getEntity().remove();
        }
        monsterCount = 0;
        activeMobs.clear();
    }

    public final MythicMob spade = MythicBukkit.inst().getAPIHelper().getMythicMob("SpadeSoldier");
    public final MythicMob heart = MythicBukkit.inst().getAPIHelper().getMythicMob("HeartSoldier");
}
