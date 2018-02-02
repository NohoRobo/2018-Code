package states;

import org.usfirst.frc.team3328.robot.subsystems.HotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;

public class Shoot implements RobotState{

	Shooter shooter;
	HotelLobby belt;
	
	public Shoot(Shooter sh, HotelLobby lobby){
		shooter = sh;
		belt = lobby;
	}

	@Override
	public void setValue(double value){
		
	}
	
	@Override
	public boolean run() {
//		if (!shooter.isEmpty()){
			shooter.startShoot();
			shooter.updateShoot();
			return false;
//		}
//		shooter.stopShoot();
//		return true;
	}
}
