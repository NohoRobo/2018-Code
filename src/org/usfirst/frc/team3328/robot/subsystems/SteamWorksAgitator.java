package org.usfirst.frc.team3328.robot.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class SteamWorksAgitator implements Agitator {

	Relay relay;
	
	public SteamWorksAgitator(Relay relay) {
		this.relay = relay;
	}

	@Override
	public void run() {
		relay.set(Value.kReverse);
	}

	@Override
	public void stop() {
		relay.set(Value.kOff);
	}

	@Override
	public void toggle() {
		relay.set((relay.get() == Value.kOff) ? Value.kForward : Value.kOff);
	}
	

}
