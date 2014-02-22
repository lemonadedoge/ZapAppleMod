package com.jsn_man.ZapApples;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.liquids.ILiquid;

public class BlockJamStationary extends BlockFluid implements ILiquid{
	
	public BlockJamStationary(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.setTickRandomly(false);
        blockIndexInTexture = 206;
        disableStats();
        setRequiresSelfNotify();
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
    
    public int tickRate(){
    	return 5;
    }
	
	public int getBlockTextureFromSide(int par1){
		if(par1 == 0){
			return blockIndexInTexture + 15;
		}else{
			return par1 != 0 && par1 != 1 ? this.blockIndexInTexture: this.blockIndexInTexture - 1;
		}
	}
	
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random){
		Block.waterStill.randomDisplayTick(par1World, par2, par3, par4, par5Random);
	}

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return this.blockMaterial != Material.lava;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (par1World.getBlockId(par2, par3, par4) == this.blockID)
        {
            this.setNotStationary(par1World, par2, par3, par4);
        }
    }
    
    public void onBlockAdded(World par1World, int par2, int par3, int par4){
    	
    }

    /**
     * Changes the block ID to that of an updating fluid.
     */
    private void setNotStationary(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        par1World.editingBlocks = true;
        par1World.setBlockAndMetadata(par2, par3, par4, this.blockID - 1, var5);
        par1World.markBlockRangeForRenderUpdate(par2, par3, par4, par2, par3, par4);
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID - 1, this.tickRate());
        par1World.editingBlocks = false;
    }

    /**
     * Checks to see if the block is flammable.
     */
    private boolean isFlammable(World par1World, int par2, int par3, int par4)
    {
        return par1World.getBlockMaterial(par2, par3, par4).getCanBurn();
    }
	
	public String getTextureFile(){
		return ZapApples.textures;
	}
}