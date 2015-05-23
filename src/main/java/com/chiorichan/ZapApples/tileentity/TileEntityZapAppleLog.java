package com.chiorichan.ZapApples.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.chiorichan.ZapApples.OrderedTriple;
import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.events.DayChangeListener;
import com.chiorichan.ZapApples.events.DaytimeManager;
import com.chiorichan.ZapApples.network.PacketHandler;
import com.chiorichan.ZapApples.network.packet.client.SendEffectsPacket;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLLog;

public class TileEntityZapAppleLog extends TileEntity implements DayChangeListener
{
	private Random rand;
	public int index[] = new int[6];
	public int indexMax[] = new int[6];
	public boolean[] finished;
	private static int[] delaysDefault = new int[] {12, 12, 8, 8, 8, 4};
	private int[] delays;
	private List<OrderedTriple> leafCopy;
	private List<OrderedTriple> appleCopy;
	public List<OrderedTriple> leafPositions;
	public List<OrderedTriple> logPositions;
	public HashMap<OrderedTriple, Integer> applePositions;
	public int day;
	
	public TileEntityZapAppleLog()
	{
		DaytimeManager.registerListener( this );
		resetValues();
	}
	
	public void resetValues()
	{
		leafPositions = Lists.newLinkedList();
		logPositions = Lists.newLinkedList();
		applePositions = Maps.newHashMap();
		finished = new boolean[6];
		delays = delaysDefault.clone();
		rand = new Random();
		day = 0;
	}
	
	@Override
	public void onDayChange()
	{
		day++;
	}
	
	@Override
	public boolean canUpdate()
	{
		return true;
	}
	
	/**
	 * Returns the description for each phase
	 * 
	 * Phase 0 = Sound Effects
	 * Phase 1 = Leaves Begin to Grow
	 * Phase 2 = Flowers Appear
	 * Phase 3 = Gray Apples Replace Flowers
	 * Phase 4 = Apples Mature
	 * Phase 5 = Apples and Leaves decay.
	 */
	public static String phaseDesc( int phase )
	{
		switch ( phase )
		{
			case 0:
				return "Timber Wolves Howl";
			case 1:
				return "Leaves Grow";
			case 2:
				return "Flowers Grow";
			case 3:
				return "Gray Apples Grow";
			case 4:
				return "Zap Apples Mature";
			case 5:
				return "Apples and Leaves Decay";
			default:
				return "<Invalid Phase>";
		}
	}
	
	public boolean phase( int phase )
	{
		switch ( phase )
		{
			case 0:
				return day == 0;
			case 1:
				return ( day >= 1 && day < 5 && !finished[1] );
			case 2:
				return ( day >= 2 && day < 5 && !finished[2] );
			case 3:
				return ( day >= 3 && day < 5 && !finished[3] );
			case 4:
				return ( day >= 4 && day < 5 && !finished[4] );
			case 5:
				return ( day >= 5 && !finished[5] );
			default:
				return false;
		}
	}
	
	/**
	 * Does a delay check for each phase
	 * We cut a delay in half on fastSpeed days for REASONS!
	 */
	private boolean delay( int i, boolean fastSpeed )
	{
		if ( delays[i]-- < 0 )
		{
			delays[i] = fastSpeed ? delaysDefault[i] / 2 : delaysDefault[i];
			return true;
		}
		return false;
	}
	
