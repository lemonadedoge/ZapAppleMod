package com.jsn_man.ZapApples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class DaytimeWatcher{
	
	public DaytimeWatcher(World world){
		worldObj = world;
		isDay = worldObj.isDaytime();
		name = worldObj.getSaveHandler().getSaveDirectoryName();
		
		if(ModLoader.getMinecraftServerInstance().isDedicatedServer()){
			save = new File("./" + name + "/ZapApples.txt");
		}else{
			save = new File(ModLoader.getMinecraftInstance().mcDataDir, "/saves/" + name + "/ZapApples.txt");
		}
		
		if(!save.exists()){
			try{
				save.createNewFile();
			}catch(Exception e){
				FMLLog.log(Level.SEVERE, e, "Zap Apples could not create a file");
			}
		}else{
			try{
				BufferedReader br = new BufferedReader(new FileReader(save));
				while(br.ready()){
					String line = br.readLine().trim();
					if(!line.startsWith("##")){
						day = Integer.parseInt(line.substring(line.indexOf(":") + 1));
					}
				}
				br.close();
			}catch(Exception e){
				FMLLog.log(Level.SEVERE, e, "Zap Apples could not read a file");
			}
		}
	}
	
	public void tick(){
		if(isDay != worldObj.isDaytime()){
			if(worldObj.isDaytime()){
				if(++day >= 25){
					day = 0;
				}
				//System.out.println("Day " + day + " in " + name);
				clearFile(save);
				try{
					BufferedWriter bw = new BufferedWriter(new FileWriter(save));
					bw.write("day:" + day);
					bw.close();
				}catch(Exception e){
					FMLLog.log(Level.SEVERE, e, "Zap Apples could not write to a file");
				}
			}
			isDay = worldObj.isDaytime();
		}
	}
	
	public void close(){
		
	}
	
	public static File clearFile(File file){
		String name = file.getName();
		File parent = file.getParentFile();
		File newFile = new File(parent, name.substring(name.lastIndexOf(".")) + ".tmp");
		file.delete();
		newFile.renameTo(new File(parent, name));
		
		return newFile;
	}
	
	public int day;
	public boolean isDay;
	public World worldObj;
	public String name;
	public File save;
}