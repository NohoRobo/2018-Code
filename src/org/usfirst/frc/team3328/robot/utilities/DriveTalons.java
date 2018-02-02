package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.SpeedController;

public class DriveTalons {

	private SpeedController fl; 
	private SpeedController fr;
	private SpeedController bl; 
	private SpeedController br;
	
	public DriveTalons(SpeedController fl, SpeedController fr,
					   SpeedController bl, SpeedController br){
		this.fl = fl;
		this.fr = fr;
		this.bl = bl;
		this.br = br;
	}
	
	public SpeedController getTalon(){
		return fl;
	}
	
	public double getfl(){
		return fl.get();
	}
	
	public void setfl(double speed){
		fl.set(speed);
	}
	
	public double getfr(){
		return fr.get();
	}
	
	public void setfr(double speed){
		fr.set(speed);
	}
	
	public double getbl(){
		return bl.get();
	}
	
	public void setbl(double speed){
		bl.set(speed);
	}
	
	public double getbr(){
		return br.get();
	}
	
	public void setbr(double speed){
		br.set(speed);
	}
	
	//sets the right set of talons to the same value
	//the right side is inverted because of how it was wired
	public void right(double speed){
		speed = -speed;
		setfr(speed);
		setbr(speed);
	}
	
	//sets the left set of talons to the same value
	public void left(double speed){
		setfl(speed);
		setbl(speed);
	}
	
	//stops motors
	public void stop(){
		setfl(0);
		setbl(0);
		setfr(0);
		setbr(0);
	}
	
}
