package mcvmcomputers.item;

import mcvmcomputers.ClientMod;
import mcvmcomputers.entities.EntityWallTV;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemWallTV extends OrderableItem{

	public ItemWallTV(Settings settings) {
		super(settings, 18);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if(!world.isClient && hand == Hand.MAIN_HAND) {
			user.getStackInHand(hand).decrement(1);
			HitResult hr = user.rayTrace(5, 0f, false);
			EntityWallTV ek = new EntityWallTV(world, 
												hr.getPos().getX(),
												hr.getPos().getY(),
												hr.getPos().getZ(),
												new Vec3d(user.getPosVector().x,
															hr.getPos().getY(),
															user.getPosVector().z), user.getUuid().toString());
			world.spawnEntity(ek);
		}
		
		if(world.isClient) {
			world.playSound(ClientMod.thePreviewEntity.getX(),
							ClientMod.thePreviewEntity.getY(),
							ClientMod.thePreviewEntity.getZ(),
							SoundEvents.BLOCK_METAL_PLACE,
							SoundCategory.BLOCKS, 1, 1, true);
		}
		
		return new TypedActionResult<ItemStack>(ActionResult.SUCCESS, user.getStackInHand(hand));
	}

}