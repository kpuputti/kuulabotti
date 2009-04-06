package ai.bot.kuula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ai.bot.kuula.game.GameSituation;
import ai.bot.kuula.game.ScoreCounter;

import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.MoveType;
import fi.zem.aiarch.game.hierarchy.Player;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

public class KuulaBot implements Player {

	private Random rnd;
	
	/** game engine */
	private Engine engine;

	/** Own side */
	private Side side;

	/** Other side */
	private Side opponent;
	
	private ScoreCounter scoreCounter;

	public KuulaBot(Random rnd) {
		this.rnd = rnd;

	}

	public void start(Engine engine, Side side) {
		this.engine = engine;
		// set sides
		this.side = side;
		this.opponent = side == Side.BLUE ? Side.RED : Side.BLUE;
		
		this.scoreCounter = new ScoreCounter(this.side);
	}

	public Move move(Situation situation, int timeLeft) {
		
		List<Move> moves = situation.legal();
		List<GameSituation> list = new ArrayList<GameSituation>();

		// init situation map
		Map<Integer, List<GameSituation>> situationMap = new HashMap<Integer, List<GameSituation>>();
		List<GameSituation> situationList = new ArrayList<GameSituation>();
		GameSituation gameSituation = new GameSituation(situation, 0);
		situationList.add(gameSituation);
		situationMap.put(0, situationList);
		
		int mapSize = situationMap.size();
		
		while (mapSize < 4) {
			situationMap = getFollowingSituation(situationMap);
			mapSize = situationMap.size();
		}
		
		
		
		// Add all situations after legal moves to the list.
		/*for (Move move : moves) {
			
			Situation newSituation = situation.copyApply(move);
			int situationScore = this.scoreCounter.count(newSituation);
			
			list.add(new GameSituation(newSituation, situationScore));
			
		}
		*/
		
		return moves.get(rnd.nextInt(moves.size()));
	}

	private Map<Integer, List<GameSituation>> getFollowingSituation(Map<Integer, List<GameSituation>> situations) {
		
		Map<Integer, GameSituation> map = new HashMap<>
		
		return null;
	}
	
	
	
	/*
	for (Move move : moves) {
		
		if (move.getType() == MoveType.DESTROY) {
			return move;
		}
		
		if (move.getType() == MoveType.ATTACK  ) {
			return move;
		}

	}
*/	
}
