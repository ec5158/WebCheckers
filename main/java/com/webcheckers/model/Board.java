package com.webcheckers.model;

import com.webcheckers.application.*;
import com.webcheckers.model.moves.Position;

import java.util.*;

/**
 * The model for the game board.
 *
*/
public class Board {
    //Board size constant (8 by 8 board)
    private static final int BOARD_SIZE = 8;

    private Player whitePlayer;
    private Player redPlayer;
    private Space[][] board;

    /**
     * The constructor for the game board that sets the players
     * and creates the board.
     * @param whitePlayer the player with the white pieces
     * @param redPlayer the player with the red pieces
     */
    public Board(Player whitePlayer, Player redPlayer){
        this.whitePlayer = whitePlayer;
        this.redPlayer = redPlayer;

        IntializeSpaces();
        PopulateBoard();
    }

    /**
     * This method initializes the spaces that represents squares on the board.
     */
    private void IntializeSpaces(){
        board = new Space[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++){
            for (int col = 0; col < BOARD_SIZE; col++){
                if (row % 2 == 0){ //for the rows that start on a light square
                    if (col % 2 == 0) { //for the spaces that represent a light square
                        board[row][col] = new Space(col, false);
                    }
                    else{ ////for the spaces that represent a dark square
                        board[row][col] = new Space(col, true);
                    }
                }
                else{ //for the rows that start on a dark square
                    if (col % 2 == 1) { //for the spaces that represent a light square
                        board[row][col] = new Space(col, false);
                    }
                    else{ ////for the spaces that represent a dark square
                        board[row][col] = new Space(col, true);
                    }
                }
            }
        }
    }

    /**
     * This method populates the board with the correct colored checker pieces.
     * This method is used assuming InitializeBoard was called before it.
     */
    private void PopulateBoard(){
        for (int row = 0; row < BOARD_SIZE; row++){
            for (int col = 0; col < BOARD_SIZE; col++){
                if (row < 3){ // the red player's side of the board with their pieces
                    if(board[row][col].isValid()){
                        board[row][col].setPiece(new Piece(Piece.color.RED, Piece.type.SINGLE));
                    }
                }
                else if(row > 4){ // the white player's side of the board with their pieces
                    if (board[row][col].isValid()){
                        board[row][col].setPiece(new Piece(Piece.color.WHITE, Piece.type.SINGLE));
                    }
                }
            }
        }
    }

    /**
     * This method gets the board
     * @return the board with all the spaces and checker pieces
     */
    public Space[][] getBoard() {
        return board;
    }

    /**
     * Return the Space[] (row) at the given index
     * @param index The index (position) of this row
     * @return Space[] representing row
     */
    public Space[] getRow(int index){
        // Throw exception if this row doesn't exist
        if (index < 0 || index > 7){
            throw new IllegalArgumentException("Index must be between 0 and 7");
        }
        return board[index];
    }

    /**
     * Get the row at the given index, with spaces in reverse order
     * @param index The index (position) of this row
     * @return Space[] representing reversed row
     */
    public Space[] getRowReversed(int index) {
        Space[] row = getRow(index);
        Space[] reverseRow = new Space[BOARD_SIZE];
        int j = BOARD_SIZE;

        // Iterate thru row backwards, placing Spaces in new array
        for (int i = 0; i < BOARD_SIZE; i++) {
            j--;
            reverseRow[i] = row[j];
        }
        return reverseRow;
    }

    /**
     * Gets the white player
     * @return the white player
     */
    public Player getWhitePlayer(){
        return whitePlayer;
    }

    /**
     * Gets the red player
     * @return the red player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Return the Space representation of this position
     * @param pos the position on the board
     * @return corresponding Space object
     */
    public Space getSpaceAtPosition(Position pos){
        return this.board[pos.getRow()][pos.getCell()];
    }

}
