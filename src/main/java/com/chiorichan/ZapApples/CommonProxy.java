package com.chiorichan.ZapApples;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.chiorichan.ZapApples.events.DaytimeManager;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy implements IGuiHandler
{
	public void registerRenderers()
	{
		MinecraftForge.EVENT_BUS.register( new DaytimeManager() );
		MinecraftForge.EVENT_BUS.register( new BonemealHandler() );
	}
	
	public void registerEffects()
	{
		
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
}
