package eu.cloudnetservice.cloudflare.module.listener;

import de.dytanic.cloudnet.event.IEventListener;
import de.dytanic.cloudnet.lib.NetworkUtils;
import de.dytanic.cloudnetcore.api.event.server.ProxyAddEvent;
import eu.cloudnetservice.cloudflare.module.ProjectMain;
import eu.cloudnetservice.cloudflare.module.services.CloudFlareService;

public class ProxyAddListener implements IEventListener<ProxyAddEvent> {

	@Override
	public void onCall(ProxyAddEvent event) {
		ProjectMain.getInstance().getExecutor().execute(new Runnable() {
			@Override
			public void run() {
				CloudFlareService.getInstance().addProxy(event.getProxyServer().getProcessMeta(), ProjectMain.getInstance().getCloudFlareDatabase());
				NetworkUtils.sleepUninterruptedly(500);
			}
		});
	}
}
