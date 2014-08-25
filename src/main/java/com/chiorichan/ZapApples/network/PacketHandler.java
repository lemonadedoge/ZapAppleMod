package com.chiorichan.ZapApples.network;

import com.chiorichan.ZapApples.network.packet.AbstractMessageHandler;
import com.chiorichan.ZapApples.network.packet.bidirectional.AbstractBiMessageHandler;
import com.chiorichan.ZapApples.network.packet.bidirectional.AttackTimePacket;
import com.chiorichan.ZapApples.network.packet.bidirectional.PlaySoundPacket;
import com.chiorichan.ZapApples.network.packet.client.AbstractClientMessageHandler;
import com.chiorichan.ZapApples.network.packet.client.SyncPlayerPropsMessage;
import com.chiorichan.ZapApples.network.packet.server.AbstractServerMessageHandler;
import com.chiorichan.ZapApples.network.packet.server.OpenGuiMessage;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler
{
	private static byte packetId = 0;
	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel( "zap" );
	
	public static final void registerPackets()
	{
		/*registerMessage( OpenGuiMessage.Handler.class, OpenGuiMessage.class );
		registerMessage( SyncPlayerPropsMessage.Handler.class, SyncPlayerPropsMessage.class );
		registerMessage( PlaySoundPacket.Handler.class, PlaySoundPacket.class );
		registerBiMessage( AttackTimePacket.Handler.class, AttackTimePacket.class );*/
	}
	
	private static final <REQ extends IMessage, REPLY extends IMessage> void registerMessage( Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass, Side side )
	{
		dispatcher.registerMessage( handlerClass, messageClass, packetId++, side );
	}
	
	private static final <REQ extends IMessage, REPLY extends IMessage> void registerBiMessage( Class<? extends IMessageHandler<REQ, REPLY>> handlerClass, Class<REQ> messageClass )
	{
		dispatcher.registerMessage( handlerClass, messageClass, packetId, Side.CLIENT );
		dispatcher.registerMessage( handlerClass, messageClass, packetId++, Side.SERVER );
	}
	
	private static final <REQ extends IMessage> void registerMessage( Class<? extends AbstractMessageHandler<REQ>> handlerClass, Class<REQ> messageClass )
	{
		if ( AbstractClientMessageHandler.class.isAssignableFrom( handlerClass ) )
		{
			registerMessage( handlerClass, messageClass, Side.CLIENT );
		}
		else if ( AbstractServerMessageHandler.class.isAssignableFrom( handlerClass ) )
		{
			registerMessage( handlerClass, messageClass, Side.SERVER );
		}
		else if ( AbstractBiMessageHandler.class.isAssignableFrom( handlerClass ) )
		{
			registerBiMessage( handlerClass, messageClass );
		}
		else
		{
			throw new IllegalArgumentException( "Cannot determine on which Side(s) to register " + handlerClass.getName() + " - unrecognized handler class!" );
		}
	}
	
	public static final SimpleNetworkWrapper getDispatcher()
	{
		return dispatcher;
	}
	
	
	
	/*
	public void onPacketData( NetworkManager manager, C17PacketCustomPayload packet, Player player )
	{
		DataInputStream dataStream = new DataInputStream( new ByteArrayInputStream( packet.data ) );
		
		byte id = 0;
		int x = 0;
		int y = 0;
		int z = 0;
		Block block = Block.getBlockById( 0 );
		int meta = 0;
		try
		{
			id = dataStream.readByte();
			if ( id == 1 )
			{
				x = dataStream.readInt();
				y = dataStream.readInt();
				z = dataStream.readInt();
				block = Block.getBlockById( dataStream.readInt() );
				meta = dataStream.readInt();
			}
		}
		catch ( IOException e )
		{
			FMLLog.severe( "Zap Apples cannot read a packet!", e );
		}
		
		if ( id == 1 )
			Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects( x, y, z, block, meta );
	}
	
	public static void sendDestroyEffectToPlayers( List<EntityPlayer> players, int x, int y, int z, Block block, int meta )
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream( out );
		try
		{
			dataStream.writeByte( 1 );
			dataStream.writeInt( x );
			dataStream.writeInt( y );
			dataStream.writeInt( z );
			dataStream.writeInt( block );
			dataStream.writeInt( meta );
		}
		catch ( IOException e )
		{
			FMLLog.severe( "Zap Apples cannot write a packet!", e );
		}
		
		S3FPacketCustomPayload packet = new S3FPacketCustomPayload();
		
		packet.channel = "zap";
		packet.data = out.toByteArray();
		packet.length = out.size();
		
		for ( EntityPlayer player : players )
			PacketDispatcher.sendPacketToPlayer( packet, (Player) player );
	}*/
}
