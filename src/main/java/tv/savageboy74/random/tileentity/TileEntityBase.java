package tv.savageboy74.random.tileentity;

/*
 * TileEntityBase.java
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import tv.savageboy74.random.util.ItemStackSrc;

import java.util.HashMap;

public class TileEntityBase extends TileEntity
{
  public static final HashMap<Class, ItemStackSrc> myItem = new HashMap();
  public int renderedFragment = 0;
  public String customName;

  public static void registerTileItem(Class c, ItemStackSrc wat) {
    myItem.put(c, wat);
  }

  @Override
  public Packet getDescriptionPacket() {
    NBTTagCompound data = new NBTTagCompound();
    writeToNBT(data);
    return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, data);
  }

  @Override
  public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
    readFromNBT(pkt.getNbtCompound());
    worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    markForUpdate();
  }

  private void markForUpdate() {
    if (this.renderedFragment > 0) {
      this.renderedFragment |= 0x1;
    } else if (this.worldObj != null) {
      this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);

      Block block = worldObj.getBlock(xCoord, yCoord, zCoord);

      this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord, block);
      this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord + 1, zCoord, block);
      this.worldObj.notifyBlocksOfNeighborChange(xCoord - 1, yCoord, zCoord, block);
      this.worldObj.notifyBlocksOfNeighborChange(xCoord + 1, yCoord, zCoord, block);
      this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord - 1, block);
      this.worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord - 1, zCoord + 1, block);
    }
  }
  public void onChunkLoad() {
    if (this.isInvalid())
      this.validate();

    markForUpdate();
  }

  @Override
  public void onChunkUnload() {
    if(!this.isInvalid())
      this.invalidate();
  }

  public boolean notLoaded() {
    return !this.worldObj.blockExists(this.xCoord, this.yCoord, this.zCoord);
  }

  public String getCustomName() {
    return hasCustomName() ? this.customName : getClass().getSimpleName();
  }

  public boolean hasCustomName() {
    return (this.customName != null) && (this.customName.length() > 0);
  }

  public void setName(String name) {
    this.customName = name;
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);

    if (this.customName != null) {
      compound.setString("customName", this.customName);
    }

  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);

    if (compound.hasKey("customName")) {
      this.customName = compound.getString("customName");
    } else {
      this.customName = null;
    }
  }
}
