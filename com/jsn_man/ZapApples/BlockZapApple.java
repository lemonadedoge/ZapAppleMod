package com.jsn_man.ZapApples;

import java.util.Random;

public class BlockZapApple extends BlockGrayApple{
	
	public BlockZapApple(int id, int texture){
		super(id, texture);
		setResistance(0F);
		setHardness(0.2F);
	}
	
	public int quantityDropped(Random rand){
		return 1;
	}
}