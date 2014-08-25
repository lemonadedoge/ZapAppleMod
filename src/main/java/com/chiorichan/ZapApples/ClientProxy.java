package com.chiorichan.ZapApples;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import com.chiorichan.ZapApples.entity.EntityMeteor;
import com.chiorichan.ZapApples.events.DaytimeManager;
import com.chiorichan.ZapApples.events.GeneralEventHandler;
import com.chiorichan.ZapApples.events.SoundHandler;
import com.chiorichan.ZapApples.mobs.EntityTimberWolf;
import com.chiorichan.ZapApples.models.ModelTimberWolf;
import com.chiorichan.ZapApples.render.Render2D;
import com.chiorichan.ZapApples.render.Render3D;
import com.chiorichan.ZapApples.render.RenderApple;
import com.chiorichan.ZapApples.render.RenderCake;
import com.chiorichan.ZapApples.render.RenderMeteor;
import com.chiorichan.ZapApples.render.RenderPie;
import com.chiorichan.ZapApples.render.RenderTimberWolf;
import com.chiorichan.ZapApples.tileentity.TileEntityPie;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		super.registerRenderers();
		
		RenderingRegistry.registerEntityRenderingHandler( EntityTimberWolf.class, new RenderTimberWolf( new ModelTimberWolf(), 0.5F ) );
		
		MinecraftForge.EVENT_BUS.register( new SoundHandler() );
		
		ClientRegistry.bindTileEntitySpecialRenderer( TileEntityPie.class, new RenderPie() );
		RenderingRegistry.registerEntityRenderingHandler( EntityMeteor.class, new RenderMeteor() );
		
		new Render2D();
		new Render3D();
		new RenderApple();
		new RenderCake();
	}
	
	@Override
	public int addArmor( String armor )
	{
		return RenderingRegistry.addNewArmourRendererPrefix( armor );
	}
	
	@Override
	public EntityPlayer getPlayerEntity( MessageContext ctx )
	{
		return ( ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity( ctx ) );
	}
}
