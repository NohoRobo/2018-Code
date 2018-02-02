package org.usfirst.frc.team3328.robot.networking;


public class SteamWorksTarget implements Target {
	//private AtomicReference<Double> pixel; 
	private double pixel;
	private double distance;
	private boolean targetFound = false;
	
	@Override
	public void setPixel(double pixel){
		//System.out.println("set pixel to " + ang);
		//pixel.set(ang);
		this.pixel = pixel;
	}
	
	@Override
	public double getPixel(){
		//return pixel.get();
		return pixel;
	}
	
	@Override
	public void setDistance(double distance){
		this.distance = distance;
	}
	
	@Override
	public double getDistance(){
		return distance;
	}
	
	@Override
	public void setFoundRect(boolean targetFound){
		this.targetFound = targetFound;
	}
	
	@Override
	public boolean foundTarget(){
		return targetFound;
	}
	
	
	@Override
	public void printValues(){
		System.out.printf("Pixel: %06.2f| Distance: %06.2f| Status: %b\n", getPixel(), getDistance(), foundTarget());
	}

	@Override
	public void setTime(long stamp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}
}
