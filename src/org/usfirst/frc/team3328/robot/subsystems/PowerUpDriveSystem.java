package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.PID;

public class PowerUpDriveSystem implements DriveSystem {
	
	ADIS16448_IMU imu;
	PID  pid;
	private DriveTalons talons;
	private DriveEncoders encoders;
	public double restraint = 1;
	public double displacement;
	private double angleSpeed = .08;
	private double gearSpeed = .1;
	private double gearDistance;
	boolean placingGear = false;
	
	public PowerUpDriveSystem(DriveEncoders encoders, DriveTalons talons,
							  ADIS16448_IMU imu, PID pid){
		this.encoders = encoders;
		this.talons = talons;
		this.imu = imu;
		this.imu.calibrate();
		this.pid = pid;
	}
	
	@Override
	public ADIS16448_IMU getImu(){
		return imu;
	}
	
	@Override
	public void resetDistance(){
		encoders.reset();
	}
	
	@Override
	public double getDistance(){
		double dist = encoders.getDistance();
		System.out.println("Encoder: " + dist);
		return dist;
	}
	
	@Override
	public boolean stopped(){
		return Math.abs(encoders.rightRate()) < 10 && Math.abs(encoders.leftRate()) < 10;
	}
	
	@Override
	public void move(double left, double right){
		talons.right(right);
		talons.left(left);
		printSpeed();
	}
	
	public double calculateSpeed(double position){
		//graph this in desmos it works i promise
		double newPosition = position*position*(Math.signum(position)); 
		return newPosition;
	}
	
	
	@Override
	public void stop(){
		talons.stop();
	}
	
	@Override
	public void printSpeed(){
		//System.out.printf("%.2f || %.2f\n",talons.getfl(), talons.getfr());
	}
	
	@Override
	public void fullSpeed() {
		restraint = .8;
	}
	
	@Override
	public void thirdSpeed() {
		restraint = .33;
	}
	
	@Override
	public void upRestraint(){
		if (restraint < 10){
			restraint += 1;
		}
	}
	
	@Override
	public void downRestraint(){
		if (restraint > 1){
			restraint -=1;
		}
	}
	
	public void updateAngleSpeed(){
		pid.setError(displacement / 360);
		angleSpeed = pid.getCorrection();
	}
	
	@Override
	public void autoAngle(double current, double desired){
		displacement = desired - current;
		updateAngleSpeed();
		//System.out.printf("Current: %.2f |Desired: %.2f\n", current, desired);
		move(angleSpeed, -angleSpeed);
	}

	
	
	@Override
	public void controlledMove(double xAxis, double yAxis){
		double x = calculateSpeed(xAxis);
		double y = calculateSpeed(yAxis);
//		double x = xAxis;
//		double y = yAxis;
		move((x + y) / restraint, 
			(x - y) / restraint);
	}

}