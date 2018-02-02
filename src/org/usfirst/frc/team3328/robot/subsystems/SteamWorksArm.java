package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SteamWorksArm implements Arm{
	
	Servo servo;
	double startAngle = 0;
	boolean isOpen = false;
	
	public SteamWorksArm(Servo servo){
		this.servo = servo;
		this.servo.setAngle(startAngle);
		SmartDashboard.putBoolean("Door Open", isOpen);
	}
	
	@Override
	public void extend() {
		servo.setAngle(100);
		isOpen = true;
		SmartDashboard.putBoolean("Door Open", isOpen);
	}

	@Override
	public void rectract() {
		servo.setAngle(startAngle);
		isOpen = false;
		SmartDashboard.putBoolean("Door Open", isOpen);
	}

	@Override
	public boolean isExtended() {
		return servo.get() > startAngle;
	}

	
	
}
