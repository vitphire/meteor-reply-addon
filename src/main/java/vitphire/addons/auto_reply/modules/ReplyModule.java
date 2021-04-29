package vitphire.addons.auto_reply.modules;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import minegame159.meteorclient.settings.BoolSetting;
import minegame159.meteorclient.settings.Setting;
import minegame159.meteorclient.settings.SettingGroup;
import minegame159.meteorclient.settings.StringSetting;
import minegame159.meteorclient.systems.modules.Categories;
import minegame159.meteorclient.systems.modules.Module;
import minegame159.meteorclient.utils.player.ChatUtils;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;
import vitphire.addons.auto_reply.ReplyAddon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class ReplyModule extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> filePath = sgGeneral.add(new StringSetting.Builder()
            .name("File path")
            .description("The path of the config .json file (read github README).")
            .defaultValue("D:\\config.json")
            .build()
    );

    private final Setting<Boolean> debug = sgGeneral.add(new BoolSetting.Builder()
            .name("Debug mode")
            .description("Prints the escaped messages into the log, so you can copy it out.")
            .defaultValue(false)
            .build()
    );

    public ReplyModule() {
        super(Categories.Misc, "auto-reply", "Automatically replies to chat messages.");
    }

    public boolean onMsg(String message, int messageId, int timestamp, List<ChatHudLine<Text>> messages, List<ChatHudLine<OrderedText>> visibleMessages) {
        if (!isActive() || messageId == 74088) return false;
        Triggers triggers;
        try {
            triggers = new Gson().fromJson(new FileReader(filePath.get()),Triggers.class);
            if (triggers.regex == null || triggers.normal == null) throw new AssertionError();
        } catch (FileNotFoundException | JsonIOException e) {
            ChatUtils.info(74088,Formatting.RED+"File at (highlight)%s(default) not found, or unreadable.", filePath.get());
            return false;
        } catch (JsonSyntaxException | AssertionError e) {
            ChatUtils.info(74088,Formatting.RED+"File at (highlight)%s(default)%s not a valid setting.\n" +
                    "Read README.md for more details.", filePath.get(), Formatting.RED);
            return false;
        }
        String messageToSend = triggers.normal.getOrDefault(message, null);
        assert mc.player != null;
        if (messageToSend != null) mc.player.sendChatMessage(messageToSend);
        else if (debug.get()){
            ReplyAddon.LOG.info("Message in chat: \""+StringEscapeUtils.escapeJava(message)+"\"");
        }
        return false;
    }

    protected static class Triggers {
        public RegexTrigger[] regex;
        public Map<String, String> normal;
    }
    protected static class RegexTrigger {
        public String trigger;
        public String response;
    }
}