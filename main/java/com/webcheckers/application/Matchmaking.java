package com.webcheckers.application;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.model.Board;
import com.webcheckers.model.Match;
import com.webcheckers.model.Player;
import com.webcheckers.model.moves.Move;

/**
 * Handles matchmaking functionality between players.
 * This includes registering matches and storing 
 * which players are in which matches.
 * 
 * @author Shane Burke
 */

public class Matchmaking {
    /**
     * Maps players to the match they are in 
     */
    private Map<Player, Match> matchMap;

    /**
     * Constructor
     */
    public Matchmaking() {
        this.matchMap = new HashMap<Player, Match>();
    }

    /**
     * Registers a match, setting both players in-game and adding it to the match map
     * @param p1 Challenging user
     * @param p2 Challenged user
     * @param vm view-model map for this match
     */
    public void registerMatch(Player p1, Player p2, Map<String, Object> vm) {
        Match m = new Match(p1, p2, vm);
        
        // Associate both players with this match
        matchMap.put(p1, m);
        matchMap.put(p2, m);

        // Mark both players in-game, so matches cannot be started with them.
        p2.setInGame(true);
        p1.setInGame(true);
    }

    /**
     * Ends a match, freeing both players.
     * @param p1 Challenging user
     * @param p2 Challenged user
     */
    public void closeMatch(Player p1, Player p2) {
        // Disassociate players with match
        matchMap.remove(p1);
        matchMap.remove(p2);

        p1.setInGame(false);
        p2.setInGame(false);
    }

    /**
     * Returns the Match a Player is currently in
     * @param p Player to get match for
     * @return Match this player is in or null if no match
     */
    public Match getMatch(Player p) {
        Match m = null;
        Map<Player, Match> matchMap = getMatchMap();
        // If Player is associated with a Match
        if (matchMap.containsKey(p)) {
            m = matchMap.get(p);
        }
        return m;
    }

    /**
     * Returns the Board of the game a player is in
     * @param p Player object
     * @return Board object
     */
    public Board getBoard(Player p) {
        return getMatch(p).getBoard();
    }

    /**
     * Returns the view-model map for a Player's game view
     * @param p Player object
     * @return Map<String, Object> view-model
     */
    public Map<String, Object> getViewModel(Player p) {
        return getMatch(p).getViewModel();
    }

    protected Map<Player, Match> getMatchMap(){
        return this.matchMap;
    }

    public Player getMatchActivePlayer(Player player) {
        return getMatch(player).getActivePlayer();
    }
}
