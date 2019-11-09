package eu.cloudnetservice.cloudflare.module.listener;

import de.dytanic.cloudnet.event.IEventListener;
import de.dytanic.cloudnetcore.api.event.server.ProxyAddEvent;
import eu.cloudnetservice.cloudflare.module.CloudflareModule;
import eu.cloudnetservice.cloudflare.module.services.CloudFlareService;

public class ProxyAddListener implements IEventListener<ProxyAddEvent> {

	@Override
	public void onCall(ProxyAddEvent event) {
		CloudflareModule.getInstance().getCloud().getScheduler().runTaskAsync(() -> CloudFlareService.getInstance().addProxy(event.getProxyServer().getProcessMeta(), CloudflareModule.getInstance().getCloudFlareDatabase()));
	}
}
