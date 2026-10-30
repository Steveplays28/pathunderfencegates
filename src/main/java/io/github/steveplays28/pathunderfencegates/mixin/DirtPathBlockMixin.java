package io.github.steveplays28.pathunderfencegates.mixin;

import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DirtPathBlock.class)
public class DirtPathBlockMixin {
	@Inject(method = "canSurvive", at = @At(value = "RETURN"), cancellable = true)
	public void pathUnderFenceGates$nonSolidBottom(BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos, @NotNull CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;
		cir.setReturnValue(!level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN, SupportType.FULL));
	}
}
