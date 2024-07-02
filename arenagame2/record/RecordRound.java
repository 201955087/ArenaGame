package me.air_bottle.arenagame2.record;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.air_bottle.arenagame2.player.PlayerData;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecordRound {
    // @Expose 어노테이션이 없는 필드는 json파일에 넘기지 않게 생성
    private static final Gson gson = new GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation().create();
    private final Path path;
    private PlayerData playerData;

    public RecordRound(Player player) {
        this.path = Path.of("./data/ArenaGame/" + player.getName() + ".json");
        this.playerData = new PlayerData(player.getName());
        loadPlayerData();
    }

    public void savePlayerData() {
        if(Files.notExists(path)) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()))) {
            gson.toJson(this.playerData, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadPlayerData() {
        if(Files.notExists(path)) {
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (BufferedReader read = new BufferedReader((new FileReader(path.toFile())))) {
            this.playerData = gson.fromJson(read, PlayerData.class);
            if(this.playerData == null) {
                this.playerData = new PlayerData(path.getFileName().toString());
            }
            if(this.playerData.clearedRounds == null) {
                this.playerData.clearedRounds = new ArrayList<>();
            }
            playerData.updateRecord();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void recordEndGame(int round) {
        playerData.clearedRounds.add(round);
        playerData.updateRecord();
        savePlayerData();
    }
    public PlayerData getPlayerData() {
        return playerData;
    }
}
