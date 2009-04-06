package ai.bot.kuula.game;

import java.util.List;

import fi.zem.aiarch.game.hierarchy.Board;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.MoveType;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

public class ScoreCounter {
	
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
		this.side = side;
	}
	
	public int count(Situation situation) {
		
		
		
		Side situationSide = situation.getTurn();
		Board board = situation.getBoard();
		int overallFirepower = 0;
		int movePoints = 0;
		List<Move> moves = situation.legal();
				
		for (Move move : moves) {
			
			MoveType moveType = move.getType();
			
			if (moveType.equals(MoveType.MOVE)) {
				
				overallFirepower += board.firepower(situationSide, move.getFrom());
				
			} else if (moveType.equals(MoveType.ATTACK)) {
				
				movePoints += this.ATTACK_POINTS * move.getTarget().getValue();
				overallFirepower += board.firepower(situationSide, move.getFrom());

			} else if (moveType.equals(MoveType.DESTROY)) {
				
				movePoints += this.DESTROY_POINTS * move.getTarget().getValue();
				overallFirepower += board.firepower(situationSide, move.getFrom());
				
			}
			
		}
				
		int sideprefix = situationSide.equals(this.side) ? 1 : -1;
				
		return this.getScore(movePoints, movePoints, sideprefix);
	}
	
	private int getScore(int movePoints, int firepower, int sideprefix) {
		
		int score = (WEIGHT_MOVE * movePoints + WEIGHT_FIREPOWER * firepower) * sideprefix;
		
		return score;
		
	}
	
}
