package io.github.steveplays28.pathunderfencegates.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.DirtPathBlock;
import net.minecraft.block.SideShapeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DirtPathBlock.class)
public class DirtPathBlockMixin {
	@Inject(method = "canPlaceAt", at = @At(value = "RETURN"), cancellable = true)
	public void pathUnderFenceGates$nonSolidBottom(BlockState state, @NotNull WorldView world, @NotNull BlockPos pos, @NotNull CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;
		cir.setReturnValue(!world.getBlockState(pos.up()).isSideSolid(world, pos.up(), Direction.DOWN, SideShapeType.FULL));
	}
}