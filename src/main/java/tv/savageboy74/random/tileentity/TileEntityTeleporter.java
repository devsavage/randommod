package tv.savageboy74.random.tileentity;

/*
 * TileEntityTeleporter.java
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

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTeleporter extends TileEntityBase
{
  private double teleportX = 0;
  private double teleportY = 0;
  private double teleportZ = 0;
  private float teleportYaw = 0;
  private float teleportPitch = 0;
  private boolean activated = false;

  public TileEntityTeleporter() {

  }

  @Override
  public void writeToNBT(NBTTagCompound nbtTagCompound) {
    super.writeToNBT(nbtTagCompound);

    nbtTagCompound.setDouble("TeleportPosX", teleportX);
    nbtTagCompound.setDouble("TeleportPosY", teleportY);
    nbtTagCompound.setDouble("TeleportPosZ", teleportZ);
    nbtTagCompound.setFloat("TeleportPosYaw", teleportYaw);
    nbtTagCompound.setFloat("TeleportPosPitch", teleportPitch);
    nbtTagCompound.setBoolean("TeleporterActivated", activated);
  }

  @Override
  public void readFromNBT(NBTTagCompound nbtTagCompound) {
    super.readFromNBT(nbtTagCompound);

    teleportX = nbtTagCompound.getDouble("TeleportPosX");
    teleportY = nbtTagCompound.getDouble("TeleportPosY");
    teleportZ = nbtTagCompound.getDouble("TeleportPosZ");
    teleportYaw = nbtTagCompound.getFloat("TeleportPosYaw");
    teleportPitch = nbtTagCompound.getFloat("TeleportPosPitch");
    activated = nbtTagCompound.getBoolean("TeleporterActivated");
  }

  public void setTeleportPos(double x, double y, double z, float yaw, float pitch) {
    this.teleportX = x;
    this.teleportY = y;
    this.teleportZ = z;
    this.teleportYaw = yaw;
    this.teleportPitch = pitch;
    this.activated = true;
  }

  public double getTeleportPosX() {
    return this.teleportX;
  }

  public double getTeleportPosY() {
    return this.teleportY;
  }

  public double getTeleportPosZ() {
    return this.teleportZ;
  }

  public float getTeleportPosYaw() {
    return this.teleportYaw;
  }

  public float getTeleportPosPitch() {
    return this.teleportPitch;
  }

  public boolean isActivated() {
    return this.activated;
  }

  public String debugCoords() {
    return String.format("X: %s Y: %s Z: %s Yaw: %s Pitch: %s Activated: %s", getTeleportPosX(), getTeleportPosY(), getTeleportPosZ(), getTeleportPosYaw(), getTeleportPosPitch(), isActivated());
  }
}
