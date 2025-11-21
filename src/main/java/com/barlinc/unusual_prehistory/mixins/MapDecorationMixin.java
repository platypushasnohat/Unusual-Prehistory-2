package com.barlinc.unusual_prehistory.mixins;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.registry.UP2MapIcons;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MapDecoration.class)
public abstract class MapDecorationMixin {

    @Shadow
    public abstract MapDecoration.Type getType();

    @Inject(method = {"render(I)Z"}, remap = false, cancellable = true, at = @At(value = "HEAD"))
    private void unusualPrehistory$render(int index, CallbackInfoReturnable<Boolean> cir) {
        if (this.getType() == UP2MapIcons.FOSSIL_SITE_MAP_DECORATION) {
            UnusualPrehistory2.PROXY.renderMapDecoration((MapDecoration) (Object) this, index);
            cir.setReturnValue(true);
        }
    }
}