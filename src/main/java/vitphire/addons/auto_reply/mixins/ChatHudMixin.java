package vitphire.addons.auto_reply.mixins;

import minegame159.meteorclient.systems.modules.Modules;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vitphire.addons.auto_reply.modules.ReplyModule;

import java.util.List;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {
    @Shadow @Final private List<ChatHudLine<OrderedText>> visibleMessages;

    @Shadow @Final private List<ChatHudLine<Text>> messages;

    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;IIZ)V", cancellable = true)
    private void onAddMessage(Text message, int messageId, int timestamp, boolean bl, CallbackInfo info) {
        // Better Chat
        if (Modules.get().get(ReplyModule.class).onMsg(message.getString(), messageId, timestamp, messages, visibleMessages)) {
            info.cancel();
        }
    }
}
