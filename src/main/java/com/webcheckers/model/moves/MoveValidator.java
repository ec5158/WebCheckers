package com.webcheckers.model.moves;

import com.webcheckers.model.Board;
import com.webcheckers.model.Space;

/**
 * Validate a move using the start and end position
 */
public class MoveValidator {
    public enum ValidationStatus {VALID, INVALID_START, INVALID_END, END_ALREADY_OCCUPIED};
    public MoveGenerator generator;

    public MoveValidator(){
        this.generator = new MoveGenerator();
    }

    public MoveValidator(MoveGenerator generator){
        this.generator = generator;
    }

    /**
     * Validates a move using the start and end positions
     * @param start start position
     * @param end end position
     * @param board board model
     * @return ValidationStatus of move
     */
    public ValidationStatus validateMove(Position start, Position end, Board board) {
        //Test for simple validation
        ValidationStatus status = generator.isMoveReasonable(start, end, board);
        if(status != ValidationStatus.VALID){
            return status;
        }

        //Make sure move is legal
        if(this.generator.generateMove(start, end, board) == null){
            return ValidationStatus.INVALID_END;
        }

        return ValidationStatus.VALID;
    }
}