package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.Pneumatics;
import org.usfirst.frc.team3328.robot.utilities.SheederSpeedControllers;

import edu.wpi.first.wpilibj.DigitalInput;

public class PowerUpSheeder implements Sheeder {
	
	SheederSpeedControllers feeder;
	SheederSpeedControllers shooter;
	DigitalInput limitswitch;
	boolean limitHit = false;
	private double speed = 1; //subject to change
	
	public PowerUpSheeder (DigitalInput limitswitch, SheederSpeedControllers feeder) {
		this.limitswitch = limitswitch;	
		this.feeder = feeder;
		feeder = shooter;
	}
	
	public void feeder(double speed) {
		feeder.setSheeder(speed); 
	}
	
	@Override
	public boolean isFeeding() {
		return feeder.getRight() < 0;//change right and sign depending on which direction
	}
	@Override
	public void controlFeeder() {
		feeder.setSheeder(feeder.getRight() == 0 ? 1 : 0); //get is going to depend on which is + and -
	}
	
	@Override
	public boolean limitHit() {
		if (limitswitch.get()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isShooting() {
		return shooter.getRight() > 0; //idk which is positive
	}

	@Override
	public void startShoot() {
		shooter.setSheeder(speed);
	}

	@Override
	public void stopShoot() {
		shooter.stop();
	}

	@Override
	public boolean isExpanded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void expand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contract() {
		// TODO Auto-generated method stub
		
	}

}
