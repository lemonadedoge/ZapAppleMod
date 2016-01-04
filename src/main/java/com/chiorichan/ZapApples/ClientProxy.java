package com.chiorichan.ZapApples;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.chiorichan.ZapApples.entity.EntityMeteor;
import com.chiorichan.ZapApples.entity.EntityMeteorHeadFX;
import com.chiorichan.ZapApples.entity.EntityZapApple;
import com.chiorichan.ZapApples.events.GeneralEventHandler;
import com.chiorichan.ZapApples.events.SoundHandler;
import com.chiorichan.ZapApples.mobs.EntityTimberWolf;
import com.chiorichan.ZapApples.models.ModelTimberWolf;
import com.chiorichan.ZapApples.render.Render2D;
import com.chiorichan.ZapApples.render.Render3D;
import com.chiorichan.ZapApples.render.RenderApple;
import com.chiorichan.ZapApples.render.RenderCake;
import com.chiorichan.ZapApples.render.RenderCakeItem;
import com.chiorichan.ZapApples.render.RenderJarItem;
import com.chiorichan.ZapApples.render.RenderMeteor;
import com.chiorichan.ZapApples.render.RenderPie;
import com.chiorichan.ZapApples.render.RenderTimberWolf;
import com.chiorichan.ZapApples.tileentity.TileEntityPie;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.EntityRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		super.registerRenderers();
		
		MinecraftForgeClient.registerItemRenderer( new ItemStack( ZapApples.cake ).getItem(), new RenderCakeItem() );
		MinecraftForgeClient.registerItemRenderer( new ItemStack( ZapApples.jar ).getItem(), new RenderJarItem() );
		
		RenderingRegistry.registerEntityRenderingHandler( EntityZapApple.class, new RenderSnowball( new ItemStack( ZapApples.zapApple ).getItem() ) );
		RenderingRegistry.registerEntityRenderingHandler( EntityTimberWolf.class, new RenderTimberWolf( new ModelTimberWolf(), 0.5F ) );
		RenderingRegistry.registerEntityRenderingHandler( EntityMeteor.class, new RenderMeteor() );
		
		MinecraftForge.EVENT_BUS.register( new GeneralEventHandler() );
		MinecraftForge.EVENT_BUS.register( new SoundHandler() );
		
		ClientRegistry.bindTileEntitySpecialRenderer( TileEntityPie.class, new RenderPie() );
		
		new Render2D();
		new Render3D();
		new RenderApple();
		new RenderCake();
	}
	
	@Override
	public void registerEffects()
	{
		EntityRegistry.registerModEntity( EntityMeteorHeadFX.class, "MeteorHead", ZapApples.id++, ZapApples.instance, 80, 3, true );
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
