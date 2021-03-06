package pl.pabilo8.immersiveintelligence.client.render.metal_device;

import blusunrize.immersiveengineering.client.ClientUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pl.pabilo8.immersiveintelligence.ImmersiveIntelligence;
import pl.pabilo8.immersiveintelligence.client.model.metal_device.ModelCrateInserterUpgrade;
import pl.pabilo8.immersiveintelligence.client.model.metal_device.ModelMediCrate;
import pl.pabilo8.immersiveintelligence.client.model.multiblock.metal.precission_assembler.ModelPrecissionSyringe;
import pl.pabilo8.immersiveintelligence.client.render.IReloadableModelContainer;
import pl.pabilo8.immersiveintelligence.client.tmt.ModelRendererTurbo;
import pl.pabilo8.immersiveintelligence.common.IIContent;
import pl.pabilo8.immersiveintelligence.common.blocks.metal.TileEntityMedicalCrate;

/**
 * @author Pabilo8
 * @since 2019-05-26
 */
@SideOnly(Side.CLIENT)
public class MedicalCrateRenderer extends TileEntitySpecialRenderer<TileEntityMedicalCrate> implements IReloadableModelContainer<MedicalCrateRenderer>
{
	private static ModelMediCrate model;
	private static ModelCrateInserterUpgrade modelUpgrade;
	private static ModelPrecissionSyringe modelInserter;

	private static final String texture = ImmersiveIntelligence.MODID+":textures/blocks/metal_device/medical_crate.png";

	@Override
	public void render(TileEntityMedicalCrate te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		if(te!=null)
		{
			ClientUtils.bindTexture(texture);
			GlStateManager.pushMatrix();
			GlStateManager.translate(x+1, y, z);
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

			GlStateManager.enableBlend();
			float angle = Math.min(1.5f, Math.max(te.lidAngle+(te.open?0.2f*partialTicks: -0.3f*partialTicks), 0f));
			model.getBlockRotation(te.facing, false);
			for(ModelRendererTurbo mod : model.baseModel)
				mod.render(0.0625f);
			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0625f, 0.5625f, -0.5f);
			GlStateManager.rotate(angle*90f, 0, 0, 1);

			for(ModelRendererTurbo mod : model.lidModel)
				mod.render(0.0625f);
			GlStateManager.popMatrix();

			if(te.hasUpgrade(IIContent.UPGRADE_INSERTER))
			{
				ClientUtils.bindTexture(ModelCrateInserterUpgrade.texture);
				for(ModelRendererTurbo mod : modelUpgrade.baseModel)
					mod.render(0.0625f);
				GlStateManager.popMatrix();
				GlStateManager.pushMatrix();
				GlStateManager.translate(x, y+0.75f+0.125f, z+1);
				float f = te.calculateInserterAnimation(partialTicks);
				modelInserter.renderProgress(f*0.5f, -te.calculateInserterAngle(partialTicks), 1);
				GlStateManager.popMatrix();

			}
			else
				GlStateManager.popMatrix();
			GlStateManager.disableBlend();

		}
		else
		{

			GlStateManager.pushMatrix();
			GlStateManager.translate(x+1, y, z);
			GlStateManager.enableBlend();
			GlStateManager.enableAlpha();

			ClientUtils.bindTexture(texture);
			GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

			model.getBlockRotation(EnumFacing.NORTH, false);
			for(ModelRendererTurbo mod : model.baseModel)
				mod.render(0.0625f);
			GlStateManager.translate(0.0625f, 0.5625f, -0.5f);
			for(ModelRendererTurbo mod : model.lidModel)
				mod.render(0.0625f);

			GlStateManager.popMatrix();
		}
	}

	@Override
	public void reloadModels()
	{
		model = new ModelMediCrate();
		modelUpgrade = new ModelCrateInserterUpgrade();
		modelInserter = new ModelPrecissionSyringe();
	}
}
