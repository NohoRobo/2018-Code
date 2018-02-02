package states;

public class Constant {

	String name;
	double value;
	
	
	public Constant(String name, double value){
		this.name = name;
		this.value = value;
	}
	
	
	public void setName(String input){
		name = input;
	}
	
	public void setValue(double input){
		value = input;
	}
	
	public void increment(){
		value++;
	}
	
	public void decrement(){
		value--;
	}
}
