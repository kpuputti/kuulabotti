package ai.bot.kuula.game;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import fi.zem.aiarch.game.hierarchy.Board;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.MoveType;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

public class ScoreCounter {

    public Logger log = Logger.getLogger(ScoreCounter.class.getName());

    private final int WEIGHT_DESTROY = 1;
    private final int WEIGHT_ATTACK = 1;
    private final int WEIGHT_TARGET_VALUE = 1;

    private final int WEIGHT_FIREPOWER = 20;	
    private final int WEIGHT_MOVE = 80;

    private final int ATTACK_POINTS = 10;
    private final int DESTROY_POINTS = 20;

    /** Own side */
    private Side side;

    public ScoreCounter (Side side) {
        this.log.setLevel(Level.INFO);
        this.side = side;
    }

    public int count(Situation situation) {

        Side situationSide = situation.getTurn();
        Board board = situation.getBoard();

        // If the current situation is opponents turn, score is going to be negative.
        int sideprefix = situationSide.equals(this.side) ? 1 : -1;

        int situationScore = 0;
        int minMoveScore = 0;
        int maxMoveScore = 0;

        List<Move> moves = situation.legal();

        for (Move move : moves) {

            MoveType moveType = move.getType();
            int movePoints = 0;
            int firepower = 0;

            if (moveType.equals(MoveType.MOVE)) {

                firepower = board.firepower(situationSide, move.getFrom());

            } else if (moveType.equals(MoveType.ATTACK)) {

                movePoints = this.ATTACK_POINTS * move.getTarget().getValue();
                firepower = board.firepower(situationSide, move.getFrom());

            } else if (moveType.equals(MoveType.DESTROY)) {

                if (!situationSide.equals(this.side)) {
                    return sideprefix * Integer.MAX_VALUE;
                }

                movePoints = this.DESTROY_POINTS * move.getTarget().getValue();
                firepower = board.firepower(situationSide, move.getFrom());

            }

            int moveScore = this.getMoveScore(moveType, situationSide, movePoints, firepower);
            situationScore += moveScore;

            if (moveScore < minMoveScore) {
                minMoveScore = moveScore;
            }

            if (moveScore > maxMoveScore) {
                maxMoveScore = moveScore;
            }

        }

        this.log.info(
                "situation score: " + situationScore + "\n" +
                "prefix: " + sideprefix +"\n" +
                "min move score: " + minMoveScore +"\n" +
                "max move score: " + maxMoveScore
        );

        return sideprefix * situationScore;

    }

    private int getMoveScore(MoveType moveType, Side side, int movePoints, int firepower) {

        int score = 
            WEIGHT_MOVE * movePoints +
            WEIGHT_FIREPOWER * firepower;

        return score;

    }

}
