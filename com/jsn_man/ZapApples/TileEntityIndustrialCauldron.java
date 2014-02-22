package com.jsn_man.ZapApples;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class TileEntityIndustrialCauldron extends TileEntity implements IInventory, ISidedInventory, ITankContainer{
	
	public TileEntityIndustrialCauldron(){
		tank = new LiquidTank(LiquidContainerRegistry.BUCKET_VOLUME * 4);
	}
	
	public int getSizeInventory(){
		return 1;
	}
	
	public ItemStack getStackInSlot(int i){
		return content;
	}
	
	public ItemStack decrStackSize(int i, int amount){
		if(content != null){
			if(content.stackSize <= amount){
				ItemStack stack = content;
				content = null;
				onInventoryChanged();
				return stack;
			}else{
				ItemStack stack = content.splitStack(amount);
				if(content.stackSize == 0){
					content = null;
				}
				onInventoryChanged();
				return stack;
			}
		}else{
			return null;
		}
	}
	
	public ItemStack getStackInSlotOnClosing(int i){
		if(content != null){
			ItemStack stack = content;
			content = null;
			return stack;
		}else{
			return null;
		}
	}
	
	public void setInventorySlotContents(int i, ItemStack stack){
		content = stack;
		if(stack != null && stack.stackSize > getInventoryStackLimit()){
			stack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}
	
	public String getInvName(){
		return "IndustrialCauldron";
	}
	
	public int getInventoryStackLimit(){
		return 1;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player){
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : player.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64.0D;
	}
	
	public void openChest(){
		
	}
	
	public void closeChest(){
		
	}
	
	public int getStartInventorySide(ForgeDirection side){
		if(content == null){
			return 0;
		}else{
			return -1;
		}
	}
	
	public int getSizeInventorySide(ForgeDirection side){
		if(content == null){
			return 1;
		}else{
			return 0;
		}
	}
	
	public void updateEntity(){
		if(!worldObj.isRemote){
			if(hasUpdate){
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				hasUpdate = false;
				/*Packet packet = getDescriptionPacket();
				
				List<EntityPlayer> players = worldObj.playerEntities;
				for(EntityPlayer player : players){
					PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
				}
				hasUpdate = false;*/
			}
			
			int filled = tank.getLiquid() != null ? tank.getLiquid().amount : 0;
			distribute(filled, xCoord + 1, yCoord, zCoord);
			distribute(filled, xCoord - 1, yCoord, zCoord);
			distribute(filled, xCoord, yCoord, zCoord + 1);
			distribute(filled, xCoord, yCoord, zCoord - 1);
			
			if(tank.getLiquid() != null){
				moveLiquidBelow();
			}
		}
	}
	
	public void distribute(int filled, int x, int y, int z){
		TileEntityIndustrialCauldron tile = getCauldronAt(worldObj, x, y, z);
		if(tile != null && tile.tank.getLiquid() != null && tile.tank.getLiquid().amount - 2 >= filled){
			LiquidStack resource = tile.tank.getLiquid().copy();
			resource.amount = (tile.tank.getLiquid().amount + filled) / 2 - filled;
			fill(0, resource, true);
			tile.drain(0, tile.tank.getLiquid().amount - (resource.amount + filled), true);
		}
	}
	
	public void breakDown(){
		if(content != null && ++breakLevel >= 8){
			breakLevel = 0;
			setHoldingItem(0, 0);
			fill(0, new LiquidStack(ZapApples.jamStill, LiquidContainerRegistry.BUCKET_VOLUME), true);
		}
	}
	
	public void setHoldingItem(ItemStack stack){
		setHoldingItem(stack.itemID, stack.getItemDamage());
	}
	
	public void setHoldingItem(int id, int meta){
		content = new ItemStack(id, 1, meta);
		if(id == 0){
			content = null;
		}
		onInventoryChanged();
	}
	
	public Packet getDescriptionPacket(){
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		Packet packet = new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
		packet.isChunkDataPacket = true;
		return packet;
		
		/*ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(out);
		
		try{
			dataStream.writeByte(1);
			dataStream.writeInt(xCoord);
			dataStream.writeInt(yCoord);
			dataStream.writeInt(zCoord);
			if(content != null){
				dataStream.writeInt(content.itemID);
				dataStream.writeInt(content.getItemDamage());
			}else{
				dataStream.writeInt(0);
				dataStream.writeInt(0);
			}
			if(tank.getLiquid() != null){
				dataStream.writeInt(tank.getLiquid().itemID);
				dataStream.writeInt(tank.getLiquid().itemMeta);
				dataStream.writeInt(tank.getLiquid().amount);
			}else{
				dataStream.writeInt(0);
				dataStream.writeInt(0);
				dataStream.writeInt(0);
			}
		}catch(IOException e){
			FMLLog.log(Level.SEVERE, e, "Zap Apples cannot write a packet!");
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		
		packet.channel = "zap";
		packet.data = out.toByteArray();
		packet.length = out.size();
		
		return packet;*/
	}
	
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet){
		worldObj.getBlockTileEntity(xCoord, yCoord, zCoord).readFromNBT(packet.customParam1);
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	public void moveLiquidBelow(){
		TileEntityIndustrialCauldron below = getCauldronBelow(this);
		if(below == null){
			return;
		}
		
		int used = below.tank.fill(tank.getLiquid(), true);
		if(used > 0){
			hasUpdate = true;
			below.hasUpdate = true;
			
			tank.drain(used, true);
		}
	}
	
	public TileEntityIndustrialCauldron getBottomCauldron(){
		TileEntityIndustrialCauldron lastTile = this;
		
		while(true){
			TileEntityIndustrialCauldron below = getCauldronBelow(lastTile);
			if(below != null){
				return lastTile = below;
			}else{
				break;
			}
		}
		
		return lastTile;
	}
	
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill){
		return fill(0, resource, doFill);
	}
	
	/*public int fill(int tankIndex, LiquidStack resource, boolean doFill){
		if(tankIndex != 0 || resource == null){
			return 0;
		}
		
		resource = resource.copy();
		int totalUsed = 0;
		
		List<OrderedTriple> list = getAdjacentCauldrons(worldObj, new LinkedList(), xCoord, yCoord, zCoord);
		int distributionAmount = resource.amount / list.size();
		for(OrderedTriple loc : list){
			TileEntityIndustrialCauldron cauldronToFill = getCauldronAt(worldObj, loc);
			if(cauldronToFill != null){
				LiquidStack toFill = resource.copy();
				toFill.amount = distributionAmount <= resource.amount ? distributionAmount : resource.amount;
				int used = cauldronToFill.transfer(0, toFill, true);
				resource.amount -= used;
				totalUsed += used;
			}
		}
		
		return totalUsed;
	}*/
	
	public int fill(int tankIndex, LiquidStack resource, boolean doFill){
		if(tankIndex != 0 || resource == null){
			return 0;
		}
		
		resource = resource.copy();
		int totalUsed = 0;
		TileEntityIndustrialCauldron cauldronToFill = getBottomCauldron();
		while(cauldronToFill != null && resource.amount > 0){
			short toDivideBy = 0;
			
			
			int used = cauldronToFill.tank.fill(resource, doFill);
			resource.amount -= used;
			
			if(used > 0){
				cauldronToFill.hasUpdate = true;
			}
			
			cauldronToFill = getCauldronAbove(cauldronToFill);
			totalUsed += used;
		}
		if(totalUsed > 0){
			hasUpdate = true;
		}
		
		return totalUsed;
	}
	
	public LiquidStack drain(ForgeDirection from, int maxEmpty, boolean doDrain){
		return drain(0, maxEmpty, doDrain);
	}
	
	public LiquidStack drain(int tankIndex, int maxEmpty, boolean doDrain){
		TileEntityIndustrialCauldron bottom = getBottomCauldron();
		bottom.hasUpdate = true;
		return bottom.tank.drain(maxEmpty, doDrain);
	}
	
	public ILiquidTank[] getTanks(ForgeDirection direction){
		LiquidTank compositeTank = new LiquidTank(tank.getCapacity());
		
		TileEntityIndustrialCauldron tile = getBottomCauldron();
		
		int capacity = tank.getCapacity();
		
		if(tile != null && tile.tank.getLiquid() != null){
			compositeTank.setLiquid(tile.tank.getLiquid().copy());
		}else{
			return new ILiquidTank[]{compositeTank};
		}
		
		tile = getCauldronAbove(tile);
		
		while(tile != null){
			LiquidStack liquid = tile.tank.getLiquid();
			
			if(liquid == null || liquid.amount == 0){
				//no
			}else if(!compositeTank.getLiquid().isLiquidEqual(liquid)){
				break;
			}else{
				compositeTank.getLiquid().amount += liquid.amount;
			}
			
			capacity += tile.tank.getCapacity();
			tile = getCauldronAbove(tile);
		}
		
		compositeTank.setCapacity(capacity);
		return new ILiquidTank[]{compositeTank};
	}
	
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type){
		if(direction == ForgeDirection.DOWN && worldObj != null && worldObj.getBlockId(xCoord, yCoord - 1, zCoord) != ZapApples.industrialCauldron.blockID){
			return tank;
		}
		return null;
	}
	
	public void readFromNBT(NBTTagCompound tag){
		super.readFromNBT(tag);
		
		if(tag.getBoolean("hasItem")){
			content = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("item"));
		}else{
			content = null;
		}
		
		if(tag.getBoolean("hasLiquid")){
			LiquidStack liquid = new LiquidStack(0, 0, 0);
			liquid.readFromNBT(tag.getCompoundTag("tank"));
			tank.setLiquid(liquid);
		}else{
			tank.setLiquid(null);
		}
	}
	
	public void writeToNBT(NBTTagCompound tag){
		super.writeToNBT(tag);
		
		if(content != null){
			tag.setTag("item", content.writeToNBT(new NBTTagCompound()));
			tag.setBoolean("hasItem", true);
		}else{
			tag.setBoolean("hasItem", false);
		}
		
		if(tank.getLiquid() != null){
			tag.setTag("tank", tank.getLiquid().writeToNBT(new NBTTagCompound()));
			tag.setBoolean("hasLiquid", true);
		}else{
			tag.setBoolean("hasLiquid", false);
		}
	}
	
	public void onInventoryChanged(){
		super.onInventoryChanged();
		if(!worldObj.isRemote){
			hasUpdate = true;
			if(content != null && content.itemID != ZapApples.zapApple.blockID){
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord + 1, zCoord, content.copy()));
				content = null;
			}else if(content != null && content.stackSize > 1){
				worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord + 1, zCoord, new ItemStack(content.itemID, content.stackSize - 1, content.getItemDamage())));
				content.stackSize = 1;
			}
		}
	}
	
	public OrderedTriple getLocation(){
		return new OrderedTriple(xCoord, yCoord, zCoord);
	}
	
	public static TileEntityIndustrialCauldron getCauldronBelow(TileEntityIndustrialCauldron tile){
		TileEntity below = tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord - 1, tile.zCoord);
		if(below instanceof TileEntityIndustrialCauldron){
			return (TileEntityIndustrialCauldron)below;
		}else{
			return null;
		}
	}
	
	public static TileEntityIndustrialCauldron getCauldronAbove(TileEntityIndustrialCauldron tile){
		TileEntity below = tile.worldObj.getBlockTileEntity(tile.xCoord, tile.yCoord + 1, tile.zCoord);
		if(below instanceof TileEntityIndustrialCauldron){
			return (TileEntityIndustrialCauldron)below;
		}else{
			return null;
		}
	}
	
	public static TileEntityIndustrialCauldron getCauldronAt(World world, OrderedTriple loc){
		TileEntity tile = world.getBlockTileEntity(loc.getX(), loc.getY(), loc.getZ());
		if(tile instanceof TileEntityIndustrialCauldron){
			return (TileEntityIndustrialCauldron)tile;
		}else{
			return null;
		}
	}
	
	public static TileEntityIndustrialCauldron getCauldronAt(World world, int x, int y, int z){
		return getCauldronAt(world, new OrderedTriple(x, y, z));
	}
	
	public static List getAdjacentCauldrons(World world, List list, int x, int y, int z){
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileEntityIndustrialCauldron){
			tile = ((TileEntityIndustrialCauldron)tile).getBottomCauldron();
			if(!list.contains(((TileEntityIndustrialCauldron)tile).getLocation())){
				list.add(((TileEntityIndustrialCauldron)tile).getLocation());
				getAdjacentCauldrons(world, list, x + 1, y, z);
				getAdjacentCauldrons(world, list, x - 1, y, z);
				getAdjacentCauldrons(world, list, x + 1, y, z + 1);
				getAdjacentCauldrons(world, list, x + 1, y, z - 1);
			}
		}
		return list;
	}
	
	public int breakLevel;
	public LiquidTank tank;
	public ItemStack content;
	protected boolean hasUpdate;
}