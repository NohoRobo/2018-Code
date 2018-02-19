package org.usfirst.frc.team3328.robot.subsystems;

import org.usfirst.frc.team3328.robot.utilities.LogLevel;
import org.usfirst.frc.team3328.robot.utilities.Logger;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class PowerUpLift implements Lift {

	TalonSRX _talon;
	DigitalInput limitswitch;
	
	public double restraint = 6;
	int _talonTimeout = 10;
    int _talonLoopIdx = 0;
    int _talonSlotIdx = 0;
    
    public double _KP;
    public double _KI;
    public double _KD;
	
	private static final int SCALE_HIGH_POSITION = 33170; 
	private static final int SCALE_MID_POSITION = 27572; 
	private static final int SCALE_LOW_POSITION = 21750;
	private static final int SWITCH_POSITION = 11120;
	private static final int EXCHANGE_POSITION = 0;
	
	public PowerUpLift(double KP, double KI, double KD, TalonSRX talon) {
		this._KP = KP;
		this._KI = KI;
		this._KD = KD;
		this._talon = talon;
	}
	
	@Override
	public void init() {
		_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,_talonLoopIdx,_talonTimeout);
		_talon.setSensorPhase(false);
		_talon.setInverted(true);
		_talon.configNominalOutputForward(0,_talonTimeout);
		_talon.configNominalOutputReverse(0, _talonTimeout);
		_talon.configPeakOutputForward(1, _talonTimeout);
		_talon.configPeakOutputReverse(-1, _talonTimeout);
		_talon.configAllowableClosedloopError(_talonSlotIdx, 0, _talonTimeout);
		_talon.config_kF(_talonSlotIdx, 0.0, _talonTimeout);
		_talon.config_kP(_talonSlotIdx, _KP, _talonTimeout);
		_talon.config_kI(_talonSlotIdx, _KI, _talonTimeout);
		_talon.config_kD(_talonSlotIdx, _KD, _talonTimeout);
		_talon.setSelectedSensorPosition(0, _talonLoopIdx, _talonTimeout);
	}
	
	@Override
	public void toScaleHigh() {
		_talon.set(ControlMode.Position,(SCALE_HIGH_POSITION));
	}

	@Override
	public void toScaleMid() {
		_talon.set(ControlMode.Position,(SCALE_MID_POSITION));
	}

	@Override
	public void toScaleLow() {
		_talon.set(ControlMode.Position,(SCALE_LOW_POSITION));
	}

	@Override
	public void toSwitch() {
		_talon.set(ControlMode.Position,(SWITCH_POSITION));
	}

	@Override
	public void toGround() {
		_talon.set(ControlMode.Position,(EXCHANGE_POSITION));
	}
	
	@Override
	public void controlledMove(double power) {
		_talon.set(ControlMode.PercentOutput, -power / restraint);
	}
	
	@Override
	public void limitReset() {
		_talon.setSelectedSensorPosition(0, _talonLoopIdx, _talonTimeout);
	}
	
	@Override
	public int getEncoderValue() {
		return _talon.getSelectedSensorPosition(_talonLoopIdx);
	}
}
