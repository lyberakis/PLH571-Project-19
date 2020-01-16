package Pandemic.player;

public class OpponetModel {
	private int goodActionCounter;
	private int totalActionCounter;
	private float prevUtil;
	
	
	public OpponetModel() {
		totalActionCounter = goodActionCounter = 1;
		prevUtil = 0;
	}
	
	protected float getTrustIndex() {
		return goodActionCounter/totalActionCounter;
	}
	
	protected void makeAction(float util) {
		totalActionCounter++;
		goodActionCounter = (util>prevUtil)?goodActionCounter+1:goodActionCounter;
		prevUtil = util;
	}
	
	// This code is provided that updates whether the action is good or not, but when it is good?
	
	

}
