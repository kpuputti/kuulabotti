package ai.bot.kuula;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import ai.bot.kuula.game.GameSituation;
import ai.bot.kuula.game.ScoreCounter;

import fi.zem.aiarch.game.hierarchy.Engine;
import fi.zem.aiarch.game.hierarchy.Move;
import fi.zem.aiarch.game.hierarchy.Player;
import fi.zem.aiarch.game.hierarchy.Side;
import fi.zem.aiarch.game.hierarchy.Situation;

public class KuulaBot implements Player {

	public Logger log = Logger.getLogger(KuulaBot.class.getName());

	private Random rnd;

	private int maxDepth;
	private GameSituation currentBest;

	/** game engine */
	private Engine engine;

	/** Own side */
	private Side side;

	/** Other side */
	private Side opponent;

	/** Used to define scores for single situations */
	private ScoreCounter scoreCounter;

	public KuulaBot(Random rnd) {

		this.log.setLevel(Level.INFO);

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

		this.log.fine("New move.");

		// set max depth for this search
		this.maxDepth = 2;
		this.currentBest = null;

		GameSituation startSituation = new GameSituation(situation, 0, 0);
		startSituation = getChildren(startSituation);
		this.log.info("**************************************************");
		this.log.info("Best score: " + this.currentBest.getScore());
		this.scoreCounter.count(this.currentBest.getSituation());
		
		GameSituation nextSituation = this.currentBest;
		
		while (nextSituation.getDepth() > 1) {
			nextSituation = nextSituation.getParentSituation();
		}
		
		Move nextMove = nextSituation.getSituation().getPreviousMove();

		if (nextMove != null) {
			return nextMove;
		}
		else {
			List<Move> moves = situation.legal();
			return moves.get(rnd.nextInt(moves.size()));
		}
	}


	/**
	 * Searches the children of a situation until fixed depth.
	 * 
	 * @param gameSituation
	 * @return the same game situation but with children
	 */
	private GameSituation getChildren (GameSituation gameSituation) {
		this.log.info("Getting children for node at depth: " + gameSituation.getDepth());

		List<Move> moves = gameSituation.getSituation().legal();
		List<GameSituation> children = new ArrayList<GameSituation>();

		for (Move move : moves) {

			Situation newSituation = gameSituation.getSituation().copyApply(move);
			int score = this.scoreCounter.count(newSituation);
			int depth = gameSituation.getDepth() + 1;
			GameSituation newGameSituation = new GameSituation(newSituation, score, depth);
			newGameSituation.setParentSituation(gameSituation);

			if (depth == this.maxDepth && (this.currentBest == null || score > this.currentBest.getScore())) {
				this.currentBest = newGameSituation;
			}


			this.log.info("DEPTH: " + newGameSituation.getDepth());

			if ( newGameSituation.getDepth() < this.maxDepth) {
				newGameSituation = getChildren(newGameSituation);
			}

			children.add(newGameSituation);
		}

		gameSituation.setChildSituations(children);

		return gameSituation;
	}


}
