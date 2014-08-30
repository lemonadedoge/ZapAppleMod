package com.chiorichan.ZapApples.tileentity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.liquids.TankBase;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLLog;

public class TileEntityJar extends TileEntity implements IFluidHandler
{
	private TankBase tank = new TankBase( getTankCapacity(), "Jar", new FluidStack[0] );
	
	private double flowTimer = Math.random() * 100.0D;
	
	public HashMap<ForgeDirection, WeakReference<TileEntityJar>> neighbours = Maps.newHashMap();
	public HashMap<ForgeDirection, Boolean> surroundingBlocks = Maps.newHashMap();
	
	public static final ForgeDirection[] horizontalDirections = { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST };
	
	protected Comparator<TileEntityJar> sortBySpace = new Comparator<TileEntityJar>()
	{
		public int compare( TileEntityJar c1, TileEntityJar c2 )
		{
			return c2.getSpace() - c1.getSpace();
		}
	};
	
	public static int getTankCapacity()
	{
		return 1000 * ZapApples.bucketsPerJar;
	}
	
	public boolean hasBlockOnSide( ForgeDirection side )
	{
		return ( surroundingBlocks.containsKey( side ) ) && ( ( (Boolean) surroundingBlocks.get( side ) ).booleanValue() );
	}
	
	public TileEntityJar getTankInDirection( ForgeDirection direction )
	{
		if ( neighbours.containsKey( direction ) )
		{
			WeakReference<TileEntityJar> neighbour = neighbours.get( direction );
			if ( neighbour != null )
			{
				TileEntityJar otherTank = (TileEntityJar) neighbour.get();
				if ( otherTank == null )
				{
					return null;
				}
				if ( otherTank.isInvalid() )
				{
					return null;
				}
				if ( otherTank.canReceiveLiquid( getTank().getFluid() ) )
				{
					return otherTank;
				}
			}
		}
		return null;
	}
	
	public TileEntityJar[] getSurroundingTanks()
	{
		ArrayList<TileEntityJar> tanks = Lists.newArrayList();
		for ( ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS )
		{
			TileEntityJar t = getTankInDirection( direction );
			if ( t != null )
			{
				tanks.add( t );
			}
		}
		return (TileEntityJar[]) tanks.toArray( new TileEntityJar[tanks.size()] );
	}
	
	public ArrayList<TileEntityJar> getHorizontalTanksOrdererdBySpace( HashSet<TileEntityJar> except )
	{
		ArrayList<TileEntityJar> horizontalTanks = Lists.newArrayList();
		for ( ForgeDirection direction : horizontalDirections )
		{
			TileEntityJar tank = getTankInDirection( direction );
			if ( ( tank != null ) && ( !except.contains( tank ) ) )
			{
				horizontalTanks.add( tank );
			}
		}
		Collections.sort( horizontalTanks, sortBySpace );
		return horizontalTanks;
	}
	
	public boolean receiveClientEvent( int eventId, int eventParam )
	{
		if ( worldObj.isRemote )
			;
		return true;
	}
	
	public boolean containsValidLiquid()
	{
		return tank.getFluid() != null;
	}
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	public void updateEntity()
	{
		super.updateEntity();
		if ( !worldObj.isRemote )
		{
			if ( tank.getFluid() == null )
				;
		}
		else
		{
			flowTimer += 0.1000000014901161D;
		}
	}
	
	public boolean canReceiveLiquid( FluidStack liquid )
	{
		if ( tank.getFluid() == null )
		{
			return true;
		}
		if ( liquid == null )
		{
			return true;
		}
		FluidStack otherLiquid = tank.getFluid();
		if ( otherLiquid != null )
		{
			return otherLiquid.isFluidEqual( liquid );
		}
		return true;
	}
	
	public TankBase getTank()
	{
		return tank;
	}
	
	public boolean isFull()
	{
		return tank.getSpace() == 0;
	}
	
