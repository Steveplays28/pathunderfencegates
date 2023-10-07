package io.github.steveplays28.pathunderfencegates.util;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.tag.BlockTags;

import static net.minecraft.block.SlabBlock.TYPE;

public class BlockStateUtil {
	public static boolean isBlockAllowedAboveDirtPathBlock(BlockState blockState) {
		Block block = blockState.getBlock();

		return !blockState.getMaterial().isSolid() || blockState.isAir() || blockState.isIn(BlockTags.FENCE_GATES) || blockState.isIn(
				BlockTags.TRAPDOORS) || block instanceof BellBlock || block instanceof WallSignBlock || block instanceof PaneBlock || (block instanceof SlabBlock && blockState.get(
				TYPE).equals(SlabType.TOP));
	}
}
