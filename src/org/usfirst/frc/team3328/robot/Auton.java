package org.usfirst.frc.team3328.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auton {
	
	PIDController _leftPID;
	PIDController _rightPID;
	
	Encoder _leftEncoder;
	Encoder _rightEncoder;

	Timer _autonDelayTimer = new Timer();
	
	boolean _firstTime;
	int _autonSelected;
	double _currentLeftSetpoint;
	double _currentRightSetpoint;
	
	public Auton(PIDController leftPID, PIDController rightPID, 
				 Encoder leftEncoder, Encoder rightEncoder, boolean firstTime) {
		this._leftPID = leftPID;
		this._rightPID = rightPID;
		this._leftEncoder = leftEncoder;
		this._rightEncoder = rightEncoder;
		this._firstTime = firstTime;
		_rightEncoder.setDistancePerPulse((6*Math.PI)/256);
		_leftEncoder.setDistancePerPulse((6*Math.PI)/256);
	}
	
	
	public void init() {
		_firstTime = true;
		_leftEncoder.reset();
		_rightEncoder.reset();
		_leftPID.setSetpoint(0);
		_rightPID.setSetpoint(0);
		_currentLeftSetpoint = _leftPID.getSetpoint();
		_currentRightSetpoint = _rightPID.getSetpoint();
		_autonSelected = 1;
		_leftPID.setPID(-0.06, 0, 0);
		_rightPID.setPID(-0.06, 0, 0);
		move(30);
		_currentLeftSetpoint = _leftPID.getSetpoint();
		_currentRightSetpoint = _rightPID.getSetpoint();
		_leftPID.enable();
		_rightPID.enable();
	}
	public void run() {
		boolean isOnTarget = (Math.abs(_leftPID.getError()) < 2) && (Math.abs(_rightPID.getError()) < 2);
		
		switch(_autonSelected){
		case 1:
			if(isOnTarget) {
				   _autonSelected = 2;
				   _autonDelayTimer.reset();
				   _autonDelayTimer.start();
			}
			break;
		case 2:
			if(_autonDelayTimer.get() > 5) {	
				_autonDelayTimer.stop();
				_autonSelected = 3;
				_leftPID.setPID(-0.04 ,0, 0);
				_rightPID.setPID(-0.04 ,0, 0);
				turnLeft(18.84955592);
				_currentLeftSetpoint = _leftPID.getSetpoint();
				_currentRightSetpoint = _rightPID.getSetpoint();
			}
		case 3:
		default:
		}
		
		SmartDashboard.putNumber("RightEncoder", _rightEncoder.getDistance());
		SmartDashboard.putNumber("LeftEncoder", _leftEncoder.getDistance());
		SmartDashboard.putNumber("Timer", _autonDelayTimer.get());
		SmartDashboard.putBoolean("On Target", isOnTarget);
	}
	
	public void turnLeft(double distance) {
		_leftPID.setSetpoint(_currentLeftSetpoint + distance);
		_rightPID.setSetpoint(_currentRightSetpoint - distance);
	}
	
	public void move(double distance) {
		_leftPID.setSetpoint(_currentLeftSetpoint - distance);
		_rightPID.setSetpoint(_currentRightSetpoint - distance);
	}

}
