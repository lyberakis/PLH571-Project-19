package Pandemic.player;

public class OpponentModel {
	private float goodActionCounter;
	private float totalActionCounter;
	private float prevUtil;
	
	
	public OpponentModel() {
		this.totalActionCounter = this.goodActionCounter = 1f;
		this.prevUtil = 0;
	}
	
	protected float getTrustIndex() {
		return this.goodActionCounter/this.totalActionCounter;
	}
	
	protected void makeAction(float util) {
		this.totalActionCounter++;
		this.goodActionCounter = (util>0.6*this.prevUtil)?this.goodActionCounter+1:this.goodActionCounter;
		this.prevUtil = util;
	}
	

	/*
	 * protected final static int TranslateRole(String role) { if
	 * (role.equals("QUARANTINE_SPECIALIST")) return 0; if
	 * (role.equals("OPERATIONS_EXPERT")) return 1; if (role.equals("SCIENTIST"))
	 * return 2; if (role.equals("MEDIC")) return 3;
	 * 
	 * return -1; }
	 */
	
	

}
