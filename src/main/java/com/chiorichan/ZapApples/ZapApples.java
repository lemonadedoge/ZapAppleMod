package com.chiorichan.ZapApples;

import java.awt.Color;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
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
import com.chiorichan.ZapApples.blocks.BlockZapApple;
import com.chiorichan.ZapApples.blocks.BlockZapAppleFlowers;
import com.chiorichan.ZapApples.blocks.BlockZapAppleJam;
import com.chiorichan.ZapApples.blocks.BlockZapAppleLeaves;
import com.chiorichan.ZapApples.blocks.BlockZapAppleLog;
import com.chiorichan.ZapApples.blocks.BlockZapApplePlanks;
import com.chiorichan.ZapApples.blocks.BlockZapAppleSapling;
import com.chiorichan.ZapApples.items.ItemCake;
import com.chiorichan.ZapApples.items.ItemFluidBucket;
import com.chiorichan.ZapApples.items.ItemGrayApple;
import com.chiorichan.ZapApples.items.ItemIcing;
import com.chiorichan.ZapApples.items.ItemJamFood;
import com.chiorichan.ZapApples.items.ItemJar;
import com.chiorichan.ZapApples.items.ItemPie;
import com.chiorichan.ZapApples.items.ItemZapApple;
import com.chiorichan.ZapApples.mobs.EntityTimberWolf;
import com.chiorichan.ZapApples.tileentity.TileEntityCake;
import com.chiorichan.ZapApples.tileentity.TileEntityJar;
import com.chiorichan.ZapApples.tileentity.TileEntityPie;
import com.chiorichan.ZapApples.tileentity.TileEntityZapAppleLog;
import com.chiorichan.ZapApples.util.BlockDictionary;
import com.chiorichan.ZapApples.util.ItemDictionary;
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
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod( modid = ZapApples.MOD_ID, name = "Zap Apple Mod", version = "1.7.10-2.2alpha" )
public class ZapApples
{
	public static final String MOD_ID = "ZapApples";
	
	@Instance( MOD_ID )
	public static ZapApples instance;
	
	@SidedProxy( clientSide = "com.chiorichan.ZapApples.ClientProxy", serverSide = "com.chiorichan.ZapApples.CommonProxy" )
	public static CommonProxy proxy;
	
	public static String[] icings = { "Black", "Red", "Green", "Chocolate", "Blue", "Purple", "Cyan", "Silver", "Gray", "Pink", "Lime", "Yellow", "Light Blue", "Magenta", "Orange", "Vanilla" };
	int zapAppleLogId;
	int zapAppleLeavesId;
	int zapAppleSaplingId;
	int zapAppleFlowersId;
	int grayAppleId;
	int zapAppleId;
	int jarId;
	int zapPlanksId;
	int cakeId;
	int pieId;
	int jamFluidId;
	int doughFluidId;
	int flourId;
	int industrialCauldronId;
	int foodId;
	int jamBucketId;
	int doughBucketId;
	int industrialCauldronItemId;
	int icingId;
	public static boolean lightningEffect;
	public static int idRender2D;
	public static int idRender3D;
	public static int idRenderCake;
	public static int idRenderPie;
	public static int idRenderApple;
	public static int bucketsPerJar = 12;
	public static BlockZapAppleLog zapAppleLog;
	public static BlockZapAppleLeaves zapAppleLeaves;
	public static BlockZapAppleSapling zapAppleSapling;
	public static BlockZapAppleFlowers zapAppleFlowers;
	public static BlockGrayApple grayApple;
	public static BlockZapApple zapApple;
	public static BlockJar jar;
	public static BlockZapApplePlanks zapPlanks;
	public static BlockPie pie;
	public static BlockCake cake;
	public static BlockFlour flour;
	public static Fluid zapAppleJam;
	public static Fluid doughFluid;
	public static BlockZapAppleJam zapAppleJamBlock;
	public static BlockDoughFluid doughFluidBlock;
	public static ItemJamFood jamFood;
	public static ItemFluidBucket jamBucket;
	public static ItemFluidBucket doughBucket;
	public static ItemIcing icing;
	
