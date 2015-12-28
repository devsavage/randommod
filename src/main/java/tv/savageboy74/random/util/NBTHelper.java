package tv.savageboy74.random.util;

/*
 * NBTHelper.java
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

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTHelper
{
  public static boolean hasTag(ItemStack itemStack, String keyName)
  {
    return itemStack != null && itemStack.stackTagCompound != null && itemStack.stackTagCompound.hasKey(keyName);
  }

  public static void removeTag(ItemStack itemStack, String keyName)
  {
    if (itemStack.stackTagCompound != null)
    {
      itemStack.stackTagCompound.removeTag(keyName);
    }
  }

  private static void initNBTTagCompound(ItemStack itemStack)
  {
    if (itemStack.stackTagCompound == null)
    {
      itemStack.setTagCompound(new NBTTagCompound());
    }
  }

  public static void setLong(ItemStack itemStack, String keyName, long keyValue)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setLong(keyName, keyValue);
  }

  public static String getString(ItemStack itemStack, String keyName)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setString(itemStack, keyName, "");
    }

    return itemStack.stackTagCompound.getString(keyName);
  }

  public static void setString(ItemStack itemStack, String keyName, String keyValue)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setString(keyName, keyValue);
  }

  public static boolean getBoolean(ItemStack itemStack, String keyName)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setBoolean(itemStack, keyName, false);
    }

    return itemStack.stackTagCompound.getBoolean(keyName);
  }

  public static void setBoolean(ItemStack itemStack, String keyName, boolean keyValue)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setBoolean(keyName, keyValue);
  }

  public static byte getByte(ItemStack itemStack, String keyName)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setByte(itemStack, keyName, (byte) 0);
    }

    return itemStack.stackTagCompound.getByte(keyName);
  }

  public static void setByte(ItemStack itemStack, String keyName, byte keyValue)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setByte(keyName, keyValue);
  }

  public static short getShort(ItemStack itemStack, String keyName)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setShort(itemStack, keyName, (short) 0);
    }

    return itemStack.stackTagCompound.getShort(keyName);
  }

  public static void setShort(ItemStack itemStack, String keyName, short keyValue)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setShort(keyName, keyValue);
  }

  public static int getInt(ItemStack itemStack, String keyName)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setInteger(itemStack, keyName, 0);
    }

    return itemStack.stackTagCompound.getInteger(keyName);
  }

  public static void setInteger(ItemStack itemStack, String keyName, int keyValue)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setInteger(keyName, keyValue);
  }

  public static long getLong(ItemStack itemStack, String keyName)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setLong(itemStack, keyName, 0);
    }

    return itemStack.stackTagCompound.getLong(keyName);
  }

  public static float getFloat(ItemStack itemStack, String keyName)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setFloat(itemStack, keyName, 0);
    }

    return itemStack.stackTagCompound.getFloat(keyName);
  }

  public static void setFloat(ItemStack itemStack, String keyName, float keyValue)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setFloat(keyName, keyValue);
  }

  public static double getDouble(ItemStack itemStack, String keyName)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setDouble(itemStack, keyName, 0);
    }

    return itemStack.stackTagCompound.getDouble(keyName);
  }

  public static void setDouble(ItemStack itemStack, String keyName, double keyValue)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setDouble(keyName, keyValue);
  }

  public static NBTTagList getTagList(ItemStack itemStack, String keyName, int nbtBaseType)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setTagList(itemStack, keyName, new NBTTagList());
    }

    return itemStack.stackTagCompound.getTagList(keyName, nbtBaseType);
  }

  public static void setTagList(ItemStack itemStack, String keyName, NBTTagList nbtTagList)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setTag(keyName, nbtTagList);
  }

  public static NBTTagCompound getTagCompound(ItemStack itemStack, String keyName)
  {
    initNBTTagCompound(itemStack);

    if (!itemStack.stackTagCompound.hasKey(keyName))
    {
      setTagCompound(itemStack, keyName, new NBTTagCompound());
    }

    return itemStack.stackTagCompound.getCompoundTag(keyName);
  }

  public static void setTagCompound(ItemStack itemStack, String keyName, NBTTagCompound nbtTagCompound)
  {
    initNBTTagCompound(itemStack);

    itemStack.stackTagCompound.setTag(keyName, nbtTagCompound);
  }
}
