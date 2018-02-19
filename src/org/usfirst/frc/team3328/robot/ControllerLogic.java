package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.Lift;
import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.PowerUpLift;
import org.usfirst.frc.team3328.robot.subsystems.Sheeder;
import org.usfirst.frc.team3328.robot.utilities.LogLevel;
import org.usfirst.frc.team3328.robot.utilities.Logger;
import org.usfirst.frc.team3328.robot.utilities.NewController;
import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox.Buttons;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

//import org.apache.log4j.Logger;
//import java.util.logging.Logger;



import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;

public class ControllerLogic {

	DriveSystem _driveSystem;
	Sheeder _sheeder;
	Lift _lifter;
	NewController _driveCont;
	NewController _utilCont;
	TalonSRX _talon;

	DigitalInput _liftSwitch;
	DigitalInput _sheedSwitch;
	Compressor _compressor;	
	Timer _sheederTimer;

	//	Logger logger = new Logger();

	final double deadzone = .15;
	double restraint = 1;

	public ControllerLogic(DriveSystem driveSystem, Sheeder sheeder, //Lift lifter, Compressor compressor,
			Lift lifter, Timer sheederTimer, NewController driveCont, NewController utilCont) {
		this._driveSystem = driveSystem;
		this._lifter = lifter;
		this._sheeder = sheeder;
		this._sheederTimer = sheederTimer;
//		this._compressor = compressor;
		this._driveCont = driveCont;
		this._utilCont = utilCont;
	}

