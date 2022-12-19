package com.webcheckers.model.moves;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Match.ActiveColor;

import java.util.Objects;

public abstract class Move {
    private ActiveColor playerColor;
    private Position start;
    private Position end;

    public static final String ATTR_LAST_MOVE = "lastMove";

    /**
     * Defines a move using the starting and ending position of the piece
     * @param start the Position of the piece before the move
     * @param end the Position after the move
     */
    protected Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public abstract void apply(Board board);

    /**
     * Get the Position of the piece before the move
     * @return start Position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Get the Position of the piece after the move
     * @return end Position
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Get the Piece involved in this move
     * @return Piece being moved
     */
    public Piece getMovingPiece(Board board) {
        // TODO: law of demeter?
        return board.getSpaceAtPosition(this.start).getPiece();
    }

    public String toJson(){
        return "{\"start\":" + this.start.toJson() + ",\"end\":"+this.end.toJson()+"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(start, move.start) &&
                Objects.equals(end, move.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    public void setPlayerColor(ActiveColor color) {
        this.playerColor = color;
    }

    public ActiveColor getPlayerColor() {
        return playerColor;
    }
}
