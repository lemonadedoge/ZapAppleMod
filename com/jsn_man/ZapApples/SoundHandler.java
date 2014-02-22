package com.jsn_man.ZapApples;

import java.util.logging.Level;
import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler{
	
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event){
		try{
			event.manager.soundPoolSounds.addSound("mob/wolf/zaphowl.wav", ZapApples.class.getResource("/com/jsn_man/ZapApples/howl.wav"));
		}catch(Exception e){
			FMLLog.log(Level.SEVERE, e, "Zap Apples could not register a sound.");
		}
	}
}