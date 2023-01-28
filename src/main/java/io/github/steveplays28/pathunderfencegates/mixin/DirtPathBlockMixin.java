package io.github.steveplays28.pathunderfencegates.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static io.github.steveplays28.pathunderfencegates.util.BlockStateUtil.BlockAllowedAboveDirtPathBlock;

@Mixin(DirtPathBlock.class)
public class DirtPathBlockMixin {
	@Inject(method = "canPlaceAt", at = @At(value = "HEAD"), cancellable = true)
	public void canPlaceAtInject(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockState blockState = world.getBlockState(pos.up());

		cir.setReturnValue(BlockAllowedAboveDirtPathBlock(blockState));
	}
}
