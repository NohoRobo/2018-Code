package org.usfirst.frc.team3328.robot.autocontrollers;

import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DrivePID;
import org.usfirst.frc.team3328.robot.utilities.NewController;
import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox.Buttons;


public class AutoForward implements NewController {

	DrivePID pid;
	DriveEncoders encoder;
	
	double desired;
	double current = encoder.getDistance();
	
	public AutoForward(double desired) {
		this.desired = desired;
	}
	
	@Override
	public double getX() {
		return 0;
	}

	@Override
	public double getY() {
		return 0;
	}

	@Override
	public double getRightTrigger() {
		while(current < desired) {
			return 0.3;
		} 
		return 0;
	}

	@Override
	public double getLeftTrigger() {
		return 0;
	}

	@Override
	public boolean getButtonRelease(Buttons but) {
		return false;
	}

	@Override
	public boolean getButtonPress(Buttons but) {
		return false;
	}
}
