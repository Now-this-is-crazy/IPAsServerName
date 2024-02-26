package io.github.redvortexdev.iasn.mixin;

import net.minecraft.client.gui.screen.multiplayer.AddServerScreen;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(AddServerScreen.class)
public class AddServerScreenMixin {
    /*
        We inject code into the AddServerScreen
        class into its addAndClose() method.

        To interact with the ServerInfo (the server
        the user is updating using the name and address inputs),
        we utilize a Shadow annotation since is a private field.

        After the addAndClose method is triggered,
        we check the ServerInfo for the server's name.

        If its "Minecraft Server" (the default name)
        we replace name with the server's address.
     */
    @Shadow
    private ServerInfo server;

    @Inject(at = @At("TAIL"), method = "addAndClose")
    private void addAndClose(CallbackInfo ci) {
        if (server.name.equals("Minecraft Server")) {
            server.name = server.address;
        }
    }
}
