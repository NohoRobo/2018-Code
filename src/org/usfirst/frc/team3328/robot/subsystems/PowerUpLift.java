package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.PID;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class PowerUpLift implements Lift {

	Encoder encoder; 
	PWMTalonSRX talon;
	PID pid;
	DigitalInput limitswitch;
	
	public double restraint = 1;
	public double targetPosition = 0;
	public double liftSpeed = 0; 
	public double errorDistance;
	public double angleSpeed = 1; //idk what value yet
	
	
	private static final int SCALE_HIGH_POSITION = 5; //idk what all these values are yet
	private static final int SCALE_MID_POSITION = 4;  //rn they're just fillers
	private static final int SCALE_LOW_POSITION = 3;
	private static final int SWITCH_POSITION = 1;
	private static final int EXCHANGE_POSITION = 0;
	
	private static final double SPEED_UP_LIFT = 0.5;
	private static final double SPEED_DOWN_LIFT = 0.5;


	
	public PowerUpLift(Encoder encoder, PWMTalonSRX talon, PID pid) {
		this.encoder = encoder;
		this.talon = talon;
		this.pid = pid;
	}
		
	@Override
	public boolean isStopped() {
		return talon.get() != 0;
	}

	@Override
	public void reset() {
		encoder.reset();
	}

	@Override
	public void stop() {
		talon.stopMotor();
	}

	@Override
	public double getHeight() {
		return encoder.getDistance();
	}
	
	public void updateAngleSpeed(){
		pid.setError(errorDistance / 360);
		angleSpeed = pid.getCorrection();
	}
	
	public void autoAdjustHeight(double current, double target){
		errorDistance = target - current;
		updateAngleSpeed();
		talon.set(angleSpeed);
	}

	@Override
	public void liftHigh() {
		moveLiftTo(SCALE_HIGH_POSITION);
		autoAdjustHeight(encoder.get(), SCALE_HIGH_POSITION);
	}

	@Override
	public void liftMid() {
		moveLiftTo(SCALE_MID_POSITION);
		autoAdjustHeight(encoder.get(), SCALE_MID_POSITION);
	}

	@Override
	public void liftLow() {
		moveLiftTo(SCALE_LOW_POSITION);
		autoAdjustHeight(encoder.get(), SCALE_LOW_POSITION);
	}

	@Override
	public void liftSwitch() {
		moveLiftTo(SWITCH_POSITION);
		autoAdjustHeight(encoder.get(), SWITCH_POSITION);
	}

	@Override
	public void liftGround() {
		moveLiftTo(EXCHANGE_POSITION);
		autoAdjustHeight(encoder.get(), EXCHANGE_POSITION);
	}
	
	@Override
	public void controlledLift(double yAxis) {
		talon.set(yAxis / restraint); 
	}
	
	public boolean limitHit() {
		if (limitswitch.get()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void moveLiftTo(int position) {
		if (encoder.get() < position) {
			liftSpeed = SPEED_UP_LIFT;
		} else if (encoder.get() > position) {
			liftSpeed = -SPEED_DOWN_LIFT;
		} else {
			liftSpeed = 0;
		}
		if (limitHit()) {
			stop();
		}
		talon.set(liftSpeed);
	}

}
