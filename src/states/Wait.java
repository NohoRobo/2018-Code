package states;

import edu.wpi.first.wpilibj.Timer;

public class Wait implements RobotState{
	
	Timer timer;
	double limit;
	
	@Override
	public void setValue(double value) {
		limit = value;
		timer = new Timer();
		timer.reset();
		timer.start();
	}

	@Override
	public boolean run() {
		if (timer.get() < limit){
			return false;
		}
		
		return true;
	}

}
