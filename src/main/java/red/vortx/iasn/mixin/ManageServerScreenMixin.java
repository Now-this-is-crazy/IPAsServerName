package red.vortx.iasn.mixin;

import red.vortx.iasn.IPAsServerName;
import net.minecraft.client.gui.components.EditBox;
//? if >=1.21.9 {
import net.minecraft.client.gui.screens.ManageServerScreen;
//?} else
/*import net.minecraft.client.gui.screens.EditServerScreen;
*/
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

//? if >=1.21.9 {
@Mixin(ManageServerScreen.class)
//?} else
/*@Mixin(EditServerScreen.class)
*/
public class ManageServerScreenMixin {

    @Final
    @Shadow
    private ServerData serverData;
    @Shadow
    private EditBox nameEdit;
    @Shadow
    private EditBox ipEdit;

    @Inject(at = @At("TAIL"), method = "onAdd")
    private void iasn$onAdd(CallbackInfo ci) {
        if (serverData.name.equals(IPAsServerName.DEFAULT_SERVER_NAME.getString())) {
            serverData.name = iasn$getServerName();
        }
    }

    //? if >=1.18 {
    @Inject(at = @At("TAIL"), method = "updateAddButtonStatus")
    private void iasn$updateAddButtonStatus(CallbackInfo ci) {
    //?} else {
    /*@Inject(at = @At("TAIL"), method = "onEdited")
    private void iasn$updateAddButtonStatus(String value, CallbackInfo ci) {
    *///?}

        //? if >=1.19.4 {
        nameEdit.setHint(Component.literal(iasn$getServerName()));
        //?} else
        /*nameEdit.setSuggestion(iasn$getServerName());
        */

    }

    @Unique
    private String iasn$getServerName() {
        String input = ipEdit.getValue();
        if (input.isEmpty()) {
            return IPAsServerName.DEFAULT_SERVER_NAME.getString();
        }
        Optional<String> serverName = IPAsServerName.SERVER_MAPPINGS.getServerName(input);
        return serverName.orElse(input);
    }

}