	public void run() {
		//drive
		_driveSystem.setMotors(speedOf(
				_driveCont.getRightTrigger()-_driveCont.getLeftTrigger()+
				(Math.abs(_driveCont.getX())>0.2?_driveCont.getX():0)),speedOf(//left
						_driveCont.getRightTrigger()-_driveCont.getLeftTrigger()-
						(Math.abs(_driveCont.getX())>0.2?_driveCont.getX():0)));//right

		/*		//turn left and right
		 if(xjoyStickisMoved(_driveCont)) {
			 if (_driveCont.getX() > 0) {
				 _driveSystem.turnRight(speedOf(_driveCont.getX()));
			 } else if (_driveCont.getX() < 0) {
				 _driveSystem.turnLeft(speedOf(-_driveCont.getX()));
			 } else {
				 _driveSystem.moveForward(0);
			 }
		 }

		 //move forward and backward
		 if(rightTriggerisPressed(_driveCont) || leftTriggerisPressed(_driveCont)) {
			 if (_driveCont.getRightTrigger() > _driveCont.getLeftTrigger()) 
				 _driveSystem.moveForward(speedOf(_driveCont.getRightTrigger()));
			 else if (_driveCont.getLeftTrigger() > _driveCont.getRightTrigger())
				 _driveSystem.moveBackward(speedOf(_driveCont.getLeftTrigger()));
			 else 
				 _driveSystem.moveForward(0);
		 }
		 //curveLeftForward
		 if(rightTriggerisPressed(_driveCont) && (_driveCont.getX() < -0.2)) 
			 _driveSystem.curveForwardLeft(speedOf(_driveCont.getRightTrigger()),
			  							   speedOf(_driveCont.getX()), restraint); 

		 //curveRightForward
		 if(rightTriggerisPressed(_driveCont) && (_driveCont.getX() > 0.2)) 
			 _driveSystem.curveForwardRight(speedOf(_driveCont.getRightTrigger()), 
			 								speedOf(_driveCont.getX()), restraint); 

		 //curveLeftBackward
		 if(leftTriggerisPressed(_driveCont) && (_driveCont.getX() < -0.2)) 
			 _driveSystem.curveBackwardLeft(speedOf(_driveCont.getLeftTrigger()), 
			 								speedOf(_driveCont.getX()), restraint); 

		 //curveRightBackward
		 if(leftTriggerisPressed(_driveCont) && (_driveCont.getX() > 0.2)) 
			 _driveSystem.curveBackwardRight(speedOf(_driveCont.getLeftTrigger()), 
			 								speedOf(_driveCont.getX()), restraint);  
		 */		
		//changeSpeedRestraint
		if(_driveCont.getButtonRelease(Buttons.RBUMP)) 
			setHighSpeed();
		else if(_driveCont.getButtonRelease(Buttons.LBUMP)) { 
			setLowSpeed();
	}
		
		//sheeder - feed and shoot
		if (_utilCont.getButtonRelease(Buttons.BACK)) {
			_sheederTimer.reset();
			_sheederTimer.start();
		}
		if(_sheederTimer.get() >= 0.3) { 
			_sheeder.stop();
			_sheederTimer.stop();
			_sheederTimer.reset();
		} else if(_sheederTimer.get() >= 0.1) {
			_sheeder.stop();
			_sheeder.shoot();
		} else if(_sheederTimer.get() >= 0.01) {
			_sheeder.stop();
			_sheeder.feed();
		}
		
		if (_utilCont.getRightTrigger() < _utilCont.getLeftTrigger() && (_utilCont.getLeftTrigger() > .2))
			_sheeder.shoot();
		else if (_utilCont.getRightTrigger() > _utilCont.getLeftTrigger() && ( _utilCont.getRightTrigger() > .2))
			_sheeder.feed();
		else 
			if(_sheederTimer.get()==0)
				_sheeder.stop();
		

/*		//sheederpiston out and in 
		if(_utilCont.getButtonRelease(Buttons.LBUMP)) {
			if(_sheeder.isExtended()) 
				_sheeder.holdPiston();
			else
				_sheeder.extend();
		 } else if(_utilCont.getButtonRelease(Buttons.RBUMP)) {
			if(_sheeder.isExtended() && _lifter.getEncoderValue < 100)
				_sheeder.contract();
			else 
				_sheeder.holdPiston();
		 }

		//compressor 
		if(_compressor.getCompressorCurrentTooHighFault() || _compressor.getCompressorCurrentTooHighStickyFault() ||
			 _compressor.getCompressorNotConnectedFault() || _compressor.getCompressorNotConnectedStickyFault() ||
			 	  _compressor.getCompressorShortedFault() || _compressor.getCompressorShortedStickyFault()) {
			_compressor.stop();
			_compressor.setClosedLoopControl(false);
		} else {
			_compressor.setClosedLoopControl(true);
		}
		
*/		 		
		//lift
		//		 if(_liftSwitch.get()) {
		//			 _lift.limitReset();
		if (yjoyStickisMoved(_utilCont)) {
			if(_utilCont.getY() > 0.2 || _utilCont.getY() < -0.2)
				_lifter.controlledMove(_utilCont.getY());
		} else if(_utilCont.getButtonPress(Buttons.X)) {
			_lifter.toSwitch(); 
		} else if(_utilCont.getButtonPress(Buttons.A)) {
			_lifter.toScaleLow();
		} else if(_utilCont.getButtonPress(Buttons.B)) {
			_lifter.toScaleMid();
		} else if(_utilCont.getButtonPress(Buttons.Y)) { 
			_lifter.toScaleHigh();
		} else if(_utilCont.getButtonPress(Buttons.START)) {
			_lifter.toGround();
		}

		SmartDashboard.putNumber("choo choo timer", _sheederTimer.get());
		SmartDashboard.putNumber("X Joystick Value", _driveCont.getX());
		SmartDashboard.putNumber("Y Joystick Value", _driveCont.getY());
		SmartDashboard.putNumber("Right Trigger Value", _driveCont.getRightTrigger());
		SmartDashboard.putNumber("Left Trigger Value", _driveCont.getLeftTrigger());
		SmartDashboard.putNumber("Restraint Value", restraint);		 
	}

	private double speedOf(double speed) {
		return (speed * speed * Math.signum(speed)) / restraint;
	}

	private boolean rightTriggerisPressed(NewController controller) {
		return controller.getRightTrigger() > deadzone;
	}

	private boolean leftTriggerisPressed(NewController controller) {
		return controller.getLeftTrigger() > deadzone;
	}

	private boolean xjoyStickisMoved(NewController controller) {
		return controller.getX() != 0;
	}

	private boolean yjoyStickisMoved(NewController controller) {
		return controller.getY() != 0;
	}

	private void setHighSpeed() {
		restraint = 1;
	}

	private void setLowSpeed() {
		restraint = 5;
	}
}

