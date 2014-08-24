package com.chiorichan.ZapApples;

import com.chiorichan.ZapApples.entity.EntityMeteor;
import com.chiorichan.ZapApples.mobs.EntityTimberWolf;
import com.chiorichan.ZapApples.models.ModelTimberWolf;
import com.chiorichan.ZapApples.render.Render2D;
import com.chiorichan.ZapApples.render.Render3D;
import com.chiorichan.ZapApples.render.RenderApple;
import com.chiorichan.ZapApples.render.RenderCake;
import com.chiorichan.ZapApples.render.RenderMeteor;
import com.chiorichan.ZapApples.render.RenderPie;
import com.chiorichan.ZapApples.render.RenderTimberWolf;
import com.chiorichan.ZapApples.tiles.TileEntityPie;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.src.Minecraft;
import net.minecraft.src.ModLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;

public class ProxyClient extends Proxy
{
  public void registerRenderInformation()
  {
    RenderingRegistry.registerEntityRenderingHandler(EntityTimberWolf.class, new RenderTimberWolf(new ModelTimberWolf(), 0.5F));

    MinecraftForge.EVENT_BUS.register(new SoundHandler());

    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPie.class, new RenderPie());
    RenderingRegistry.registerEntityRenderingHandler(EntityMeteor.class, new RenderMeteor());

    new Render2D();
    new Render3D();
    new RenderApple();
    new RenderCake();
  }

  public void addFX()
  {
  }

  public void onTickInGame()
  {
    if (ModLoader.getMinecraftInstance().theWorld != null)
    {
      DaytimeManager.tick();
    }
    else
    {
      DaytimeManager.possibleClose();
    }
  }
}