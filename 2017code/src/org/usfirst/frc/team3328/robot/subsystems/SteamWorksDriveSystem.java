package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.PID;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class SteamWorksDriveSystem implements DriveSystem {
	
	Tracking track;
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
	
	public SteamWorksDriveSystem(DriveEncoders encoders, DriveTalons talons, 
								Tracking track, ADIS16448_IMU imu, PID pid){
		this.encoders = encoders;
		this.talons = talons;
		this.track = track;
		this.track.setGoal(320);
		this.imu = imu;
		this.imu.calibrate();
		this.pid = pid;
	}
	
	@Override
	public ADIS16448_IMU getImu(){
		return imu;
	}
	
	@Override
	public Tracking getTrack(){
		return track;
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
		double newPosition = (Math.sin(Math.abs(position) - (Math.PI / 2)) + 1) * 2; 
		return (newPosition * Math.signum(position));
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
	public void setGearDistance(double gearDistance) {
		this.gearDistance = gearDistance;
	}
	
	@Override
	public void placeGear(){
		placingGear = true;
		if(encoders.getDistance() < gearDistance){
			move(gearSpeed, gearSpeed);
		}else{
			placingGear = false;
			stop();
		}
	}
	
	@Override
	public boolean getPlacingGear(){
		return placingGear;
	}

	public void trackingMove(){
		double turn = track.getTurn();
		double move = track.getMove();
		move(turn + move, -turn + move);

	}
	
	@Override
	public void controlledMove(double xAxis, double yAxis){
		double x = calculateSpeed(xAxis);
		double y = calculateSpeed(yAxis);
		if (!track.getTracking(stopped())){
			move((x + y) / restraint, 
				(x - y) / restraint);
		}else{
			trackingMove();
		}
	}

}