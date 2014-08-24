package com.chiorichan.ZapApples;

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
import com.chiorichan.ZapApples.tiles.TileEntityCake;
import com.chiorichan.ZapApples.tiles.TileEntityJar;
import com.chiorichan.ZapApples.tiles.TileEntityPie;
import com.chiorichan.ZapApples.tiles.TileEntityZapAppleLog;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumChatFormatting;
import net.minecraft.src.EnumCreatureType;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import net.minecraftforge.client.event.TextureStitchEvent.Post;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid="ZapApples", name="Zap Apple Mod", version="2.1")
@NetworkMod(channels={"zap"}, clientSideRequired=true, serverSideRequired=false, packetHandler=PacketHandler.class)
public class ZapApples
{

  @Mod.Instance("ZapApples")
  public static ZapApples instance;

  @SidedProxy(clientSide="com.chiorichan.ZapApples.ProxyClient", serverSide="com.chiorichan.ZapApples.Proxy")
  public static Proxy proxy;
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

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    try
    {
      config.load();

      zapAppleLogId = config.getBlock("zapAppleLogId", 1551).getInt(1551);
      zapAppleSaplingId = config.getBlock("zapAppleSaplingId", 1552).getInt(1552);
      zapAppleLeavesId = config.getBlock("zapAppleLeavesId", 1553).getInt(1553);
      zapAppleFlowersId = config.getBlock("zapAppleFlowersId", 1554).getInt(1554);
      grayAppleId = config.getBlock("grayAppleId", 1555).getInt(1555);
      zapAppleId = config.getBlock("zapAppleId", 1556).getInt(1556);
      jarId = config.getBlock("jarId", 1557).getInt(1557);
      jamFluidId = config.getBlock("jamFluidId", 1558).getInt(1558);
      doughFluidId = config.getBlock("doughFluidId", 1559).getInt(1559);
      zapPlanksId = config.getBlock("zapApplePlanksId", 1560).getInt(1560);
      pieId = config.getBlock("pieId", 1561).getInt(1561);
      cakeId = config.getBlock("cakeId", 1562).getInt(1562);
      flourId = config.getBlock("flourId", 1563).getInt(1563);

      foodId = config.getItem("foodId", 8370).getInt(8370);
      icingId = config.getItem("icingId", 8371).getInt(8371);
      jamBucketId = config.getItem("jamBucketId", 8372).getInt(8372);
      doughBucketId = config.getItem("doughBucketId", 8373).getInt(8373);

      lightningEffect = config.get("general", "lightningEffect", true).getBoolean(true);
      bucketsPerJar = config.get("general", "bucketsPerJar", 6).getInt(6);
    }
    catch (Exception e)
    {
      FMLLog.log(Level.SEVERE, e, "Zap Apples could not load its config properly.", new Object[0]);
    }
    finally
    {
      config.save();

      MinecraftForge.EVENT_BUS.register(new BonemealHandler());
      proxy.registerRenderInformation();
    }
  }

  @ForgeSubscribe
  public void postStitch(TextureStitchEvent.Post event)
  {
    zapAppleJam.setIcons(zapAppleJamBlock.m(0), zapAppleJamBlock.m(1));
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event)
  {
    zapAppleLog = new BlockZapAppleLog(zapAppleLogId);
    zapAppleLeaves = new BlockZapAppleLeaves(zapAppleLeavesId);
    zapAppleSapling = new BlockZapAppleSapling(zapAppleSaplingId);
    zapAppleFlowers = new BlockZapAppleFlowers(zapAppleFlowersId);
    grayApple = new BlockGrayApple(grayAppleId);
    zapApple = new BlockZapApple(zapAppleId);
    jar = new BlockJar(jarId);
    zapPlanks = new BlockZapApplePlanks(zapPlanksId);
    pie = new BlockPie(pieId);
    cake = new BlockCake(cakeId);
    flour = new BlockFlour(flourId);

    List biomes = Lists.newArrayList();

    biomes.add(BiomeGenBase.forest);
    biomes.add(BiomeGenBase.taiga);
    biomes.add(BiomeGenBase.sky);
    biomes.add(BiomeGenBase.forestHills);
    biomes.add(BiomeGenBase.taigaHills);
    biomes.add(BiomeGenBase.jungle);
    biomes.add(BiomeGenBase.jungleHills);
    biomes.add(BiomeGenBase.swampland);

    int id = EntityRegistry.findGlobalUniqueEntityId();

    EntityRegistry.registerGlobalEntityID(EntityTimberWolf.class, "TimberWolf", id, Color.DARK_GRAY.getRGB(), Color.GREEN.getRGB());

    EntityRegistry.addSpawn(EntityTimberWolf.class, 2, 0, 1, EnumCreatureType.creature, (BiomeGenBase[])biomes.toArray(new BiomeGenBase[0]));

    doughFluid = new Fluid("Dough");
    doughFluid.setBlockID(doughFluidId);
    doughFluid.setLuminosity(1);
    doughFluid.setDensity(1000);

    FluidRegistry.registerFluid(doughFluid);

    doughFluidBlock = new BlockDoughFluid(doughFluidId, doughFluid, Material.water);
    GameRegistry.registerBlock(doughFluidBlock, "Dough");

    doughFluid.setUnlocalizedName(doughFluidBlock.a());

    zapAppleJam = new Fluid("Zap Apple Jam");
    zapAppleJam.setBlockID(jamFluidId);
    zapAppleJam.setLuminosity(3);
    zapAppleJam.setDensity(100);

    FluidRegistry.registerFluid(zapAppleJam);

    zapAppleJamBlock = new BlockZapAppleJam(jamFluidId, zapAppleJam, Material.water);
    GameRegistry.registerBlock(zapAppleJamBlock, "Zap Apple Jam");

    zapAppleJam.setUnlocalizedName(zapAppleJamBlock.a());

    jamFood = new ItemJamFood(foodId);
    icing = new ItemIcing(icingId);
    jamBucket = new ItemFluidBucket(jamBucketId, zapAppleJam.getBlockID(), "zapAppleJamBucket", "jam_bucket");
    doughBucket = new ItemFluidBucket(doughBucketId, doughFluid.getBlockID(), "doughBucket", "dough_bucket");

    GameRegistry.registerBlock(zapAppleLog, "zapAppleLog");
    GameRegistry.registerBlock(zapAppleLeaves, "zapAppleLeaves");
    GameRegistry.registerBlock(zapAppleSapling, "zapAppleSapling");
    GameRegistry.registerBlock(zapAppleFlowers, "zapAppleFlowers");
    GameRegistry.registerBlock(grayApple, ItemGrayApple.class, "grayApple");
    GameRegistry.registerBlock(zapApple, ItemZapApple.class, "zapApple");
    GameRegistry.registerBlock(jar, ItemJar.class, "jar");
    GameRegistry.registerBlock(zapPlanks, "zapApplePlanks");
    GameRegistry.registerBlock(flour, "flour");
    GameRegistry.registerBlock(cake, ItemCake.class, "cake");
    GameRegistry.registerBlock(pie, ItemPie.class, "pie");

    GameRegistry.registerItem(jamFood, "jamFood");
    GameRegistry.registerItem(jamBucket, "jamBucket");
    GameRegistry.registerItem(doughBucket, "doughBucket");
    GameRegistry.registerItem(icing, "icing");

    GameRegistry.registerTileEntity(TileEntityZapAppleLog.class, "ZapAppleLog");
    GameRegistry.registerTileEntity(TileEntityJar.class, "Jar");
    GameRegistry.registerTileEntity(TileEntityPie.class, "ZapAppleCakething");
    GameRegistry.registerTileEntity(TileEntityCake.class, "ZapAppleCake");

    FluidContainerRegistry.registerFluidContainer(new FluidStack(zapAppleJam, 1000), new ItemStack(jamBucket), new ItemStack(Item.bucketEmpty));
    FluidContainerRegistry.registerFluidContainer(new FluidStack(doughFluid, 1000), new ItemStack(doughBucket), new ItemStack(Item.bucketEmpty));

    BucketHandler.INSTANCE.buckets.put(zapAppleJamBlock, jamBucket);
    BucketHandler.INSTANCE.buckets.put(doughFluidBlock, doughBucket);
    MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);

    LanguageRegistry.instance().addStringLocalization("entity.TimberWolf.name", "en_US", "Timber Wolf");

    LanguageRegistry.addName(zapAppleLog, "Zap Apple Log");
    LanguageRegistry.addName(zapAppleLeaves, "Zap Apple Leaves");
    LanguageRegistry.addName(zapAppleSapling, "Zapling");
    LanguageRegistry.addName(zapAppleFlowers, "Zap Apple Flowers");
    LanguageRegistry.addName(grayApple, "Premature Zap Apple");
    LanguageRegistry.addName(zapApple, "Zap Apple");
    LanguageRegistry.addName(zapPlanks, "Zap Apple Wood Planks");
    LanguageRegistry.addName(jamFood, "Zap Apple Bread");
    LanguageRegistry.addName(flour, "Flour");
    LanguageRegistry.addName(zapAppleJamBlock, "Zap Apple Jam");
    LanguageRegistry.addName(jamBucket, "Zap Apple Jam");
    LanguageRegistry.addName(doughFluidBlock, "Dough");
    LanguageRegistry.addName(doughBucket, "Dough");

    LanguageRegistry.addName(jar, "Glass Fluid Jar");

    LanguageRegistry.addName(cake, "Cake");
    LanguageRegistry.addName(pie, "Pie");

    cake.registerBaseOption("plain", "Plain", null);
    cake.registerFrostOption("plain", "Plain", null);

    cake.registerBaseOption("zapjam", "Zap Apple Jam", null);
    cake.registerFrostOption("zapjam", "Zap Apple Jam", new ItemStack(jamBucket, 1));

    for (int i = 0; i < 16; i++)
    {
      LanguageRegistry.addName(new ItemStack(icing, 1, i), icings[i] + " Icing");

      cake.registerBaseOption("" + i, icings[i], null);
      cake.registerFrostOption("" + i, icings[i], new ItemStack(icing, 1, i));
    }

    OreDictionary.registerOre("logWood", zapAppleLog);
    OreDictionary.registerOre("plankWood", zapPlanks);
    OreDictionary.registerOre("leavesTree", zapAppleLeaves);
    OreDictionary.registerOre("saplingTree", zapAppleSapling);

    GameRegistry.registerWorldGenerator(new TreeGenerator());

    GameRegistry.addRecipe(new ItemStack(jar), new Object[] { " I ", "G G", " G ", Character.valueOf('I'), Item.ingotIron, Character.valueOf('G'), Block.thinGlass });
    GameRegistry.addShapelessRecipe(new ItemStack(jamBucket, 1), new Object[] { Item.bucketEmpty, zapApple });
    GameRegistry.addShapelessRecipe(new ItemStack(zapPlanks, 4), new Object[] { new ItemStack(zapAppleLog) });
    GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 1, 5), new Object[] { new ItemStack(zapPlanks) });

    GameRegistry.addShapelessRecipe(new ItemStack(jamFood, 3, 0), new Object[] { new ItemStack(Item.bread), new ItemStack(Item.bread), new ItemStack(Item.bread), new ItemStack(jamBucket, 1, 1) });

    GameRegistry.addRecipe(new ItemStack(cake, 1), new Object[] { " M ", "WWW", Character.valueOf('M'), Item.bucketMilk, Character.valueOf('W'), Item.wheat });
    GameRegistry.addRecipe(new ItemStack(icing, 1, 15), new Object[] { "   ", "SMS", "   ", Character.valueOf('S'), Item.sugar, Character.valueOf('M'), Item.bucketMilk });

    for (int i = 0; i < 16; i++)
    {
      GameRegistry.addShapelessRecipe(new ItemStack(icing, 1, i), new Object[] { new ItemStack(Item.dyePowder, 1, i), new ItemStack(icing, 1, 15) });
      GameRegistry.addShapelessRecipe(new ItemStack(icing, 1, 15), new Object[] { new ItemStack(Item.dyePowder, 1, 15), new ItemStack(icing, 1, i) });
    }

    proxy.addFX();
  }

  public static void dayChangeEvent(World world, int day)
  {
    String msg = null;

    switch (25 - day)
    {
    case 10:
      msg = "The Zap Apples will begin their growth cycle in 15 Days";
      break;
    case 3:
      msg = "The Zap Apples will be here in 8 Days.";
      break;
    case 1:
      msg = "The Zap Apples will begin their cycle tomorrow!, It would time to have those trees prepared.";
      break;
    case 25:
      msg = "The Zap Apples will be here in 4 Days, Sure hope you were prepared in advanced.";
      break;
    case 21:
      msg = "The Zap Apples are now ready for picking. :D";
    }

    if (msg == null) {
      return;
    }
    for (Iterator i$ = playerEntities.iterator(); i$.hasNext(); ) { Object player = i$.next();
      if ((player instanceof EntityPlayer))
        ((EntityPlayer)player).addChatMessage(EnumChatFormatting.DARK_PURPLE + msg);
    }
  }
}