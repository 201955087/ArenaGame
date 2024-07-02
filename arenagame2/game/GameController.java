package me.air_bottle.arenagame2.game;

import me.air_bottle.arenagame2.arena.ArenaCreator;
import me.air_bottle.arenagame2.monster.MonsterMenu;
import me.air_bottle.arenagame2.player.PlayerController;
import me.air_bottle.arenagame2.record.RecordRound;
import me.air_bottle.arenagame2.reward.RewardController;
import me.air_bottle.arenagame2.reward.RewardGUI.Gui;
import me.air_bottle.arenagame2.reward.items.SpecialItems;
import me.air_bottle.arenagame2.round.RoundController;
import org.bukkit.plugin.java.JavaPlugin;

public class GameController {
    private final MonsterMenu monstermenu;
    private JavaPlugin plugin;
    private final PlayerController playercontroller;
    private final RoundController roundcontroller;
    private final RewardController rewardcontroller;
    private final Gui gui;
    private final SpecialItems specialitems;

    public GameController(JavaPlugin plugin) {
        this.plugin = plugin;
        ArenaCreator arenacreator = new ArenaCreator();
        this.specialitems = new SpecialItems(plugin);
        this.rewardcontroller = new RewardController(specialitems);
        this.gui = new Gui(rewardcontroller);
        this.roundcontroller = new RoundController(arenacreator, rewardcontroller, gui);
        this.monstermenu = new MonsterMenu(roundcontroller, plugin);
        this.playercontroller = new PlayerController(roundcontroller);
        this.roundcontroller.setMonsterMenu(monstermenu);
    }

    public MonsterMenu getMonstermenu() {
        return monstermenu;
    }

    public PlayerController getPlayercontroller() {
        return playercontroller;
    }

    public RoundController getRoundcontroller() {
        return roundcontroller;
    }
    public Gui getGui() {
        return gui;
    }
    public SpecialItems getSpecialitems() {
        return specialitems;
    }
    public JavaPlugin getPlugin() {
        return plugin;
    }
}
