package com.jsn_man.ZapApples;

import java.util.EnumSet;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class DaytimeManager implements ITickHandler{
	
	public DaytimeManager(){
		TickRegistry.registerTickHandler(this, Side.SERVER);
	}
	
	public EnumSet<TickType> ticks(){
		return EnumSet.of(TickType.SERVER);
	}
	
	public String getLabel() {
		return "ZapApple DaytimeManager";
	}
	
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}
	
	public void tickEnd(EnumSet<TickType> type, Object... tickData){
		if(type.equals(EnumSet.of(TickType.SERVER))){
			tick();
		}
	}
	
	public static void tick(){
		if(ModLoader.getMinecraftServerInstance() == null){
			possibleClose();
			return;
		}
		
		if(watchers.length != ModLoader.getMinecraftServerInstance().worldServers.length){
			createNewWatchers();
			return;
		}
		
		watchers[index++].tick();
		
		if(index >= watchers.length){
			index = 0;
		}
	}
	
	public static void possibleClose(){
		if(watchers.length > 0){
			for(DaytimeWatcher dw : watchers){
				dw.close();
			}
			watchers = new DaytimeWatcher[0];
			index = 0;
		}
	}
	
	public static int getDay(World world){
		for(DaytimeWatcher dw : watchers){
			if(dw.name.equalsIgnoreCase(world.getSaveHandler().getSaveDirectoryName())){
				return dw.day;
			}
		}
		return 0;
	}
	
	private static void createNewWatchers(){
		World[] worlds = ModLoader.getMinecraftServerInstance().worldServers;
		if(worlds.length > 0){
			for(DaytimeWatcher dw : watchers){
				dw.close();
			}
			watchers = new DaytimeWatcher[worlds.length];
			for(int i = 0; i < worlds.length; i++){
				watchers[i]  = new DaytimeWatcher(worlds[i]);
			}
			index = 0;
		}
	}
	
	private static int index;
	public static DaytimeWatcher[] watchers = new DaytimeWatcher[0];
}