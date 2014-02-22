package com.jsn_man.ZapApples;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class BlockZapAppleLeaves extends Block implements IShearable{
	
	public BlockZapAppleLeaves(int id, int texture){
		super(id, texture, Material.leaves);
		setTickRandomly(true);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	public int idDropped(int par1, Random par2Random, int par3){
		return ZapApples.zapAppleSapling.blockID;
	}
	
	public void randomDisplayTick(World world, int x, int y, int z, Random rand){
		if(world.isRaining() && !world.doesBlockHaveSolidTopSurface(x, y - 1, z) && rand.nextInt(15) == 1){
			double dropX = (double)((float)x + rand.nextFloat());
			double dropY = (double)y - 0.05D;
			double dropZ = (double)((float)z + rand.nextFloat());
			world.spawnParticle("dripWater", dropX, dropY, dropZ, 0D, 0D, 0D);
		}
	}
	
	public void breakBlock(World world, int x, int y, int z, int side, int meta){
		byte var7 = 1;
        int var8 = var7 + 1;

        if(world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8)){
            for(int var9 = -var7; var9 <= var7; ++var9){
                for(int var10 = -var7; var10 <= var7; ++var10){
                    for(int var11 = -var7; var11 <= var7; ++var11){
                        int var12 = world.getBlockId(x + var9, y + var10, z + var11);

                        if(Block.blocksList[var12] != null){
                            Block.blocksList[var12].beginLeavesDecay(world, x + var9, y + var10, z + var11);
                        }
                    }
                }
            }
        }
	}
	
	public void updateTick(World world, int x, int y, int z, Random rand){
        if(!world.isRemote){
            int meta = world.getBlockMetadata(x, y, z);

            if((meta & 8) != 0 && (meta & 4) == 0){
                byte var7 = 4;
                int var8 = var7 + 1;
                byte var9 = 32;
                int var10 = var9 * var9;
                int var11 = var9 / 2;

                if (this.adjacentTreeBlocks == null)
                {
                    this.adjacentTreeBlocks = new int[var9 * var9 * var9];
                }

                int var12;

                if (world.checkChunksExist(x - var8, y - var8, z - var8, x + var8, y + var8, z + var8))
                {
                    int var13;
                    int var14;
                    int var15;

                    for (var12 = -var7; var12 <= var7; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                var15 = world.getBlockId(x + var12, y + var13, z + var14);

                                Block block = Block.blocksList[var15];

                                if (block != null && block.canSustainLeaves(world, x + var12, y + var13, z + var14))
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
                                }
                                else if (block != null && block.isLeaves(world, x + var12, y + var13, z + var14))
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
                                }
                                else
                                {
                                    this.adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
                                }
                            }
                        }
                    }

                    for (var12 = 1; var12 <= 4; ++var12)
                    {
                        for (var13 = -var7; var13 <= var7; ++var13)
                        {
                            for (var14 = -var7; var14 <= var7; ++var14)
                            {
                                for (var15 = -var7; var15 <= var7; ++var15)
                                {
                                    if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1)
                                    {
                                        if (this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + (var15 + var11 - 1)] = var12;
                                        }

                                        if (this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
                                        {
                                            this.adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                var12 = this.adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11];

                if (var12 >= 0)
                {
                    world.setBlockMetadata(x, y, z, meta & -9);
                }
                else
                {
                    this.removeLeaves(world, x, y, z);
                }
            }
        }
    }
	
	public void removeLeaves(World world, int x, int y, int z){
		dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		world.setBlockWithNotify(x, y, z, 0);
	}
	
	public int quantityDropped(Random rand){
		return rand.nextInt(20) == 0 ? 1 : 0;
	}
	
	public boolean isOpaqueCube(){
		return false;
	}
	
	public boolean isShearable(ItemStack item, World world, int x, int y, int z){
		return true;
	}
	
	public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune){
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1));
		return ret;
	}
	
	public void beginLeavesDecay(World world, int x, int y, int z){
		world.setBlockMetadata(x, y, z, world.getBlockMetadata(x, y, z) | 8);
	}
	
	public boolean isLeaves(World world, int x, int y, int z){
		return true;
	}
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
	
	int[] adjacentTreeBlocks;
}