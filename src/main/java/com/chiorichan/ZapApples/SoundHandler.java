package com.chiorichan.ZapApples;

import cpw.mods.fml.common.FMLLog;
import java.util.logging.Level;
import net.minecraft.src.SoundManager;
import net.minecraft.src.SoundPool;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler
{
  @ForgeSubscribe
  public void onSound(SoundLoadEvent event)
  {
    try
    {
      event.manager.soundPoolSounds.addSound("mob/wolf/zaphowl.wav");
    }
    catch (Exception e)
    {
      FMLLog.log(Level.SEVERE, e, "Zap Apples could not register a sound.", new Object[0]);
    }
  }

  @ForgeSubscribe
  public void onSoundLoad(SoundLoadEvent event)
  {
  }
}