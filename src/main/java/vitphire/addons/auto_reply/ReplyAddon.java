package vitphire.addons.auto_reply;

import vitphire.addons.auto_reply.modules.ReplyModule;
import minegame159.meteorclient.MeteorAddon;
import minegame159.meteorclient.systems.modules.Category;
import minegame159.meteorclient.systems.modules.Modules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReplyAddon extends MeteorAddon {

	public static final Logger LOG = LogManager.getLogger();

	@Override
	public void onInitialize() {
		LOG.info("Initializing Meteor Addon \"Auto reply\"");
		Modules.get().add(new ReplyModule());
	}

}