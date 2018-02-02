package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksHotelLobby implements HotelLobby {
	
	SpeedController belt;
	double beltSpeed = -.4;
	
	public SteamWorksHotelLobby(SpeedController belt) {
		this.belt = belt;
	}
	
	@Override
	public void run(){
		belt.set(beltSpeed);
	}
	
	@Override
	public void stop(){
		belt.set(0);
	}

	@Override
	public boolean isRunning() {
		return belt.get() != 0;
	}

}
