package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.PID;

public class NewPowerUpDriveSystem implements NewDriveSystem {
	
	ADIS16448_IMU imu;
	PID  pid;
	private DriveTalons talons;
//	private DriveEncoders encoders;
	
	public NewPowerUpDriveSystem(DriveEncoders encoders, DriveTalons talons,
			  					 ADIS16448_IMU imu, PID pid){
//		this.encoders = encoders;
		this.talons = talons;
//		this.imu = imu;
//		this.imu.calibrate();
//		this.pid = pid;
	}
	
	@Override
	public void moveForward(double speed) {
		talons.right(speed);
		talons.left(speed);
	}

	@Override
	public void moveBackward(double speed) {
		speed = -speed;
		talons.right(speed);
		talons.left(speed);

	}

	@Override
	public void turnLeft(double speed) {
		talons.right(speed);
		talons.left(-speed);
	}

	@Override
	public void turnRight(double speed) {
		talons.right(-speed);
		talons.left(speed);

	}

	@Override
	public void curveForwardLeft(double speed, double radius) {
		talons.right(speed);
		talons.left(radius);
	}

	@Override
	public void curveForwardRight(double speed, double radius) {
		// TODO Auto-generated method stub

	}

	@Override
	public void curveBackwardLeft(double speed, double radius) {
		// TODO Auto-generated method stub

	}

	@Override
	public void curveBackwardRight(double speed, double radius) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		talons.right(0);
		talons.left(0);
		
	}

}
