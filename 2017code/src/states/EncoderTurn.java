package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;

public class EncoderTurn implements RobotState{
	
	DriveSystem drive;
	double distance; 
	double range = 5;
	
	public EncoderTurn(DriveSystem drive){
		this.drive = drive;
	}
	
	@Override
	public void setValue(double value) {
		distance = value;
		drive.resetDistance();
	}
	
	public boolean inRange(){
		if (Math.abs(drive.getDistance() - distance) < range){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean run() {
		if (!inRange()){
			if(drive.getDistance() > distance){
				drive.move(.2, -.2);
			}else{
				drive.move(-.2, .2);
			}
			return false;
		}
		return true;
	}

}
