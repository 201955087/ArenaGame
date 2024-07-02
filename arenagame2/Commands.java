package me.air_bottle.arenagame2;

import me.air_bottle.arenagame2.arena.ArenaCreator;
import me.air_bottle.arenagame2.game.GameController;
import me.air_bottle.arenagame2.reward.items.SpecialItems;
import me.air_bottle.arenagame2.round.RoundController;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Commands extends BukkitCommand {
    private final RoundController roundcontroller;
    private final GameController gameController;

    protected Commands(@NotNull String name, RoundController roundcontroller, GameController gameController) {
        super(name);
        this.roundcontroller = roundcontroller;
        this.gameController = gameController;
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player player)) {
            commandSender.sendMessage("플레이어만 명령어 사용 가능");
            return true;
        }
        if(strings.length == 0) {
            return false;
        }

        if (strings[0].equals("start")) {
            if (strings.length > 1) {
                switch (strings[1]) {
                    case "easy":
                        roundcontroller.startGame(player);
                        roundcontroller.startRound(player, "easy");
                        break;
                    case "normal":
                        roundcontroller.startGame(player);
                        roundcontroller.startRound(player, "normal");
                        break;
                    case "hard":
                        roundcontroller.startGame(player);
                        roundcontroller.startRound(player, "hard");
                        break;
                    default:
                        player.sendMessage("올바른 난이도를 입력하세요: easy, normal, hard");
                        break;
                }
            } else {
                roundcontroller.startGame(player);
                roundcontroller.startRound(player, "normal");
            }
            return true;
        } else if(strings[0].equals("stop")) {
            roundcontroller.endGame(player);
            return true;
        } else if(strings[0].equals("items")) {
            player.getInventory().addItem(gameController.getSpecialitems().armorSpecial());
            player.getInventory().addItem(gameController.getSpecialitems().weaponSpecial());
            player.getInventory().addItem(gameController.getSpecialitems().buffSpecial());
            player.sendMessage("특수 아이템 지급");
            return true;
        }
        return false;
    }
}
