package com.webcheckers.ui;

import com.webcheckers.application.GameCenter;
import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * UI-tier entity to represent board view
 *
 */
public class BoardView {
    private ArrayList<Row> rows;
    private Player redPlayer;
    private Player whitePlayer;

    /**
     * Constructor
     * @param player current user
     * @param model Board model this view represents
     */
    public BoardView(Player player, Board model){
        this.rows = new ArrayList<>();
        this.redPlayer = model.getRedPlayer();
        this.whitePlayer = model.getWhitePlayer();

        Piece.color playerColor = getPlayerColor(player, model);

        // Add white side rows
        if (playerColor == Piece.color.WHITE){
            for (int r = 0; r < 8; r++){
                rows.add(new Row(r, model.getRow(r)));
            }
        }
        // Create red side rows
        if (playerColor == Piece.color.RED){
            for (int r = 7; r >= 0; r--){
                rows.add(new Row(r, model.getRowReversed(r)));
            }
        }
    }

    /**
     * Creates an Iterator the rows in the checkers board
     * @return the iterable list of rows
     */
    public synchronized Iterator<Row> iterator(){
        return rows.iterator();
    }

    /**
     * Get the color of a player
     * @param player Player to get color for
     * @param model Board model
     * @return Piece color (Piece.color)
     */
    private Piece.color getPlayerColor(Player player, Board model){
        if (model.getRedPlayer() == player){
            return Piece.color.RED;
        }
        else if (model.getWhitePlayer() == player){
            return  Piece.color.WHITE;
        }
        return null;
    }
}
