package org.usfirst.frc.team3328.robot;

//import org.usfirst.frc.team3328.robot.networking.NetworkTablesTargetProvider;
//import org.usfirst.frc.team3328.robot.subsystems.PowerUpDriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.NewPowerUpDriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.PowerUpLift;
//import org.usfirst.frc.team3328.robot.subsystems.PowerUpFeeder;
//import org.usfirst.frc.team3328.robot.subsystems.PowerUpLift;
//import org.usfirst.frc.team3328.robot.subsystems.PowerUpSheeder;
//import org.usfirst.frc.team3328.robot.subsystems.PowerUpShooter;
//import org.usfirst.frc.team3328.robot.subsystems.SteamWorksAgitator;
//import org.usfirst.frc.team3328.robot.subsystems.SteamWorksArm;
//import org.usfirst.frc.team3328.robot.subsystems.SteamWorksClimber;
//import org.usfirst.frc.team3328.robot.subsystems.SteamWorksDriveSystem;
//import org.usfirst.frc.team3328.robot.subsystems.SteamWorksFeeder;
//import org.usfirst.frc.team3328.robot.subsystems.SteamWorksHotelLobby;
//import org.usfirst.frc.team3328.robot.subsystems.SteamWorksShooter;
import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.PID;
import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox;
//import org.usfirst.frc.team3328.robot.utilities.SheederSpeedControllers;
//import org.usfirst.frc.team3328.robot.utilities.REVDigitBoard;
//import org.usfirst.frc.team3328.robot.utilities.ShooterTalons;
//import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox;
//import org.usfirst.frc.team3328.robot.utilities.Tracking;
//import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;

//import com.ctre.CANTalon;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.PWMTalonSRX;
//import edu.wpi.first.wpilibj.PWMVictorSPX;
//import edu.wpi.first.wpilibj.Relay;
//import edu.wpi.first.wpilibj.Servo;
//import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
//import edu.wpi.first.wpilibj.command.Command;
	//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
	//import states.StateMachine;
	//import states.StateMachine.Modes;

public class Robot extends IterativeRobot {
	ControllerLogic logic;
	CameraServer stream;
	UsbCamera usbCam;
	Teleop telop;
		//StateMachine auto;
	Controller xbox;
	PID pid;
	boolean autoActive = false;
	
	@Override
	public void robotInit() {
//		stream = CameraServer.getInstance();
//		usbCam = stream.startAutomaticCapture();
//		xbox = new SteamWorksXbox(1);
		pid = new PID(8 ,0, 1);
		logic = new ControllerLogic(
				new NewPowerUpDriveSystem(
				    new DriveEncoders(
					    new Encoder(0,1),
					    new Encoder(2,3)),
				    new DriveTalons(
					    new VictorSP(2),
					    new VictorSP(0),
					    new VictorSP(3),
					    new VictorSP(1)),
				    new ADIS16448_IMU(), pid), 
				new PowerUpXbox(0),
				new PowerUpLift(
						new Encoder(0,1),
						new ));
//		auto = new StateMachine(telop, new SendableChooser<Modes>());
//		auto.setMode();
//		System.out.println("Mode " + auto.getMode());
	}

	@Override
	public void autonomousInit() {
//		auto.setMode();
//		System.out.println("Mode " + auto.getMode());
	}

	@Override
	public void autonomousPeriodic() {
//		auto.run();
	}

	@Override
	public void teleopPeriodic() {
		logic.run();
	}

	@Override
	public void testPeriodic() {
//		if (xbox.getButtonRelease(Buttons.A)){
//			autoActive = !autoActive;
//		}
//		if (xbox.getButtonPress(Buttons.RBUMP)) {
//			if (xbox.getButtonPress(Buttons.X)){
//				pid.adjustP(-.02);
//				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
//			}
//			if (xbox.getButtonPress(Buttons.Y)){
//				pid.adjustI(-.01);
//				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
//			}
//			if (xbox.getButtonPress(Buttons.B)){
//				pid.adjustD(-.02);
//				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
//			}
//		}
//		else if (xbox.getButtonPress(Buttons.LBUMP)){
//			if (xbox.getButtonRelease(Buttons.X)){
//				pid.adjustP(-.02);
//				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
//			}
//			if (xbox.getButtonRelease(Buttons.Y)){
//				pid.adjustI(-.01);
//				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
//			}
//			if (xbox.getButtonRelease(Buttons.B)){
//				pid.adjustD(-.02);
//				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
//			}
//		}else{
//			if (xbox.getButtonRelease(Buttons.X)){
//				pid.adjustP(.02);
//				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
//			}
//			if (xbox.getButtonRelease(Buttons.Y)){
//				pid.adjustI(.01);
//				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
//			}
//			if (xbox.getButtonRelease(Buttons.B)){
//				pid.adjustD(.02);
//				System.out.printf("P: %05.2f|I: %05.2f|D: %05.2f\n", pid.getP(), pid.getI(), pid.getD());
//			}
//		}
//		if (autoActive){
//			auto.run();
//		}else{
//			auto.reset();
//			pid.reset();
//		}
	}
}