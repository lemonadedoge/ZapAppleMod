package com.chiorichan.ZapApples;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.chiorichan.ZapApples.blocks.BlockCake;
import com.chiorichan.ZapApples.blocks.BlockDoughFluid;
import com.chiorichan.ZapApples.blocks.BlockFlour;
import com.chiorichan.ZapApples.blocks.BlockGrayApple;
import com.chiorichan.ZapApples.blocks.BlockJar;
import com.chiorichan.ZapApples.blocks.BlockPie;
import com.chiorichan.ZapApples.blocks.BlockStoneDoor;
import com.chiorichan.ZapApples.blocks.BlockZapApple;
import com.chiorichan.ZapApples.blocks.BlockZapAppleDeadLog;
import com.chiorichan.ZapApples.blocks.BlockZapAppleFlowers;
import com.chiorichan.ZapApples.blocks.BlockZapAppleJam;
import com.chiorichan.ZapApples.blocks.BlockZapAppleLeaves;
import com.chiorichan.ZapApples.blocks.BlockZapAppleLog;
import com.chiorichan.ZapApples.blocks.BlockZapApplePlanks;
import com.chiorichan.ZapApples.blocks.BlockZapAppleSapling;
import com.chiorichan.ZapApples.blocks.BlockZapAppleWoodDoor;
import com.chiorichan.ZapApples.items.ItemCake;
import com.chiorichan.ZapApples.items.ItemFluidBucket;
import com.chiorichan.ZapApples.items.ItemIcing;
import com.chiorichan.ZapApples.items.ItemJamFood;
import com.chiorichan.ZapApples.items.ItemJar;
import com.chiorichan.ZapApples.items.ItemPie;
import com.chiorichan.ZapApples.items.ItemStoneDoor;
import com.chiorichan.ZapApples.items.ItemZapApple;
import com.chiorichan.ZapApples.items.ItemZapAppleGray;
import com.chiorichan.ZapApples.items.ItemZapAppleMushed;
import com.chiorichan.ZapApples.items.ItemZapAppleWoodDoor;
import com.chiorichan.ZapApples.mobs.EntityTimberWolf;
import com.chiorichan.ZapApples.network.PacketHandler;
import com.chiorichan.ZapApples.tileentity.TileEntityCake;
import com.chiorichan.ZapApples.tileentity.TileEntityJar;
import com.chiorichan.ZapApples.tileentity.TileEntityPie;
import com.chiorichan.ZapApples.tileentity.TileEntityZapAppleLog;
import com.google.common.collect.Lists;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod( modid = ZapApples.MOD_ID, name = "Zap Apple Mod", version = "1.7.10-2.3alpha" )
public class ZapApples
{
	public static final String MOD_ID = "zapapples";
	
	@Instance( MOD_ID )
	public static ZapApples instance;
	
	@SidedProxy( clientSide = "com.chiorichan.ZapApples.ClientProxy", serverSide = "com.chiorichan.ZapApples.CommonProxy" )
	public static CommonProxy proxy;
	
	public static String[] icings = { "Black", "Red", "Green", "Chocolate", "Blue", "Purple", "Cyan", "Silver", "Gray", "Pink", "Lime", "Yellow", "Light Blue", "Magenta", "Orange", "Vanilla" };
	public static boolean lightningEffect;
	public static boolean spawnTimberWolves;
	public static List<Integer> disableZapAppleTreesInDimensions;
	public static List<Integer> disableZapAppleTreesInBiomes;
	public static int idRender2D;
	public static int idRender3D;
	public static int idRenderCake;
	public static int idRenderPie;
	public static int idRenderApple;
	public static int bucketsPerJar = 6;
	public static BlockZapAppleLog zapAppleLog;
	public static BlockZapAppleDeadLog zapAppleDeadLog;
	public static BlockZapAppleLeaves zapAppleLeaves;
	public static BlockZapAppleSapling zapAppleSapling;
	public static BlockZapAppleFlowers zapAppleFlowers;
	public static BlockGrayApple grayApple;
	public static BlockZapApple zapApple;
	public static BlockJar jar;
	public static BlockZapApplePlanks zapPlanks;
	public static BlockZapAppleWoodDoor blockZapWoodDoor;
	public static BlockStoneDoor blockStoneDoor;
	public static ItemZapAppleWoodDoor itemZapWoodDoor;
	public static ItemStoneDoor itemStoneDoor;
	public static BlockPie pie;
	public static BlockCake cake;
	public static BlockFlour flour;
	public static Fluid zapAppleJam;
	public static Fluid doughFluid;
	public static BlockZapAppleJam zapAppleJamBlock;
	public static BlockDoughFluid doughFluidBlock;
	public static ItemJamFood jamFood;
	public static ItemZapAppleMushed zapAppleMushed;
	public static ItemFluidBucket jamBucket;
	public static ItemFluidBucket doughBucket;
	public static ItemIcing icing;
	
