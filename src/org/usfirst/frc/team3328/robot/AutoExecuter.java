package org.usfirst.frc.team3328.robot;

import org.usfirst.frc.team3328.robot.utilities.DrivePID;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoExecuter {
	
	DrivePID pid;
	SmartDashboard board;
	
	int mode = 1;
	
	String modeName;
	
	public void run() {
		switch (mode) {
			case 1: modeName = "CrossLine"; 
					pid.setDesiredDistance(400); 
		}
		SmartDashboard.putString("Mode", modeName);
	}
}
