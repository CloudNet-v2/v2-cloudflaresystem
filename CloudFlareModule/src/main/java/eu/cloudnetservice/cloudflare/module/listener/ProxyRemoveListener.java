package eu.cloudnetservice.cloudflare.module.listener;

import de.dytanic.cloudnet.event.IEventListener;
import de.dytanic.cloudnetcore.api.event.server.ProxyRemoveEvent;
import eu.cloudnetservice.cloudflare.module.CloudflareModule;
import eu.cloudnetservice.cloudflare.module.services.CloudFlareService;

public class ProxyRemoveListener implements IEventListener<ProxyRemoveEvent> {

	@Override
	public void onCall(ProxyRemoveEvent event) {
		CloudflareModule.getInstance().getCloud().getScheduler().runTaskAsync(() -> CloudFlareService.getInstance().removeProxy(event.getProxyServer().getProcessMeta(),
				CloudflareModule.getInstance().getCloudFlareDatabase()));
	}
}
