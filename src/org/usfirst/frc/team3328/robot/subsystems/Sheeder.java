package org.usfirst.frc.team3328.robot.subsystems;

public interface Sheeder {
	
	boolean isFeeding();
	
	void controlFeeder();
	
	boolean limitHit();
	
	boolean isShooting();
	
	void startShoot();
	
	void stopShoot();
	
	boolean isExpanded();
	
	void expand();

	void contract();

}