	@EventHandler
	public void preInit( FMLPreInitializationEvent event )
	{
		Configuration config = new Configuration( event.getSuggestedConfigurationFile() );
		try
		{
			config.load();
			
			lightningEffect = config.get( "general", "lightningEffect", true ).getBoolean( true );
			bucketsPerJar = config.get( "general", "bucketsPerJar", 6 ).getInt( 6 );
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
		zapAppleLeaves = new BlockZapAppleLeaves();
		zapAppleSapling = new BlockZapAppleSapling();
		zapAppleFlowers = new BlockZapAppleFlowers();
		grayApple = new BlockGrayApple();
		zapApple = new BlockZapApple();
		jar = new BlockJar();
		zapPlanks = new BlockZapApplePlanks();
		pie = new BlockPie();
		cake = new BlockCake();
		flour = new BlockFlour();
		
		List<BiomeGenBase> biomes = Lists.newArrayList();
		
		biomes.add( BiomeGenBase.forest );
		biomes.add( BiomeGenBase.taiga );
		biomes.add( BiomeGenBase.sky );
		biomes.add( BiomeGenBase.forestHills );
		biomes.add( BiomeGenBase.taigaHills );
		biomes.add( BiomeGenBase.jungle );
		biomes.add( BiomeGenBase.jungleHills );
		biomes.add( BiomeGenBase.swampland );
		
		int id = EntityRegistry.findGlobalUniqueEntityId();
		
		EntityRegistry.registerGlobalEntityID( EntityTimberWolf.class, "TimberWolf", id, Color.DARK_GRAY.getRGB(), Color.GREEN.getRGB() );
		
		EntityRegistry.addSpawn( EntityTimberWolf.class, 2, 0, 1, EnumCreatureType.creature, (BiomeGenBase[]) biomes.toArray( new BiomeGenBase[0] ) );
		
		doughFluid = new Fluid( "Dough" );
		doughFluid.setLuminosity( 1 );
		doughFluid.setDensity( 1000 );
		
		FluidRegistry.registerFluid( doughFluid );
		
		doughFluidBlock = new BlockDoughFluid( doughFluid, Material.water );
		GameRegistry.registerBlock( doughFluidBlock, "Dough" );
		
		doughFluid.setUnlocalizedName( doughFluidBlock.getUnlocalizedName() );
		
		zapAppleJam = new Fluid( "Zap Apple Jam" );
		zapAppleJam.setLuminosity( 3 );
		zapAppleJam.setDensity( 100 );
		
		FluidRegistry.registerFluid( zapAppleJam );
		
		zapAppleJamBlock = new BlockZapAppleJam( zapAppleJam, Material.water );
		GameRegistry.registerBlock( zapAppleJamBlock, "Zap Apple Jam" );
		
		zapAppleJam.setUnlocalizedName( zapAppleJamBlock.getUnlocalizedName() );
		
		jamFood = new ItemJamFood();
		icing = new ItemIcing();
		jamBucket = new ItemFluidBucket( zapAppleJamBlock, "zapAppleJamBucket", "jam_bucket" );
		doughBucket = new ItemFluidBucket( doughFluidBlock, "doughBucket", "dough_bucket" );
		
		GameRegistry.registerBlock( zapAppleLog, "zapAppleLog" );
		GameRegistry.registerBlock( zapAppleLeaves, "zapAppleLeaves" );
		GameRegistry.registerBlock( zapAppleSapling, "zapAppleSapling" );
		GameRegistry.registerBlock( zapAppleFlowers, "zapAppleFlowers" );
		GameRegistry.registerBlock( grayApple, ItemGrayApple.class, "grayApple" );
		GameRegistry.registerBlock( zapApple, ItemZapApple.class, "zapApple" );
		GameRegistry.registerBlock( jar, ItemJar.class, "jar" );
		GameRegistry.registerBlock( zapPlanks, "zapApplePlanks" );
		GameRegistry.registerBlock( flour, "flour" );
		GameRegistry.registerBlock( cake, ItemCake.class, "cake" );
		GameRegistry.registerBlock( pie, ItemPie.class, "pie" );
		
		GameRegistry.registerItem( jamFood, "jamFood" );
		GameRegistry.registerItem( jamBucket, "jamBucket" );
		GameRegistry.registerItem( doughBucket, "doughBucket" );
		GameRegistry.registerItem( icing, "icing" );
		
		GameRegistry.registerTileEntity( TileEntityZapAppleLog.class, "ZapAppleLog" );
		GameRegistry.registerTileEntity( TileEntityJar.class, "Jar" );
		GameRegistry.registerTileEntity( TileEntityPie.class, "ZapAppleCakething" );
		GameRegistry.registerTileEntity( TileEntityCake.class, "ZapAppleCake" );
		
		FluidContainerRegistry.registerFluidContainer( new FluidStack( zapAppleJam, 1000 ), new ItemStack( jamBucket ), new ItemStack( ItemDictionary.bucketEmpty.getItem() ) );
		FluidContainerRegistry.registerFluidContainer( new FluidStack( doughFluid, 3500 ), new ItemStack( doughBucket ), new ItemStack( ItemDictionary.bucketEmpty.getItem() ) );
		
		BucketHandler.INSTANCE.buckets.put( zapAppleJamBlock, jamBucket );
		BucketHandler.INSTANCE.buckets.put( doughFluidBlock, doughBucket );
		MinecraftForge.EVENT_BUS.register( BucketHandler.INSTANCE );
		
		LanguageRegistry.instance().addStringLocalization( "entity.TimberWolf.name", "en_US", "Timber Wolf" );
		
		LanguageRegistry.addName( zapAppleLog, "Zap Apple Log" );
		LanguageRegistry.addName( zapAppleLeaves, "Zap Apple Leaves" );
		LanguageRegistry.addName( zapAppleSapling, "Zapling" );
		LanguageRegistry.addName( zapAppleFlowers, "Zap Apple Flowers" );
		LanguageRegistry.addName( grayApple, "Premature Zap Apple" );
		LanguageRegistry.addName( zapApple, "Zap Apple" );
		LanguageRegistry.addName( zapPlanks, "Zap Apple Wood Planks" );
		LanguageRegistry.addName( jamFood, "Zap Apple Bread" );
		LanguageRegistry.addName( flour, "Flour" );
		LanguageRegistry.addName( zapAppleJamBlock, "Zap Apple Jam" );
		LanguageRegistry.addName( jamBucket, "Zap Apple Jam" );
		LanguageRegistry.addName( doughFluidBlock, "Dough" );
		LanguageRegistry.addName( doughBucket, "Dough" );
		
		LanguageRegistry.addName( jar, "Glass Fluid Jar" );
		
		LanguageRegistry.addName( cake, "Cake" );
		LanguageRegistry.addName( pie, "Pie" );
		
		cake.registerBaseOption( "plain", "Plain", null );
		cake.registerFrostOption( "plain", "Plain", null );
		
		cake.registerBaseOption( "zapjam", "Zap Apple Jam", null );
		cake.registerFrostOption( "zapjam", "Zap Apple Jam", new ItemStack( jamBucket, 1 ) );
		
		for ( int i = 0; i < 16; i++ )
		{
			LanguageRegistry.addName( new ItemStack( icing, 1, i ), icings[i] + " Icing" );
			
			cake.registerBaseOption( "" + i, icings[i], null );
			cake.registerFrostOption( "" + i, icings[i], new ItemStack( icing, 1, i ) );
		}
		
		OreDictionary.registerOre( "logWood", zapAppleLog );
		OreDictionary.registerOre( "plankWood", zapPlanks );
		OreDictionary.registerOre( "leavesTree", zapAppleLeaves );
		OreDictionary.registerOre( "saplingTree", zapAppleSapling );
		
		GameRegistry.registerWorldGenerator( new TreeGenerator(), 0 );
		
		GameRegistry.addRecipe( new ItemStack( jar ), new Object[] { " I ", "G G", " G ", Character.valueOf( 'I' ), ItemDictionary.ingotIron.getItem(), Character.valueOf( 'G' ), BlockDictionary.thinGlass.getBlock() } );
		GameRegistry.addShapelessRecipe( new ItemStack( jamBucket, 1 ), new Object[] { ItemDictionary.bucketEmpty.getItem(), zapApple } );
		GameRegistry.addShapelessRecipe( new ItemStack( zapPlanks, 4 ), new Object[] { new ItemStack( zapAppleLog ) } );
		GameRegistry.addShapelessRecipe( new ItemStack( ItemDictionary.dyePowder.getItem(), 1, 5 ), new Object[] { new ItemStack( zapPlanks ) } );
		
		GameRegistry.addShapelessRecipe( new ItemStack( jamFood, 3, 0 ), new Object[] { new ItemStack( ItemDictionary.bread.getItem() ), new ItemStack( ItemDictionary.bread.getItem() ), new ItemStack( ItemDictionary.bread.getItem() ), new ItemStack( jamBucket, 1, 1 ) } );
		
		GameRegistry.addRecipe( new ItemStack( cake, 1 ), new Object[] { " M ", "WWW", Character.valueOf( 'M' ), ItemDictionary.bucketMilk.getItem(), Character.valueOf( 'W' ), ItemDictionary.wheat.getItem() } );
		GameRegistry.addRecipe( new ItemStack( icing, 1, 15 ), new Object[] { "   ", "SMS", "   ", Character.valueOf( 'S' ), ItemDictionary.sugar.getItem(), Character.valueOf( 'M' ), ItemDictionary.bucketMilk.getItem() } );
		
		for ( int i = 0; i < 16; i++ )
		{
			GameRegistry.addShapelessRecipe( new ItemStack( icing, 1, i ), new Object[] { new ItemStack( ItemDictionary.dyePowder.getItem(), 1, i ), new ItemStack( icing, 1, 15 ) } );
			GameRegistry.addShapelessRecipe( new ItemStack( icing, 1, 15 ), new Object[] { new ItemStack( ItemDictionary.dyePowder.getItem(), 1, 15 ), new ItemStack( icing, 1, i ) } );
		}
	}
	
	@EventHandler
	public void load( FMLInitializationEvent event )
	{
		proxy.registerRenderers();
	}
	
	public static void dayChangeEvent( World world, int day )
	{
		String msg = null;
		
		switch ( 25 - day )
		{
			case 10:
				msg = "The Zap Apples will begin their growth cycle in 15 Days.";
				break;
			case 3:
				msg = "The Zap Apples will be here in 8 Days.";
				break;
			case 1:
				msg = "The Zap Apples will begin their cycle tomorrow.";
				break;
			case 25:
				msg = "The Zap Apples will be here in 4 Days.";
				break;
			case 21:
				msg = "The Zap Apples are now ready for picking. :D";
		}
		
		if ( msg == null )
		{
			return;
		}
		
		// PacketHandler.getDispatcher().sendToDimension( new ChatComponentText( EnumChatFormatting.DARK_PURPLE + msg ), );
		
		for ( Object player : world.playerEntities )
		{
			if ( ( player instanceof EntityPlayer ) )
				( (EntityPlayer) player ).addChatMessage( new ChatComponentText( EnumChatFormatting.DARK_PURPLE + msg ) );
		}
	}
}
