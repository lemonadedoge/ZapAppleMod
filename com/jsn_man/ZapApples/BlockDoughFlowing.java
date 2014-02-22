package com.jsn_man.ZapApples;

import net.minecraft.block.material.Material;

public class BlockDoughFlowing extends BlockJamFlowing{
	
	public BlockDoughFlowing(int id, Material material){
		super(id, material);
		blockIndexInTexture += 32;
	}
	
	public int stillLiquidId(){
		return ZapApples.doughStill.blockID;
	}
}