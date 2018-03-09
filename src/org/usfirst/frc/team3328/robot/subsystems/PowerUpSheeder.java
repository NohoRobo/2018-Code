package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWMVictorSPX;

public class PowerUpSheeder implements Sheeder {
	
	PWMVictorSPX ls;
	PWMVictorSPX rs;
	DigitalInput _limitswitch;
	private double speed = .5; //subject to change and must be less than 1
	
	public PowerUpSheeder (/*DigitalInput limitswitch,*/ PWMVictorSPX leftSheeder, PWMVictorSPX rightSheeder) {
//		this._limitswitch = limitswitch;	
		this.ls = leftSheeder;
		this.rs = rightSheeder;
	}
	public void setTo(double _speed) {
		ls.set(_speed); 
		rs.set(-_speed);
	}
	
	@Override
	public void feed() {
		ls.set(speed); 
		rs.set(-speed);
	}
	
	@Override 
	public void hold() {
		ls.set(0.1);
		rs.set(-0.1);
	}
	
	@Override
	public void shoot() {
		ls.set(-speed); 
		rs.set(speed);
	}

	@Override
	public void stop() {
		ls.set(0); 
		rs.set(0);
	}
}
