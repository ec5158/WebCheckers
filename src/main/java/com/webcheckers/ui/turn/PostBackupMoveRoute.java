package com.webcheckers.ui.turn;

import com.google.gson.Gson;
import com.webcheckers.application.Matchmaking;
import com.webcheckers.model.Board;
import com.webcheckers.model.Match;
import com.webcheckers.model.Player;
import com.webcheckers.model.moves.Move;
import com.webcheckers.ui.PostSigninRoute;
import com.webcheckers.util.Message;

import freemarker.log.Logger;
import spark.Route;
import spark.Session;
import spark.Request;
import spark.Response;

import static com.webcheckers.ui.PostSigninRoute.PLAYER_KEY;

import java.util.Map;

/**
 * Handles backing up of moves.
 */
public class PostBackupMoveRoute implements Route {
    private Gson gson;
    private Matchmaking matchmaking;

    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());
    private static final String NULL_ERROR_MSG = "PostBackupMoveRoute tried to get the last move, but it was null.";
    
    /**
     * Constructor.
     * @param matchmaking Matchmaking object
     */
    public PostBackupMoveRoute(Matchmaking matchmaking) {
        this.matchmaking = matchmaking;
        this.gson = new Gson();
    }
    

    @Override
    public Object handle(Request request, Response response) {
        Session httpSession = request.session();
        Player player = httpSession.attribute(PostSigninRoute.PLAYER_KEY);
        Match match = matchmaking.getMatch(player);
        Message message;
        
        // Get pending move
        Move currentMove = match.getCurrentMove();

        // If there is no pending move, give error.
        if (currentMove == null) {
            LOG.error(NULL_ERROR_MSG);
            message = Message.error(NULL_ERROR_MSG);
        } else { // Remove pending move from board
            Board board = matchmaking.getBoard(player);
            match.setCurrentMove(null);

            message = Message.info("Last move backed up.");
        }

        return gson.toJson(message);
    }
}
