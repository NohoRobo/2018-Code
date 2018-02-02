package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public interface DriveSystem {
	
	boolean getPlacingGear();
	
	ADIS16448_IMU getImu();
	
	Tracking getTrack();
	
	void resetDistance();
	
	double getDistance();
	
	boolean stopped();
	
	void printSpeed();
	
	void stop();

	void autoAngle(double current, double desired);
	
	void upRestraint();
	
	void downRestraint();
	
	void move(double left, double right);
	
	void setGearDistance(double gearDistance);

	void placeGear();

	void controlledMove(double xAxis, double yAxis);

}