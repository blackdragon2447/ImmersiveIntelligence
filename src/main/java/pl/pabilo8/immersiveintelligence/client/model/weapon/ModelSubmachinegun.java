package pl.pabilo8.immersiveintelligence.client.model.weapon;

import pl.pabilo8.immersiveintelligence.client.model.ModelBlockBase;
import pl.pabilo8.immersiveintelligence.client.render.item.SubmachinegunItemStackRenderer;
import pl.pabilo8.immersiveintelligence.client.tmt.Coord2D;
import pl.pabilo8.immersiveintelligence.client.tmt.ModelRendererTurbo;
import pl.pabilo8.immersiveintelligence.client.tmt.Shape2D;
import pl.pabilo8.immersiveintelligence.client.tmt.TmtNamedBoxGroup;

/**
 * Created by Pabilo8 on 07-06-2019.
 * Created using SMP-Toolbox 2.0 (Old stuff, probably considered dead ^^)
 * Thanks to The Flan's Mod Team for the Turbo Model Thingy used in there
 */
public class ModelSubmachinegun extends ModelBlockBase
{
	int textureX = 64;
	int textureY = 128;

	ModelRendererTurbo[] barrelModel, sightsModel, triggerModel, ammoModel, slideModel, gripModel, stockModel, casingEjectionModel;
	public TmtNamedBoxGroup baseBox, barrelBox, sightsBox, triggerBox, ammoBox, slideBox, gripBox, stockBox, casingEjectionBox;

	//ModelRendererTurbo[] heavyBarrelModel;
	//public TmtNamedBoxGroup heavyBarrelBox;


