package org.usfirst.frc.team3328.robot.networking;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class Listener implements Runnable, ITableListener {
	
	SpeedController talon;
	Thread listener;
	Target target;
	NetworkTable table;
	double angle = 0;
	double distance = 0;
	double time = 0;
	
	public Listener(){
	}
	
	public Listener(String name, Target target){
		listener = new Thread(this, name);
		this.target = target;
//		NetworkTable.shutdown();
//		NetworkTable.setClientMode();
//		NetworkTable.initialize();
//		NetworkTable.setIPAddress("10.33.28.4");
		table = NetworkTable.getTable("JetsonData");
		table.addTableListener(this);
		System.out.printf("Created thread %s, %s, key: %s\n", name, table.isConnected(), table.getKeys().toString());
		listener.start();
	}
	
	@Override
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {
		target.setFoundRect(true);
		//System.out.printf("Pixels: %f |Distance: %f\n", table.getNumber("pixels", 0.0), table.getNumber("distance", 0.0));
		SmartDashboard.putNumber("Pixel", table.getNumber("pixels", 0.0));
		SmartDashboard.putNumber("Distance", table.getNumber("distance", 0.0));
		if (key.equals("pixels")){
			time = System.currentTimeMillis();
			target.setPixel(table.getNumber("pixels", 0.0)); 
		}
		if (key.equals("distance")){
			target.setDistance(table.getNumber("distance", 0.0));
		}
	}

	public void run(){
		for(;;){
			if (System.currentTimeMillis() - time > 1000){
				target.setFoundRect(false);
			}
		}
	}
}
