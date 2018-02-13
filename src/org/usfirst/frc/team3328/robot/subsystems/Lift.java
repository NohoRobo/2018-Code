package org.usfirst.frc.team3328.robot.subsystems;

public interface Lift {

	boolean isStopped();
	
	void stop();
	
	double getHeight();
	
	void liftHigh();
	
	void liftMid();
	
	void liftLow();
	
	void liftSwitch();
	
	void liftGround();
	
	void reset();

	void controlledLift(double yAxis);

	void moveLiftTo(int position);
	
	
}
