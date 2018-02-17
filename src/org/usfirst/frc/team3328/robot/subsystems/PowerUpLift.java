package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.LiftPID;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class PowerUpLift implements Lift {

	Encoder encoder; 
	PWMTalonSRX talon;
	DigitalInput limitswitch;
	LiftPID pid;
	
	public double restraint = 1;
	public double targetPosition = 0;
	public double liftSpeed = 0; 
	public double desiredHeight;
	public double angleSpeed = 1; //idk what value yet
	
	
	private static final int SCALE_HIGH_POSITION = 5; //idk what all these values are yet
	private static final int SCALE_MID_POSITION = 4;  //rn they're just fillers
	private static final int SCALE_LOW_POSITION = 3;
	private static final int SWITCH_POSITION = 1;
	private static final int EXCHANGE_POSITION = 0;
	


	
	public PowerUpLift(LiftPID pid) {
		this.pid = pid;
		pid.start();
	}
		
	@Override
	public boolean isStopped() {
		return talon.get() != 0;
	}

	@Override
	public void reset() {
		pid.desiredValue = encoder.get();
	}

	@Override
	public void stop() {
		talon.stopMotor();
	}

	@Override
	public double getHeight() {
		return encoder.getDistance();
	}
	
	public void autoAdjustHeight(double target){
		desiredHeight = target;
		pid.desiredValue = target;
	}

	@Override
	public void toScaleHigh() {
		autoAdjustHeight(SCALE_HIGH_POSITION);
	}

	@Override
	public void toScaleMid() {
		autoAdjustHeight(SCALE_MID_POSITION);
	}

	@Override
	public void toScaleLow() {
		autoAdjustHeight(SCALE_LOW_POSITION);
	}

	@Override
	public void toSwitch() {
		autoAdjustHeight(SWITCH_POSITION);
	}

	@Override
	public void toGround() {
		autoAdjustHeight(EXCHANGE_POSITION);
	}
	
	@Override
	public void controlledMove(double power) {
		talon.set(power / restraint); 
	}
	
	public boolean limitHit() {
		if (limitswitch.get()) {
			return true;
		} else {
			return false;
		}
	}
}
