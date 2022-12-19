package com.webcheckers.model;

import  java.util.*;

/**
 * The model for a space in the checker game board.
 * A space represents one square of the checker board.
 * @author Eric Chen
*/
public class Space {

    private int cellIdx;

    //If the space represents a dark square this is true
    //False if this space represents a light square
    private boolean darkSquare;
    private Piece piece;

    /**
     * Constructor for Space without a piece on it.
     * @param cellIdx the index of this space with a row
     * @param darkSquare whether or not the Space represents a dark square
     */
    public Space(int cellIdx, boolean darkSquare){
        this.cellIdx = cellIdx;
        this.darkSquare = darkSquare;
        this.piece = null;
    }

    /**
     * Constructor for Space with a piece on it.
     * @param cellIdx the index of this space with a row
     * @param darkSquare whether or not the Space represents a dark square
     * @param piece the piece that resides on this space
     */
    public Space(int cellIdx, boolean darkSquare, Piece piece){
        this.cellIdx = cellIdx;
        this.darkSquare = darkSquare;
        this.piece = piece;
    }

    /**
     * Gets the index of this space within the board.
     * @return an index number from zero to seven
     */
    public int getCellIdx(){
        return cellIdx;
    }

    /**
     * Method that determines whether a piece can be placed on this space
     * @return true if the space is a dark square and has no piece on it
     */
    public boolean isValid(){
        return darkSquare && (piece == null);
    }

    /**
     * Gets the piece that resides on this space if there is one
     * @return the piece or null if there is no piece
     */
    public Piece getPiece(){
        return piece;
    }

    /**
     * Sets the checker piece on the space.
     * This method is used assuming the space is valid for a piece.
     * @param piece the checker piece to be set on this space
     */
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    /**
     * Gets if the space is occupied by a piece
     * @return true or false if it is occupied or not
     */
    public boolean isOccupied(){
        return this.piece != null;
    }

    /**
     * Can a piece reside on this square?
     * @return true if legal (dark) square
     */
    public boolean isLegalSquare(){
        return this.darkSquare;
    }

    /**
     * Is this a dark square on the board?
     * @return true if this is a dark square
     */
    public boolean isDarkSquare() {
        return darkSquare;
    }
}