	public ModelSubmachinegun() //Same as Filename
	{

		baseModel = new ModelRendererTurbo[11];
		baseModel[0] = new ModelRendererTurbo(this, 29, 0, textureX, textureY); // Shape 0
		baseModel[1] = new ModelRendererTurbo(this, 16, 24, textureX, textureY); // Box 1
		baseModel[2] = new ModelRendererTurbo(this, 28, 41, textureX, textureY); // Box 2
		baseModel[3] = new ModelRendererTurbo(this, 6, 43, textureX, textureY); // Box 1
		baseModel[4] = new ModelRendererTurbo(this, 2, 4, textureX, textureY); // Box 7
		baseModel[5] = new ModelRendererTurbo(this, 50, 28, textureX, textureY); // Box 1
		baseModel[6] = new ModelRendererTurbo(this, 38, 33, textureX, textureY); // Box 1
		baseModel[7] = new ModelRendererTurbo(this, 28, 15, textureX, textureY); // BoxTriggerPart
		baseModel[8] = new ModelRendererTurbo(this, 34, 19, textureX, textureY); // BoxTriggerPart
		baseModel[9] = new ModelRendererTurbo(this, 34, 17, textureX, textureY); // BoxTriggerPart
		baseModel[10] = new ModelRendererTurbo(this, 28, 17, textureX, textureY); // BoxTriggerPart

		baseModel[0].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[]{new Coord2D(0, 0, 0, 0), new Coord2D(12, 0, 12, 0), new Coord2D(14, 2, 14, 2), new Coord2D(14, 4, 14, 4), new Coord2D(0, 4, 0, 4)}), 5, 14, 4, 35, 5, ModelRendererTurbo.MR_FRONT, new float[]{4, 14, 2, 3, 12}); // Shape 0
		baseModel[0].setRotationPoint(2.5F, 0F, 0F);
		baseModel[0].rotateAngleY = -1.57079633F;

		baseModel[1].addBox(0F, 0F, 0F, 4, 3, 14, 0F); // Box 1
		baseModel[1].setRotationPoint(-2F, -5.5F, -1F);

		baseModel[2].addBox(0F, 0F, 0F, 1, 4, 8, 0F); // Box 2
		baseModel[2].setRotationPoint(-3F, -5F, 1F);

		baseModel[3].addShapeBox(0F, 0F, 0F, 4, 1, 14, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 1
		baseModel[3].setRotationPoint(-2F, -6.5F, -1F);

		baseModel[4].addBox(0F, 0F, 0F, 2, 3, 6, 0F); // Box 7
		baseModel[4].setRotationPoint(2F, -5.5F, 4F);

		baseModel[5].addShapeBox(0F, 0F, 0F, 4, 3, 2, 0F, 0F, -1F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 1
		baseModel[5].setRotationPoint(-2F, -5.5F, -3F);

		baseModel[6].addBox(0F, 0F, 0F, 2, 2, 3, 0F); // Box 1
		baseModel[6].setRotationPoint(-1F, -6F, -4F);

		baseModel[7].addShapeBox(0F, 0F, 0F, 2, 1, 1, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F); // BoxTriggerPart
		baseModel[7].setRotationPoint(-1F, 0.5F, 5F);

		baseModel[8].addShapeBox(0F, 0F, 0F, 2, 1, 1, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // BoxTriggerPart
		baseModel[8].setRotationPoint(-1F, 0.5F, 4F);

		baseModel[9].addShapeBox(0F, 0F, 0F, 2, 1, 1, 0F, 0F, 0.5F, -0.25F, 0F, 0.5F, -0.25F, 0F, 0.5F, -0.25F, 0F, 0.5F, -0.25F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F); // BoxTriggerPart
		baseModel[9].setRotationPoint(-1F, 0.5F, 3F);

		baseModel[10].addShapeBox(0F, 0F, 0F, 2, 2, 1, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, -0.5F, 0F, -0.5F, -0.5F); // BoxTriggerPart
		baseModel[10].setRotationPoint(-1F, -0.5F, 6F);


		barrelModel = new ModelRendererTurbo[5];
		barrelModel[0] = new ModelRendererTurbo(this, 15, 0, textureX, textureY); // Box 5
		barrelModel[1] = new ModelRendererTurbo(this, 32, 41, textureX, textureY); // Box 5
		barrelModel[2] = new ModelRendererTurbo(this, 38, 15, textureX, textureY); // Box 5
		barrelModel[3] = new ModelRendererTurbo(this, 22, 27, textureX, textureY); // Box 5
		barrelModel[4] = new ModelRendererTurbo(this, 22, 24, textureX, textureY); // Box 5

		barrelModel[0].addBox(0F, 0F, 0F, 3, 3, 4, 0F); // Box 5
		barrelModel[0].setRotationPoint(-1.5F, -6F, 13F);

		barrelModel[1].addBox(0F, 0F, 0F, 2, 2, 14, 0F); // Box 5
		barrelModel[1].setRotationPoint(-1F, -5.5F, 15F);

		barrelModel[2].addBox(0F, 0F, 0F, 3, 3, 10, 0F); // Box 5
		barrelModel[2].setRotationPoint(-1.5F, -6F, 17F);

		barrelModel[3].addBox(0F, 0F, 0F, 3, 3, 1, 0F); // Box 5
		barrelModel[3].setRotationPoint(-1.5F, -6F, 27.5F);

		barrelModel[4].addShapeBox(0F, 0F, 0F, 1, 2, 1, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F); // Box 5
		barrelModel[4].setRotationPoint(-0.5F, -8F, 27.5F);


		sightsModel = new ModelRendererTurbo[8];
		sightsModel[0] = new ModelRendererTurbo(this, 17, 28, textureX, textureY); // Box 28
		sightsModel[1] = new ModelRendererTurbo(this, 17, 28, textureX, textureY); // Box 28
		sightsModel[2] = new ModelRendererTurbo(this, 22, 31, textureX, textureY); // Box 28
		sightsModel[3] = new ModelRendererTurbo(this, 22, 31, textureX, textureY); // Box 28
		sightsModel[4] = new ModelRendererTurbo(this, 18, 7, textureX, textureY); // Box 28
		sightsModel[5] = new ModelRendererTurbo(this, 12, 24, textureX, textureY); // Box 28
		sightsModel[6] = new ModelRendererTurbo(this, 28, 9, textureX, textureY); // Box 28
		sightsModel[7] = new ModelRendererTurbo(this, 28, 9, textureX, textureY); // Box 28

		sightsModel[0].addBox(0F, 0F, 0F, 1, 1, 3, 0F); // Box 28
		sightsModel[0].setRotationPoint(1F, -6.5F, 8F);

		sightsModel[1].addBox(0F, 0F, 0F, 1, 1, 3, 0F); // Box 28
		sightsModel[1].setRotationPoint(-2F, -6.5F, 8F);

		sightsModel[2].addBox(0F, -2F, 0F, 1, 2, 3, 0F); // Box 28
		sightsModel[2].setRotationPoint(-2F, -6.5F, 8F);
		sightsModel[2].rotateAngleZ = -0.06981317F;

		sightsModel[3].addBox(-1F, -2F, 0F, 1, 2, 3, 0F); // Box 28
		sightsModel[3].setRotationPoint(1F, -6.5F, 11F);
		sightsModel[3].rotateAngleY = -3.14159265F;
		sightsModel[3].rotateAngleZ = -0.06981317F;

		sightsModel[4].addBox(0F, 0F, 0F, 2, 1, 5, 0F); // Box 28
		sightsModel[4].setRotationPoint(-1F, -7F, 7F);

		sightsModel[5].addShapeBox(0F, 0F, 0F, 2, 1, 1, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 28
		sightsModel[5].setRotationPoint(-1F, -8F, 9F);

		sightsModel[6].addShapeBox(0F, 0F, 0F, 1, 1, 1, 0F, 0F, -0.45F, 0F, -0.5F, -0.45F, 0F, -0.5F, -0.45F, 0F, 0F, -0.45F, 0F, 0F, 0F, 0F, -0.25F, 0F, 0F, -0.25F, 0F, 0F, 0F, 0F, 0F); // Box 28
		sightsModel[6].setRotationPoint(-1F, -8.75F, 9F);

		sightsModel[7].addShapeBox(0F, 0F, 0F, 1, 1, 1, 0F, -0.5F, -0.45F, 0F, 0F, -0.45F, 0F, 0F, -0.45F, 0F, -0.5F, -0.45F, 0F, -0.25F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -0.25F, 0F, 0F); // Box 28
		sightsModel[7].setRotationPoint(0F, -8.75F, 9F);


		stockModel = new ModelRendererTurbo[3];
		stockModel[0] = new ModelRendererTurbo(this, 0, 13, textureX, textureY); // Shape 16
		stockModel[1] = new ModelRendererTurbo(this, 19, 7, textureX, textureY); // Box 1
		stockModel[2] = new ModelRendererTurbo(this, 19, 7, textureX, textureY); // Box 1

		stockModel[0].addShape3D(0F, 0F, 0F, new Shape2D(new Coord2D[]{new Coord2D(0, 0, 0, 0), new Coord2D(2, 0, 2, 0), new Coord2D(14, 4, 14, 4), new Coord2D(14, 8, 14, 8), new Coord2D(10, 8, 10, 8), new Coord2D(9, 7, 9, 7), new Coord2D(8, 7, 8, 7), new Coord2D(7, 8, 7, 8), new Coord2D(1, 8, 1, 8), new Coord2D(0, 7, 0, 7)}), 3, 14, 8, 43, 3, ModelRendererTurbo.MR_FRONT, new float[]{7, 2, 6, 2, 1, 2, 4, 4, 13, 2}); // Shape 16
		stockModel[0].setRotationPoint(1.5F, 4F, -14F);
		stockModel[0].rotateAngleY = -1.57079633F;

		stockModel[1].addShapeBox(0F, 0F, 0F, 1, 4, 1, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 1
		stockModel[1].setRotationPoint(-2.5F, -4F, -1F);

		stockModel[2].addShapeBox(0F, 0F, 0F, 1, 4, 1, 0F, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, -1F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F); // Box 1
		stockModel[2].setRotationPoint(1.5F, -4F, -1F);


		gripModel = new ModelRendererTurbo[3];
		gripModel[0] = new ModelRendererTurbo(this, 38, 28, textureX, textureY); // Box 14
		gripModel[1] = new ModelRendererTurbo(this, 0, 24, textureX, textureY); // GripBottom
		gripModel[2] = new ModelRendererTurbo(this, 54, 15, textureX, textureY); // Box 14

		gripModel[0].addBox(0F, 0F, -1F, 3, 2, 3, 0F); // Box 14
		gripModel[0].setRotationPoint(-1.5F, -1F, 11F);
		gripModel[0].rotateAngleX = 0.08726646F;

		gripModel[1].addShape3D(0.5F, 0F, -0.5F, new Shape2D(new Coord2D[]{new Coord2D(0, 5, 0, 5), new Coord2D(0, 4, 0, 4), new Coord2D(3, 0, 3, 0), new Coord2D(6, 0, 6, 0), new Coord2D(6, 2, 6, 2), new Coord2D(3, 5, 3, 5)}), 2, 6, 5, 19, 2, ModelRendererTurbo.MR_FRONT, new float[]{3, 5, 2, 3, 5, 1}); // GripBottom
		gripModel[1].setRotationPoint(-0.5F, 5F, 3F);
		gripModel[1].rotateAngleY = -4.71238898F;

		gripModel[2].addBox(0.5F, 2F, -0.5F, 2, 6, 2, 0F); // Box 14
		gripModel[2].setRotationPoint(-1.5F, -1F, 11F);
		gripModel[2].rotateAngleX = 0.08726646F;


		ammoModel = new ModelRendererTurbo[1];
		ammoModel[0] = new ModelRendererTurbo(this, 28, 9, textureX, textureY); // Box 7

		ammoModel[0].addBox(0F, 0F, 0F, 12, 2, 4, 0F); // Box 7
		ammoModel[0].setRotationPoint(4F, -5F, 5.5F);
		ammoModel[0].rotateAngleY = 0.06981317F;
		ammoModel[0].rotateAngleZ = 0.05235988F;


		slideModel = new ModelRendererTurbo[1];
		slideModel[0] = new ModelRendererTurbo(this, 18, 24, textureX, textureY); // Box 7

		slideModel[0].addBox(-1F, -1F, 0F, 1, 1, 1, 0F); // Box 7
		slideModel[0].setRotationPoint(-2F, -5.5F, 7F);
		slideModel[0].rotateAngleZ = -0.43633231F;


		casingEjectionModel = new ModelRendererTurbo[1];
		casingEjectionModel[0] = new ModelRendererTurbo(this, 40, 15, textureX, textureY); // Box 7

		casingEjectionModel[0].addBox(0F, 0F, 0F, 1, 2, 3, 0F); // Box 7
		casingEjectionModel[0].setRotationPoint(2F, -5.5F, 10F);


		triggerModel = new ModelRendererTurbo[1];
		triggerModel[0] = new ModelRendererTurbo(this, 34, 15, textureX, textureY); // BoxTrigger

		triggerModel[0].addShapeBox(0F, 0F, 0F, 2, 1, 1, 0F, 0F, 0.5F, -0.25F, 0F, 0.5F, -0.25F, 0F, 0.5F, -0.25F, 0F, 0.5F, -0.25F, 0F, 0F, -0.5F, 0F, 0F, -0.5F, 0F, 0F, 0F, 0F, 0F, 0F); // BoxTrigger
		triggerModel[0].setRotationPoint(-1F, -0.25F, 4F);

		parts.put("base", baseModel);
		parts.put("barrel", barrelModel);
		parts.put("sights", sightsModel);
		parts.put("trigger", triggerModel);
		parts.put("ammo", ammoModel);
		parts.put("slide", slideModel);
		parts.put("grip", gripModel);
		parts.put("stock", stockModel);
		parts.put("casing_ejection", casingEjectionModel);

		flipAll();

		//parts.remove("heavyBarrel");

		baseBox = new TmtNamedBoxGroup("base", baseModel, SubmachinegunItemStackRenderer.texture);
		barrelBox = new TmtNamedBoxGroup("barrel", barrelModel, SubmachinegunItemStackRenderer.texture);
		sightsBox = new TmtNamedBoxGroup("sights", sightsModel, SubmachinegunItemStackRenderer.texture);
		triggerBox = new TmtNamedBoxGroup("trigger", triggerModel, SubmachinegunItemStackRenderer.texture);
		ammoBox = new TmtNamedBoxGroup("ammo", ammoModel, SubmachinegunItemStackRenderer.texture);
		slideBox = new TmtNamedBoxGroup("slide", slideModel, SubmachinegunItemStackRenderer.texture);
		gripBox = new TmtNamedBoxGroup("grip", gripModel, SubmachinegunItemStackRenderer.texture);
		stockBox = new TmtNamedBoxGroup("stock", stockModel, SubmachinegunItemStackRenderer.texture);
		casingEjectionBox = new TmtNamedBoxGroup("casing_ejection", casingEjectionModel, SubmachinegunItemStackRenderer.texture);
	}
}
