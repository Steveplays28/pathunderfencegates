package io.github.steveplays28.pathunderfencegates.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.SideShapeType;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
	@WrapOperation(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isAir()Z"))
	public boolean pathUnderFenceGates$moreThanAir(@NotNull BlockState instance, Operation<Boolean> original, @Local(ordinal = 0) @NotNull World world, @Local(ordinal = 0) @NotNull BlockPos blockPos) {
		return !instance.isSideSolid(world, blockPos.up(), Direction.DOWN, SideShapeType.FULL);
	}
}