package me.air_bottle.arenagame2.player;

import com.google.gson.annotations.Expose;
import io.lumine.mythic.bukkit.utils.lib.jooq.Stringly;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    @Expose
    public String playerName;
    public List<Integer> clearedRounds;
    @Expose
    public int highestRound;
    @Expose
    public double averageRounds;

    public PlayerData(String playerName) {
        this.playerName = playerName;
        this.clearedRounds = new ArrayList<>();
        this.highestRound = 0;
        this.averageRounds = 0.0;
    }

    public void updateRecord() {
        if(clearedRounds == null) {
            this.clearedRounds = new ArrayList<>();
        }
        if(!clearedRounds.isEmpty()) {
            this.highestRound = clearedRounds.stream().mapToInt(Integer::intValue).max().orElse(0);
            this.averageRounds = clearedRounds.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        }
    }
}
