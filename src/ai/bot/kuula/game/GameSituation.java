package ai.bot.kuula.game;

import java.util.List;

import fi.zem.aiarch.game.hierarchy.Situation;

public class GameSituation {
	
	/** Actual situation */
	private Situation situation;
	
	private GameSituation parentSituation;
	private List<GameSituation> childSituations;
	
	private int depth;
	
	/** Value of this situation */
	private int score;
	
	public GameSituation (Situation sit, int score, int depth) {
		this.situation = sit;
		this.score = score;
		this.depth = depth;
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

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public GameSituation getParentSituation() {
		return parentSituation;
	}

	public void setParentSituation(GameSituation parentSituation) {
		this.parentSituation = parentSituation;
	}

	public List<GameSituation> getChildSituations() {
		return childSituations;
	}

	public void setChildSituations(List<GameSituation> childSituations) {
		this.childSituations = childSituations;
	}
	
}
