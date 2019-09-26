package eu.cloudnetservice.cloudflare.module.listener;

import de.dytanic.cloudnet.event.IEventListener;
import de.dytanic.cloudnet.lib.NetworkUtils;
import de.dytanic.cloudnetcore.api.event.server.ProxyRemoveEvent;
import eu.cloudnetservice.cloudflare.module.ProjectMain;
import eu.cloudnetservice.cloudflare.module.services.CloudFlareService;

public class ProxyRemoveListener implements IEventListener<ProxyRemoveEvent> {

    @Override
    public void onCall(ProxyRemoveEvent event) {
        ProjectMain.getInstance().getExecutor().execute(() -> {
            CloudFlareService.getInstance().removeProxy(event.getProxyServer().getProcessMeta(),
                                                        ProjectMain.getInstance().getCloudFlareDatabase());
            NetworkUtils.sleepUninterruptedly(500);
        });
    }
}
