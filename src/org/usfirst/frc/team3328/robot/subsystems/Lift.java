package org.usfirst.frc.team3328.robot.subsystems;

public interface Lift {

	boolean isStopped();
	
	void stop();
	
	double getHeight();
	
	void toScaleHigh();
	
	void toScaleMid();
	
	void toScaleLow();
	
	void toSwitch();
	
	void toGround();
	
	void reset();

	void controlledMove(double yAxis);
	
}
