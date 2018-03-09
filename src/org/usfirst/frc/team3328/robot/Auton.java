package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.Lift;
import org.usfirst.frc.team3328.robot.subsystems.Sheeder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class Auton {
	
	PIDController _leftPID;
	PIDController _rightPID;
	
	Encoder _leftEncoder;
	Encoder _rightEncoder;
	ADXRS450_Gyro _gyro;
	
	Lift _lifter;
	Sheeder _sheeder;

	Timer _autonDelayTimer = new Timer();
	Timer _movementTimer = new Timer();
	Timer _speedTimer = new Timer();
	
	int _autonSelected;
	
	String fieldData;
	String autoPosition;
	String autoSelected;
	
	double speedThresh = 0.5;
	double allowableError = 4;
	
	double initAngle = 0;
	double finalAngle = 0;
	
	boolean leftSwitch;
	boolean leftScale;
	boolean rightPos;
	boolean midPos;
	boolean leftPos;
	
	//?TODO Delete autonSelected
	public Auton(int autonSelected, PIDController leftPID, PIDController rightPID, 
				 Encoder leftEncoder, Encoder rightEncoder, ADXRS450_Gyro gyro,
				 Lift lifter, Sheeder sheeder) {
		this._autonSelected = autonSelected;
		this._leftPID = leftPID;
		this._rightPID = rightPID;
		this._leftEncoder = leftEncoder;
		this._rightEncoder = rightEncoder;
		this._gyro = gyro;
		this._lifter = lifter;
		this._sheeder = sheeder;
		_rightEncoder.setDistancePerPulse((6*Math.PI)/256);
		_leftEncoder.setDistancePerPulse((6*Math.PI)/256);
	}
	
	
	public void initDrivePID() {
		_leftEncoder.reset();
		_rightEncoder.reset();
		_leftPID.setPercentTolerance(5);
		_rightPID.setPercentTolerance(5);
		_leftPID.setSetpoint(0);
		_rightPID.setSetpoint(0);
		_leftPID.enable();
		_rightPID.enable();
	}
	
	public void init() {
		autoSelected = SmartDashboard.getString("Auto Selector", autoPosition);
		fieldData = DriverStation.getInstance().getGameSpecificMessage();
		leftSwitch = fieldData.charAt(0) == 'L';
		leftScale = fieldData.charAt(1) == 'L';
		rightPos = autoSelected.toLowerCase().charAt(0) == 'r';
		midPos = autoSelected.toLowerCase().charAt(0) == 'm';
		leftPos = autoSelected.toLowerCase().charAt(0) == 'l';
		
			    if(leftPos && leftSwitch && leftScale) {
			_autonSelected = 1;
			//_autonSelected = 10
		}  else if(leftPos && !leftSwitch && leftScale) {
			_autonSelected = 2;
		}  else if(leftPos && leftSwitch && !leftScale) {
			_autonSelected = 3;
			//_autonSelected = 10
		}  else if(leftPos && !leftSwitch && !leftScale) {
			_autonSelected = 4;
		}  else if(rightPos && leftSwitch && leftScale) {
			_autonSelected = 4;
		}  else if(rightPos && !leftSwitch && leftScale) {
			_autonSelected = 5;
			//_autonSelected = 11
		}  else if(rightPos && leftSwitch && !leftScale) {
			_autonSelected = 6;
		}  else if(rightPos && !leftSwitch && !leftScale) {
			_autonSelected = 7;
			//_autonSelected = 11
		}  else if(midPos && leftSwitch && leftScale) {
			_autonSelected = 8;
		}  else if(midPos && !leftSwitch && leftScale) {
			_autonSelected = 9;
		}  else if(midPos && leftSwitch && !leftScale) {
			_autonSelected = 8;
		}  else if(midPos && !leftSwitch && !leftScale) {
			_autonSelected = 9;
		} 
		SmartDashboard.putNumber("Auto selected: ",_autonSelected);

		initDrivePID();
		_lifter.init();
	}
	
	public void run() {
		switch(_autonSelected){
		case 0: //test
			move(20);
			turnLeft(6*Math.PI);
			turnRight(6*Math.PI);
			move(-20);
			break;
		case 1: //starting: L, switchL + scaleL
			move(303.75);
			_sheeder.hold();
			lift(_lifter.getScaleMid());
			waitForPID();
			turnRight(90);
			_sheeder.shoot();
			turnRight(90);
			_sheeder.stop();
			move(74.68);
			lift(_lifter.getGround());
			waitForPID();
			turnLeft(39.84);
			move(33.73);
			_sheeder.feed();
			waitForPID(); //possibly chu chu
			pause(1);
			_sheeder.hold();
			lift(_lifter.getSwitch());
			pause(1);
			moveD(6);
			_sheeder.shoot();
			pause(1);
			_sheeder.stop();
			break;
		case 2: //starting L, switch R + scale L
			move(303.75);
			_sheeder.hold();
			lift(_lifter.getScaleMid());
			waitForPID();
			turnRight(90);
			_sheeder.shoot();
			turnRight(90);
			_sheeder.stop();
			move(74.68);
			lift(_lifter.getGround());
			waitForPID();
			turnLeft(39.84);
			move(33.73);
			_sheeder.feed();
			waitForPID(); //possibly chu chu
			pause(1);
			_sheeder.hold();
			move(-18.41);
			lift(_lifter.getScaleHigh());
			waitForPID();
			turnLeft(130.39);
			moveD(51.07);
			_sheeder.shoot();
			pause(1);
			_sheeder.stop();
			break;
		case 3: //Starting: L, switch L + scale R
			move(149.83);
			_sheeder.hold();
			waitForPID();
			turnRight(90);
			move(14.75);
			lift(_lifter.getSwitch());
			waitForPID();
			_sheeder.shoot();
			move(-10);
			_sheeder.stop();
			waitForPID();
			turnRight(90);
			move(-74.83);
			lift(_lifter.getGround());
			waitForPID();
			turnLeft(39.84);
			move(27.99);
			_sheeder.feed();
			waitForPID(); //possibly chu chu
			pause(1);
			_sheeder.hold();
			lift(_lifter.getSwitch());
			pause(1);
			moveD(6);
			_sheeder.shoot();
			pause(1);
			_sheeder.stop();
			break;
		case 4: //Starting: L, switch R + scale R, Starting R, switch L + scale L
			moveD(160);
			break;
		case 5: //Starting: R, switch R + scale L
			move(149.83);
			_sheeder.hold();
			waitForPID();
			turnLeft(90);
			move(14.75);
			lift(_lifter.getSwitch());
			waitForPID();
			_sheeder.shoot();
			move(-10);
			_sheeder.stop();
			waitForPID();
			turnLeft(90);
			move(-74.83);
			lift(_lifter.getGround());
			waitForPID();
			turnRight(39.84);
			move(27.99);
			_sheeder.feed();
			waitForPID(); //possibly chu chu
			pause(1);
			_sheeder.hold();
			lift(_lifter.getSwitch());
			pause(1);
			moveD(6);
			_sheeder.shoot();
			pause(1);
			_sheeder.stop();
			break;
		case 6: //starting R, switch L + scale R 
			move(303.75);
			_sheeder.hold();
			lift(_lifter.getScaleMid());
			waitForPID();
			turnLeft(90);
			_sheeder.shoot();
			turnLeft(90);
			_sheeder.stop();
			move(74.68);
			lift(_lifter.getGround());
			waitForPID();
			turnRight(39.84);
			move(33.73);
			_sheeder.feed();
			waitForPID(); //possibly chu chu
			pause(1);
			_sheeder.hold();
			move(-18.41);
			lift(_lifter.getScaleHigh());
			waitForPID();
			turnRight(130.39);
			moveD(51.07);
			_sheeder.shoot();
			pause(1);
			_sheeder.stop();
			break;
		case 7: //starting: R, switchR + scaleR
			move(303.75);
			_sheeder.hold();
			lift(_lifter.getScaleMid());
			waitForPID();
			turnLeft(90);
			_sheeder.shoot();
			turnLeft(90);
			_sheeder.stop();
			move(74.68);
			lift(_lifter.getGround());
			waitForPID();
			turnRight(39.84);
			move(33.73);
			_sheeder.feed();
			waitForPID(); //possibly chu chu
			pause(1);
			_sheeder.hold();
			lift(_lifter.getSwitch());
			pause(1);
			moveD(6);
			_sheeder.shoot();
			pause(1);
			_sheeder.stop();
			break;
		case 8: //starting: M, switchL + scaleLR
			move(10);
			_sheeder.hold();
			waitForPID();
			turnLeft(40.44);
			moveD(109.06); 
			turnRight(40.44);
			move(10); //was 1.5 sec wait
			lift(_lifter.getSwitch());
			waitForPID();
			_sheeder.shoot();
			move(-80.75);
			_sheeder.stop();
			lift(_lifter.getGround());
			waitForPID();
			turnRight(45);
			move(68.71);
			_sheeder.feed();
			waitForPID();
			move(-68.71);
			_sheeder.hold();
			waitForPID();
			turnLeft(45);
			moveD(80.75);
			lift(_lifter.getSwitch());
			_sheeder.shoot();
			pause(1);
			_sheeder.stop();
			break;	
		case 9: //starting: M, switch R + scaleLR
			move(10);
			_sheeder.hold();
			waitForPID();
			turnRight(37.09);
			moveD(104.05); 
			turnLeft(37.09);
			move(10); //was 1.5 sec wait
			lift(_lifter.getSwitch());
			waitForPID();
			_sheeder.shoot();
			move(-70.75);
			_sheeder.stop();
			lift(_lifter.getGround());
			waitForPID();
			turnLeft(45);
			move(68.71);
			_sheeder.feed();
			waitForPID();
			move(-68.71);
			_sheeder.hold();
			waitForPID();
			turnRight(45);
			moveD(80.75);
			lift(_lifter.getSwitch());
			_sheeder.shoot();
			pause(1);
			_sheeder.stop();
			break;
		case 10: //starting L: switch L + scale LR
			move(149.83);
			_sheeder.hold();
			waitForPID();
			turnRight(90);
			move(14.75);
			lift(_lifter.getSwitch());
			waitForPID();
			_sheeder.shoot();
			pause(2);
			_sheeder.stop();
			break;
		case 11: //starting R: switch R + scale LR
			move(149.83);
			_sheeder.hold();
			waitForPID();
			turnLeft(90);
			move(14.75);
			lift(_lifter.getSwitch());
			waitForPID();
			_sheeder.shoot();
			pause(2);
			_sheeder.stop();
			break;
		default: break;
		}
		_speedTimer.stop();
		
		_leftPID.disable();
		_rightPID.disable();
	}
	
	public void turnLeft(double distance) {
		initAngle = _gyro.getAngle();
		_leftPID.setPID(-0.04, 0, 0);
		_rightPID.setPID(-0.04, 0, 0);
		_leftPID.setSetpoint(_leftPID.getSetpoint() + distance*Math.PI/15);
		_rightPID.setSetpoint(_rightPID.getSetpoint() - distance*Math.PI/15);
		waitForPID();
		finalAngle = _gyro.getAngle();
		SmartDashboard.putNumber("Gyro Angle", finalAngle - initAngle);
	}
	
	public void turnRight(double distance) {
		turnLeft(-distance);
	}
	
	public void move(double distance) {
		_leftPID.setPID(-0.06 ,0, 0);
		_rightPID.setPID(-0.06 ,0, 0);
		_leftPID.setSetpoint(_leftPID.getSetpoint() - distance);
		_rightPID.setSetpoint(_rightPID.getSetpoint() - distance);
	}
	public void moveD(double distance) {
		move(distance);
		waitForPID();
	}
	
	public void lift(int position) {
		_lifter.autoMoveTo(position);
	}
	
	public void pause(double time) {
		_autonDelayTimer.reset();
		_autonDelayTimer.start();
		while(_autonDelayTimer.get()<=time) {;}
		_autonDelayTimer.stop();
	}
		
	public boolean isErrorGood() {
		return Math.abs(_leftPID.getError()) < allowableError || Math.abs(_rightPID.getError()) < allowableError;
	}
	
	public void waitForPID() {
		int escCount = 0;
		int failCount = 0;
		while (true) {
			if((Math.abs(_leftEncoder.getRate()) < speedThresh && Math.abs(_rightEncoder.getRate()) < speedThresh) && isErrorGood()) {
				escCount++;
			} else if ((Math.abs(_leftEncoder.getRate()) < speedThresh && Math.abs(_rightEncoder.getRate()) < speedThresh) && !isErrorGood()) {
				failCount++;
			} else {
				escCount = 0;
				failCount = 0;
			}
			if(escCount > 6) {
				break;
			}
			if(failCount > 200) {
				_leftPID.disable();
				_rightPID.disable();
				break;
			}
			pause(0.005);
		}
	}
}
