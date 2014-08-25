package com.chiorichan.ZapApples;

import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.chiorichan.ZapApples.events.DaytimeManager;
import com.chiorichan.ZapApples.events.GeneralEventHandler;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy implements IGuiHandler
{
	private static final Map<String, NBTTagCompound> extendedEntityData = Maps.newHashMap();
	
	public void registerRenderers()
	{
		MinecraftForge.EVENT_BUS.register( new DaytimeManager() );
		MinecraftForge.EVENT_BUS.register( new GeneralEventHandler() );
		MinecraftForge.EVENT_BUS.register( new BonemealHandler() );
	}
	
	public int addArmor( String string )
	{
		return 0;
	}
	
	public EntityPlayer getPlayerEntity( MessageContext ctx )
	{
		return ctx.getServerHandler().playerEntity;
	}
	
	@Override
	public Object getServerGuiElement( int guiId, EntityPlayer player, World world, int x, int y, int z )
	{
		return null;
	}
	
	@Override
	public Object getClientGuiElement( int guiId, EntityPlayer player, World world, int x, int y, int z )
	{
		return null;
	}
	
	/**
	 * Adds an entity's custom data to the map for temporary storage
	 */
	public static void storeEntityData( String name, NBTTagCompound compound )
	{
		extendedEntityData.put( name, compound );
	}
	
	/**
	 * Removes the compound from the map and returns the NBT tag stored for name or null if none exists
	 */
	public static NBTTagCompound getEntityData( String name )
	{
		return extendedEntityData.remove( name );
	}
}
