package sg.edu.nus.cs2020;

public class IntegerWithKey implements Comparable<IntegerWithKey> {
	//Variables
	protected int _integer = 0; //initial value;
	protected int _key = 1; //initial value
	
	//Constructor
	public IntegerWithKey(int num){
		_integer = num;
		
		//Assign a random key value to the integer, to identify one integer from another of same value
		_key = (int) (Math.random()*1000);
	}
	
	//Interface functions
	public int compareTo(IntegerWithKey compareInteger){
		return (this._integer - compareInteger._integer);
	}
	
	//Accessor functions
	public int getInteger(){
		return _integer;
	}
	
	public void setInteger(int num){
		_integer = num;
	}
	
	public int getKey(){
		return _key;
	}
	
	public void setKey(int newKey){
		_key = newKey;
	}
	
	public IntegerWithKey[] clone(IntegerWithKey[] sample){
		IntegerWithKey[] copy = new IntegerWithKey[sample.length];
		for(int i=0; i < sample.length; i++){
			copy[i].setInteger(sample[i].getInteger());
			copy[i].setKey(sample[i].getKey());
		}
		
		return copy;
	}
}
