package org.usfirst.frc.team3328.robot.subsystems;

public interface NewDriveSystem {

	void moveForward(double speed);
	
	void moveBackward(double speed);
	
	void turnLeft(double speed);
	
	void turnRight(double speed);
	
	void curveForwardLeft(double speed, double radius);
	
	void curveForwardRight(double speed, double radius);
	
	void curveBackwardLeft(double speed, double radius);
	
	void curveBackwardRight(double speed, double radius);
	
	void stop();

	void setMotors(double left, double right);
}
