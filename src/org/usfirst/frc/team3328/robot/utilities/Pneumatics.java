package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Pneumatics {
	
	DoubleSolenoid solenoid;
	Compressor compress;
	
	public Pneumatics(DoubleSolenoid solenoid, 
							 Compressor compress) {
		this.solenoid = solenoid;
		this.compress = compress;
	}

	public boolean isCompressing() {
		return compress.enabled();
	}

	public void start() {
		compress.setClosedLoopControl(true);
	}

	public void stop() {
		compress.setClosedLoopControl(false);
	}
	
	public boolean isExtended() {
		return true;
	}

	public void extend() {
		solenoid.set(DoubleSolenoid.Value.kForward);
	}

	public void retract() {
		solenoid.set(DoubleSolenoid.Value.kReverse);
	}

	public void turnOff() {
		solenoid.set(DoubleSolenoid.Value.kOff);

	}

}
