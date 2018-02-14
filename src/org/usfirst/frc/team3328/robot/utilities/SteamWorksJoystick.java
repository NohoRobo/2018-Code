package org.usfirst.frc.team3328.robot.utilities;

import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;

import edu.wpi.first.wpilibj.Joystick;

public class SteamWorksJoystick implements Controller {
	
	Joystick joy;

	//array to store previous value of buttons
	boolean[] button = new boolean[10];
	
	//instantiates the joystick
	public SteamWorksJoystick(int channel){
		joy = new Joystick(channel);
	}
	
	//returns the x axis value for the joystick
	@Override
	public double getX(){
		return joy.getX();
	}
	
	//returns the y axis for the joystick
	@Override
	public double getY(){
		return joy.getY();
	}
	
	//returns true when the button with the index "num" has been released
	@Override
	public boolean getButtonRelease(Buttons but){
		int num = but.value;
		if (joy.getRawButton(num) &&  !button[num]){
			button[num] = joy.getRawButton(num);
			return true;
		}
		button[num] = joy.getRawButton(num);
		return false;
	}
	
	//return true when button "num" 
	@Override
	public boolean getButtonPress(Buttons but){
		int num = but.value;
		return joy.getRawButton(num);
	}

	@Override
	public double getRightTrigger() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLeftTrigger() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getVerticalMovement() {
		// TODO Auto-generated method stub
		return 0;
	}

	
}