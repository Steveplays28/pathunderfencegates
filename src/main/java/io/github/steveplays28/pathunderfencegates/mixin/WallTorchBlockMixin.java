package io.github.steveplays28.pathunderfencegates.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WallTorchBlock.class)
public class WallTorchBlockMixin {

	@ModifyReturnValue(method = "canPlaceAt(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z", at = @At("RETURN"))
	private static boolean pathUnderFenceGates$allowWallTorchesOnDirtPath(boolean original, @Local(argsOnly = true) WorldView world, @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) Direction facing) {
		//gets the blockpos of the block the torch is supposed to be connected to
		BlockPos truePos = pos.offset(facing.getOpposite());
		BlockState blockState = world.getBlockState(truePos);
		if(blockState.getBlock() == Blocks.DIRT_PATH) {
			//allow for wall torches on dirt path blocks
			return true;
		}
		return original;
	}

}
