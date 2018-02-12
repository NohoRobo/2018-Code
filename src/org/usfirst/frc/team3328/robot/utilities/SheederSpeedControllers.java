package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.PWMVictorSPX;

public class SheederSpeedControllers { //sheeder is the name for shooter and feeder
	
	PWMVictorSPX ls;
	PWMVictorSPX rs;
	
	public SheederSpeedControllers(PWMVictorSPX leftSheeder, PWMVictorSPX rightSheeder){
		this.ls = leftSheeder;
		this.rs = rightSheeder;
	}
	
	public double getLeft(){
		return ls.get();
	}
	
	public void setLeft(double speed){
		ls.set(speed);
	}
	
	public double getRight(){
		return rs.get();
	}
	
	public void setRight(double speed){
		rs.set(speed);
	}
	
	public void setSheeder(double speed){
		setLeft(speed);
		setRight(-speed); //idk which one will be which direction
	}
	
	public void stop(){
		setLeft(0);
		setRight(0);
	}
}
