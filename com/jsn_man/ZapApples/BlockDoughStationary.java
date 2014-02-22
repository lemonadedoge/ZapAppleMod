package com.jsn_man.ZapApples;

import net.minecraft.block.material.Material;

public class BlockDoughStationary extends BlockJamStationary{
	
	public BlockDoughStationary(int id, Material material){
		super(id, material);
		blockIndexInTexture += 32;
	}
}