	@Override
	public void updateEntity()
	{
		try
		{
			if ( worldObj.isRemote )
				return;
			
			/**
			 * Play the Timber Wolf howl at random chance of 1 out of a 1000.
			 * worldObj.playSoundEffect( xCoord, yCoord, zCoord, "zapapples:zapapple_wolf_zaphowl", 1.0F, 1.0F );
			 */
			if ( phase( 0 ) && delay( 0, false ) && ZapApples.playHowlSound && rand.nextInt( 1000 ) == 0 )
				worldObj.playSound( xCoord, yCoord, zCoord, "zapapples:zaphowl", 1.0F, 1.0F, false );
			
			/**
			 * The day is between 2 and 5
			 * If it's day 3+, then we go faster
			 * We also don't want to try and complete phases 1-4 after phase 5, by phase 5 everything needs to reset.
			 */
			if ( phase( 1 ) && delay( 1, day > 1 ) )
				dayTwoTick();
			
			if ( phase( 2 ) && delay( 2, day > 2 ) )
				dayThreeTick();
			
			if ( phase( 3 ) && delay( 3, day > 3 ) )
				dayFourTick();
			
			if ( phase( 4 ) && delay( 4, day > 4 ) )
				dayFiveTick();
			
			if ( phase( 5 ) && delay( 5, day > 5 ) )
				daySixTick();
			
			// We repeat the cycle every 25 days
			if ( day > 25 )
				resetValues();
			
			for ( int i = 0; i < 6; i++ )
				delays[i]--; // Tick Delays Down
		}
		catch ( Throwable t )
		{
			t.printStackTrace();
			FMLLog.bigWarning( "Exception Thrown in the Zap Apple Tick Cycle. Please kindly report this stacktrace on the author's GitHub at https://github.com/ChioriGreene/ZapAppleMod/issues The tick cycle has been reset but is not a permanent fix." );
			resetValues();
		}
	}
	
