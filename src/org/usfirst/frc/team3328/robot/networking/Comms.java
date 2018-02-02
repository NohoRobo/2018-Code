package org.usfirst.frc.team3328.robot.networking;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Comms {

	NetworkTable table;
	double x = 0, y = 0;
	boolean connected = false;
	
	public Comms(){
		table = NetworkTable.getTable("JetsonData");
		System.out.println("Created table " + table);
	}
	
	public void update(){
		if (!connected && table.isConnected()){
			connected = true;
			System.out.println("Table " + table + " connected");
		}
			table.putNumber("angle", x++);
			table.putNumber("distance", y++);
	}
	
	
}