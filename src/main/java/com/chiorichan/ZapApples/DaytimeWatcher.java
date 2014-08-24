package com.chiorichan.ZapApples;

import cpw.mods.fml.common.FMLLog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraft.src.World;

public class DaytimeWatcher
{
  public int day;
  public boolean isDay;
  public World worldObj;
  public String name;
  public File save;

  public DaytimeWatcher(World world)
  {
    worldObj = world;
    isDay = worldObj.isDaytime();
    name = worldObj.getSaveHandler().getWorldDirectoryName();

    if (ModLoader.getMinecraftServerInstance().isSinglePlayer())
    {
      save = new File(Minecraft.getMinecraft().mcDataDir, "/saves/" + name + "/ZapApples.txt");
    }
    else
    {
      save = new File("./" + name + "/ZapApples.txt");
    }

    if (!save.exists())
    {
      try
      {
        save.createNewFile();
      }
      catch (FileNotFoundException e)
      {
        save = new File("./world/" + name + "/ZapApples.txt");
        try
        {
          if (!save.exists()) {
            save.createNewFile();
          }

        }
        catch (FileNotFoundException e1)
        {
          save = new File("./" + name + "-ZapApples.txt");
          try
          {
            if (!save.exists())
              save.createNewFile();
          }
          catch (Exception e2)
          {
            FMLLog.log(Level.SEVERE, e, "Zap Apples could not create a file", new Object[0]);
          }

        }
        catch (Exception e1)
        {
          FMLLog.log(Level.SEVERE, e, "Zap Apples could not create a file", new Object[0]);
        }
      }
      catch (Exception e)
      {
        FMLLog.log(Level.SEVERE, e, "Zap Apples could not create a file", new Object[0]);
      }
    }

    if (save.exists())
    {
      try
      {
        BufferedReader br = new BufferedReader(new FileReader(save));
        while (br.ready())
        {
          String line = br.readLine().trim();
          if (!line.startsWith("##"))
          {
            day = Integer.parseInt(line.substring(line.indexOf(":") + 1));
          }
        }
        br.close();
      }
      catch (Exception e)
      {
        FMLLog.log(Level.SEVERE, e, "Zap Apples could not read a file", new Object[0]);
      }
    }
  }

  public void tick()
  {
    if (isDay != worldObj.isDaytime())
    {
      if (worldObj.isDaytime())
      {
        ZapApples.dayChangeEvent(worldObj, day);

        if (++day >= 25)
        {
          day = 0;
        }

        clearFile(save);
        try
        {
          BufferedWriter bw = new BufferedWriter(new FileWriter(save));
          bw.write("day:" + day);
          bw.close();
        }
        catch (Exception e)
        {
          FMLLog.log(Level.SEVERE, e, "Zap Apples could not write to a file", new Object[0]);
        }
      }
      isDay = worldObj.isDaytime();
    }
  }

  public void close()
  {
  }

  public static File clearFile(File file)
  {
    String name = file.getName();
    File parent = file.getParentFile();
    File newFile = new File(parent, name.substring(name.lastIndexOf(".")) + ".tmp");
    file.delete();
    newFile.renameTo(new File(parent, name));

    return newFile;
  }
}