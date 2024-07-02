package me.air_bottle.arenagame2.player;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import me.air_bottle.arenagame2.monster.MonsterMenu;
import me.air_bottle.arenagame2.round.RoundController;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerController implements PlayerMenu, Listener {
    private final RoundController roundcontroller;

    public PlayerController(RoundController roundcontroller) {
        this.roundcontroller = roundcontroller;
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if(roundcontroller.inRound) {
            player.sendMessage(roundcontroller.getRound() + "라운드에서 사망");
            roundcontroller.endGame(player);
        }
    }
    @EventHandler
    public void onFoodLevel(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        if(roundcontroller.inRound) {
            player.setFoodLevel(20);
            event.setCancelled(true);
        }
    }
}
