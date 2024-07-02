package me.air_bottle.arenagame2;

import me.air_bottle.arenagame2.arena.ArenaCreator;
import me.air_bottle.arenagame2.game.GameController;
import me.air_bottle.arenagame2.monster.MonsterMenu;
import me.air_bottle.arenagame2.player.PlayerController;
import me.air_bottle.arenagame2.reward.RewardGUI.Gui;
import me.air_bottle.arenagame2.reward.items.SpecialItems;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ArenaGame2 extends JavaPlugin {
    public static ArenaGame2 instance;

    @Override
    public void onEnable() {
        instance = this;

        GameController gamecontroller = new GameController(this);
        MonsterMenu monstermenu = gamecontroller.getMonstermenu();
        PlayerController playercontroller = gamecontroller.getPlayercontroller();
        ArenaCreator arenacreator = new ArenaCreator();
        Gui gui = gamecontroller.getGui();
        SpecialItems specialitems = gamecontroller.getSpecialitems();

        Bukkit.getLogger().warning("플러그인 활성화");
        Bukkit.getCommandMap().register("arenaGame", new Commands("arenaGame", gamecontroller.getRoundcontroller(),gamecontroller));
        Bukkit.getPluginManager().registerEvents(playercontroller, this);
        Bukkit.getPluginManager().registerEvents(monstermenu, this);
        Bukkit.getPluginManager().registerEvents(gui, this);
        Bukkit.getPluginManager().registerEvents(specialitems, this);
        Bukkit.getPluginManager().registerEvents(arenacreator, this);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
