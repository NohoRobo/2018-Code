package org.usfirst.frc.team3328.robot.subsystems;


import edu.wpi.first.wpilibj.SpeedController;

public class SteamWorksFeeder implements Feeder{ 

	SpeedController feeder;

	public SteamWorksFeeder(SpeedController feeder){ 
		this.feeder = feeder;
	}

	@Override
	public void controlFeeder() {
		feeder.set((feeder.get() == 0) ? 1 : 0);
	}
	
	
	
}
