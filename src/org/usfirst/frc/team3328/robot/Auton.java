package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.Lift;
import org.usfirst.frc.team3328.robot.subsystems.Sheeder;
import org.usfirst.frc.team3328.robot.utilities.PigeonGyroPIDInput;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import com.ctre.phoenix.sensors.PigeonIMU;

public class Auton {
	
	PIDController _leftPID;
	PIDController _rightPID;
	
	PIDController _leftTurningPID;
	PIDController _rightTurningPID;
	
	Encoder _leftEncoder;
	Encoder _rightEncoder;
	PigeonGyroPIDInput _gyro;
	
	Lift _lifter;
	Sheeder _sheeder;
	
	Timer _liftTimer = new Timer();
	Timer _autonDelayTimer = new Timer();
	Timer _movementTimer = new Timer();
	Timer _speedTimer = new Timer();
	
	int _autonSelected;
	
	String fieldData;
	String autoPosition;
	String autoSelected;
	
	double speedThresh = 2;
	double allowableErrorDistance = 12;
	double allowableErrorAngle = 3;
	
	boolean turning = false;
	
	double initAngle = 0;
	double finalAngle = 0;
	
	boolean leftSwitch;
	boolean leftScale;
	boolean rightPos;
	boolean midPos;
	boolean leftPos;
	boolean firstTime = true;
	
	//?TODO Delete autonSelected
	public Auton(int autonSelected, PIDController leftPID, PIDController rightPID, 
				 PIDController leftTurningPID, PIDController rightTurningPID,
				 Encoder leftEncoder, Encoder rightEncoder, PigeonGyroPIDInput gyro,
				 Lift lifter, Sheeder sheeder) {
		this._autonSelected = autonSelected;
		this._leftPID = leftPID;
		this._rightPID = rightPID;
		this._leftTurningPID = leftTurningPID;
		this._rightTurningPID = rightTurningPID;
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
		_gyro.setYaw(0, 0);//set to what		
		
		_leftPID.setPercentTolerance(5);
		_rightPID.setPercentTolerance(5);
		_leftTurningPID.setPercentTolerance(5);
		_rightTurningPID.setPercentTolerance(5);
		
		_leftPID.setSetpoint(0);
		_rightPID.setSetpoint(0);
		_leftTurningPID.setSetpoint(0);
		_rightTurningPID.setSetpoint(0);
		
	}
	
	public void init() {
		autoSelected = SmartDashboard.getString("Auto Selector", autoPosition);
		System.out.println("abc" + autoSelected);
		fieldData = DriverStation.getInstance().getGameSpecificMessage();
		leftSwitch = fieldData.charAt(0) == 'L';
		leftScale = fieldData.charAt(1) == 'L';
		//rightPos = autoSelected.toLowerCase().charAt(0) == 'r';
		//midPos = autoSelected.toLowerCase().charAt(0) == 'm';
		//leftPos = autoSelected.toLowerCase().charAt(0) == 'l';
		
		
/*		if(leftPos && leftSwitch && leftScale) {
			//_autonSelected = 1;
			_autonSelected = 10;
		}  else if(leftPos && !leftSwitch && leftScale) {
			_autonSelected = 2;
		}  else if(leftPos && leftSwitch && !leftScale) {
			_autonSelected = 3;
			//_autonSelected = 10;
		}  else if(leftPos && !leftSwitch && !leftScale) {
			_autonSelected = 4;
		}  else if(rightPos && leftSwitch && leftScale) {
			_autonSelected = 4;
		}  else if(rightPos && !leftSwitch && leftScale) {
			_autonSelected = 5;
			//_autonSelected = 11;
		}  else if(rightPos && leftSwitch && !leftScale) {
			_autonSelected = 6;
		}  else if(rightPos && !leftSwitch && !leftScale) {
			//_autonSelected = 7;
			_autonSelected = 11;
		}  else if(midPos && leftSwitch && leftScale) {
			_autonSelected = 8;
		}  else if(midPos && !leftSwitch && leftScale) {
			_autonSelected = 9;
		}  else if(midPos && leftSwitch && !leftScale) {
			_autonSelected = 8;
		}  else if(midPos && !leftSwitch && !leftScale) {
			_autonSelected = 9;
		} 
*/		SmartDashboard.putNumber("Auto selected: ",_autonSelected);

		initDrivePID();
		_lifter.init();
	}
	
