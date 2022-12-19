package com.webcheckers.ui.turn;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.webcheckers.model.Board;
import com.webcheckers.model.moves.Move;
import com.webcheckers.model.moves.MoveGenerator;
import com.webcheckers.model.moves.MoveValidator;
import com.webcheckers.model.moves.Position;

/**
 * Parses a move from JSON request data
 */
public class MoveParser {
    MoveGenerator moveGenerator;
    MoveValidator moveValidator;
    Gson gson;
    public MoveParser(){
        this.moveGenerator = new MoveGenerator();
        this.moveValidator = new MoveValidator(moveGenerator);

        this.gson = new Gson();
    }
    public MoveParser(MoveGenerator generator, MoveValidator validator){
        this.moveGenerator = generator;
        this.moveValidator = validator;

        this.gson = new Gson();
    }

    /**
     * Parses a move from JSON.
     * @param json the json string of this move
     * @param board the board model
     * @return parsed Move
     */
    public Move parseMove(String json, Board board){
        JsonParser parser = new JsonParser();
        JsonObject jobj = parser.parse(json).getAsJsonObject();
        Position start = gson.fromJson(jobj.getAsJsonObject("start"), Position.class);
        Position end = gson.fromJson(jobj.getAsJsonObject("end"), Position.class);
        return moveGenerator.generateMove(start, end, board);
    }

    /**
     * Validates a move from a JSON string
     * @param json JSON string representing move
     * @param board board model
     * @return ValidationStatus of moves
     */
    public MoveValidator.ValidationStatus validateMove(String json, Board board){
        JsonParser parser = new JsonParser();
        JsonObject jobj = parser.parse(json).getAsJsonObject();
        Position start = gson.fromJson(jobj.getAsJsonObject("start"), Position.class);
        Position end = gson.fromJson(jobj.getAsJsonObject("end"), Position.class);

        return moveValidator.validateMove(start, end, board);
    }

}
