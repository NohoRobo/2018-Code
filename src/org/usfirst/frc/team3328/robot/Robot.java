package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.networking.NetworkTablesTargetProvider;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksAgitator;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksArm;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksClimber;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksDriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksFeeder;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksHotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.SteamWorksShooter;
import org.usfirst.frc.team3328.robot.utilities.ADIS16448_IMU;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.DriveEncoders;
import org.usfirst.frc.team3328.robot.utilities.DriveTalons;
import org.usfirst.frc.team3328.robot.utilities.PID;
import org.usfirst.frc.team3328.robot.utilities.REVDigitBoard;
//import org.usfirst.frc.team3328.robot.utilities.ShooterTalons;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox;
import org.usfirst.frc.team3328.robot.utilities.Tracking;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;

import com.ctre.CANTalon;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import states.StateMachine;
import states.StateMachine.Modes;
//hi
public class Robot extends IterativeRobot {
	CameraServer stream;
	UsbCamera usbCam;
	Teleop telop;
	StateMachine auto;
	Controller xbox;
	PID pid;
	boolean autoActive = false;
	
	@Override
	public void robotInit() {
		stream = CameraServer.getInstance();
		usbCam = stream.startAutomaticCapture();
		xbox = new SteamWorksXbox(1);
		pid = new PID(8 ,0, 1);
		telop = new Teleop(
				new SteamWorksDriveSystem(
					new DriveEncoders(
						new Encoder(0,1),
						new Encoder(2,3)),
					new DriveTalons(
						new Talon(0),
						new Talon(1),
						new Talon(2),
						new Talon(3)),
				new SteamWorksShooter(
					new Encoder(4,5),
					new CANTalon(1),  
					new CANTalon(2),
					new SteamWorksHotelLobby(
						new CANTalon(3)),
					new SteamWorksAgitator(
						new Relay(1))),
				new SteamWorksFeeder(
					new Talon(4)),
				new SteamWorksClimber(
					new CANTalon(4)),
				new SteamWorksArm(
					new Servo(5)),
				xbox, //util Omar was here
				new SteamWorksXbox(0)); //drive
		auto = new StateMachine(telop, new SendableChooser<Modes>());
		auto.setMode();
		System.out.println("Mode " + auto.getMode());
	}

	@Override
	public void autonomousInit() {
		auto.setMode();
		System.out.println("Mode " + auto.getMode());
	}

	@Override
	public void autonomousPeriodic() {
		auto.run();
	}

	@Override
	public void teleopPeriodic() {
		telop.init();
		telop.run();
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