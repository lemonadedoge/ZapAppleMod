package com.jsn_man.ZapApples;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.src.ModLoader;
import net.minecraftforge.client.ForgeHooksClient;
import cpw.mods.fml.client.FMLTextureFX;
import cpw.mods.fml.common.FMLLog;

public class TextureLiquidFX extends FMLTextureFX{
	
	public TextureLiquidFX(int iconIndex){
		super(iconIndex);
		setup();
	}
	
	public void setup(){
		super.setup();
		int[] icon = new int[tileSizeSquare];
		iconImageData = new int[tileSizeBase][tileSizeBase];
		offset = tileSizeMask;
		try{
			BufferedImage image = ImageIO.read(ModLoader.getMinecraftInstance().texturePackList.getSelectedTexturePack().getResourceAsStream(ZapApples.textures));
			int x = iconIndex % 16 * tileSizeBase;
			int y = iconIndex / 16 * tileSizeBase;
			image.getRGB(x, y, tileSizeBase, tileSizeBase, icon, 0, tileSizeBase);
			
			int index = 0;
			for(int h = 0; h < iconImageData.length; h++){
				for(int k = 0; k < iconImageData[h].length; k++){
					iconImageData[h][k] = icon[index++];
				}
			}
		}catch(IOException e){
			FMLLog.log(Level.SEVERE, e, "Zap Apples cannot load its texture file!");
		}
	}
	
	public void bindImage(RenderEngine renderengine){
		ForgeHooksClient.bindTexture(ZapApples.textures, 0);
	}
	
	public void onTick(){
		if(++delay > 1){
			delay = 0;
			if(--offset < 0){
				offset = tileSizeMask;
			}
		}
		
		int i = 0;
		for(int x = 0; x < tileSizeBase; x++){
			for(int y = 0; y < tileSizeBase; y++){
				int index = x + offset < tileSizeBase ? offset : offset - tileSizeBase;
				imageData[i * 4 + 0] = (byte)(iconImageData[x + index][y] >> 16 & 255);
		        imageData[i * 4 + 1] = (byte)(iconImageData[x + index][y] >> 8 & 255);
		        imageData[i * 4 + 2] = (byte)(iconImageData[x + index][y] >> 0 & 255);
		        imageData[i * 4 + 3] = (byte)(iconImageData[x + index][y] >> 24 & 255);
		        i++;
			}
		}
	}
	
	private int delay;
	private int offset;
	private int[][] iconImageData;
}