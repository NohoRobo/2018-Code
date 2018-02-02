package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;

public class Stop implements RobotState{
	
	DriveSystem drive;
	Shooter shooter;

	public Stop(DriveSystem dr, Shooter sh){
		drive = dr;
		shooter = sh;
	}
	
	@Override
	public void setValue(double val){
		System.out.println("stopped");
	}
	
	@Override
	public boolean run() {
		drive.move(0, 0);
		shooter.stopShoot();
		return false;
	}

}
