package org.usfirst.frc.team3328.robot.utilities;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWMVictorSPX;

public class LiftPID extends Thread{
	//desired values
	public volatile double desiredValue;
	Encoder encoder = new Encoder(0,1);//change port\
	DigitalInput limitSwitch = new DigitalInput(1);//change port
	PWMVictorSPX victor = new PWMVictorSPX(1);
	double KP = 0.1;
	double KI = 0.0;
	double KD = 0.0;
		
	//intermediate values for PID
	double proportional;
	double integral;
	double derivative;
	
	double integralSum = 0;
	
	//error variables
	double error = 0;
	double errorOld = 0;
	
	//time storage variables
	double timeChange;
	long lastTime;
	
	//{left drive value, right drive value}
	double motorValue;
	
	public LiftPID(double KP, double KI, double KD, Encoder encoder, DigitalInput limitSwitch, PWMVictorSPX victor) {
		this.KP = KP;
		this.KI = KI;
		this.KD = KD;
		this.encoder = encoder;
		this.limitSwitch = limitSwitch;
		this.victor = victor;
	}
	
	public void run() {
		while(true) {
			victor.set(getMotorValue());
			
		}
	}
	
	public double getMotorValue(){
		//calculate change in time since last call of this function
		if(limitSwitch.get()) {
			encoder.reset();
		}
		error = desiredValue - encoder.get();
		long now = System.nanoTime();
		timeChange = (double)(now - lastTime);
		timeChange /= 1000000000;
				
//		System.out.println(error);
		
		
		//add error to integral sums
		integralSum += error*timeChange;
				
		//set proportional values
		proportional = KP*error;
		
		//set integral values
		integral = KI*integralSum;
		
		//calculate change in time
		derivative = KD*(errorOld - error) / timeChange;
		
		//integral reset for small or large errors
		if(Math.abs(error)<2) {
			integralSum=0;
		}
		else if(Math.abs(error)>500) {//change from 500
			integralSum=0;
		}
				
//		System.out.println("Iout " + iOut);
		
		//record the time that this function was run
		lastTime = now;
		
		//set drive values
		motorValue = proportional+integral+derivative;
		return motorValue;
	}
}
 