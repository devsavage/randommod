package tv.savageboy74.random.items;

/*
 * ItemTeleporterLinker.java
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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import tv.savageboy74.random.client.ModCreativeTab;
import tv.savageboy74.random.tileentity.TileEntityTeleporter;
import tv.savageboy74.random.util.LogHelper;
import tv.savageboy74.random.util.NBTHelper;
import tv.savageboy74.random.util.TileHelper;

import java.util.List;

public class ItemTeleporterLinker extends ItemBase
{
  public ItemTeleporterLinker() {
    this.setUnlocalizedName("itemRandom");
  }

  @Override
  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer player) {
    if(player.isSneaking()) {
      if(itemStackIn.stackTagCompound == null) {
        itemStackIn.stackTagCompound = new NBTTagCompound();
      }

      NBTHelper.setDouble(itemStackIn, "TeleportPosX", player.posX);
      NBTHelper.setDouble(itemStackIn, "TeleportPosY", player.posY);
      NBTHelper.setDouble(itemStackIn, "TeleportPosZ", player.posZ);
      NBTHelper.setFloat(itemStackIn, "TeleportPosYaw", player.rotationYaw);
      NBTHelper.setFloat(itemStackIn, "TeleportPosPitch", player.rotationPitch);

      player.playSound("random.successful_hit", 1.0F, 1.0F);
    }

    return itemStackIn;
  }

  @Override
  public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
    if(!player.isSneaking()) {
      if(itemStack.stackTagCompound == null) {
        return false;
      }

      TileEntityTeleporter tileEntityTeleporter = TileHelper.getTileEntity(world, x, y, z, TileEntityTeleporter.class);
      if(tileEntityTeleporter != null) {
        double posX = NBTHelper.getDouble(itemStack, "TeleportPosX");
        double posY = NBTHelper.getDouble(itemStack, "TeleportPosY");
        double posZ = NBTHelper.getDouble(itemStack, "TeleportPosZ");
        float  posYaw = NBTHelper.getFloat(itemStack, "TeleportPosYaw");
        float  posPitch = NBTHelper.getFloat(itemStack, "TeleportPosPitch");

        tileEntityTeleporter.setTeleportPos(posX, posY, posZ, posYaw, posPitch);
        player.playSound("random.wood_click", 1.0F, 1.0F);

        return true;
      }
    }

    return false;
  }

  @Override
  public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
    if(itemStack.stackTagCompound != null) {
      String teleportPos = EnumChatFormatting.GREEN + "Linked Position: X: " + NBTHelper.getInt(itemStack, "TeleportPosX") + " Y: " + NBTHelper.getInt(itemStack, "TeleportPosY") + " Z: " + NBTHelper.getInt(itemStack, "TeleportPosZ");
      list.add(teleportPos);
    }
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean hasEffect(ItemStack itemStack) {
    return itemStack.stackTagCompound != null;
  }

  @Override
  public EnumRarity getRarity(ItemStack itemStack) {
    return itemStack.stackTagCompound == null ? EnumRarity.common : EnumRarity.epic;
  }
}
