package com.webcheckers.model;

import java.util.HashMap;
import java.util.Map;

import com.webcheckers.model.moves.Move;
import com.webcheckers.ui.GetGameRoute;

/** 
 * Represents an ongoing match between two players
 * @author Shane Burke
 */
public class Match {
    private Map<String, Object> vm; // View-model map for the game view
    private Board board; // Board model
    private ActiveColor activeColor = ActiveColor.RED;

    public enum ActiveColor {RED, WHITE} // Color of the user in question
    public static final String ATTR_ACTIVE_COLOR = "activeColor";

    private Map<ActiveColor, Player> playerColors;
    private Move currentMove;

    /**
     * Constructor
     * @param p1 Challenging user
     * @param p2 Challenged user
     * @param vm View-model map for the game view for this match
     */
    public Match(Player p1, Player p2, Map<String, Object> vm) {
        playerColors = new HashMap<ActiveColor, Player>();
        playerColors.put(ActiveColor.RED, p1);
        playerColors.put(ActiveColor.WHITE, p2);
        this.vm = vm;
        this.board = new Board(p2, p1);

        initViewModel();
    }

    /**
     * Creates the appropriate view-model for this match
     */
    private void initViewModel() {
        // Read player goes first
        vm.put(ATTR_ACTIVE_COLOR, ActiveColor.RED);
        vm.put("currentUser", getPlayerFromColor(ActiveColor.RED));
        vm.put("redPlayer", getPlayerFromColor(ActiveColor.RED)); // Challenging user is red
        vm.put("whitePlayer", getPlayerFromColor(ActiveColor.WHITE)); // Challenged user is white
        vm.put("viewMode", GetGameRoute.viewMode.PLAY);
    }

    /**
     * Gets the view-model map for this match's game view
     * @return view-model map for this match's game view
     */
    public Map<String, Object> getViewModel() {
        return vm;
    }

    /**
     * Get the challenged Player
     * @return Player object
     */
    public Player getOpponent() {
        return getPlayerFromColor(ActiveColor.WHITE);
    }

    protected Player getPlayerFromColor(ActiveColor color) {
        return playerColors.get(color);
    }

    /**
     * Get the board associated with this match
     * @return Board object
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the player whose turn it is
     * @return the active Player
     */
    public Player getActivePlayer() {
        return getPlayerFromColor(activeColor);
    }

    /**
     * Changes the active color to initiate a new turn
     */
    public void newTurn() {
        setCurrentMove(null);
        flipActiveColor();
        vm.put(ATTR_ACTIVE_COLOR, activeColor);
    }

    /**
     * Changes the active color to the specified value
     * @param color An ActiveColor (RED or WHITE)
     */
    protected void setActiveColor(ActiveColor color) {
        activeColor = color;
    }

    /**
     * Flips the active color (e.g. RED becomes WHITE)
     */
    private void flipActiveColor() {
        if (activeColor == ActiveColor.RED) {
            setActiveColor(ActiveColor.WHITE);
        } else {
            setActiveColor(ActiveColor.RED);
        }
    }

    /**
     * Records a pending move for this turn.
     * @param move The pending Move
     */
    public void setCurrentMove(Move move) {
        this.currentMove = move;
    }

    /**
     * Returns the pending move for this turn.
     * @return The Move that has not been submitted
     */
    public Move getCurrentMove() {
        return currentMove;
    }
}
