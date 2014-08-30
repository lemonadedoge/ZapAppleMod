package com.chiorichan.ZapApples.events;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import com.chiorichan.ZapApples.ZapApples;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLLog;

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
		
		if ( Minecraft.getMinecraft().isSingleplayer() )
		{
			save = new File( Minecraft.getMinecraft().mcDataDir, "/saves/" + name + "/ZapApples.txt" );
		}
		else
		{
			save = new File( "./" + name + "/ZapApples.txt" );
		}
		
		if ( !save.exists() )
		{
			try
			{
				save.createNewFile();
			}
			catch ( FileNotFoundException e )
			{
				save = new File( "./world/" + name + "/ZapApples.txt" );
				try
				{
					if ( !save.exists() )
					{
						save.createNewFile();
					}
					
				}
				catch ( FileNotFoundException e1 )
				{
					save = new File( "./" + name + "-ZapApples.txt" );
					try
					{
						if ( !save.exists() )
							save.createNewFile();
					}
					catch ( Exception e2 )
					{
						FMLLog.severe( "Zap Apples could not create a file", e );
					}
					
				}
				catch ( Exception e1 )
				{
					FMLLog.severe( "Zap Apples could not create a file", e );
				}
			}
			catch ( Exception e )
			{
				FMLLog.severe( "Zap Apples could not create a file", e );
			}
		}
		
		if ( save.exists() )
		{
			try
			{
				BufferedReader br = new BufferedReader( new FileReader( save ) );
				while ( br.ready() )
				{
					String line = br.readLine().trim();
					if ( !line.startsWith( "##" ) )
					{
						day = Integer.parseInt( line.substring( line.indexOf( ":" ) + 1 ) );
					}
				}
				br.close();
			}
			catch ( Exception e )
			{
				FMLLog.severe( "Zap Apples could not read a file", e );
			}
		}
	}
	
	public void tick()
	{
		if ( isDay != worldObj.isDaytime() )
		{
			if ( worldObj.isDaytime() )
			{
				ZapApples.dayChangeEvent( worldObj, day );
				FMLLog.info( "The zap apple cycle is now on day: " + day );
				
				if ( ++day >= 25 )
				{
					day = 0;
				}
				
				clearFile( save );
				try
				{
					BufferedWriter bw = new BufferedWriter( new FileWriter( save ) );
					bw.write( "day:" + day );
					bw.close();
				}
				catch ( Throwable e )
				{
					FMLLog.severe( "Zap Apples could not write to a file", e );
				}
			}
			isDay = worldObj.isDaytime();
		}
	}
	
	public void close()
	{
	}
	
	public static File clearFile( File file )
	{
		String name = file.getName();
		File parent = file.getParentFile();
		File newFile = new File( parent, name.substring( name.lastIndexOf( "." ) ) + ".tmp" );
		file.delete();
		newFile.renameTo( new File( parent, name ) );
		
		return newFile;
	}
}
