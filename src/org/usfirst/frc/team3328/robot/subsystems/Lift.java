package org.usfirst.frc.team3328.robot.subsystems;

public interface Lift {
			
	void toScaleHigh();
	
	void toScaleMid();
	
	void toScaleLow();
	
	void toSwitch();
	
	void toGround();

	void controlledMove(double power);

	void limitReset();

	int getEncoderValue();

	void init();
	
}
