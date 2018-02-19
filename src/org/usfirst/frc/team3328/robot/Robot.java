package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.autocontrollers.AutoForward;
import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.Lift;
import org.usfirst.frc.team3328.robot.subsystems.PowerUpDriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.PowerUpLift;
import org.usfirst.frc.team3328.robot.subsystems.PowerUpSheeder;
import org.usfirst.frc.team3328.robot.subsystems.Sheeder;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	ControllerLogic logic;
	Auton auto;

	DriveSystem driveSystem;
	Lift lift;
	Sheeder sheeder;

	Encoder leftEncoder;
	Encoder rightEncoder;
	VictorSP left;
	VictorSP right;

	CameraServer stream;
	UsbCamera usbCam;

	PIDController leftPID;
	PIDController rightPID;

	boolean firstTimeRunning = true;

	@Override
	public void robotInit() {
		//		stream = CameraServer.getInstance();
		//		usbCam = stream.startAutomaticCapture();
		rightEncoder = new Encoder(2,3);
		leftEncoder = new Encoder(0,1, true);

		left = new VictorSP(0);
		right = new VictorSP(1);
		right.setInverted(true);

		double KP = 0;
		double KI = 0;
		double KD = 0;

		leftPID = new PIDController(KP, KI, KD, //tuned on carpet: (-0.02, 0, -0.0001)
				leftEncoder, left);
		rightPID = new PIDController(KP, KI, KD, //tuned on carpet: (-0.02, 0, -0.0001)
				rightEncoder, right);

		driveSystem = new PowerUpDriveSystem(left, right);
		lift = new PowerUpLift(0.02, 0.001, 0,
				new TalonSRX(3));
		sheeder = new PowerUpSheeder(
				new PWMVictorSPX(4), 
				new PWMVictorSPX(5) 
//				new DoubleSolenoid(0,1)
				);

		logic = new ControllerLogic(
				driveSystem,
				sheeder, 
				lift,
				new PowerUpXbox(0),
				new PowerUpXbox(1));

		auto = new Auton(leftPID, rightPID, 
				leftEncoder, rightEncoder, firstTimeRunning);

		lift.init();
	}

	@Override
	public void autonomousInit() {
		auto.init();
	}

	@Override
	public void autonomousPeriodic() {		
		auto.run();
	}  

	@Override
	public void teleopPeriodic() {
//		System.out.printf("RightDistance\t%.3f\t|\tLeftDistance\t%.3f\n", rightEncoder.getDistance(), leftEncoder.getDistance());
		if(firstTimeRunning) {
			rightPID.disable();
			leftPID.disable();
			firstTimeRunning = false;
		}
		logic.run();
		System.out.println("Lift Encoder Value " + lift.getEncoderValue());	
	}

	@Override
	public void testPeriodic() {
		firstTimeRunning = true;
	}


}