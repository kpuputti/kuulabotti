package ai.bot.kuula.game;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import fi.zem.aiarch.game.hierarchy.Situation;

public class GameSituation {
	
	public Logger log = Logger.getLogger(GameSituation.class);

	/** Actual situation */
	private Situation situation;
	
	private Situation parentSituation;
	private List<Situation> childSituations;
	
	/** Value of this situation */
	private int score;
	
	public GameSituation (Situation sit, int val) {
		this.log.setLevel(Level.DEBUG);
		this.situation = sit;
		this.score = val;
	}

	public Situation getSituation() {
		return situation;
	}

	public void setSituation(Situation situation) {
		this.situation = situation;
	}

	public int getScore() {
		return score;
	}

}
