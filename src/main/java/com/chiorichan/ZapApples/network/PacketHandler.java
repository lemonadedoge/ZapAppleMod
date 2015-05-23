package com.chiorichan.ZapApples.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.chiorichan.ZapApples.ZapApples;
import com.chiorichan.ZapApples.network.packet.bidirectional.AbstractBiMessageHandler;
import com.chiorichan.ZapApples.network.packet.client.AbstractClientMessageHandler;
import com.chiorichan.ZapApples.network.packet.client.SendEffectsPacket;
import com.chiorichan.ZapApples.network.packet.client.SendEffectsPacketHandler;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@SuppressWarnings( "unused" )
public class PacketHandler
{
	private static byte packetId = 0;
	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel( ZapApples.MOD_ID );
	
	public static final void registerPackets()
	{
		/*
		 * registerMessage( OpenGuiMessage.Handler.class, OpenGuiMessage.class );
		 * registerMessage( SyncPlayerPropsMessage.Handler.class, SyncPlayerPropsMessage.class );
		 * registerMessage( PlaySoundPacket.Handler.class, PlaySoundPacket.class );
		 * registerBiMessage( AttackTimePacket.Handler.class, AttackTimePacket.class );
		 */
		registerBiMessage( SendEffectsPacketHandler.class, SendEffectsPacket.class );
	}
	
	private static final <REQ extends IMessage, REPLY extends IMessage> void registerMessage( Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass, Side side )
	{
		dispatcher.registerMessage( handlerClass, messageClass, packetId++, side );
	}
	
	private static final <REQ extends IMessage, REPLY extends IMessage> void registerBiMessage( Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass )
	{
		if ( AbstractBiMessageHandler.class.isAssignableFrom( handlerClass ) )
		{
			dispatcher.registerMessage( handlerClass, messageClass, packetId, Side.CLIENT );
			dispatcher.registerMessage( handlerClass, messageClass, packetId++, Side.SERVER );
		}
		else
		{
			throw new IllegalArgumentException( "Cannot register " + handlerClass.getName() + " on both sides - must extend AbstractBiMessageHandler!" );
		}
	}
	
	private static final <REQ extends IMessage, REPLY extends IMessage> void registerMessage( Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass )
	{
		Side side = AbstractClientMessageHandler.class.isAssignableFrom( handlerClass ) ? Side.CLIENT : Side.SERVER;
		dispatcher.registerMessage( handlerClass, messageClass, packetId++, side );
	}
	
	public static final SimpleNetworkWrapper getDispatcher()
	{
		return dispatcher;
	}
	
	/**
	 * Send this message to the specified player.
	 * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	 */
	public static final void sendTo( IMessage message, EntityPlayerMP player )
	{
		dispatcher.sendTo( message, player );
	}
	
	/**
	 * Send this message to everyone within a certain range of a point.
	 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, NetworkRegistry.TargetPoint)}
	 */
	public static final void sendToAllAround( IMessage message, NetworkRegistry.TargetPoint point )
	{
		dispatcher.sendToAllAround( message, point );
	}
	
	/**
	 * Sends a message to everyone within a certain range of the coordinates in the same dimension.
	 */
	public static final void sendToAllAround( IMessage message, int dimension, double x, double y, double z, double range )
	{
		sendToAllAround( message, new NetworkRegistry.TargetPoint( dimension, x, y, z, range ) );
	}
	
	/**
	 * Sends a message to everyone within a certain range of the player provided.
	 */
	public static final void sendToAllAround( IMessage message, EntityPlayer player, double range )
	{
		sendToAllAround( message, player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, range );
	}
	
	/**
	 * Send this message to everyone within the supplied dimension.
	 * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
	 */
	public static final void sendToDimension( IMessage message, int dimensionId )
	{
		dispatcher.sendToDimension( message, dimensionId );
	}
	
	/**
	 * Send this message to the server.
	 * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
	 */
	public static final void sendToServer( IMessage message )
	{
		dispatcher.sendToServer( message );
	}
}
