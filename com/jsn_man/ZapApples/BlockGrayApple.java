package com.jsn_man.ZapApples;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockGrayApple extends Block{
	
	public BlockGrayApple(int id, int texture){
		super(id, texture, Material.plants);
		setBlockUnbreakable();
		setResistance(6000000.0F);
		setCreativeTab(CreativeTabs.tabFood);
	}
	
	public int quantityDropped(Random rand){
		return 0;
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z){
		return null;
	}
	
	private boolean canAppleStay(World world, int x, int y, int z){
		return isBlockAt(world, x + 1, y, z) || isBlockAt(world, x - 1, y, z) || isBlockAt(world, x, y + 1, z) || isBlockAt(world, x, y - 1, z) || isBlockAt(world, x, y, z + 1) || isBlockAt(world, x, y, z - 1);
	}
	
	private boolean isBlockAt(World world, int x, int y, int z){
		int id = world.getBlockId(x, y, z);
		return id != 0 && id != blockID;
	}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, int meta){
		if(!world.isRemote && !canAppleStay(world, x, y, z)){
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockAndMetadataWithNotify(x, y, z, 0, 0);
		}
	}
	
	public boolean isOpaqueCube(){
		return false;
	}
	
	public boolean renderAsNormalBlock(){
		return false;
	}
	
	public int getRenderType(){
		return 1;
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}