	@EventHandler
	public void preInit( FMLPreInitializationEvent event )
	{
		PacketHandler.registerPackets();
		
		Configuration config = new Configuration( event.getSuggestedConfigurationFile() );
		try
		{
			config.load();
			
			spawnTimberWolves = config.get( "general", "spawnTimberWolves", false, "True will enable the natural spawning of Timber Wolves" ).getBoolean( false );
			lightningEffect = config.get( "general", "lightningEffect", true ).getBoolean( true );
			bucketsPerJar = config.get( "general", "bucketsPerJar", 6 ).getInt( 6 );
			
			disableZapAppleTreesInDimensions = Lists.newArrayList();
			for ( int i : config.get( "general", "disableZapAppleTreesInDimensions", new int[] { -1, 1 }, "Zap Apple Tree generation will be disabled in these dimensions" ).getIntList() )
				disableZapAppleTreesInDimensions.add( i );
			
			disableZapAppleTreesInBiomes = Lists.newArrayList();
			for ( int i : config.get( "general", "disableZapAppleTreesInBiomes", new int[] { 0, 2, 8, 9, 10, 11, 12, 13, 17, 24, 35, 36, 37, 38, 39 }, "Zap Apple Tree generation will be disabled in these biomes. See http://minecraft.gamepedia.com/Biome for vanilla biome ids." ).getIntList() )
				disableZapAppleTreesInBiomes.add( i );
		}
		catch ( Exception e )
		{
			FMLLog.severe( "Zap Apples could not load its config properly.", e );
		}
		finally
		{
			config.save();
		}
		
		zapAppleLog = new BlockZapAppleLog();
		zapAppleDeadLog = new BlockZapAppleDeadLog();
		zapAppleLeaves = new BlockZapAppleLeaves();
		zapAppleSapling = new BlockZapAppleSapling();
		zapAppleFlowers = new BlockZapAppleFlowers();
		grayApple = new BlockGrayApple();
		zapApple = new BlockZapApple();
		jar = new BlockJar();
		zapPlanks = new BlockZapApplePlanks();
		blockZapWoodDoor = new BlockZapAppleWoodDoor();
		itemZapWoodDoor = new ItemZapAppleWoodDoor();
		blockStoneDoor = new BlockStoneDoor();
		itemStoneDoor = new ItemStoneDoor();
		pie = new BlockPie();
		cake = new BlockCake();
		flour = new BlockFlour();
		
		int id = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerModEntity( EntityTimberWolf.class, "timberwolf", id, this, 80, 3, true );
		
		if ( spawnTimberWolves )
			for ( int i = 0; i < BiomeGenBase.getBiomeGenArray().length; i++ )
				if ( BiomeGenBase.getBiomeGenArray()[i] != null )
					EntityRegistry.addSpawn( EntityTimberWolf.class, 6, 1, 3, EnumCreatureType.monster, BiomeGenBase.getBiomeGenArray()[i] );
		
		doughFluid = new Fluid( "dough" );
		doughFluid.setLuminosity( 1 );
		doughFluid.setDensity( 100 );
		
		FluidRegistry.registerFluid( doughFluid );
		
		doughFluidBlock = new BlockDoughFluid( doughFluid, Material.water );
		GameRegistry.registerBlock( doughFluidBlock, "Dough" );
		
		zapAppleJam = new Fluid( "zapAppleJam" );
		zapAppleJam.setLuminosity( 3 );
		zapAppleJam.setDensity( 100 );
		
		FluidRegistry.registerFluid( zapAppleJam );
		
		zapAppleJamBlock = new BlockZapAppleJam( zapAppleJam, Material.water );
		GameRegistry.registerBlock( zapAppleJamBlock, "Zap Apple Jam" );
		
		zapAppleMushed = new ItemZapAppleMushed();
		jamFood = new ItemJamFood();
		icing = new ItemIcing();
		jamBucket = new ItemFluidBucket( zapAppleJamBlock, "zapAppleJamBucket", "jam_bucket" );
		doughBucket = new ItemFluidBucket( doughFluidBlock, "doughBucket", "dough_bucket" );
		
		GameRegistry.registerBlock( zapAppleLog, "zapAppleLog" );
		GameRegistry.registerBlock( zapAppleDeadLog, "zapAppleDeadLog" );
		GameRegistry.registerBlock( zapAppleLeaves, "zapAppleLeaves" );
		GameRegistry.registerBlock( zapAppleSapling, "zapAppleSapling" );
		GameRegistry.registerBlock( zapAppleFlowers, "zapAppleFlowers" );
		GameRegistry.registerBlock( grayApple, ItemZapAppleGray.class, "grayApple" );
		GameRegistry.registerBlock( zapApple, ItemZapApple.class, "zapApple" );
		GameRegistry.registerBlock( jar, ItemJar.class, "jar" );
		GameRegistry.registerBlock( zapPlanks, "zapApplePlanks" );
		GameRegistry.registerBlock( flour, "flour" );
		GameRegistry.registerBlock( cake, ItemCake.class, "cake" );
		GameRegistry.registerBlock( pie, ItemPie.class, "pie" );
		GameRegistry.registerBlock( blockZapWoodDoor, "zapAppleWoodDoor" );
		GameRegistry.registerBlock( blockStoneDoor, "stoneDoor" );
		
		GameRegistry.registerItem( itemZapWoodDoor, "zapAppleWoodDoorItem" );
		GameRegistry.registerItem( itemStoneDoor, "stoneDoorItem" );
		GameRegistry.registerItem( zapAppleMushed, "zapAppleMushed" );
		GameRegistry.registerItem( jamFood, "jamFood" );
		GameRegistry.registerItem( jamBucket, "jamBucket" );
		GameRegistry.registerItem( doughBucket, "doughBucket" );
		GameRegistry.registerItem( icing, "icing" );
		
		GameRegistry.registerTileEntity( TileEntityZapAppleLog.class, "ZapAppleLog" );
		GameRegistry.registerTileEntity( TileEntityJar.class, "Jar" );
		GameRegistry.registerTileEntity( TileEntityPie.class, "ZapAppleCakething" );
		GameRegistry.registerTileEntity( TileEntityCake.class, "ZapAppleCake" );
		
		FluidContainerRegistry.registerFluidContainer( new FluidStack( zapAppleJam, 1000 ), new ItemStack( jamBucket ), new ItemStack( Items.bucket ) );
		FluidContainerRegistry.registerFluidContainer( new FluidStack( doughFluid, 3500 ), new ItemStack( doughBucket ), new ItemStack( Items.bucket ) );
		
		BucketHandler.INSTANCE.buckets.put( zapAppleJamBlock, jamBucket );
		BucketHandler.INSTANCE.buckets.put( doughFluidBlock, doughBucket );
		MinecraftForge.EVENT_BUS.register( BucketHandler.INSTANCE );
		
		cake.registerBaseOption( "plain", "Plain", null );
		cake.registerFrostOption( "plain", "Plain", null );
		
		cake.registerBaseOption( "zapjam", "Zap Apple Jam", null );
		cake.registerFrostOption( "zapjam", "Zap Apple Jam", new ItemStack( jamBucket, 1 ) );
		
		for ( int i = 0; i < 16; i++ )
		{
			cake.registerBaseOption( "" + i, icings[i], null );
			cake.registerFrostOption( "" + i, icings[i], new ItemStack( icing, 1, i ) );
		}
		
		OreDictionary.registerOre( "logWood", zapAppleDeadLog );
		OreDictionary.registerOre( "leavesTree", zapAppleLeaves );
		OreDictionary.registerOre( "saplingTree", zapAppleSapling );
		
		GameRegistry.registerWorldGenerator( new TreeGenerator(), 0 );
		
		GameRegistry.addRecipe( new ItemStack( jar ), new Object[] { " I ", "G G", " G ", Character.valueOf( 'I' ), Items.iron_ingot, Character.valueOf( 'G' ), Blocks.glass_pane } );
		GameRegistry.addShapelessRecipe( new ItemStack( jamBucket, 1 ), new Object[] { Items.bucket, zapApple } );
		GameRegistry.addShapelessRecipe( new ItemStack( zapPlanks, 4 ), new Object[] { new ItemStack( zapAppleLog ) } );
		GameRegistry.addShapelessRecipe( new ItemStack( Blocks.planks, 4, 0 ), new Object[] { new ItemStack( zapAppleDeadLog ) } );
		
		GameRegistry.addShapelessRecipe( new ItemStack( Items.dye, 1, 5 ), new Object[] { new ItemStack( zapPlanks ) } );
		
		GameRegistry.addShapelessRecipe( new ItemStack( jamFood, 3, 0 ), new Object[] { new ItemStack( Items.bread ), new ItemStack( Items.bread ), new ItemStack( Items.bread ), new ItemStack( jamBucket, 1, 1 ) } );
		
		GameRegistry.addRecipe( new ItemStack( itemZapWoodDoor, 1 ), new Object[] { "WW ", "WW ", "WW ", Character.valueOf( 'W' ), zapPlanks } );
		GameRegistry.addRecipe( new ItemStack( itemStoneDoor, 1 ), new Object[] { "SS ", "SS ", "SS ", Character.valueOf( 'S' ), Blocks.stone } );
		
		GameRegistry.addRecipe( new ItemStack( cake, 1 ), new Object[] { " M ", "WWW", Character.valueOf( 'M' ), Items.milk_bucket, Character.valueOf( 'W' ), Items.wheat } );
		GameRegistry.addRecipe( new ItemStack( icing, 1, 15 ), new Object[] { "   ", "SMS", "   ", Character.valueOf( 'S' ), Items.sugar, Character.valueOf( 'M' ), Items.milk_bucket } );
		
		for ( int i = 0; i < 16; i++ )
		{
			GameRegistry.addShapelessRecipe( new ItemStack( icing, 1, i ), new Object[] { new ItemStack( Items.dye, 1, i ), new ItemStack( icing, 1, 15 ) } );
			GameRegistry.addShapelessRecipe( new ItemStack( icing, 1, 15 ), new Object[] { new ItemStack( Items.dye, 1, 15 ), new ItemStack( icing, 1, i ) } );
		}
	}
	
	@EventHandler
	public void load( FMLInitializationEvent event )
	{
		proxy.registerRenderers();
	}
	
	public static void onDayChange( World world )
	{
		// Current disabled
		/*
		 * if ( !showDayChangeMessages )
		 * return;
		 * String msg = null;
		 * switch ( 25 - day )
		 * {
		 * case 10:
		 * msg = "The Zap Apples will begin their growth cycle in 15 Days.";
		 * break;
		 * case 3:
		 * msg = "The Zap Apples will be here in 8 Days.";
		 * break;
		 * case 1:
		 * msg = "The Zap Apples will begin their cycle tomorrow.";
		 * break;
		 * case 25:
		 * msg = "The Zap Apples will be here in 4 Days.";
		 * break;
		 * case 21:
		 * msg = "The Zap Apples are now ready for picking. :D";
		 * }
		 * if ( msg == null )
		 * {
		 * return;
		 * }
		 * for ( Object player : world.playerEntities )
		 * {
		 * if ( ( player instanceof EntityPlayer ) )
		 * ( (EntityPlayer) player ).addChatMessage( new ChatComponentText( EnumChatFormatting.DARK_PURPLE + msg ) );
		 * }
		 */
	}
}
