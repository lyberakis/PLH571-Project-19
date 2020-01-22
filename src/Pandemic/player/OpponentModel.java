package Pandemic.player;

public class OpponentModel {
	private int goodActionCounter;
	private int totalActionCounter;
	private float prevUtil;
	
	
	public OpponentModel() {
		totalActionCounter = goodActionCounter = 1;
		prevUtil = 0;
	}
	
	protected float getTrustIndex() {
		return goodActionCounter/totalActionCounter;
	}
	
	protected void makeAction(float util) {
		totalActionCounter++;
		goodActionCounter = (util>0.9*prevUtil)?goodActionCounter+1:goodActionCounter;
		prevUtil = util;
	}
	

	protected final static int TranslateRole(String role) {
		if (role.equals("QUARANTINE_SPECIALIST")) return 0;
		if (role.equals("OPERATIONS_EXPERT")) return 1;
		if (role.equals("SCIENTIST")) return 2;
		if (role.equals("MEDIC")) return 3;
		
		return -1;
	}
	
	

}
