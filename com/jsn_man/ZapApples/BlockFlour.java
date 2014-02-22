package com.jsn_man.ZapApples;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingSand;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;

public class BlockFlour extends BlockSand implements ILiquid{
	
	public BlockFlour(int id, int texture){
		super(id, texture, Material.sand);
		setCreativeTab(CreativeTabs.tabFood);
	}
	
	public int stillLiquidId(){
		return blockID;
	}
	
	public boolean isMetaSensitive(){
		return false;
	}
	
	public int stillLiquidMeta(){
		return 0;
	}
	
	public void onBlockAdded(World world, int x, int y, int z){
		super.onBlockAdded(world, x, y, z);
		if(adjacentWater(world, x - 1, y, z) || adjacentWater(world, x + 1, y, z) || adjacentWater(world, x, y - 1, z) || adjacentWater(world, x, y + 1, z) || adjacentWater(world, x, y, z - 1) || adjacentWater(world, x, y, z + 1)){
			world.setBlockAndMetadataWithNotify(x, y, z, ZapApples.doughStill.blockID, 0);
		}
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, int i){
		super.onNeighborBlockChange(world, x, y, z, i);
		if(adjacentWater(world, x - 1, y, z) || adjacentWater(world, x + 1, y, z) || adjacentWater(world, x, y - 1, z) || adjacentWater(world, x, y + 1, z) || adjacentWater(world, x, y, z - 1) || adjacentWater(world, x, y, z + 1)){
			world.setBlockAndMetadataWithNotify(x, y, z, ZapApples.doughStill.blockID, 0);
		}
	}
	
	protected boolean adjacentWater(World world, int x, int y, int z){
		int id = world.getBlockId(x, y, z);
		return id == Block.waterMoving.blockID || id == Block.waterStill.blockID;
	}
	
	protected void onStartFalling(EntityFallingSand entity){
		
	}
	
	public void onFinishFalling(World world, int x, int y, int z, int i){
		
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}