package org.usfirst.frc.team3328.robot.subsystems;


import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Encoder;

public class SteamWorksShooter implements Shooter {

	HotelLobby belt;
	Agitator agitator;
	Encoder encoder;
	private double speed = .8;
	private double maxRate = 9000;
	boolean active = false;
	@SuppressWarnings("deprecation")
	private CANTalon t1;
	private CANTalon t2;
	double rampRate = 12;
	boolean stoppedLoad = true;
	
	public SteamWorksShooter(Encoder encoder, CANTalon t1, CANTalon t2, HotelLobby belt, Agitator agitator){
		this.encoder = encoder;
		this.belt = belt;
		this.agitator = agitator;
		this.t1 = t1;
		t1.changeControlMode(CANTalon.TalonControlMode.Follower);
		t1.set(t2.getDeviceID());
		this.t2 = t2;
		this.t2.setVoltageRampRate(rampRate);
	}
	
	@Override
	public HotelLobby getBelt(){
		return belt;
	}
	
	@Override
	public boolean isEmpty(){
		return false;
	}
	
	@Override
	public boolean isShooting(){
		return t2.get() != 0;
	}
	
	@Override
	public void spinUp(){
		t2.set(speed);
	}
	
	@Override
	public void startShoot(){
		//t2.changeControlMode(TalonControlMode.Speed);
		t2.set(speed);
		startLoad();
	}
	
	@Override
	public void stopShoot(){
		t2.set(0);
		stopLoad();
	}

	@Override
	public void startLoad() {
		belt.run();
		agitator.run();
		stoppedLoad = false;
	}

	@Override
	public void stopLoad() {
		belt.stop();
		agitator.stop();
		stoppedLoad = true;
	}

	@Override
	public boolean isLoading() {
		return belt.isRunning();
	}
	
	@Override
	public void updateShoot(){
//		if (isMax()){
//			startLoad();
//		}

	}
	
	@Override
	public boolean isMax() {
		System.out.println("rate " + t2.getPulseWidthVelocity());
		return t2.getPulseWidthVelocity() > maxRate;
	}

	
}
