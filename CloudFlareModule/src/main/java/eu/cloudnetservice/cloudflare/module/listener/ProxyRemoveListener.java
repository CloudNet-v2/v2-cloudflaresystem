package eu.cloudnetservice.cloudflare.module.listener;

import de.dytanic.cloudnet.event.IEventListener;
import de.dytanic.cloudnetcore.api.event.server.ProxyRemoveEvent;
import eu.cloudnetservice.cloudflare.module.ModuleMain;
import eu.cloudnetservice.cloudflare.module.services.CloudFlareService;

public class ProxyRemoveListener implements IEventListener<ProxyRemoveEvent> {

	@Override
	public void onCall(ProxyRemoveEvent event) {
		ModuleMain.getInstance().getCloud().getScheduler().runTaskAsync(() -> CloudFlareService.getInstance().removeProxy(event.getProxyServer().getProcessMeta(),
				ModuleMain.getInstance().getCloudFlareDatabase()));
	}
}
