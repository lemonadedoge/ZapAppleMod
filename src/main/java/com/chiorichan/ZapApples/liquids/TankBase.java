package com.chiorichan.ZapApples.liquids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TankBase extends FluidTank
{
	protected String name;
	protected FluidStack[] acceptableFluids;
	
	public TankBase(int capacity, String tankName, FluidStack[] acceptableFluids)
	{
		super( capacity );
		name = tankName;
		this.acceptableFluids = acceptableFluids;
	}
	
	public FluidStack drain( FluidStack resource, boolean doDrain )
	{
		if ( resource == null )
		{
			return null;
		}
		if ( fluid == null )
		{
			return null;
		}
		if ( !fluid.isFluidEqual( resource ) )
		{
			return null;
		}
		return drain( resource.amount, doDrain );
	}
	
	public int getSpace()
	{
		return getCapacity() - getFluidAmount();
	}
	
	public double getPercentFull()
	{
		return (double) getFluidAmount() / getCapacity();
	}
	
	public int fill( FluidStack resource, boolean doFill )
	{
		if ( resource == null )
		{
			return 0;
		}
		
		if ( acceptableFluids.length == 0 )
		{
			return super.fill( resource, doFill );
		}
		for ( FluidStack acceptableFluid : acceptableFluids )
		{
			if ( acceptableFluid.isFluidEqual( resource ) )
			{
				return super.fill( resource, doFill );
			}
		}
		
		return 0;
	}
	
	public boolean isEmpty()
	{
		return ( getFluid() == null ) || ( getFluid().amount <= 0 );
	}
	
	public boolean isFull()
	{
		return ( getFluid() != null ) && ( getFluid().amount >= getCapacity() );
	}
	
	public Fluid getFluidType()
	{
		return getFluid() != null ? getFluid().getFluid() : null;
	}
	
	public final NBTTagCompound writeToNBT( NBTTagCompound nbt )
	{
		NBTTagCompound tankData = new NBTTagCompound();
		super.writeToNBT( tankData );
		writeTankToNBT( tankData );
		
		nbt.setTag( name, tankData );
		return nbt;
	}
	
	public final FluidTank readFromNBT( NBTTagCompound nbt )
	{
		if ( nbt.hasKey( name ) )
		{
			NBTTagCompound tankData = nbt.getCompoundTag( name );
			super.readFromNBT( tankData );
			
			readTankFromNBT( tankData );
		}
		return this;
	}
	
	public void writeTankToNBT( NBTTagCompound nbt )
	{
	}
	
	public void readTankFromNBT( NBTTagCompound nbt )
	{
	}
}
