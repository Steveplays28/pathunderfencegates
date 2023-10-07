package io.github.steveplays28.pathunderfencegates.util;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.registry.tag.BlockTags;

import static net.minecraft.block.SlabBlock.TYPE;

public class BlockStateUtil {
	@SuppressWarnings("deprecation")
	public static boolean isBlockAllowedAboveDirtPathBlock(BlockState blockState) {
		Block block = blockState.getBlock();

		return !blockState.isSolid() || blockState.isAir() || blockState.isIn(BlockTags.FENCE_GATES) || blockState.isIn(
				BlockTags.TRAPDOORS) || block instanceof BellBlock || block instanceof WallSignBlock || block instanceof PaneBlock || (block instanceof SlabBlock && blockState.get(
				TYPE).equals(SlabType.TOP));
	}
}
