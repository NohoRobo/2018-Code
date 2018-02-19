package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;

public class PowerUpSheeder implements Sheeder {
	
	PWMVictorSPX ls;
	PWMVictorSPX rs;
	DigitalInput _limitswitch;
	DoubleSolenoid _piston;
	private double speed = .3; //subject to change and must be less than 1
	
	public PowerUpSheeder (/*DigitalInput limitswitch,*/ PWMVictorSPX leftSheeder, PWMVictorSPX rightSheeder
							  /*,DoubleSolenoid piston*/) {
//		this._limitswitch = limitswitch;	
		this.ls = leftSheeder;
		this.rs = rightSheeder;
//		this._piston = piston;
	}
	
	@Override
	public void feed() {
		ls.set(speed); 
		rs.set(-speed);
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
	
	

	@Override
	public void extend() {
		_piston.set(DoubleSolenoid.Value.kForward);		
	}

	@Override
	public void contract() {
		_piston.set(DoubleSolenoid.Value.kReverse);		
		
	}
	
	@Override
	public void holdPiston() {
		_piston.set(DoubleSolenoid.Value.kOff);	
	}
	
	@Override
	public boolean isExtended() {
		return _piston.equals(DoubleSolenoid.Value.kForward);
	}
}
