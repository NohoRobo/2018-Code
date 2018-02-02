package org.usfirst.frc.team3328.robot.networking;

public class NetworkTablesTargetProvider implements TargetProvider{
	
	Target target = new SteamWorksTarget();
	
	Listener listener = new Listener("listenerThread", target);
	
	@Override
	public Target getTarget() {
		return target;
	}

	
	
}
