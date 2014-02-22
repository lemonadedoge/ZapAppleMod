package com.jsn_man.ZapApples;

import java.util.Arrays;

public class OrderedTriple{
	
	public OrderedTriple(int x, int y, int z){
		coordinates = new int[]{x, y, z};
	}
	
	public int getX(){
		return coordinates[0];
	}
	
	public int getY(){
		return coordinates[1];
	}
	
	public int getZ(){
		return coordinates[2];
	}
	
	public boolean equals(Object obj){
		if(obj instanceof OrderedTriple){
			return Arrays.equals(coordinates, ((OrderedTriple)obj).coordinates);
		}else{
			return false;
		}
	}
	
	public int hashCode(){
		return Arrays.hashCode(coordinates);
	}
	
	public String toString(){
		return "(" + coordinates[0] + ", " + coordinates[1] + ", " + coordinates[2] + ")";
	}
	
	public static OrderedTriple valueOf(String s){
		int x = Integer.parseInt(s.substring(1, s.indexOf(",")));
		s = s.substring(s.indexOf(",") + 2);
		int y = Integer.parseInt(s.substring(0, s.indexOf(",")));
		s = s.substring(s.indexOf(",") + 2);
		int z = Integer.parseInt(s.substring(0, s.length() - 1));
		
		return new OrderedTriple(x, y, z);
	}
	
	public int[] coordinates;
}
