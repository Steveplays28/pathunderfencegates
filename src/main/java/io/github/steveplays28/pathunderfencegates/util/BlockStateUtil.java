package io.github.steveplays28.pathunderfencegates.util;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import org.jetbrains.annotations.NotNull;

#if MC_1_19_2
import net.minecraft.tag.BlockTags;
#else
import net.minecraft.registry.tag.BlockTags;
#endif

import static net.minecraft.block.SlabBlock.TYPE;

public class BlockStateUtil {
	#if MC_1_20_1 @SuppressWarnings("deprecation") #endif
	public static boolean BlockAllowedAboveDirtPathBlock(@NotNull BlockState blockState) {
		Block block = blockState.getBlock();

		return #if MC_1_19_2 !blockState.getMaterial().isSolid() #else !blockState.isSolid() #endif || blockState.isAir() || blockState.isIn(
				BlockTags.FENCE_GATES) || blockState.isIn(
				BlockTags.TRAPDOORS) || block instanceof BellBlock || block instanceof WallSignBlock || block instanceof PaneBlock || (block instanceof SlabBlock && blockState.get(
				TYPE).equals(SlabType.TOP));
	}
}
