package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.subsystems.Arm;
import org.usfirst.frc.team3328.robot.subsystems.Climber;
import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.Feeder;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	
	DriveSystem drive;
	Shooter shoot;
	Feeder feed;
	Climber climb;
	Controller utilXbox;
	Controller driveXbox;
	Arm arm;
	Tracking track;
	boolean firstTime = true;
	
	public Teleop(DriveSystem drive, Shooter shoot, Feeder feed, Climber climb,
				Arm arm, Controller utilXbox, Controller driveXbox){
		this.drive = drive;
		this.shoot = shoot;
		this.feed = feed;
		this.climb = climb;
		this.utilXbox = utilXbox;
		this.driveXbox = driveXbox;
		this.arm = arm;
		this.track = this.drive.getTrack();
	}
	
	public void init(){
		if (firstTime){
			shoot.stopShoot();
		}
		firstTime = false;
	}
	
	public void run(){
		if (driveXbox.getButtonRelease(Buttons.B)){
			track.toggleLight();
		}
		//tracking
		if (driveXbox.getButtonRelease(Buttons.A)){
			track.toggleTurn();
		}
		if (driveXbox.getButtonRelease(Buttons.X)){
			track.toggleMove();
		}
		//driving
//		if (driveXbox.getButtonRelease(Buttons.LBUMP)){
//			drive.upRestraint();
//		}
//		if (driveXbox.getButtonRelease(Buttons.RBUMP)){
//			drive.downRestraint();
//		}
		drive.controlledMove(driveXbox.getX(), driveXbox.getY());
		if (driveXbox.getButtonRelease(Buttons.LBUMP)){
			feed.controlFeeder();
		}
		//shooting
		if (driveXbox.getButtonRelease(Buttons.RBUMP)){
			if (shoot.isShooting()){
				shoot.stopShoot();
			}else{
				shoot.startShoot();
			}
		}
		shoot.updateShoot();
		shoot.isMax();
//		if (utilXbox.getButtonRelease(Buttons.X)){
//			if (shoot.isLoading()){
//				shoot.stopLoad();
//			}else{
//				shoot.startLoad();
//			}
//		}
		//feeding
//		if (utilXbox.getButtonRelease(Buttons.B) || utilXbox.getButtonRelease(Buttons.Y)){
//			feed.controlFeeder();
//		}
//		//Extending
//		if(utilXbox.getButtonRelease(Buttons.A)){
//			if (arm.isExtended()){
//				arm.rectract();
//			}else{
//				arm.extend();
//			}
//		}
//		//climbing
//		if (utilXbox.getButtonPress(Buttons.LBUMP)){
//			climb.controlClimber(-utilXbox.getLeftTrigger());
//		}else {
//			climb.controlClimber(utilXbox.getRightTrigger());
//		}
	}
	
	public DriveSystem getDrive(){
		return drive;
	}
	
	public Shooter getShooter(){
		return shoot;
	}
	
	public Feeder getFeeder(){
		return feed;
	}
	
	public Climber getClimber(){
		return climb;
	}
	
	public Controller getXbox(){
		return utilXbox;
	}
}