	public void run() {
		if(firstTime) {
	//		_lifter.calibrate();
			firstTime = false;
		}
		/*if(!leftSwitch)
		{
			moveD(93);
			//liftUp(3);
			pause(2);
			_sheeder.shoot();
			pause(2);
			_sheeder.stop();
		}
		else if(leftSwitch)
		{
			moveD(93);
		}*/
		
		moveD(130);
		
//		turnRight(90);
		
/*		switch(_autonSelected){
		case 0: //test
			move(20);
			turnLeft(6*Math.PI);
			turnRight(6*Math.PI);
			move(-20);
			_autonSelected = 42;
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
			_autonSelected = 42;
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
			_autonSelected = 42;
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
			_autonSelected = 42;
			break;
		case 4: //Starting: L, switch R + scale R, Starting R, switch L + scale L
			moveD(160);
			_autonSelected = 42;
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
			_autonSelected = 42;
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
			_autonSelected = 42;
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
			_autonSelected = 42;
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
			_autonSelected = 42;
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
			_autonSelected = 42;
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
			_autonSelected = 42;
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
			_autonSelected = 42;
			break;
		case 42: break;
		default: break;
		}
		_speedTimer.stop();
*/		
		_leftPID.disable();
		_rightPID.disable();
	}
	
	public void turnLeft(double angle) {
		turning = true;
		_leftTurningPID.enable();
		_rightTurningPID.enable();
		_leftPID.disable();
		_rightPID.disable();
		initAngle = _gyro.getYaw();
		_leftTurningPID.setPID(-0.04, 0, 0);//tune
		_rightTurningPID.setPID(-0.04, 0, 0);
		_leftTurningPID.setSetpoint(initAngle + angle);
		_rightTurningPID.setSetpoint(initAngle - angle);
		SmartDashboard.putNumber("Gyro Turn", finalAngle - initAngle);
		waitForPID();
//		finalAngle = _gyro.getAngle();
	}
	
	public void turnRight(double distance) {
		turnLeft(-distance);
	}
	
	public void move(double distance) {
		turning = false;
		_leftTurningPID.disable();
		_rightTurningPID.disable();
		_leftPID.enable();
		_rightPID.enable();
		_leftPID.setPID(-0.006 ,0, 0);
		_rightPID.setPID(-0.006 ,0, 0);
		_leftPID.setSetpoint(_leftEncoder.getDistance() - distance);
		_rightPID.setSetpoint(_rightEncoder.getDistance() - distance);
	}
	public void moveD(double distance) {
		move(distance);
		waitForPID();
	}
	
	public void lift(int position) {
		_lifter.autoMoveTo(position);
	}
	
	public void liftUp(double time) {
		_liftTimer.reset();
		_liftTimer.start();
		while(_liftTimer.get()<time && _liftTimer.get() > 0 && ! DriverStation.getInstance().isOperatorControl() ) 
		{
			_lifter.controlledMove(-.15);
		}
		while((_liftTimer.get() > time || _liftTimer.get()<=0) && ! DriverStation.getInstance().isOperatorControl()) {
			_lifter.controlledMove(0);
			_liftTimer.reset();
			_liftTimer.stop();
		}
	}
	
	public void pause(double time) {
		_autonDelayTimer.reset();
		_autonDelayTimer.start();
		while(_autonDelayTimer.get()<=time && ! DriverStation.getInstance().isOperatorControl() ) {;}
		_autonDelayTimer.stop();
	}
		
	public boolean isErrorGood() {
		if(turning) {
			return Math.abs(_leftPID.getError()) < allowableErrorDistance && Math.abs(_rightPID.getError()) < allowableErrorDistance;
		}
		else {
			return Math.abs(_leftTurningPID.getError()) < allowableErrorAngle && Math.abs(_rightTurningPID.getError()) < allowableErrorAngle;
		}
	}
	
	public void waitForPID() {
		int escCount = 0;
		while (!DriverStation.getInstance().isOperatorControl()) {
			if((Math.abs(_leftEncoder.getRate()) < speedThresh && Math.abs(_rightEncoder.getRate()) < speedThresh) && isErrorGood()) {
				escCount++;
			} else {
				escCount = 0;
			}
			if(escCount > 6) {
				break;
			}
		}
	}
}
