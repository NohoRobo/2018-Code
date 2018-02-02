package states;

import states.StateMachine.States;

public class State {
	
	double val;
	States state;
	
	public State(States autoState, double value){
		state = autoState;
		val = value;
	}
	
	public double getValue(){
		return val;
	}
	
	public States getState(){
		return state;
	}
	
}
