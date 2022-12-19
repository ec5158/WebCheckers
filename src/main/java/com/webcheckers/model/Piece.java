package com.webcheckers.model;

import com.webcheckers.model.moves.Move;
import com.webcheckers.model.moves.SimpleMove;

import java.util.*;

/**
 * The model for a checker piece.
 * Contains the type (single or king) and color (red or white) of the piece.
 * @author Eric Chen
*/
public class Piece {
    //Board size constant (8 by 8 board)
    public enum type{
        SINGLE,
        KING
    }

    public enum color{
        RED,
        WHITE
    }

    private color pieceColor;
    private type pieceType;

    /**
     * Constructor for a new checker piece based on color and type.
     * @param pieceColor the color of the piece to be created (RED or WHITE)
     * @param pieceType the type of the piece to be created (SINGLE or KING)
     */
    public Piece(color pieceColor, type pieceType){
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
    }

    /**
     * Gets the type of the checker piece
     * @return the type of the checker piece (SINGLE or KING)
     */
    public type getType(){
        return pieceType;
    }

    /**
     * Gets the color of the checker piece
     * @return the color of the checker piece (RED or WHITE)
     */
    public color getColor(){
        return pieceColor;
    }

    /**
     * Sets the type of this piece to king. It is assumed
     * that the piece has met the conditions to become
     * a king.
     */
    public void setKing(){
        this.pieceType = type.KING;
    }


    /**
     * Checks if a piece has reached the end row needed
     * to become a king piece. If it matches the right
     * conditions then the piece becomes a king piece.
     * @param piece the piece to be checked
     * @param endMove the end move of the current turn and the position the piece moved to
     * @return true if the piece becomes a king piece, false if it doesn't become one
     */
    public boolean checkKingStatus(Piece piece, SimpleMove endMove){
        if(piece.getColor().equals(color.WHITE)){
            if(endMove.getEnd().getRow() == 0){
                piece.setKing();
                return true;
            }
        }
        else if(piece.getColor().equals(color.RED)){
            if(endMove.getEnd().getRow() == 7){
                piece.setKing();
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Piece){
            return (this.pieceColor == ((Piece) obj).getColor()) && (this.pieceType == ((Piece) obj).getType());
        }
        return false;
    }
}
