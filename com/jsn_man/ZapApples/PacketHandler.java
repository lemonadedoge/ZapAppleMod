package com.jsn_man.ZapApples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraftforge.liquids.LiquidStack;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler{

	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player){
		DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		byte id = 0;
		int x = 0, y = 0, z = 0, blockID = 0, meta = 0;
		
		try{
			id = dataStream.readByte();
			if(id == 1){
				x = dataStream.readInt();
				y = dataStream.readInt();
				z = dataStream.readInt();
				blockID = dataStream.readInt();
				meta = dataStream.readInt();
			}
		}catch(IOException e){
			FMLLog.log(Level.SEVERE, e, "Zap Apples cannot read a packet!");
		}
		
		if(id == 1){
			ModLoader.getMinecraftInstance().effectRenderer.addBlockDestroyEffects(x, y, z, blockID, meta);
		}
	}
	
	public static void sendDestroyEffectToPlayers(List<EntityPlayer> players, int x, int y, int z, int blockID, int meta){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(out);
		
		try{
			dataStream.writeByte(1);
			dataStream.writeInt(x);
			dataStream.writeInt(y);
			dataStream.writeInt(z);
			dataStream.writeInt(blockID);
			dataStream.writeInt(meta);
		}catch(IOException e){
			FMLLog.log(Level.SEVERE, e, "Zap Apples cannot write a packet!");
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		
		packet.channel = "zap";
		packet.data = out.toByteArray();
		packet.length = out.size();
		
		for(EntityPlayer player : players){
			PacketDispatcher.sendPacketToPlayer(packet, (Player)player);
		}
	}
}