	public int getSpace()
	{
		return tank.getSpace();
	}
	
	public int fill( FluidStack resource, boolean doFill )
	{
		if ( resource == null )
		{
			return 0;
		}
		
		int startAmount = resource.amount;
		
		if ( startAmount > 0 )
		{
			startAmount -= tank.fill( resource, doFill );
		}
		
		return startAmount;
	}
	
	public int countDownwardsTanks()
	{
		int count = 1;
		TileEntityJar below = getTankInDirection( ForgeDirection.DOWN );
		if ( below != null )
		{
			count += below.countDownwardsTanks();
		}
		return count;
	}
	
	public double getHeightForRender()
	{
		return tank.getFluidAmount() / tank.getCapacity();
	}
	
	public double getPercentFull()
	{
		return tank.getPercentFull();
	}
	
	public double getFlowOffset()
	{
		return Math.sin( flowTimer ) / 35.0D;
	}
	
	public double getLiquidHeightForSide( ForgeDirection[] sides )
	{
		if ( containsValidLiquid() )
		{
			double percentFull = getHeightForRender();
			
			if ( percentFull > 0.98D )
			{
				return 1.0D;
			}
			double fullness = percentFull + getFlowOffset();
			int count = 1;
			for ( ForgeDirection side : sides )
			{
				TileEntityJar sideTank = getTankInDirection( side );
				if ( ( sideTank != null ) && ( sideTank.canReceiveLiquid( tank.getFluid() ) ) )
				{
					fullness += sideTank.getHeightForRender() + sideTank.getFlowOffset();
					count++;
				}
			}
			return Math.max( 0.0D, Math.min( 1.0D, fullness / count ) );
		}
		return 0.0D;
	}
	
	public NBTTagCompound getItemNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		tank.writeToNBT( nbt );
		return nbt;
	}
	
	public int fill( ForgeDirection from, FluidStack resource, boolean doFill )
	{
		return fill( resource, doFill );
	}
	
	public int getFluidLightLevel()
	{
		FluidStack fluid = tank.getFluid();
		if ( fluid != null )
		{
			try
			{
				return fluid.getFluid().getLuminosity();
			}
			catch ( Exception e )
			{}
		}
		return 0;
	}
	
	public void readFromNBT( NBTTagCompound data )
	{
		tank.readFromNBT( data );
		super.readFromNBT( data );
	}
	
	public void writeToNBT( NBTTagCompound data )
	{
		data = tank.writeToNBT( data );
		super.writeToNBT( data );
	}
	
	public FluidTankInfo getTankInfo()
	{
		return tank.getInfo();
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT( tag );
		return new S35PacketUpdateTileEntity( xCoord, yCoord, zCoord, 0, tag );
	}
	
	@Override
	public void onDataPacket( NetworkManager net, S35PacketUpdateTileEntity pkt )
	{
		readFromNBT( pkt.func_148857_g() );
		markForUpdate();
	}
	
	public void markForUpdate()
	{
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
	}
	
	public IIcon getFluidTexture()
	{
		if ( tank.getFluid() == null )
		{
			return null;
		}
		return tank.getFluidType().getIcon();
	}
	
	public FluidStack drain( ForgeDirection from, FluidStack resource, boolean doDrain )
	{
		return tank.drain( resource, doDrain );
	}
	
	public boolean canFill( ForgeDirection from, Fluid fluid )
	{
		return true;
	}
	
	public boolean canDrain( ForgeDirection from, Fluid fluid )
	{
		return true;
	}
	
	public FluidTankInfo[] getTankInfo( ForgeDirection from )
	{
		FluidTankInfo[] rtn = new FluidTankInfo[1];
		rtn[0] = tank.getInfo();
		return rtn;
	}
	
	public FluidStack drain( ForgeDirection from, int maxDrain, boolean doDrain )
	{
		return tank.drain( maxDrain, doDrain );
	}
}
