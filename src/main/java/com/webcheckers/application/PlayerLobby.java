package com.webcheckers.application;

import com.webcheckers.model.Player;
import com.webcheckers.util.UsernameValidator;
import com.webcheckers.util.UsernameValidator.UsernameValidity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tracks users currently logged in.
 */
public class PlayerLobby {
    // Map of usernames to Player objects
    private Set<Player> playerSet;

    /**
     * Constructor
     */
    public PlayerLobby(){
        this.playerSet = new HashSet<Player>();
    }

    /**
     * Register a player in the lobby, returning a UsernameValidity of their username
     * @param player Player to sign-in
     * @return {@link UsernameValidator}  {@code HELLO} indicating validity of username
     */
    public synchronized UsernameValidity signin(Player player) {
        String username = player.getName();

        // Check that username is valid (not taken, right format)
        UsernameValidity usernameValidity = UsernameValidator.isValidUsername(playerSet, player);

        // If the username is valid, register a player in the player map
        if(usernameValidity == UsernameValidity.VALID) {
            System.out.println("Logged in " + username);
            playerSet.add(player);
        }

        return usernameValidity;
    }

    /**
     * Return a message indicating the number of players, to be shown in
     * the home view when not signed in.
     * @return message to be shown
     */
    public String getNumPlayersMessage() {
        String message;
        int numPlayers = getNumPlayers(); // Get number of players

        // Create unique message based on number of players
        if (numPlayers == 0) {
            message = "<i>There are no other players available to play at this time.</i>";
        } else if (numPlayers == 1) {
            message = "There is 1 player online.";
        } else {
            message = "There are " + numPlayers + " players online.";
        }

        return message;
    }

    /**
     * Get an array of the Players signed in, excluding the current user
     * @param Player Player to exclude
     * @return Player[] of usernames
     */
    public Player[] getOtherPlayers(Player player) {
        List<Player> otherPlayersList = new ArrayList<Player>(getPlayerSet());
        otherPlayersList.remove(player); // Remove this user's username

        // Return usernames as String[]
        return otherPlayersList.toArray(new Player[otherPlayersList.size()]);
    }

    /**
     * Remove a player from the player map, when they sign-out
     * @param player Player to be signed out
     */
    public synchronized void removePlayer(Player player){
        // Remove username and associated Player from map
        playerSet.remove(player);
    }

    /**
     * Returns true if the player with this username is currently in a game
     * @param username The player's username
     * @return true if player is in a game, otherwise false.
     */
    public boolean getPlayerInGame(String username) {
        return getPlayer(username).isInGame();
    }

    /**
     * Get the Player object associated with a user's username
     * @param username The username of the user
     * @return The Player object for this user
     */
    public Player getPlayer(String username) {
        for (Player player : getPlayerSet()) {
            if (player.getName().equals(username)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Get the number of players signed in.
     * @return The number of players
     */
    protected int getNumPlayers() {
        return getPlayerSet().size();
    }

    protected Set<Player> getPlayerSet() {
        return playerSet;
    }
}
