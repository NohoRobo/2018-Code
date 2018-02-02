package org.usfirst.frc.team3328.robot.utilities;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.usfirst.frc.team3328.robot.networking.Target;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Tracking {
	
	Relay spike;
	Target target;
	Controller utilXbox;
	PID pidAngle;
	PID pidDistance;
	public enum Stages {TRACK, MOVE};
	Stages stage;
	List<Stages> stages = Arrays.asList(Stages.TRACK, Stages.MOVE, Stages.TRACK);
	private int iteration = 0;
	private double pixel;
	private double distance;
	private double move = 0;
	private double turn = 0;
	private double pixelGoal = 290;
	private double distanceGoal = 140;
	private double pixelDeadZone = 25;
	private double distanceDeadZone = 10;
	private boolean tracking = false;
	private boolean moving = false;
	private boolean turning = false;
	private boolean started = false;
	
	
	public Tracking(Target target, Relay spike, PID pidAngle, PID pidDistance){
		this.target = target;
		this.pidAngle = pidAngle;
		this.pidDistance = pidDistance;
		this.spike = spike;
		SmartDashboard.putBoolean("tracking", tracking);
	}
	
	public void setGoal(int target){
		pixelGoal = target;
	}
		
	public void toggleTracking(){
		if (tracking){
			stopTracking();
		}else{
			tracking = true;
			moving = true;
			spike.set(Value.kForward);
			SmartDashboard.putBoolean("tracking", tracking);
		}
//		if (spike.get() == Value.kOff){
//			spike.set(Value.kForward);
//		}else{
//			spike.set(Value.kOff);
//		}
		
	}
	
	public void toggleMove(){
		tracking = !tracking;
		turning = !turning;
	}
	
	public void toggleTurn(){
		tracking = !tracking;
		moving = !moving;
	}
	
	public void toggleLight(){
		if (spike.get() == Value.kForward){
			spike.set(Value.kOff);
		}else{
			spike.set(Value.kForward);
		}
	}
	
	public void stopTracking(){
		tracking = false;
		SmartDashboard.putBoolean("tracking", tracking);
		//spike.set(Value.kOff);
		pidAngle.reset();
		pidDistance.reset();
	}
	
	public void updateTracking(boolean stopped){
		pixel = target.getPixel();
		distance = target.getDistance();
		double pixelDisplacement = Math.abs(pixel - pixelGoal);
		double distanceDisplacement = Math.abs(distance - distanceGoal);
//		if (target.foundTarget() && iteration < 3){
//			stage = stages.get(iteration);
//			if (stage == Stages.TRACK){
//				turn = updateTurn(pixel - pixelGoal);
//				move = 0;
//				if (pixelDisplacement < pixelDeadZone && stopped){
//					iteration++;
//					if (iteration == 3){
//						stopTracking();
//					}
//				}
//			}else if (stage == Stages.MOVE){
//				move = updateMove(distance - distanceGoal);
//				turn = 0;
//				if (distanceDisplacement < distanceDeadZone && stopped){
//					iteration++;
//				}
//			}
			//System.out.println("pixel " + pixel);
			if (moving){
				if (distanceDisplacement > distanceDeadZone){
					started = true;
					move = updateMove(distance - distanceGoal);
				}else if (true){
					moving = false;
					tracking = false;
					move = 0;
					started = false;
					//turning = true;
				}
			}
			if (turning){
				if (pixelDisplacement > pixelDeadZone){
					started = true;
					turn = updateTurn(pixel - pixelGoal);
				}else if (true){
					tracking = false;
					turning = false;
					turn = 0;
					started = false;
				}
			}
//			SmartDashboard.putNumber("pixel", pixel);
//			if (pixelDisplacement > pixelDeadZone && distanceDisplacement > distanceDeadZone){
//				turn = updateTurn(pixel - pixelGoal);
//				move = updateMove(distance - distanceGoal);
//			}else{
//				turn = 0;
//				move = 0;
//				stopTracking();
//			}
//		}else{
//			iteration = 0;
//		}
	}
	
	public boolean getTracking(boolean stopped){
		updateTracking(stopped);
//		System.out.printf("Status: %b |Tracking: %b |Pixel %f |Distance: %.2f\n", 
//				target.foundTarget(), tracking, pixel, target.getDistance());
		return tracking;
	}	

	public double updateMove(double error) {
//		pidDistance.setError(-error);
//		return pidDistance.getCorrection();
		if (error > 0){
			return -.2;
		}else{
			return .2;
		}
	}
	
	public double getMove(){
		return move; 
	}
	
	public double updateTurn(double error){
//		pidAngle.setError(error);
//		return pidAngle.getCorrection();
		if (error > 0){
			return .22;
		}else{
			return -.22;
		}
	}
	
	public double getTurn(){
		return turn;
	}
	
}
