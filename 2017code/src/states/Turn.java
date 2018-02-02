package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.utilities.IMU;

import edu.wpi.first.wpilibj.Timer;


public class Turn implements RobotState{
	
	DriveSystem drive;
	IMU imu;
	double range = 6;
	double current;
	double desired;
	
	public Turn(DriveSystem dr, IMU ADIS){
		drive = dr;
		imu = ADIS;
	}
	
	private boolean inRange(){
		current = imu.getAngleZ();
		//System.out.printf("current: %f|Desired: %f\n", current, desired);
		if (Math.abs(desired - current) < range){
			return true;
		}
		return false;
	}
	
	@Override
	public void setValue(double value) {
		current = imu.getAngleZ();
		desired = current + value;
	}
	
	@Override
	public boolean run() {
		if (!inRange()){
			drive.autoAngle(current, desired);
			return false;
		}
		if (!drive.stopped()){
			return false;
		}
		drive.stop();
		return true;
	}
}
	
