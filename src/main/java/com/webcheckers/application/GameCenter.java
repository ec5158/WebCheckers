package com.webcheckers.application;

import java.util.Map;

import com.webcheckers.model.Match;
import com.webcheckers.model.Player;

/**
 * The GameCenter is a common interface for
 * functionality that is required throughout
 * the application.
 */
public class GameCenter {
    private PlayerLobby playerLobby;
    private Matchmaking matchmaking;

    /**
     * Constructor
     */
    public GameCenter() {
        this.playerLobby = new PlayerLobby();
        this.matchmaking = new Matchmaking();
    }

    /**
     * Returns the PlayerLobby object that handles player-related functionality.
     * @return The server's PlayerLobby object
     */
    public PlayerLobby getPlayerLobby(){
        return playerLobby;
    }

    /**
     * Returns the Matchmaking object that handles matchmaking functionality.
     * @return The server's Matchmaking object
     */
    public Matchmaking getMatchmaking() {
        return this.matchmaking;
    }
}
