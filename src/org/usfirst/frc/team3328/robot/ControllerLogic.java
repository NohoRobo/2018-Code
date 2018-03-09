package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.Lift;
import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.PowerUpLift;
import org.usfirst.frc.team3328.robot.subsystems.Ramp;
import org.usfirst.frc.team3328.robot.subsystems.Sheeder;
import org.usfirst.frc.team3328.robot.utilities.LogLevel;
import org.usfirst.frc.team3328.robot.utilities.Logger;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox.Buttons;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

//import org.apache.log4j.Logger;
//import java.util.logging.Logger;



import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;

public class ControllerLogic {
	
	DriveSystem _driveSystem;
	Sheeder _sheeder;
	Lift _lifter;
	Controller _driveCont;
	Controller _utilCont;
	TalonSRX _talon;
	Encoder _leftTeleopEncoder;
	Encoder _rightTeleopEncoder;
	PIDController _leftTeleopPID;
	PIDController _rightTeleopPID;
	DigitalInput _liftSwitch;
	DigitalInput _sheedSwitch;
	Ramp _ramp;
	
	Timer _sheederTimer = new Timer();
	Timer driveMacroTimer = new Timer();	

	//	Logger logger = new Logger();

	final double deadzone = .15;
	double restraint = 1;
	boolean macroControl = false;
	boolean manualFeeder = false;

	public ControllerLogic(Encoder leftTeleopEncoder, Encoder rightTeleopEncoder, PIDController leftTeleopPID,
						   PIDController rightTeleopPID, DriveSystem driveSystem,
						   Sheeder sheeder, Lift lifter, Ramp ramp, Controller driveCont, Controller utilCont) {
		this._leftTeleopEncoder = leftTeleopEncoder;
		this._rightTeleopEncoder = rightTeleopEncoder;
		this._leftTeleopPID = leftTeleopPID;
		this._rightTeleopPID = rightTeleopPID;
		_leftTeleopPID.disable();
		_rightTeleopPID.disable();
		this._driveSystem = driveSystem;
		this._lifter = lifter;
		this._sheeder = sheeder;
		this._ramp = ramp;
		this._driveCont = driveCont;
		this._utilCont = utilCont;
	}

	public void run() {
		//drive
		if(!macroControl) {
			_driveSystem.setMotors(speedOf(
				_driveCont.getRightTrigger()-_driveCont.getLeftTrigger()+
				(Math.abs(_driveCont.getX())>0.2?_driveCont.getX():0)),speedOf(//left
						_driveCont.getRightTrigger()-_driveCont.getLeftTrigger()-
						(Math.abs(_driveCont.getX())>0.2?_driveCont.getX():0)));//right
		}
		//changeSpeedRestraint
		if(_driveCont.getButtonRelease(Buttons.RBUMP)) 
			setHighSpeed();
		else if(_driveCont.getButtonRelease(Buttons.LBUMP)) 
			setLowSpeed();
		
		//sheeder - chew chew
		if (_utilCont.getButtonRelease(Buttons.BACK)) {
			_sheederTimer.reset();
			_sheederTimer.start();
		}
		if(_sheederTimer.get() >= 0.1) { 
			_sheeder.stop();
			_sheederTimer.stop();
			_sheederTimer.reset();
		} else if(_sheederTimer.get() >= 0.03) {
			_sheeder.stop();
			_sheeder.feed();
		} else if(_sheederTimer.get() >= 0.0) {
			_sheeder.stop();
			_sheeder.shoot();
		}
		if (_utilCont.getRightTrigger() < _utilCont.getLeftTrigger() && (_utilCont.getLeftTrigger() > .2))
			_sheeder.shoot();
		else if (_utilCont.getRightTrigger() > _utilCont.getLeftTrigger() && ( _utilCont.getRightTrigger() > .2))
			_sheeder.feed();
		else if(_sheederTimer.get()==0)
				_sheeder.hold();
		 		
		//lift
		if(Math.abs(_utilCont.getLeftY()) > 0.2) {
			_lifter.controlledMove(_utilCont.getLeftY());
			manualFeeder = true;
		}
		else if(manualFeeder = true) {
			_lifter.controlledMove(0);
		}
		if(_utilCont.getButtonPress(Buttons.X)) {
			_lifter.autoMoveTo(_lifter.getSwitch()); 
			manualFeeder = false;
		} else if(_utilCont.getButtonPress(Buttons.A)) {
			_lifter.autoMoveTo(_lifter.getScaleLow()); 
			manualFeeder = false;
		} else if(_utilCont.getButtonPress(Buttons.B)) {
			_lifter.autoMoveTo(_lifter.getScaleMid()); 
			manualFeeder = false;
		} else if(_utilCont.getButtonPress(Buttons.Y)) { 
			_lifter.autoMoveTo(_lifter.getScaleHigh()); 
			manualFeeder = false;
		} else if(_utilCont.getButtonPress(Buttons.START)) {
			_lifter.autoMoveTo(_lifter.getGround()); 
			manualFeeder = false;
		}
		
		//button for exchange?
		
		
		//PID Move
		if (_driveCont.getButtonRelease(Buttons.BACK)) {
			_leftTeleopPID.setSetpoint(_leftTeleopEncoder.getDistance() + 4);
			_rightTeleopPID.setSetpoint(_rightTeleopEncoder.getDistance() + 4);
			_leftTeleopPID.setPID(-0.06 ,0, 0);
			_rightTeleopPID.setPID(-0.06 ,0, 0); //tune later *lower than auto values* 
			_leftTeleopPID.enable();
			_rightTeleopPID.enable();
			driveMacroTimer.reset();
			driveMacroTimer.start();
			macroControl = true;
		}
		if(driveMacroTimer.get()>0.75) {
			_leftTeleopPID.disable();
			_rightTeleopPID.disable();
			macroControl = false;
		}
		
		//ramp
		if(_ramp.isDeployed()) {
			if(_utilCont.getRightY() > 0.2 || _utilCont.getRightY() < -0.2) 
				_ramp.winch(_utilCont.getRightY());
			else 
				_ramp.winch(0);
		} else if(_utilCont.getButtonRelease(Buttons.RIGHTSTICK)) 
			_ramp.deploy();
		
			

		SmartDashboard.putNumber("choo choo timer", _sheederTimer.get());
		SmartDashboard.putNumber("X Joystick Value", _driveCont.getX());
		SmartDashboard.putNumber("Y Joystick Value", _driveCont.getLeftY());
		SmartDashboard.putNumber("Right Trigger Value", _driveCont.getRightTrigger());
		SmartDashboard.putNumber("Left Trigger Value", _driveCont.getLeftTrigger());
		SmartDashboard.putNumber("Restraint Value", restraint);	
		SmartDashboard.putNumber("Lift Encoder Value-teleop", _lifter.getEncoderValue());
		

	}

	private double speedOf(double speed) {
		return (speed * speed * Math.signum(speed)) / restraint;
	}

	private boolean rightTriggerisPressed(Controller controller) {
		return controller.getRightTrigger() > deadzone;
	}

	private boolean leftTriggerisPressed(Controller controller) {
		return controller.getLeftTrigger() > deadzone;
	}

	private boolean xjoyStickisMoved(Controller controller) {
		return controller.getX() != 0;
	}

	private boolean yjoyStickisMoved(Controller controller) {
		return controller.getLeftY() != 0;
	}

	private void setHighSpeed() {
		restraint = 1;
	}

	private void setLowSpeed() {
		restraint = 5;
	}
}


