package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.NewDriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.PowerUpLift;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.NewController;
import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox.Buttons;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ControllerLogic {
	
	NewDriveSystem _driveSystem;
	NewController _controller;
	PowerUpLift _lift;
	
	final double deadzone = 0;
	double restraint = 1;
	
	public ControllerLogic(NewDriveSystem driveSystem, NewController controller) {
		this._driveSystem = driveSystem;
		this._controller = controller;
		this._lift = lift;
	}

	public void run() {
		//turn left and right
		_driveSystem.setMotors(speedOf(
				_controller.getRightTrigger()-_controller.getLeftTrigger()+
				(Math.abs(_controller.getX())>0.2?_controller.getX():-0)),speedOf(//left
				_controller.getRightTrigger()-_controller.getLeftTrigger()-
				(Math.abs(_controller.getX())>0.2?_controller.getX():0)));//right
		 /*if(joyStickisMoved()) {
			 if (_controller.getX() > 0) {
				 _driveSystem.turnRight(speedOf(_controller.getX()));
			 } else if (_controller.getX() < 0) {
				 _driveSystem.turnLeft(speedOf(-_controller.getX()));
			 } else {
				 _driveSystem.moveForward(0);
			 }
		 }
		 
		 //move forward and backward
		 if(rightTriggerisPressed() || leftTriggerisPressed()) {
			 if (_controller.getRightTrigger() > _controller.getLeftTrigger()) 
				 _driveSystem.moveForward(speedOf(_controller.getRightTrigger()));
			 else if (_controller.getLeftTrigger() > _controller.getRightTrigger())
				 _driveSystem.moveBackward(speedOf(_controller.getLeftTrigger()));
			 else 
				 _driveSystem.moveForward(0);
		 }
		 //curveLeftForward
		 if(rightTriggerisPressed() && (_controller.getX() < -0.2)) {
			 _driveSystem.curveForwardLeft(speedOf(_controller.getRightTrigger()), speedOf(1 +_controller.getX())); 
		 }
			*/
		 //changeSpeedRestraint
		 if (_controller.getButtonRelease(Buttons.RBUMP)) {
			 setHighSpeed();
		 } else if (_controller.getButtonRelease(Buttons.LBUMP)) {
			 setLowSpeed();
		 }
		 
		 
		 
		 
		 SmartDashboard.putNumber("Joystick Value", _controller.getX());
		 SmartDashboard.putNumber("Right Trigger Value", _controller.getRightTrigger());
		 SmartDashboard.putNumber("Left Trigger Value", _controller.getLeftTrigger());
		 SmartDashboard.putNumber("Restraint Value", restraint);
			 
	}
	
	private double speedOf(double speed) {
		return (speed * speed * Math.signum(speed)) / restraint;
	}
	
	/*private boolean rightTriggerisPressed() {
		return _controller.getRightTrigger() > deadzone;
	}
	
	private boolean leftTriggerisPressed() {
		return _controller.getLeftTrigger() > deadzone;
	}

	private boolean joyStickisMoved() {
		return _controller.getX() != 0;
	}*/
	
	private void setHighSpeed() {
		restraint = 1;
	}
	
	private void setLowSpeed() {
		restraint = 7;
	}
}

		 