package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.Lift;
import org.usfirst.frc.team3328.robot.subsystems.NewDriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.NewSheeder;
import org.usfirst.frc.team3328.robot.subsystems.PowerUpLift;
import org.usfirst.frc.team3328.robot.utilities.LogLevel;
import org.usfirst.frc.team3328.robot.utilities.Logger;
import org.usfirst.frc.team3328.robot.utilities.NewController;
import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox.Buttons;

//import org.apache.log4j.Logger;
//import java.util.logging.Logger;



import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.DoubleSolenoid;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;

public class ControllerLogic {
	
	NewDriveSystem _driveSystem;
	NewSheeder _sheeder;
	Lift _lifter;
	NewController _driveCont;
	NewController _utilCont;
	PowerUpLift _lift;
	
	DigitalInput _limitSwitch;
	Compressor _compressor;	
	
//	Logger logger = new Logger();
	
	final double deadzone = .15;
	double restraint = 2;
	
	public ControllerLogic(NewDriveSystem driveSystem, //NewSheeder sheeder,
						   Compressor compressor, Lift lifter, NewController driveCont, NewController utilCont) {
		this._driveSystem = driveSystem;
		this._driveCont = driveCont;
		this._lifter = lifter;
//		this._sheeder = sheeder;
		this._compressor = compressor;
		this._driveCont = driveCont;
		this._utilCont = utilCont;
	}

	public void run() {
		//drive
/*		_driveSystem.setMotors(speedOf(
				_driveCont.getRightTrigger()-_driveCont.getLeftTrigger()+
				(Math.abs(_driveCont.getX())>0.2?_driveCont.getX():-0)),speedOf(//left
				_driveCont.getRightTrigger()-_driveCont.getLeftTrigger()-
				(Math.abs(_driveCont.getX())>0.2?_driveCont.getX():0)));//right
		 
*/		//turn left and right
		 if(joyStickisMoved(_driveCont)) {
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
		
		 //changeSpeedRestraint
		 if(_driveCont.getButtonRelease(Buttons.RBUMP)) 
			 setHighSpeed();
		 else if(_driveCont.getButtonRelease(Buttons.LBUMP)) 
			 setLowSpeed();
		 
		 //sheeder - feed and shoot
/*		 if (_utilCont.getRightTrigger() > _utilCont.getLeftTrigger()) {
			 while(_utilCont.getRightTrigger() > .4) {
				_sheeder.feed();
			 	if(_limitSwitch.get())
			 		_sheeder.stop();
			 } 
		 } else if (_utilCont.getRightTrigger() < _utilCont.getLeftTrigger()) {
			 while(_utilCont.getLeftTrigger() > .4) {
					_sheeder.shoot();
				 }
		 } else 
			 _sheeder.stop();
		 
		 //sheederpiston out and in 
		 if(_utilCont.getButtonRelease(Buttons.RBUMP)) {
			if(_sheeder.isExtended()) 
				_sheeder.holdPiston();
			else
				_sheeder.extend();
		 } else if(_utilCont.getButtonRelease(Buttons.RBUMP)) {
			if(_sheeder.isExtended())
				_sheeder.contract();
			else 
				_sheeder.holdPiston();
		 }
		 
		 //compressor 
		_compressor.setClosedLoopControl(true);
*/		
		
		if(_utilCont.getButtonPress(Buttons.X)) {
			_lift.toSwitch();
		}
		 
		
		 	 
		 
		 
		 
		 
		 
		 SmartDashboard.putNumber("Joystick Value", _driveCont.getX());
		 SmartDashboard.putNumber("Right Trigger Value", _driveCont.getRightTrigger());
		 SmartDashboard.putNumber("Left Trigger Value", _driveCont.getLeftTrigger());
		 SmartDashboard.putNumber("Restraint Value", restraint);
		 
		 
		 Logger.log("hello", LogLevel.debug);
			 
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

	private boolean joyStickisMoved(NewController controller) {
		return controller.getX() != 0;
	}
	
	private void setHighSpeed() {
		restraint = 2;
	}
	
	private void setLowSpeed() {
		restraint = 5;
	}
}

		 