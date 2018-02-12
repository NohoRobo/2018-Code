package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.SheederSpeedControllers;
import edu.wpi.first.wpilibj.DigitalInput;

public class PowerUpFeeder implements Feeder {
	
	SheederSpeedControllers feeder;
	DigitalInput limitswitch;
	
	public PowerUpFeeder (SheederSpeedControllers feeder, DigitalInput limitswitch) {
		this.feeder = feeder;
		this.limitswitch = limitswitch;	
	}
	
	public void feeder(double speed) {
		feeder.setSheeder(speed); 
	}
	
	@Override
	public void controlFeeder() {
		feeder.setSheeder(feeder.getRight() == 0 ? 1 : 0); //get is going to depend on which is + and -

	}

	public void stopFeed() {
		if (limitswitch.get()) {
			feeder.setSheeder(0);
			
		}
	}
}
