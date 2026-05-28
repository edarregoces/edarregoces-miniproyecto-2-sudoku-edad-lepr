package com.example.miniproyecto2sudokuedadlepr.model.user;

/**
 * Represents a user of the Sudoku application.
 * <p>
 * This class stores basic information about the player,
 * such as their playerName, and provides getter and setter
 * methods for accessing and modifying it.
 */
public class PlayerData {

    /** The user's chosen playerName. */
    private String playerName;

    /**
     * Constructs a new {@code PlayerData} with the specified playerName.
     *
     * @param playerName the playerName of the user.
     */
    public PlayerData(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Returns the user's playerName.
     *
     * @return the playerName of the user.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Updates the user's playerName.
     *
     * @param playerName the new playerName to set.
     */
    public void updatePlayerName(String playerName) {
        this.playerName = playerName;
    }
}
