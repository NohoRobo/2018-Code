package org.usfirst.frc.team3328.robot.subsystems;

public interface Lift {

	void controlledMove(double power);

	int getEncoderValue();

	void init();

	void autoMoveTo(int position);

	int getScaleHigh();

	int getGround();

	int getSwitch();

	int getScaleLow();

	int getScaleMid();

	void resetLimitIfAtBottom();
	
}
