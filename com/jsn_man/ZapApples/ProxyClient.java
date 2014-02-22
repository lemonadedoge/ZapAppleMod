package com.jsn_man.ZapApples;

import java.util.EnumSet;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.src.ModLoader;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ProxyClient extends Proxy{
	
	public void registerRenderInformation(){
		MinecraftForge.EVENT_BUS.register(new SoundHandler());
		MinecraftForgeClient.preloadTexture(ZapApples.textures);
		MinecraftForgeClient.preloadTexture(ZapApples.cakeTextures);
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCakething.class, new TileEntityPieRenderer());
		RenderingRegistry.registerEntityRenderingHandler(EntityMeteor.class, new RenderMeteor());
		
		new Render2D();
		new Render3D();
	}
	
	public void addFX(){
		ModLoader.addAnimation(new TextureLiquidFX(ZapApples.jamMoving.blockIndexInTexture));
		ModLoader.addAnimation(new TextureLiquidFX(ZapApples.jamMoving.blockIndexInTexture + 1));
		ModLoader.addAnimation(new TextureLiquidFX(ZapApples.jamMoving.blockIndexInTexture + 16));
		ModLoader.addAnimation(new TextureLiquidFX(ZapApples.jamMoving.blockIndexInTexture + 17));
		
		ModLoader.addAnimation(new TextureLiquidFX(ZapApples.doughMoving.blockIndexInTexture));
		ModLoader.addAnimation(new TextureLiquidFX(ZapApples.doughMoving.blockIndexInTexture + 1));
		ModLoader.addAnimation(new TextureLiquidFX(ZapApples.doughMoving.blockIndexInTexture + 16));
		ModLoader.addAnimation(new TextureLiquidFX(ZapApples.doughMoving.blockIndexInTexture + 17));
	}
	
	public void onTickInGame(){
		if(ModLoader.getMinecraftInstance().theWorld != null){
			DaytimeManager.tick();
		}else{
			DaytimeManager.possibleClose();
		}
	}
}