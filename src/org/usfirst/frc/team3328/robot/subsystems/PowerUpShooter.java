package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.SheederSpeedControllers;

public class PowerUpShooter implements Shooter {
	
	SheederSpeedControllers shooter;
	
	private double speed = 1; //subject to change
	
	public PowerUpShooter(SheederSpeedControllers shooter) {
		this.shooter = shooter;
	}

	@Override
	public boolean isShooting() {
		return shooter.getRight() != 0; //idk which is positive
	}

	@Override
	public void startShoot() {
		shooter.setSheeder(speed);
	}

	@Override
	public void stopShoot() {
		shooter.stop();
	}
}
