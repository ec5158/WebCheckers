package com.webcheckers.model;

/**
 * Represents a signed-in player
 */
public class Player {
    private String name; // Username
    private boolean inGame = false; // Currently playing a game?

    /**
     * Constructor
     * @param name Username
     */
    public Player(String name){
        this.name = name;
    }

    /**
     * Returns player's username
     * @return username
     */
    public String getName(){
        return name;
    }

    /**
     * Returns true if user is currently in a game
     * @return true if user is in a game, otherwise false (waiting for game)
     */
    public boolean isInGame() {
        return inGame;
    }

    /**
     * Marks the player as in/out of game
     * @param inGame true if user is now in a game, false if no longer in a game
     * @return true if state changed, false if no change was made
     */
    public boolean setInGame(boolean inGame) {
        boolean stateChanged = false;

        // Is the new state different from the current state?
        if (this.inGame == !inGame) {
            this.inGame = inGame;
            stateChanged = true;
        }
        return stateChanged;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;

        if ((obj == null) || !(obj instanceof Player)) {
            return false;
        }

        Player other = (Player)obj;
        return this.getName().equals(other.getName());
    }

}
