package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.NewController;
import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox.Buttons;

public class AutoControllerMoveForward implements NewController {

	@Override
	public double getX() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getButtonPress(Buttons but) {
		// TODO Auto-generated method stub
		return false;
	}
}
