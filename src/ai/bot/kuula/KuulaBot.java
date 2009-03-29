package ai.bot.kuula;

import java.util.List;
import java.util.Random;

import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.Player;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

public class KuulaBot implements Player {
	public KuulaBot(Random rnd) {
		this.rnd = rnd;
	}
	
	public void start(Engine engine, Side side) {
	}
	
	public Move move(Situation situation, int timeLeft) {
		List<Move> moves = situation.legal();
		return moves.get(rnd.nextInt(moves.size()));
	}
	
	private Random rnd;
}
