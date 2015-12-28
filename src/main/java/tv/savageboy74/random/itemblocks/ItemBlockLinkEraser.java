package tv.savageboy74.random.itemblocks;

/*
 * ItemBlockLinkEraser.java
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

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import tv.savageboy74.random.client.ModCreativeTab;

import java.util.List;

public class ItemBlockLinkEraser extends ItemBlock
{
  public ItemBlockLinkEraser(Block block) {
    super(block);
  }

  @Override
  public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
    String infoLine1 = EnumChatFormatting.YELLOW + "Warning:";
    String infoLine2 = EnumChatFormatting.DARK_RED + "This block will erase the data on your "+ StatCollector.translateToLocal("item.randommod:itemRandom.name") + "!";
    list.add(infoLine1);
    list.add(infoLine2);
  }
}
