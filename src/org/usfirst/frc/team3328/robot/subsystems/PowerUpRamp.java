package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Spark;

public class PowerUpRamp implements Ramp {
	
	Spark _winch;
	Servo _leftServo;
	Servo _rightServo;
	
	double restraint = 1;
//	double startAngle = 180;
	double stopPosition = 0.5; //subject to change
	
	public PowerUpRamp(Spark winch, Servo leftServo, Servo rightServo) {
		_winch = winch;
		_leftServo = leftServo;
		_rightServo = rightServo;
        _leftServo.set(1);
        _rightServo.set(0);
	}

	@Override
	public void deploy() {
		_leftServo.set(stopPosition);
		_rightServo.set(stopPosition);
	}

	@Override
	public void winch(double power) {
		_winch.set(-power / restraint);
	}
	
	@Override
	public boolean isDeployed() {
		if(_leftServo.get() == stopPosition && _rightServo.get() == stopPosition) {
			return true;
		}
		else 
			return false;
	}

}
