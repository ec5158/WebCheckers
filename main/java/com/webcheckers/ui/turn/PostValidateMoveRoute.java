package com.webcheckers.ui.turn;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.webcheckers.application.GameCenter;
import com.webcheckers.application.Matchmaking;
import com.webcheckers.application.Matchmaking;
import com.webcheckers.application.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Match;
import com.webcheckers.model.Player;
import com.webcheckers.model.moves.Move;
import com.webcheckers.model.moves.MoveValidator;
import com.webcheckers.model.moves.Position;
import com.webcheckers.ui.GetHomeRoute;
import com.webcheckers.ui.NavbarUtils;
import com.webcheckers.ui.PostSigninRoute;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Validates a move so that the frontend can handle it appropriately.
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostValidateMoveRoute.class.getName());
    private Matchmaking matchmaking;
    private MoveParser moveParser;
    private Gson gson;

    private static final String ERR_PENDING_MOVE = "A move is already pending. Press Backup to choose another move";

    /**
     * Create the Spark Route (UI controller) to handle all
     * {@code POST /validateMove} HTTP requests.
     *
     * @param templateEngine the HTML template rendering engine
     */
    public PostValidateMoveRoute(Matchmaking matchmaking) {
        this.matchmaking = matchmaking;
        this.moveParser = new MoveParser();
        this.gson = new Gson();

        LOG.config(this.getClass().getSimpleName() + " is initialized.");
    }

    /**
     * Check the validity of the move, returning an info message if valid or an
     * error message if invalid.
     * 
     * @return Gson Message representing move validity
     */
    @Override
    public Object handle(Request request, Response response) {
        Player player = NavbarUtils.getPlayer(request);
        String actionData = request.queryParams("actionData");
        Board board = matchmaking.getBoard(player);
        Match match = matchmaking.getMatch(player);
        MoveParser parser = getMoveParser();
        Message message;
        Move move;

        try {
            move = parser.parseMove(actionData, board);
        } catch (NullPointerException e) {
            message = Message.error(ERR_PENDING_MOVE);
            return gson.toJson(message);
        }
        MoveValidator.ValidationStatus status = parser.validateMove(actionData, board);

        if (status == MoveValidator.ValidationStatus.VALID) {
            match.setCurrentMove(move);
        }

        message = getMessage(status);

        return gson.toJson(message);
    }

    protected MoveParser getMoveParser() {
        return this.moveParser;
    }

    protected Message getMessage(MoveValidator.ValidationStatus status) {
        Message message;
        switch (status) {
        case VALID:
            message = Message.info("Valid Move!");
            break;
        case END_ALREADY_OCCUPIED:
            message = Message.error("End position already occupied.");
            break;
        case INVALID_END:
            message = Message.error("End position impossible.");
            break;
        case INVALID_START:
            message = Message.error("Bad start position.");
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + status);
        }
        return message;
    }

}