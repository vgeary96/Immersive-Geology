/*
 * Muddykat
 * Copyright (c) 2025
 *
 * This code is licensed under "GNU LESSER GENERAL PUBLIC LICENSE"
 * Details can be found in the license file in the root folder of this project
 */

package com.igteam.immersivegeology.gametest.tests;

import com.igteam.immersivegeology.common.block.helper.OreRichness;
import com.igteam.immersivegeology.core.lib.IGLib;
import com.igteam.immersivegeology.core.material.data.enums.MetalEnum;
import com.igteam.immersivegeology.core.material.data.enums.MineralEnum;
import com.igteam.immersivegeology.core.material.data.enums.StoneEnum;
import com.igteam.immersivegeology.core.material.data.stone.IGStoneTypes;
import com.igteam.immersivegeology.core.material.data.stone.compat.adastra.MaterialAdAstraStone;
import com.igteam.immersivegeology.core.material.helper.flags.BlockCategoryFlags;
import com.igteam.immersivegeology.core.material.helper.flags.ItemCategoryFlags;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public class ServerTests
{
	private static final String TEST_AREA = IGLib.MODID + ":test_area";
	public static List<TestFunction> all()
	{
		List<TestFunction> all = new ArrayList<>();
		all.addAll(basics());
		all.add(new TestFunction("server", "fork_ores", TEST_AREA, 40, 0, true, ServerTests::forkOres));
		return all;
	}

	private static void forkOres(GameTestHelper helper)
	{
		assertOresForPlanet(helper, StoneEnum.MoonStone, MaterialAdAstraStone.MOON.toString(),
				List.of(MineralEnum.Chromite, MineralEnum.Cobaltite, MineralEnum.Millerite,
						MineralEnum.Molybdenite, MineralEnum.Pyrite, MineralEnum.Sphalerite, MineralEnum.Wolframite));
		assertOresForPlanet(helper, StoneEnum.MarsStone, MaterialAdAstraStone.MARS.toString(),
				List.of(MineralEnum.Alumina, MineralEnum.Anatase, MineralEnum.Cryolite,
						MineralEnum.Thorianite, MineralEnum.Thorite, MineralEnum.Zircon));
		helper.assertTrue(!MetalEnum.Kovar.hasFlag(BlockCategoryFlags.ORE_BLOCK), "Kovar should be an alloy, not a vein");
		helper.assertTrue(!MetalEnum.Kovar.getStack(ItemCategoryFlags.INGOT).isEmpty(), "Missing Kovar ingot");
		helper.succeed();
	}

	private static void assertOresForPlanet(GameTestHelper helper, StoneEnum host, String dimension, List<MineralEnum> ores)
	{
		for(MineralEnum ore : ores)
		{
			helper.assertTrue(ore.getDefaultDimensions().contains(dimension), ore+" missing dimension "+dimension);
			helper.assertTrue(ore.instance().acceptableStoneType(host), ore+" missing host "+host);
		}

		if(host.isStoneTypeValid())
		{
			Block block = ForgeRegistries.BLOCKS.getValue(((MaterialAdAstraStone)host.instance()).getHostBlockId());
			helper.assertTrue(block!=null && IGStoneTypes.fromWorldState(block.defaultBlockState())==host, "Missing host "+host);
			for(MineralEnum ore : ores)
				helper.assertTrue(ore.instance().getOreBlock(host, OreRichness.NORMAL)!=null, ore+" missing block in "+host);
		}
	}

	private static List<TestFunction> basics()
	{
		List<TestFunction> tests = new ArrayList<>();
		tests.add(new TestFunction(
				"server", "startup", TEST_AREA,
				200, 0, true, ServerTests::testServerStartup
		));

		tests.add(new TestFunction(
				"server", "connection", TEST_AREA,
				200, 0, true, ServerTests::testNetworkConnectivity
		));

		return tests;
	}

	private static void testServerStartup(GameTestHelper helper) {
		MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
		helper.assertTrue(server != null, "Server failed to initialize");
		helper.assertTrue(server.isRunning(), "Server is not running");
		helper.succeed();
	}

	private static void testNetworkConnectivity(GameTestHelper helper) {
		helper.runAfterDelay(40, () -> {
			try {
				MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
				helper.assertTrue(server.getPlayerList() != null,
						"Server player list not initialized");
				helper.succeed();
			} catch (Exception e) {
				helper.fail("Network test failed: " + e.getMessage());
			}
		});
	}
}
