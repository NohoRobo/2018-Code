package org.usfirst.frc.team3328.robot.utilities;

public class PID2{
	//desired values
	double desiredValue;
	
	
	double KP;
	double KI;
	double KD;
		
	//intermediate values for PID
	double proportional;
	double integral;
	double derivative;
	
	//integral sum variable
	double integralSum = 0;
	
	//error variables
	double error = 0;
	double errorOld = 0;
	
	//time storage variables
	double timeChange;
	long lastTime;
	
	//{left drive value, right drive value}
	double motorValue;
	
	public PID2(double P, double I, double D){
		KP = P;
		KI = I;
		KD = D;
	}
	
	public void setP(double newP){
		KP = newP;
	}
	
	public void setI(double newI){
		KI = newI;
	}
	
	public void setD(double newD){
		KD = newD;
	}
	
	public double getP(){
		return KP;
	}
	
	public double getI(){
		return KI;
	}
	
	public double getD(){
		return KD;
	}
	
	public double setSensorValue(double sensorValue) {
		error = desiredValue-sensorValue;
		return error;
	}
	
	public void setDesiredValue(double desiredValue) {
		this.desiredValue = desiredValue;
	}
	
	public double getMotorValue(){
		//calculate chang in time since last call of this function
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
 