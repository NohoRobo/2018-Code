package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;

public class Move implements RobotState{
	
	DriveSystem drive;
	Shooter shoot;
	double distance = -1;
	double distanceTraveled = 0;
	double speed = .3;
	double restraint = 1;
	boolean backwards = false;
	
	public Move(DriveSystem dr){
		drive = dr;
	}
	
	@Override
	public void setValue(double value) {
		distance = value;
		if (distance < 0){
			backwards = true;
		}else{
			backwards = false;
		}
		drive.resetDistance();
	}
	
	@Override
	public boolean run() {
		distanceTraveled = drive.getDistance();
		if(backwards){
			if (distanceTraveled > distance){
				drive.move(-speed, -speed * restraint);
				return false;
			}
		}else if (distanceTraveled < distance){
			drive.move(speed, speed * restraint);
			return false;
		}
		drive.stop();
		drive.resetDistance();
		return true;
	}

}
