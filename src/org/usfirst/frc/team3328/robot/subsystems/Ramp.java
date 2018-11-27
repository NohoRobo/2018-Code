package org.usfirst.frc.team3328.robot.subsystems;

public interface Ramp {
	
	void deploy();
	
	void winch(double power);

	boolean isDeployed();
}
