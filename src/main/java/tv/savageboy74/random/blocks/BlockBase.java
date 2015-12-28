package tv.savageboy74.random.blocks;

/*
 * BlockBase.java
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

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Random;

import tv.savageboy74.random.RandomMod;
import tv.savageboy74.random.client.ModCreativeTab;
import tv.savageboy74.random.tileentity.TileEntityBase;
import tv.savageboy74.random.util.ItemStackSrc;

public class BlockBase extends BlockContainer
{
  @SideOnly(Side.CLIENT)
  public IIcon renderIcon;
  protected boolean isInventory = false;
  protected boolean hasSubtypes = false;
  private Class<? extends TileEntity> tileEntityType = null;

  public BlockBase(Material materialIn) {
    super(materialIn);
    this.setCreativeTab(ModCreativeTab.randomTab);
  }

  public static <T> T getTileEntity(IBlockAccess world, int x, int y, int z, Class<T> clazz) {
    TileEntity tileEntity = world.getTileEntity(x, y, z);
    return !clazz.isInstance(tileEntity) ? null : (T) tileEntity;
  }

  @Override
  public TileEntity createNewTileEntity(World world, int metadata) {
    if (hasBlockTileEntity()) {
      try {
        return (TileEntity) this.tileEntityType.newInstance();
      } catch (Throwable e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  private boolean hasBlockTileEntity() {
    return this.tileEntityType != null;
  }

  public Class<? extends TileEntity> getTileEntityClass() {
    return this.tileEntityType;
  }

  public <T extends TileEntity> TileEntity getTileEntity(IBlockAccess world, int x, int y, int z) {
    if (!hasBlockTileEntity()) {
      return null;
    }

    TileEntity tileEntity = world.getTileEntity(x, y, z);
    if (this.tileEntityType.isInstance(tileEntity)) {
      return tileEntity;
    }

    return null;
  }

  protected void setTileEntity(Class<? extends TileEntity> c) {
    TileEntityBase.registerTileItem(c, new ItemStackSrc(this, 0));
    String tileName = "tileentity." + RandomMod.MODID + "." + c.getSimpleName();

    GameRegistry.registerTileEntity(this.tileEntityType = c, tileName);
    this.isInventory = IInventory.class.isAssignableFrom(c);
    setTileProvider(hasBlockTileEntity());
  }

  private void setTileProvider(boolean b) {
    ReflectionHelper.setPrivateValue(Block.class, this, b, "isTileProvider");
  }

  @Override
  public String getUnlocalizedName() {
    return String.format("tile.%s%s", RandomMod.MODID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister iconRegister) {
    blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
  }

  protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
    return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
  }

  @Override
  public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
    dropInventory(world, x, y, z);
    super.breakBlock(world, x, y, z, block, meta);
  }

  protected void dropInventory(World world, int x, int y, int z) {
    TileEntity tileEntity = world.getTileEntity(x, y, z);

    if (!(tileEntity instanceof IInventory)) {
      return;
    }

    IInventory inventory = (IInventory) tileEntity;

    for (int i = 0; i < inventory.getSizeInventory(); i++) {
      ItemStack itemStack = inventory.getStackInSlot(i);

      if (itemStack != null && itemStack.stackSize > 0) {
        Random rand = new Random();

        float dX = rand.nextFloat() * 0.8F + 0.1F;
        float dY = rand.nextFloat() * 0.8F + 0.1F;
        float dZ = rand.nextFloat() * 0.8F + 0.1F;

        EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());

        if (itemStack.hasTagCompound()) {
          entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
        }

        float factor = 0.05F;
        entityItem.motionX = rand.nextGaussian() * factor;
        entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
        entityItem.motionZ = rand.nextGaussian() * factor;
        world.spawnEntityInWorld(entityItem);
        itemStack.stackSize = 0;
      }
    }
  }
}
