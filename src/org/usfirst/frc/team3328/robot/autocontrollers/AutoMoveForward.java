package org.usfirst.frc.team3328.robot.autocontrollers;

import org.usfirst.frc.team3328.robot.utilities.NewController;
import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox.Buttons;

public class AutoMoveForward implements NewController {

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
		return 1;
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
