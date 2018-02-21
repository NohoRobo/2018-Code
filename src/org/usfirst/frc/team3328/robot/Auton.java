package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.Lift;
import org.usfirst.frc.team3328.robot.subsystems.Sheeder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Auton {
	
	PIDController _leftPID;
	PIDController _rightPID;
	
	Encoder _leftEncoder;
	Encoder _rightEncoder;
	
	Lift _lifter;
	Sheeder _sheeder;

	Timer _autonDelayTimer = new Timer();
	Timer _sheederTimer = new Timer();
	Timer _movementTimer = new Timer();
	
	int _autonSelected;
	
	double allowableError = 4;
	
	public Auton(int autonSelected, PIDController leftPID, PIDController rightPID, 
				 Encoder leftEncoder, Encoder rightEncoder, 
				 Lift lifter, Sheeder sheeder) {
		this._autonSelected = autonSelected;
		this._leftPID = leftPID;
		this._rightPID = rightPID;
		this._leftEncoder = leftEncoder;
		this._rightEncoder = rightEncoder;
		this._lifter = lifter;
		this._sheeder = sheeder;
		_rightEncoder.setDistancePerPulse((6*Math.PI)/256);
		_leftEncoder.setDistancePerPulse((6*Math.PI)/256);
	}
	
	
	public void initDrivePID() {
		_leftEncoder.reset();
		_rightEncoder.reset();
		_leftPID.setSetpoint(0);
		_rightPID.setSetpoint(0);
		_leftPID.enable();
		_rightPID.enable();
	}
	
	public void init() {
		initDrivePID();
		_lifter.init();
	}
	
	public void run() {
		
		switch(_autonSelected){
		case 0: 
			
		case 1:
			move(20,2);
			turnLeft(6*Math.PI,3);
			turnRight(6*Math.PI,2);
			move(-20,0);
			_autonSelected = 10;
		case 2:
			_sheeder.setTo(-0.05);
			_lifter.autoMoveTo(_lifter.getSwitch());
			move(10,1.5);
			turnLeft(55.21, 1);
			move(106,4);
			turnRight(55.21,1);
			move(10,1.5);
			_sheeder.shoot();
			//:)			
		case 3:
		case 10: 
			_leftPID.disable();
			_rightPID.disable();
		default:
		}
	}
	
	public void turnLeft(double distance, double thisLong) {
		_leftPID.setPID(-0.04, 0, 0);
		_rightPID.setPID(-0.04, 0, 0);
		_leftPID.setSetpoint(_leftPID.getSetpoint() + distance*Math.PI/15);
		_rightPID.setSetpoint(_rightPID.getSetpoint() - distance*Math.PI/15);
		pause(thisLong);
		checkDriveError();
	}
	
	public void turnRight(double distance, double thisLong) {
		turnLeft(-distance, thisLong);
	}
	
	public void move(double distance, double waitTime) {
		_leftPID.setPID(-0.06 ,0, 0);
		_rightPID.setPID(-0.06 ,0, 0);
		_leftPID.setSetpoint(_leftPID.getSetpoint() - distance);
		_rightPID.setSetpoint(_rightPID.getSetpoint() - distance);
		pause(waitTime);
		checkDriveError();
	}
	
	public void lift(int position, double waitTime) {
		_lifter.autoMoveTo(position);
		pause(waitTime);
	}

	public void shoot(double thisLong, double waitTime) {
		_sheederTimer.reset();
		_sheederTimer.start();
		_sheeder.shoot();
		while(_sheederTimer.get() <= thisLong) {;}
		_sheeder.stop();
		_sheederTimer.stop();
		pause(waitTime);
	}
	
	public void feed(double thisLong, double waitTime) {
		_sheederTimer.reset();
		_sheederTimer.start();
		_sheeder.feed();
		while(_sheederTimer.get() <= thisLong) {;}
		_sheeder.stop();
		_sheederTimer.stop();
		pause(waitTime);
	}
	
	public void pause(double time) {
		_autonDelayTimer.reset();
		_autonDelayTimer.start();
		while(_autonDelayTimer.get()<=time) {;}
		_autonDelayTimer.stop();
	}
		
	public void checkDriveError() {
		if(Math.abs(_leftPID.getError()) < allowableError || Math.abs(_rightPID.getError()) < allowableError) {
			_autonSelected = 10;
		}
	}
	
}
