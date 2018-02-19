package org.usfirst.frc.team3328.robot.subsystems;

public interface Sheeder {
	
	void shoot();
	
	void feed();
	
	void stop();
		
	void extend();
	
	void contract();

	void holdPiston();

	boolean isExtended();

	

}
