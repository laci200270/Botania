/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Jan 24, 2015, 4:42:33 PM (GMT)]
 */
package vazkii.botania.common.item.lens;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.common.item.ModItems;

public class LensInfluence extends Lens {

	@Override
	public void updateBurst(IManaBurst burst, EntityThrowable entity, ItemStack stack) {
		if(!burst.isFake()) {
			double range = 3.5;
			List<Entity> movables = entity.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range));
			movables.addAll(entity.worldObj.getEntitiesWithinAABB(EntityXPOrb.class, AxisAlignedBB.getBoundingBox(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range)));
			movables.addAll(entity.worldObj.getEntitiesWithinAABB(EntityArrow.class, AxisAlignedBB.getBoundingBox(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range)));
			movables.addAll(entity.worldObj.getEntitiesWithinAABB(EntityFallingBlock.class, AxisAlignedBB.getBoundingBox(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range)));
			movables.addAll(entity.worldObj.getEntitiesWithinAABB(IManaBurst.class, AxisAlignedBB.getBoundingBox(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range)));

			for(Entity movable : movables) {
				if(movable == burst)
					continue;
				
				if(movable instanceof IManaBurst) {
					IManaBurst otherBurst = (IManaBurst) movable;
					ItemStack lens = otherBurst.getSourceLens();
					if(lens != null && lens.getItem() == ModItems.lens && lens.getItemDamage() == ItemLens.INFLUENCE)
						continue;
					
					((IManaBurst) movable).setMotion(entity.motionX, entity.motionY, entity.motionZ);
				} else {
					movable.motionX = entity.motionX;
					movable.motionY = entity.motionY;
					movable.motionZ = entity.motionZ;
				}
			}
		}
	}

}
