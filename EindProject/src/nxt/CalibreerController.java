package nxt;

import java.util.ArrayList;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor.Color;

public class CalibreerController {
	
	private ColorSensor cs;
	private LightSensor ls;
	
	private ArrayList<Integer> lightValues;
	private ArrayList<Integer> colorValues;
	
	private final int CIRCLE = 360;
	private final int MEDIAN = 50;
	
	public CalibreerController(ColorSensor col, LightSensor lig) {
		
		this.cs = col;
		this.ls = lig;
		
		lightValues = new ArrayList<Integer>();
		colorValues = new ArrayList<Integer>();
		
		calibrateAllSensors();		
	}
	
	public void calibrateAllSensors(){
		
		cs.setFloodlight(Color.RED);
		cs.setFloodlight(true);				
		ls.setFloodlight(true);
		
		MotorController.setRotateSpeed(45);
		MotorController.rotate(CIRCLE, true);
		
		while(MotorController.moving()){
			lightValues.add(ls.getNormalizedLightValue());
			colorValues.add(1023-cs.getRawLightValue());
		}
		
		ls.setHigh(getHighestValue(lightValues));
		ls.setLow(getLowestValue(lightValues));
		cs.setHigh(getHighestValue(colorValues));
		cs.setLow(getLowestValue(colorValues));
		
		MotorController.rotate(CIRCLE, true);
		
		while(MotorController.moving()){
			
			System.out.println("L: "+ls.getLightValue()+" C: "+cs.getLightValue());
			
			if(ls.getLightValue() < MEDIAN && cs.getLightValue() > MEDIAN){
				MotorController.stop();
			}
		}
		
		Button.waitForAnyPress();
		
	}
	
	public int getHighestValue(ArrayList<Integer> al){
		int highestValue = 0;
		for(int currentValue : al){
			if(currentValue > highestValue)highestValue = currentValue;
		}
		return highestValue;
	}
	
	public int getLowestValue(ArrayList<Integer> al){
		int lowestValue = 1023;
		for(int currentValue : al){
			if(currentValue < lowestValue)lowestValue = currentValue;
		}
		return lowestValue;
	}
	
}