	private void dayTwoTick()
	{
		// Generate Leaf Positions
		if ( leafCopy == null )
		{
			leafCopy = new ArrayList<OrderedTriple>( leafPositions );
			index[1] = 0;
			indexMax[1] = leafCopy.size();
		}
		
		if ( index[1] >= indexMax[1] )
		{
			index[1] = 0;
			leafCopy = null;
			finished[1] = true;
		}
		else
		{
			OrderedTriple pos = ( OrderedTriple ) leafCopy.get( index[1]++ );
			if ( ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == Blocks.air ) || ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == Blocks.snow ) || ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == Blocks.leaves ) || ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == Blocks.vine ) )
			{
				if ( ( ZapApples.lightningEffect ) && ( rand.nextInt( 40 ) == 1 ) )
				{
					int x = xCoord + rand.nextInt( 21 ) - 9;
					int y = yCoord;
					int z = zCoord + rand.nextInt( 21 ) - 9;
					worldObj.addWeatherEffect( new EntityLightningBolt( worldObj, x, y, z ) );
				}
				worldObj.setBlock( pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapAppleLeaves );
			}
		}
	}
	
	private void dayThreeTick()
	{
		if ( appleCopy == null )
		{
			appleCopy = new ArrayList<OrderedTriple>( applePositions.keySet() );
			index[2] = 0;
			indexMax[2] = appleCopy.size();
			
			if ( ZapApples.lightningEffect )
			{
				int x = xCoord + rand.nextInt( 21 ) - 9;
				int y = yCoord;
				int z = zCoord + rand.nextInt( 21 ) - 9;
				worldObj.addWeatherEffect( new EntityLightningBolt( worldObj, x, y, z ) );
			}
		}
		
		if ( index[2] >= indexMax[2] )
		{
			index[2] = 0;
			
			if ( !phase( 1 ) )
			{
				// We can't place flowers until leaves are fully done
				appleCopy = null;
				finished[2] = true;
			}
		}
		else
		{
			OrderedTriple pos = ( OrderedTriple ) appleCopy.get( index[2]++ );
			if ( ( ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == Blocks.air ) || ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == Blocks.snow ) || ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == Blocks.leaves ) || ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == Blocks.vine ) ) && ( ZapApples.zapAppleFlowers.canPlaceBlockOnSide( worldObj, pos.getX(), pos.getY(), pos.getZ(), ( ( Integer ) applePositions.get( pos ) ).intValue() ) ) )
			{
				worldObj.setBlock( pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapAppleFlowers );
				ZapApples.zapAppleFlowers.updateBlockMetadata( worldObj, pos.getX(), pos.getY(), pos.getZ(), ( ( Integer ) applePositions.get( pos ) ).intValue(), 0.0F, 0.0F, 0.0F );
			}
		}
	}
	
	private void dayFourTick()
	{
		if ( appleCopy == null )
		{
			appleCopy = new ArrayList<OrderedTriple>( applePositions.keySet() );
			index[3] = 0;
			indexMax[3] = appleCopy.size();
		}
		
		if ( index[3] >= indexMax[3] )
		{
			index[3] = 0;
			
			if ( !phase( 1 ) )
			{
				// We can't place flowers until leaves are fully done
				appleCopy = null;
				finished[3] = true;
			}
		}
		else
		{
			OrderedTriple pos = ( OrderedTriple ) appleCopy.get( index[3]++ );
			if ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == ZapApples.zapAppleFlowers )
			{
				worldObj.setBlock( pos.getX(), pos.getY(), pos.getZ(), ZapApples.grayApple );
				PacketHandler.sendToDimension( new SendEffectsPacket( 0, pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapAppleFlowers, 0 ), worldObj.provider.dimensionId );
				ZapApples.grayApple.updateBlockMetadata( worldObj, pos.getX(), pos.getY(), pos.getZ(), ( ( Integer ) applePositions.get( pos ) ).intValue(), 0.0F, 0.0F, 0.0F );
			}
		}
	}
	
	private void dayFiveTick()
	{
		if ( appleCopy == null )
		{
			appleCopy = new ArrayList<OrderedTriple>( applePositions.keySet() );
			index[4] = 0;
			indexMax[4] = appleCopy.size();
			
			if ( ZapApples.lightningEffect )
			{
				int x = xCoord + rand.nextInt( 21 ) - 9;
				int y = yCoord;
				int z = zCoord + rand.nextInt( 21 ) - 9;
				worldObj.addWeatherEffect( new EntityLightningBolt( worldObj, x, y, z ) );
			}
		}
		
		if ( index[4] >= indexMax[4] )
		{
			index[4] = 0;
			
			if ( !phase( 1 ) )
			{
				// We can't place flowers until leaves are fully done
				appleCopy = null;
				finished[4] = true;
			}
		}
		else
		{
			OrderedTriple pos = ( OrderedTriple ) appleCopy.get( index[4]++ );
			if ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == ZapApples.grayApple )
			{
				if ( ( ZapApples.lightningEffect ) && ( rand.nextInt( 40 ) == 1 ) )
				{
					int x = xCoord + rand.nextInt( 21 ) - 9;
					int y = yCoord;
					int z = zCoord + rand.nextInt( 21 ) - 9;
					worldObj.addWeatherEffect( new EntityLightningBolt( worldObj, x, y, z ) );
				}
				worldObj.setBlock( pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapApple, 0, 2 );
				PacketHandler.getDispatcher().sendToDimension( new SendEffectsPacket( 1, pos.getX(), pos.getY(), pos.getZ(), ZapApples.grayApple, 0 ), worldObj.provider.dimensionId );
				ZapApples.zapApple.updateBlockMetadata( worldObj, pos.getX(), pos.getY(), pos.getZ(), ( ( Integer ) applePositions.get( pos ) ).intValue(), 0.0F, 0.0F, 0.0F );
			}
		}
	}
	
	private void daySixTick()
	{
		if ( leafCopy == null || appleCopy == null )
		{
			leafCopy = new ArrayList<OrderedTriple>( leafPositions );
			appleCopy = new ArrayList<OrderedTriple>( applePositions.keySet() );
			index[5] = 0;
			indexMax[5] = appleCopy.size() + leafCopy.size();
		}
		
		if ( index[5] >= indexMax[5] )
		{
			index[5] = 0;
			leafCopy = null;
			appleCopy = null;
			finished[5] = true;
		}
		else
		{
			if ( index[5] < appleCopy.size() )
			{
				OrderedTriple pos = ( OrderedTriple ) appleCopy.get( index[5] );
				if ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == ZapApples.zapApple || worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == ZapApples.grayApple )
				{
					PacketHandler.getDispatcher().sendToDimension( new SendEffectsPacket( 1, pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapApple, 0 ), worldObj.provider.dimensionId );
					worldObj.setBlockToAir( pos.getX(), pos.getY(), pos.getZ() );
				}
			}
			
			if ( index[5] >= appleCopy.size() )
			{
				OrderedTriple pos = ( OrderedTriple ) leafCopy.get( index[5] - appleCopy.size() );
				if ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == ZapApples.zapAppleLeaves )
					ZapApples.zapAppleLeaves.removeLeaves( worldObj, pos.getX(), pos.getY(), pos.getZ() );
			}
			
			index[5]++;
		}
	}
	
	public void readFromNBT( NBTTagCompound tag )
	{
		super.readFromNBT( tag );
		
		resetValues();
		day = tag.getInteger( "day" );
		
		for ( int i = 0; i < 6; i++ )
			index[i] = tag.getInteger( "finished[" + index + "]" );
		
		for ( int i = 0; i < 6; i++ )
			finished[i] = tag.getBoolean( "finished[" + i + "]" );
		
		NBTTagCompound leaves = tag.getCompoundTag( "leaves" );
		int leafSize = leaves.getInteger( "size" );
		for ( int i = 0; i < leafSize; i++ )
		{
			leafPositions.add( OrderedTriple.valueOf( leaves.getString( "" + i ) ) );
		}
		
		NBTTagCompound logs = tag.getCompoundTag( "logs" );
		int logSize = logs.getInteger( "size" );
		for ( int i = 0; i < logSize; i++ )
		{
			logPositions.add( OrderedTriple.valueOf( logs.getString( "" + i ) ) );
		}
		
		NBTTagCompound apples = tag.getCompoundTag( "apples" );
		int appleSize = apples.getInteger( "size" );
		for ( int i = 0; i < appleSize; i++ )
		{
			applePositions.put( OrderedTriple.valueOf( apples.getString( "key:" + i ) ), Integer.valueOf( apples.getInteger( "value:" + i ) ) );
		}
	}
	
	public void writeToNBT( NBTTagCompound tag )
	{
		super.writeToNBT( tag );
		
		tag.setInteger( "day", day );
		
		for ( int i = 0; i < 6; i++ )
			tag.setInteger( "finished[" + index + "]", index[i] );
		
		for ( int i = 0; i < 6; i++ )
			tag.setBoolean( "finished[" + i + "]", finished[i] );
		
		NBTTagCompound leaves = new NBTTagCompound();
		leaves.setInteger( "size", leafPositions.size() );
		for ( int i = 0; i < leafPositions.size(); i++ )
		{
			leaves.setString( "" + i, ( ( OrderedTriple ) leafPositions.get( i ) ).toString() );
		}
		tag.setTag( "leaves", leaves );
		
		NBTTagCompound logs = new NBTTagCompound();
		logs.setInteger( "size", logPositions.size() );
		for ( int i = 0; i < logPositions.size(); i++ )
		{
			logs.setString( "" + i, ( ( OrderedTriple ) logPositions.get( i ) ).toString() );
		}
		tag.setTag( "logs", logs );
		
		NBTTagCompound apples = new NBTTagCompound();
		List<OrderedTriple> temp = new ArrayList<OrderedTriple>( applePositions.keySet() );
		apples.setInteger( "size", temp.size() );
		for ( int i = 0; i < temp.size(); i++ )
		{
			apples.setString( "key:" + i, ( ( OrderedTriple ) temp.get( i ) ).toString() );
			apples.setInteger( "value:" + i, ( ( Integer ) applePositions.get( temp.get( i ) ) ).intValue() );
		}
		tag.setTag( "apples", apples );
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
	
	public void notifyRemoval()
	{
		DaytimeManager.unregisterListener( this );
		
		for ( OrderedTriple pos : logPositions )
		{
			int meta = worldObj.getBlockMetadata( pos.getX(), pos.getY(), pos.getZ() );
			if ( meta < 0 )
				meta = 0;
			if ( worldObj.getBlock( pos.getX(), pos.getY(), pos.getZ() ) == ZapApples.zapAppleLog )
			{
				worldObj.setBlock( pos.getX(), pos.getY(), pos.getZ(), ZapApples.zapAppleDeadLog, meta, 0 );
				worldObj.markBlockForUpdate( pos.getX(), pos.getY(), pos.getZ() );
			}
		}
	}
	
	@Override
	public World getWorld()
	{
		return worldObj;
	}
}
