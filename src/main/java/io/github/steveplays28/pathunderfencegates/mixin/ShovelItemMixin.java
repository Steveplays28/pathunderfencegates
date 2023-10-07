package io.github.steveplays28.pathunderfencegates.mixin;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

import static io.github.steveplays28.pathunderfencegates.util.BlockStateUtil.isBlockAllowedAboveDirtPathBlock;

@Mixin(value = ShovelItem.class, priority = 2000)
public class ShovelItemMixin {
	@Final
	@Shadow
	protected static Map<Block, BlockState> PATH_STATES;

	@Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemUsageContext;getWorld()Lnet/minecraft/world/World;"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	public void useOnBlockInject(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);

		if (context.getSide() != Direction.DOWN) {
			PlayerEntity playerEntity = context.getPlayer();
			BlockState blockState2 = PATH_STATES.get(blockState.getBlock());
			BlockState blockState3 = null;
			BlockState blockStateBlockUp = world.getBlockState(blockPos.up());

			if (blockState2 != null && isBlockAllowedAboveDirtPathBlock(blockStateBlockUp)) {
				world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
				blockState3 = blockState2;

				if (playerEntity != null) {
					playerEntity.swingHand(context.getHand());
				}
			} else if (blockState.getBlock() instanceof CampfireBlock && blockState.get(CampfireBlock.LIT)) {
				if (!world.isClient()) {
					world.syncWorldEvent(null, WorldEvents.FIRE_EXTINGUISHED, blockPos, 0);
				}
				CampfireBlock.extinguish(context.getPlayer(), world, blockPos, blockState);
				blockState3 = blockState.with(CampfireBlock.LIT, false);

				if (playerEntity != null) {
					playerEntity.swingHand(context.getHand());
				}
			}

			if (blockState3 != null) {
				if (!world.isClient) {
					world.setBlockState(blockPos, blockState3, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
					world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState3));
					if (playerEntity != null) {
						context.getStack().damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
						playerEntity.swingHand(context.getHand());
					}
				}
				cir.setReturnValue(ActionResult.success(world.isClient));
			}
		}
	}
}
