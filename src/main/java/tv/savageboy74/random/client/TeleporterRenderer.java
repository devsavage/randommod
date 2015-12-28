package tv.savageboy74.random.client;

/*
 * TeleporterRenderer.java
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

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL12;
import tv.savageboy74.random.tileentity.TileEntityTeleporter;
import tv.savageboy74.random.util.Coord;
import tv.savageboy74.random.util.LogHelper;
import tv.savageboy74.random.util.TileHelper;

import static org.lwjgl.opengl.GL11.*;

public class TeleporterRenderer
{
  private Coord coord;
  private float maxWith, maxHeight;
  private static final int DEFAULT_TEXT_COLOR = 255 + (255 << 8) + (255 << 16) + (170 << 24);

  public static final TeleporterRenderer INSTANCE = new TeleporterRenderer();

  @SubscribeEvent
  public void renderEvent(RenderWorldLastEvent event)
  {
    try {
      doRenderEvent();
    } catch (Exception e) {
      LogHelper.warn("There was an error while trying to render the info box for the Teleporter.");

      e.printStackTrace();
    }
  }

  private void doRenderEvent() {
    Minecraft mc = Minecraft.getMinecraft();

    if(mc.renderEngine == null || RenderManager.instance == null || RenderManager.instance.getFontRenderer() == null || mc.gameSettings.thirdPersonView != 0 || mc.objectMouseOver == null)
      return;
    coord = new Coord(mc.theWorld.provider.dimensionId, mc.objectMouseOver);
    switch (mc.objectMouseOver.typeOfHit) {
      case BLOCK:
        TileEntity te = mc.theWorld.getTileEntity((int) coord.x, (int) coord.y, (int) coord.z);
        if(TileHelper.isTeleporter(te)) {
          coord.x += 0.5;
          coord.y += 0.5;
          coord.z += 0.5;
          renderInfoBox((TileEntityTeleporter)te);
        }
    }
  }

  private void renderInfoBox(TileEntityTeleporter tileEntityTeleporter) {
    if(tileEntityTeleporter.isActivated()) {
      doRenderInfoBox(String.format("Linked Position: X: %s, Y: %s, Z: %s",
          (int)tileEntityTeleporter.getTeleportPosX(),
          (int)tileEntityTeleporter.getTeleportPosY(),
          (int)tileEntityTeleporter.getTeleportPosZ()), true);
    } else {
      doRenderInfoBox("No Linked Position", false);
    }
  }

  private void doRenderInfoBox(String info, boolean teleporterHasData) {
    glPushMatrix();

    moveAndRotate(-1);

    glEnable(GL12.GL_RESCALE_NORMAL);
    glDisable(GL_DEPTH_TEST);

    float blockScale = 1.0F;
    glTranslated(0f, maxHeight + blockScale / 1.25, 0f);

    glScaled(blockScale, blockScale, blockScale);
    glScalef(0.3f, 0.3f, 0.3f);
    glScalef(0.03f, 0.03f, 0.03f);
    glTranslated(2.5f * info.length(), 0f, 0f);
    glRotatef(180, 0.0F, 0.0F, 1.0F);
    glTranslatef(-1f, 1f, 0f);
    RenderManager.instance.getFontRenderer().drawString(info, 0, 0, DEFAULT_TEXT_COLOR, true);

    glDisable(GL12.GL_RESCALE_NORMAL);
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_DEPTH_TEST);

    glPopMatrix();
  }

  private void moveAndRotate(double depth) {
    glTranslated(coord.x - RenderManager.renderPosX, coord.y - RenderManager.renderPosY, coord.z - RenderManager.renderPosZ);
    glRotatef(-RenderManager.instance.playerViewY, 0.0F, 0.5F, 0.0F);
    glRotatef(RenderManager.instance.playerViewX, 0.5F, 0.0F, 0.0F);
    glTranslated(0, 0, depth);
  }
}
