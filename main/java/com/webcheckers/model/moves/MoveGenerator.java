package com.webcheckers.model.moves;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;
import com.webcheckers.ui.PostSigninRoute;

import freemarker.log.Logger;

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {
    // TODO: Implement to generate moves for each space
    // public List<Move> generateMoves(){
    //     return null;
    // }

    /**
     * Checks if a move is reasonable (not rule breaking)
     * @param start the start Position of the piece
     * @param end the end Position of the piece
     * @param board the Board model
     * @return ValidationStatus of this move
     */
    public MoveValidator.ValidationStatus isMoveReasonable(Position start, Position end, Board board){
        Space startSpace = board.getSpaceAtPosition(start);
        if(!startSpace.isLegalSquare() || !startSpace.isOccupied()){
            return MoveValidator.ValidationStatus.INVALID_START;
        }
        Space endSpace = board.getSpaceAtPosition(end);
        if(!endSpace.isLegalSquare()){
            return MoveValidator.ValidationStatus.INVALID_END;
        }
        if(endSpace.isOccupied()) {
            return MoveValidator.ValidationStatus.END_ALREADY_OCCUPIED;
        }
        return MoveValidator.ValidationStatus.VALID;
    }

    /**
     * Generates the Move corresponding to the start and end positions of the piece
     * @param start The start position
     * @param end the end position
     * @param board The Board model
     * @return the appropriate Move object
     */
    public Move generateMove(Position start, Position end, Board board){
        Space startSpace = board.getSpaceAtPosition(start);
        //Check if reasonable. If not, bail early
        if(isMoveReasonable(start, end, board) != MoveValidator.ValidationStatus.VALID){
            return null;
        }
        //Check all SimpleMoves
        List<Move> moves = generateSimpleMoves(start, board);
        moves.addAll(generateJumpMoves(start, board));

        for(Move move : moves){
            if(move.getEnd().equals(end)){
                return move;
            }
        }

        return null;
    }

    /**
     * Generates all possible simple moves from the given position
     * @param startPos the start position of the piece
     * @param board the board model
     * @return a list of possible simple moves
     */
    protected List<Move> generateSimpleMoves(Position startPos, Board board){
        Piece movingPiece = board.getSpaceAtPosition(startPos).getPiece();

        List<Move> moves = new ArrayList<Move>();
        Position upRight = new Position(startPos.getRow() + 1, startPos.getCell() + 1);
        Position upLeft = new Position(startPos.getRow() + 1, startPos.getCell() - 1);
        Position downRight = new Position(startPos.getRow() - 1, startPos.getCell() + 1);
        Position downLeft = new Position(startPos.getRow() - 1, startPos.getCell() - 1);

        boolean up = movingPiece.getColor() == Piece.color.RED || movingPiece.getType() == Piece.type.KING;
        boolean down = movingPiece.getColor() == Piece.color.WHITE || movingPiece.getType() == Piece.type.KING;

        if(up){
            if(upRight.isValid()){
                    moves.add(new SimpleMove(startPos, upRight));
            }
            if(upLeft.isValid()){
                moves.add(new SimpleMove(startPos, upLeft));
            }
        }
        if(down){
            if(downRight.isValid()){
                moves.add(new SimpleMove(startPos, downRight));
            }
            if(downLeft.isValid()){
                moves.add(new SimpleMove(startPos, downLeft));
            }
        }
        return moves;
    }



    protected List<Move> generateJumpMoves(Position startPos, Board board){
        List<Move> moves = new ArrayList<Move>();
        if(isValidJumpMove(startPos, board, 1, 1)){
            moves.add(new JumpMove(startPos, new Position(startPos.getRow() + 2, startPos.getCell() + 2)));
        }
        if(isValidJumpMove(startPos, board, 1, -1)){
            moves.add(new JumpMove(startPos, new Position(startPos.getRow() + 2, startPos.getCell() - 2)));
        }
        if(isValidJumpMove(startPos, board, -1, 1)){
            moves.add(new JumpMove(startPos, new Position(startPos.getRow() - 2, startPos.getCell() + 2)));
        }
        if(isValidJumpMove(startPos, board, -1, -1)){
            moves.add(new JumpMove(startPos, new Position(startPos.getRow() - 2, startPos.getCell() - 2)));
        }
        return moves;
    }

    protected boolean isValidJumpMove(Position startPos, Board board, int rowDir, int colDir){
        Piece movingPiece = board.getSpaceAtPosition(startPos).getPiece();
        Piece.color jumpedColor = movingPiece.getColor() == Piece.color.RED ? Piece.color.WHITE : Piece.color.RED;

        Position jumpedSpace = new Position(startPos.getRow() + rowDir, startPos.getCell() + colDir);
        Position landSpace = new Position(startPos.getRow() + rowDir*2, startPos.getCell() + colDir*2);
        if(!landSpace.isValid() || !jumpedSpace.isValid()) {
            return false;
        }

        if(!board.getSpaceAtPosition(jumpedSpace).isOccupied()){
            return false;
        }

        if(board.getSpaceAtPosition(jumpedSpace).getPiece().getColor() != jumpedColor){
            return false;
        }
        boolean canMoveUp = movingPiece.getColor() == Piece.color.RED || movingPiece.getType() == Piece.type.KING;
        boolean canMoveDown = movingPiece.getColor() == Piece.color.WHITE || movingPiece.getType() == Piece.type.KING;

        if(rowDir == -1 && !canMoveDown){
            return false;
        }
        if(rowDir == 1 && !canMoveUp){
            return false;
        }
        if(board.getSpaceAtPosition(landSpace).isOccupied()){
            return false;
        }
        return true;
    }
}
