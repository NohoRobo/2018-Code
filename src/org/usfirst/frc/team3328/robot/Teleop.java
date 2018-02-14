package org.usfirst.frc.team3328.robot;


import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.Lift;
import org.usfirst.frc.team3328.robot.subsystems.Sheeder;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;
//import org.usfirst.frc.team3328.robot.utilities.PowerUpXbox.Buttons;



//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	
	DriveSystem drive;
	Sheeder sheed;
	Lift lift;
	Controller utilXbox;
	Controller driveXbox;
	
	
	public Teleop(DriveSystem drive, Sheeder sheed, Lift lift,
				  Controller utilXbox, Controller driveXbox){
		this.drive = drive;
		this.sheed = sheed;
		this.lift = lift;
		this.utilXbox = utilXbox;
		this.driveXbox = driveXbox;
	}
	
	public void run(){
		//driving
		if (driveXbox.getButtonRelease(Buttons.A))
			drive.fullSpeed();
	
		if (driveXbox.getButtonRelease(Buttons.B))
			drive.thirdSpeed();
			
		drive.controlledMove(driveXbox.getX(), driveXbox.getY());

		//lift
/*		if (utilXbox.getButtonRelease(Buttons.START)) 
			lift.toGround();
		
		if (utilXbox.getButtonRelease(Buttons.X)) 
			lift.toSwitch(); 
		
		if (utilXbox.getButtonRelease(Buttons.A)) 
			lift.toScaleLow();
		
		if (utilXbox.getButtonRelease(Buttons.B)) 
			lift.toScaleMid();
		
		if (utilXbox.getButtonRelease(Buttons.Y)) 
			lift.toScaleHigh();
		
		lift.controlledMove(utilXbox.getVerticalMovement());
		
		//sheeder
		if (utilXbox.getButtonRelease(Buttons.LBUMP))
			sheed.controlFeeder();
*/
		
		/*
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
*///		}
//		shoot.updateShoot();
//		shoot.isMax();
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
	
	public Sheeder getShooter(){
		return sheed;
	}
	
//	public Climber getClimber(){
//		return climb;
//	}
	
	public Controller getXbox(){
		return utilXbox;
	}
}
