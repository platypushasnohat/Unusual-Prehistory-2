package com.barlinc.unusual_prehistory.mixins;

import com.barlinc.unusual_prehistory.registry.tags.UP2BlockTags;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.component.Tool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {

    @ModifyReturnValue(method = "createToolProperties", at = @At("RETURN"))
    private static Tool addShearsRules(Tool original) {
        List<Tool.Rule> rules = new ArrayList<>(original.rules());
        rules.add(Tool.Rule.overrideSpeed(UP2BlockTags.MINEABLE_WITH_SHEARS, 5.0F));
        return new Tool(rules, original.defaultMiningSpeed(), original.damagePerBlock());
    }
}