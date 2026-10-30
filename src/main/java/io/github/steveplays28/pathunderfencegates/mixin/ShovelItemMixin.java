package io.github.steveplays28.pathunderfencegates.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
	@WrapOperation(method = "useOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"))
	public boolean pathUnderFenceGates$moreThanAir(@NotNull BlockState instance, Operation<Boolean> original, @Local(ordinal = 0) @NotNull Level world, @Local(ordinal = 0) @NotNull BlockPos blockPos) {
		return !instance.isFaceSturdy(world, blockPos.above(), Direction.DOWN, SupportType.FULL);
	}
}
