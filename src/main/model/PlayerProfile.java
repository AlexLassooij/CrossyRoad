package model;


public class PlayerProfile {
    private String playerName;
    private int pointsAchieved;
    private int levelAchieved;

    public PlayerProfile(String playerName) {
        this.playerName = playerName;
        this.pointsAchieved = 0;
        this.levelAchieved = 1;
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

    public int getLevelAchieved() {
        return this.levelAchieved;
    }

    public void increaseLevelAchieved() {
        this.levelAchieved += 1;
    }
}


