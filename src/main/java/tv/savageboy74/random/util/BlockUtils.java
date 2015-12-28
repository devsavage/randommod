package tv.savageboy74.random.util;

/*
 * BlockUtils.java
 * Copyright (C) 2015 Savage - github.com/savageboy74
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockUtils
{
  public static void dropAsEntity(World world, int x, int y, int z, ItemStack stack) {
    if(stack != null) {
      double d = 0.7D;
      double e = (double)world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
      double f = (double)world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
      double g = (double)world.rand.nextFloat() * d + (1.0D - d) * 0.5D;
      EntityItem entityItem = new EntityItem(world, (double)x + e, (double)y + f, (double)z + g, stack.copy());
      entityItem.delayBeforeCanPickup = 10;
      world.spawnEntityInWorld(entityItem);
    }
  }
}
