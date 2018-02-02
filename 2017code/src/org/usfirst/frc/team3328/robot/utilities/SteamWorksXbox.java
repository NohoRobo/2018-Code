package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.XboxController;

public class SteamWorksXbox implements Controller {
	
	XboxController xbox;

	boolean[] button = new boolean[10];
	
	public enum Buttons {A(1), B(2), X(3), Y(4), LBUMP(5), RBUMP(6); 
		public int value;
		Buttons(int value) {
			this.value = value;
		}
	}
	
	public SteamWorksXbox(int channel){
		xbox = new XboxController(channel);
	}
	
	@Override
	public double getX(){
		return xbox.getRawAxis(3) - xbox.getRawAxis(2);
	}
	
	@Override
	public double getRightTrigger(){
		return xbox.getRawAxis(3);
	}
	
	@Override
	public double getLeftTrigger(){
		return xbox.getRawAxis(2);
	}
	
	@Override
	public double getY(){
		if (Math.abs(xbox.getRawAxis(0)) > .15){
			return xbox.getRawAxis(0);
		}
		return 0;
	}
	
	@Override
	public boolean getButtonRelease(Buttons but){
		int num = but.value;
		if (xbox.getRawButton(num) &&  !button[num]){
			button[num] = xbox.getRawButton(num);
			return true;
		}
		button[num] = xbox.getRawButton(num);
		return false;
	}
	
	@Override
	public boolean getButtonPress(Buttons but){
		int num = but.value;
		return xbox.getRawButton(num);
	}
	
	public double getPad(){
		return xbox.getPOV();
	}
	
	
}