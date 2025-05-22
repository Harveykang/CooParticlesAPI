// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package cn.coostack.cooparticlesapi.test.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class TestEntityModel<T extends TestEntity> extends SinglePartEntityModel<T> {
    private final ModelPart entity;
    private final ModelPart head;
    private final ModelPart body;

    public TestEntityModel(ModelPart root) {
        this.entity = root.getChild("test_entity");
        this.head = entity.getChild("head");
        this.body = entity.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData test_entity = modelPartData.addChild("test_entity", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, 0.0F));

        ModelPartData head = test_entity.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -9.0F, -2.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 6).cuboid(-3.0F, -7.0F, -1.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 3.0F, 0.0F));

        ModelPartData body = test_entity.addChild("body", ModelPartBuilder.create().uv(-4, -4).cuboid(-1.0F, -5.0F, -3.0F, 7.0F, 2.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 3.0F, 0.0F));
        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        entity.render(matrices, vertices, light, overlay, color);
    }

    @Override
    public ModelPart getPart() {
        return entity;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.animateMovement(TestEntityAnimation.INSTANCE.getRotate(),
                limbAngle,
                limbDistance,
                2f,
                2.5f);
        this.updateAnimation(TestEntity.getState(),
                TestEntityAnimation.INSTANCE.getRotate(), 1f);
    }
}