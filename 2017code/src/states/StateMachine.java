package states;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.usfirst.frc.team3328.robot.Teleop;
import org.usfirst.frc.team3328.robot.subsystems.DriveSystem;
import org.usfirst.frc.team3328.robot.subsystems.HotelLobby;
import org.usfirst.frc.team3328.robot.subsystems.Shooter;
import org.usfirst.frc.team3328.robot.utilities.Controller;
import org.usfirst.frc.team3328.robot.utilities.IMU;
import org.usfirst.frc.team3328.robot.utilities.SteamWorksXbox.Buttons;
import org.usfirst.frc.team3328.robot.utilities.Tracking;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateMachine {
	
	Constant linelong = new Constant("linelong", 4000);
	Constant lineshort = new Constant("lineshort", 1000);
	Constant shootlong = new Constant("shootlong", 800);
	Constant shootmiddle = new Constant("shootmiddle", 4000);
	Constant shootshort = new Constant("shootshort", 4000);
	Constant gearlong = new Constant("gearlong", 4000);
	Constant gearmiddle = new Constant("gearmiddle", 4000);
	Constant gearshort = new Constant("gearshort", 4000);
	
	int iteration = -1;
	int constant = 0;
	double value;
	boolean newState = true;
	States state;
	Modes choice;
	SendableChooser<Modes> chooser;
	Constant[] constants = new Constant[]{linelong, lineshort, shootlong, shootmiddle, shootshort, gearlong, gearmiddle, gearshort};
	//File stateMachineVariables = new File("C:\\Users\\Anton\\Desktop\\StateMachine Constants.txt");
	

	
	public enum States {WAIT, TURN, ENCODERTURN, MOVE, SPINMOVE, TRACKSHOT, SHOOT, TRACKGEAR, GEAR, STOP};
	public enum Modes {LINELONG, LINEMIDDLE, SHOOTFL, SHOOTFM, SHOOTFR, SHOOTBL, SHOOTBM, SHOOTBR, 
						GEARFL, GEARFM, GEARFR, GEARBL, GEARBM, GEARBR, CUSTOM1, CUSTOM2, NOTHING};
	
	Controller xbox;
	DriveSystem drive;
	Tracking track;
	Shooter shooter;
	HotelLobby belt;
	IMU imu;
	
	Map<States, RobotState> classes = new HashMap<States, RobotState>()/*{
		
		private static final long serialVersionUID = 1368017743131992753L;

	{
		put(States.MOVE, new Move(drive));
		put(States.TURN, new Turn(drive, imu));
		put(States.TRACKSHOT, new TrackShot(drive));
		put(States.SHOOT, new Shoot(shooter, belt));
		put(States.TRACKGEAR, new TrackGear(drive));
		put(States.GEAR, new Gear(drive));
		put(States.STOP, new Stop(drive, shooter));
	}}*/;

	
	List<State> lineLong = Arrays.asList(
			new State(States.MOVE, linelong.value),
			new State(States.STOP, 0));
	List<State> lineMiddle = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.MOVE, lineshort.value),
			new State(States.TURN, -90),
			new State(States.MOVE, linelong.value),
			new State(States.STOP, 0));
	List<State> shootFL = Arrays.asList(
			new State(States.SPINMOVE, shootlong.value * .9),
			new State(States.TURN, 32),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootFM = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.MOVE, shootmiddle.value),
			new State(States.TURN, -90),
			new State(States.MOVE, shootmiddle.value),
			new State(States.TURN, 90),
			new State(States.MOVE, shootshort.value),
			new State(States.TRACKSHOT, 0),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootFR = Arrays.asList(
			new State(States.MOVE, shootmiddle.value),
			new State(States.TURN, 45),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootBL = Arrays.asList(
			new State(States.MOVE, shootmiddle.value),
			new State(States.TURN, 90),
			new State(States.MOVE, shootshort.value),
			new State(States.TRACKSHOT, 0),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootBM = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.MOVE, shootmiddle.value),
			new State(States.TURN, -90),
			new State(States.MOVE, shootmiddle.value),
			new State(States.TURN, 90),
			new State(States.MOVE, shootshort.value),
			new State(States.TRACKSHOT, 0),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> shootBR = Arrays.asList(
			new State(States.SPINMOVE, shootlong.value * .9),
			new State(States.TURN, -47),
			new State(States.SHOOT, 0),
			new State(States.STOP, 0));
	List<State> gearFL = Arrays.asList(
			new State(States.MOVE, gearlong.value),
			new State(States.TURN, 90),
			new State(States.MOVE, gearshort.value),
			new State(States.GEAR, 0));
	List<State> gearFM = Arrays.asList(
			new State(States.MOVE, gearmiddle.value),
			new State(States.STOP, 0));
	List<State> gearFR = Arrays.asList(
			new State(States.MOVE, gearlong.value),
			new State(States.TURN, -90),
			new State(States.MOVE, gearshort.value),
			new State(States.GEAR, 0));
	List<State> gearBL = Arrays.asList(
			new State(States.MOVE, gearlong.value),
			new State(States.TURN, -90),
			new State(States.MOVE, gearshort.value),
			new State(States.GEAR, 0));
	List<State> gearBM = Arrays.asList(
			new State(States.MOVE, gearmiddle.value),
			new State(States.GEAR, 0));
	List<State> gearBR = Arrays.asList(
			new State(States.MOVE, gearlong.value),
			new State(States.TURN, 90),
			new State(States.MOVE, gearshort.value),
			new State(States.GEAR, 0));
	List<State> custom1 = Arrays.asList(
			new State(States.SPINMOVE, 2000),
			new State(States.SHOOT, 0));
	List<State> custom2 = Arrays.asList(
			new State(States.TURN, 90),
			new State(States.WAIT, 3),
			new State(States.TURN, -90),
			new State(States.WAIT, 3),
			new State(States.TURN, 180),
			new State(States.STOP, 0));
	List<State> nothing = Arrays.asList(
			new State(States.STOP, 0));
	
	List<State> mode;
	List<List<State>> modes = Arrays.asList(lineLong, lineMiddle, shootFL, shootFM, shootFR, shootBL, shootBM, shootBR,
											gearFL, gearFM, gearFR, gearBL, gearBM, gearBR, custom1, custom2, nothing);
	
	public StateMachine(Teleop teleop, SendableChooser<Modes> chooser_){
		this.chooser = chooser_;
		xbox = teleop.getXbox();
		drive = teleop.getDrive();
		track = drive.getTrack();
		shooter = teleop.getShooter();
		imu = drive.getImu();
		belt = shooter.getBelt();
		classes.put(States.WAIT,  new Wait());
		classes.put(States.MOVE, new Move(drive));
		classes.put(States.SPINMOVE, new SpinMove(drive, shooter));
		classes.put(States.TURN, new Turn(drive, imu));
		classes.put(States.ENCODERTURN, new EncoderTurn(drive));
		classes.put(States.TRACKSHOT, new TrackShot(drive));
		classes.put(States.SHOOT, new Shoot(shooter, belt));
		classes.put(States.TRACKGEAR, new TrackGear(drive));
		classes.put(States.GEAR, new Gear(drive));
		classes.put(States.STOP, new Stop(drive, shooter));
		addModes();
	}

	public void write(File file){
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(LINELONG + "\r\n");
//			bw.write(LINESHORT + "\r\n");
//			bw.write(SHOOTLONG + "\r\n");
//			bw.write(SHOOTMIDDLE + "\r\n");
//			bw.write(SHOOTSHORT + "\r\n");
//			bw.write(GEARLONG + "\r\n");
//			bw.write(GEARMIDDLE + "\r\n");
//			bw.write(GEARSHORT + "\r\n");
			bw.close();
			fw.close();
		}catch(IOException e){
			System.out.println(e.getStackTrace());
		}
		
	}
	
	public void read(File file){
		try{
			FileReader fr =  new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
//			LINELONG = Double.parseDouble(br.readLine());	
//			LINESHORT= Double.parseDouble(br.readLine());	
//			SHOOTLONG = Double.parseDouble(br.readLine());	
//			SHOOTMIDDLE = Double.parseDouble(br.readLine());	
//			SHOOTSHORT = Double.parseDouble(br.readLine());	
//			GEARLONG = Double.parseDouble(br.readLine());	
//			GEARMIDDLE = Double.parseDouble(br.readLine());	
//			GEARSHORT = Double.parseDouble(br.readLine());	
			fr.close();
			br.close();
		}catch(IOException e){
			System.out.println(e.getStackTrace());
		}
	}
	
	
	
	public void setMode(){
		choice = chooser.getSelected();
		mode = modes.get(choice.ordinal());
	}

	public List<State> getMode(){
		return mode;
	}
	
	private void addModes(){
		chooser.addDefault(Modes.NOTHING.toString(), Modes.NOTHING);
		chooser.addObject(Modes.LINELONG.toString(), Modes.LINELONG);
		chooser.addObject(Modes.LINEMIDDLE.toString(), Modes.LINEMIDDLE);
		chooser.addObject(Modes.SHOOTFL.toString(), Modes.SHOOTFL);
		chooser.addObject(Modes.SHOOTFM.toString(), Modes.SHOOTFM);	
		chooser.addObject(Modes.SHOOTFR.toString(), Modes.SHOOTFR);
		chooser.addObject(Modes.SHOOTBL.toString(), Modes.SHOOTBL);
		chooser.addObject(Modes.SHOOTBM.toString(), Modes.SHOOTBM);
		chooser.addObject(Modes.SHOOTBR.toString(), Modes.SHOOTBR);
		chooser.addObject(Modes.GEARFL.toString(), Modes.GEARFL);
		chooser.addObject(Modes.GEARFM.toString(), Modes.GEARFM);
		chooser.addObject(Modes.GEARFR.toString(), Modes.GEARFR);
		chooser.addObject(Modes.GEARBL.toString(), Modes.GEARBL);
		chooser.addObject(Modes.GEARBM.toString(), Modes.GEARBM);
		chooser.addObject(Modes.GEARBR.toString(), Modes.GEARBR);
		chooser.addObject(Modes.CUSTOM1.toString(), Modes.CUSTOM1);
		chooser.addObject(Modes.CUSTOM2.toString(), Modes.CUSTOM2);
		SmartDashboard.putData("Auto choices", chooser);
	}
	
	public void reset(){
		System.out.println("resetting");
		iteration = -1;
		newState = true;
		choice = chooser.getSelected();
		mode = modes.get(choice.ordinal());
	}
	
	
	public void printConstant(){
		System.out.printf("%s: %f\n", constants[constant].name, constants[constant].value);
	}
	
	public void updateValues(){
		if (xbox.getButtonPress(Buttons.A)){
			if (xbox.getButtonRelease(Buttons.RBUMP)){
				if (constant == constants.length - 1){
					constant = 0;
				}else{
					constant++;
				}
				printConstant();
			}
			if (xbox.getButtonRelease(Buttons.LBUMP)){
				if (constant == 0){
					constant = constants.length - 1; 
				}else{
					constant--;
				}
				printConstant();
			}
		}else{
			if (xbox.getButtonRelease(Buttons.RBUMP)){
				constants[constant].increment();
				printConstant();
			}
			if (xbox.getButtonRelease(Buttons.LBUMP)){
				constants[constant].decrement();
				printConstant();
			}
		}
		if (xbox.getButtonRelease(Buttons.B)){
			reset();
		}
	}
	
	
	public void run(){
		if (newState){
			iteration++;
			state = mode.get(iteration).getState();
			value = mode.get(iteration).getValue();
			classes.get(state).setValue(value);
			System.out.printf("State: %s\n", state.toString());
			newState = false;
			if (state == States.STOP){
				for (int i = 0; i < constants.length; i++){
					if (constant == constants.length - 1){
						constant = 0;
					}else{
						constant++;
					}
					printConstant();
				}
			}
		}else {
			newState = classes.get(state).run();
			if (state == States.STOP){
				updateValues();
			}
		}
	}
}
