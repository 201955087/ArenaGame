package me.air_bottle.arenagame2.round;

import me.air_bottle.arenagame2.ArenaGame2;
import me.air_bottle.arenagame2.arena.ArenaCreator;
import me.air_bottle.arenagame2.monster.MonsterMenu;
import me.air_bottle.arenagame2.player.PlayerData;
import me.air_bottle.arenagame2.record.RecordRound;
import me.air_bottle.arenagame2.reward.RewardController;
import me.air_bottle.arenagame2.reward.RewardGUI.Gui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoundController implements RoundMenu{
    private MonsterMenu monstermenu;
    private final ArenaCreator arenacreator;
    private final RewardController rewardcontroller;
    private final Gui gui;
    private static int ROUND = 1;
    public boolean inRound = false;
    private String currentDifficulty = "normal";
    private final Map<Player, RecordRound> playerRecords = new HashMap<>();

    public RoundController(ArenaCreator arenacreator, RewardController rewardcontroller, Gui gui) {
        this.arenacreator = arenacreator;
        this.rewardcontroller = rewardcontroller;
        this.gui = gui;
    }

    public void setMonsterMenu(MonsterMenu monstermenu) {
        this.monstermenu = monstermenu;
    }
    public RecordRound getPlayerRecord(Player player) {
        return playerRecords.computeIfAbsent(player, RecordRound::new);
    }
    @Override
    public void startGame(Player player) {
        arenacreator.createArena(player);
        playerRecords.putIfAbsent(player, new RecordRound(player));
        rewardcontroller.startingItems(player);
    }

    @Override
    public void startRound(Player player, String difficulty) {
        this.currentDifficulty = difficulty;
        if(!inRound) {
            inRound = true;
            player.sendMessage("10초 후에 몬스터가 스폰됩니다");
            rewardcontroller.defaultItems(player);
            roundCountdown(player, 5);
            Bukkit.getScheduler().scheduleSyncDelayedTask(ArenaGame2.instance, () -> {
                switch(difficulty) {
                    case "easy":
                        monstermenu.createMonsters(monstermenu.spade, arenacreator.cornerLocation, ROUND);
                        break;
                    case "normal":
                        monstermenu.createMonsters(monstermenu.heart, arenacreator.halfLocation, ROUND);
                        break;
                    case "hard":
                        monstermenu.createMonsters(monstermenu.spade, arenacreator.centerLocation, ROUND);
                        monstermenu.createMonsters(monstermenu.heart, arenacreator.centerLocation, ROUND);
                        break;
                }
            }, 200L);
        }
    }
    @Override
    public void clearRound(Player player) {
        player.sendMessage(ROUND + "라운드 클리어");
        getPlayerRecord(player).recordEndGame(ROUND);
        ROUND++;
        inRound = false;
        gui.openRewardGUI(player);
        startRound(player, currentDifficulty);
    }
    @Override
    public void endGame(Player player) {
        // 종료 전 최종 라운드 기록
        RecordRound recordRound = getPlayerRecord(player);
        PlayerData playerdata = recordRound.getPlayerData();
        if(playerdata.clearedRounds == null) {
            playerdata.clearedRounds = new ArrayList<>();
        }
        playerdata.updateRecord();
        recordRound.savePlayerData();

        // 게임 상태 초기화;
        arenacreator.removeArena(player);
        arenacreator.removeBorder(player.getLocation());
        monstermenu.removeMonsters();
        rewardcontroller.resetReward(player);
        ROUND = 1;
        inRound = false;
    }
    @Override
    public void roundCountdown(Player player, int delay) {
            for (int i = 0; i < 5; i++) {
                final int countdown = 5 - i;
                Bukkit.getScheduler().scheduleSyncDelayedTask(ArenaGame2.instance, () -> {
                    if(inRound) {
                        player.sendTitle(String.valueOf(countdown), ROUND + "라운드 시작까지", 10, 20, 10);
                    }
                }, (delay + i) * 20L);
            }
    }
    @Override
    public int getRound() {
        return ROUND;
    }
}
