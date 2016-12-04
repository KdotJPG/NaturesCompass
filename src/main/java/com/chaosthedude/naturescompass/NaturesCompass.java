package com.chaosthedude.naturescompass;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chaosthedude.naturescompass.config.ConfigHandler;
import com.chaosthedude.naturescompass.gui.GuiHandler;
import com.chaosthedude.naturescompass.items.ItemNaturesCompass;
import com.chaosthedude.naturescompass.network.PacketCompassSearch;
import com.chaosthedude.naturescompass.network.PacketRequestSync;
import com.chaosthedude.naturescompass.network.PacketSync;
import com.chaosthedude.naturescompass.network.PacketTeleport;
import com.chaosthedude.naturescompass.proxy.CommonProxy;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = NaturesCompass.MODID, name = NaturesCompass.NAME, version = NaturesCompass.VERSION, acceptedMinecraftVersions = "[1.11]")

public class NaturesCompass {

	public static final String MODID = "naturescompass";
	public static final String NAME = "Nature's Compass";
	public static final String VERSION = "1.3.0";

	public static final Logger logger = LogManager.getLogger(MODID);

	public static SimpleNetworkWrapper network;
	public static ItemNaturesCompass naturesCompass;

	public static boolean canTeleport;

	@Instance(MODID)
	public static NaturesCompass instance;

	@SidedProxy(clientSide = "com.chaosthedude.naturescompass.client.ClientProxy", serverSide = "com.chaosthedude.naturescompass.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		naturesCompass = new ItemNaturesCompass();
		naturesCompass.setRegistryName(ItemNaturesCompass.NAME);
		GameRegistry.register(naturesCompass);

		network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		network.registerMessage(PacketCompassSearch.Handler.class, PacketCompassSearch.class, 0, Side.SERVER);
		network.registerMessage(PacketTeleport.Handler.class, PacketTeleport.class, 1, Side.SERVER);
		network.registerMessage(PacketRequestSync.Handler.class, PacketRequestSync.class, 2, Side.SERVER);
		network.registerMessage(PacketSync.Handler.class, PacketSync.class, 3, Side.CLIENT);

		proxy.registerEvents();
		proxy.registerModels();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		GameRegistry.addRecipe(new ShapedOreRecipe(naturesCompass, "SLS", "LCL", "SLS", 'C', Items.COMPASS, 'L', Blocks.LOG, 'S', "treeSapling"));
	}

}
