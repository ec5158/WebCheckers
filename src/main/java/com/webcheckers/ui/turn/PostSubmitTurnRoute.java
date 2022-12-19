package com.webcheckers.ui.turn;

import com.google.gson.Gson;
import com.webcheckers.application.Matchmaking;
import com.webcheckers.model.Board;
import com.webcheckers.model.Match;
import com.webcheckers.model.Player;
import com.webcheckers.model.moves.Move;
import com.webcheckers.ui.NavbarUtils;
import com.webcheckers.util.Message;

import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Submits a turn, applying it to the model
 */
public class PostSubmitTurnRoute implements Route {

    private Gson gson;
    private Matchmaking matchmaking;

    public static final String ERR_NO_MOVE = "No move to submit.";
    public static final String INFO_TURN_COMPLETE = "Your turn has been completed.";

    public PostSubmitTurnRoute(Matchmaking matchmaking) {
        this.gson = new Gson();
        this.matchmaking = matchmaking;
    }
    
    @Override
    public Object handle(Request request, Response response) {
        Message message;
        Player player = getPlayer(request);

        Match match = matchmaking.getMatch(player);
        Board board = match.getBoard();
        Move currentMove = match.getCurrentMove();

        // No pending move? => give error
        if (currentMove == null) {
            message = Message.error(ERR_NO_MOVE);
        } else {
            // Apply pending move to the board and flip turn
            currentMove.apply(board);
            match.newTurn();
            message = Message.info(INFO_TURN_COMPLETE);
        }

        return gson.toJson(message);
    }

    /**
     * Gets the player using the handle request
     * @param request Spark Request
     * @return The user's Player object
     */
    protected Player getPlayer(Request request) {
        return NavbarUtils.getPlayer(request);
    }

}