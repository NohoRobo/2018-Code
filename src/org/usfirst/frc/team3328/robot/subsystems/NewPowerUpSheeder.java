package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.SheederSpeedControllers;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class NewPowerUpSheeder implements NewSheeder {
	
	SheederSpeedControllers _feeder;
	SheederSpeedControllers _shooter;
	DigitalInput _limitswitch;
	DoubleSolenoid _piston;
	private double speed = .8; //subject to change and must be less than 1
	
	public NewPowerUpSheeder (DigitalInput limitswitch, SheederSpeedControllers feeder,
							  DoubleSolenoid piston) {
		this._limitswitch = limitswitch;	
		this._feeder = feeder;
		feeder = _shooter;
		this._piston = piston;
	}
	
	@Override
	public void feed() {
		_feeder.setSheeder(speed); //get is going to depend on which is + and -
	}
	
	@Override
	public void shoot() {
		_shooter.setSheeder(-speed); //idk which is + and -
	}

	@Override
	public void stop() {
		_shooter.stop();
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
