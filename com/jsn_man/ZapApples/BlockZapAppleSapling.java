package com.jsn_man.ZapApples;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class BlockZapAppleSapling extends BlockFlower{
	
	public BlockZapAppleSapling(int id, int texture){
		super(id, texture);
		float var3 = 0.4F;
		setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 2.0F, 0.5F + var3);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand){
		if(!world.isRemote){
			super.updateTick(world, x, y, z, rand);
			
			if(world.getBlockLightValue(x, y + 1, z) >= 9 && rand.nextInt(7) == 0){
				growTree(world, x, y, z, rand);
			}
		}
	}
	
	public void growTree(World world, int x, int y, int z, Random rand){
		WorldGenZapAppleTree gen = new WorldGenZapAppleTree(true);
		world.setBlock(x, y, z, 0);
		
		if(!gen.generate(world, rand, x, y, z)){
			world.setBlock(x, y, z, blockID);
		}
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}