package ai.bot.kuula;

import java.util.List;
import java.util.Random;

import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.MoveType;
import fi.zem.aiarch.game.hierarchy.Player;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

public class KuulaBot implements Player {

	private Random rnd;

	/** Own side */
	private Side side;

	/** Other side */
	private Side opponent;

	public KuulaBot(Random rnd) {
		this.rnd = rnd;

	}

	public void start(Engine engine, Side side) {
		// set sides
		this.side = side;
		this.opponent = side == Side.BLUE ? Side.RED : Side.BLUE;
	}

	public Move move(Situation situation, int timeLeft) {

		List<Move> moves = situation.legal();

		// picks the first destroy/attack move
		// TODO: this can be removed when a designed architecture is implemented
		for (Move move : moves) {
			
			if (move.getType() == MoveType.DESTROY) {
				return move;
			}
			
			if (move.getType() == MoveType.ATTACK  ) {
				return move;
			}

		}

		return moves.get(rnd.nextInt(moves.size()));
	}


}
