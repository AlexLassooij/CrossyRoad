package model;


import org.json.JSONObject;

public class PlayerProfile {
    private String playerName;
    private int pointsAchieved;
    private int levelAchieved;

    public PlayerProfile(String playerName) {
        this.playerName = playerName;
        this.pointsAchieved = 0;
        this.levelAchieved = 1;
    }

    public PlayerProfile(String playerName, int pointsAchieved, int levelAchieved) {
        this.playerName = playerName;
        this.pointsAchieved = pointsAchieved;
        this.levelAchieved = levelAchieved;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void changePlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void addPoints(int points) {
        this.pointsAchieved += points;
    }

    public int getPoints() {
        return this.pointsAchieved;
    }

    public int getLevelAchieved() {
        return this.levelAchieved;
    }

    public void increaseLevelAchieved() {
        this.levelAchieved += 1;
    }

    public JSONObject profileToJsonObject() {
        JSONObject json = new JSONObject();
        json.put("playerName", this.playerName);
        json.put("pointsAchieved", this.pointsAchieved);
        json.put("levelAchieved", this.levelAchieved);
        return json;
    }
}


