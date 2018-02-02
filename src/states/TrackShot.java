package states;

import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

public class TrackShot implements RobotState {
	
	DriveSystem drive;
	Tracking track;
	double trackSpeed;
	
	public TrackShot(DriveSystem drive){
		this.drive = drive;
		track = this.drive.getTrack();
	}
	
	@Override
	public void setValue(double value){
		track.toggleTracking();
	}
	
	@Override
	public boolean run() {
		trackSpeed = track.getTurn();
		if (track.getTracking(true)){
			double turn = track.getTurn();
			double move = track.getMove();
			drive.move(turn + move, -turn + move);
			return false;
		}
		drive.stop();
		return true;
	}

}
