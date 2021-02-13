package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.listener;

import eu.cloudnetservice.cloudnet.v2.event.EventListener;
import eu.cloudnetservice.cloudnet.v2.lib.NetworkUtils;
import eu.cloudnetservice.cloudnet.v2.master.api.event.server.ProxyRemoveEvent;
import eu.cloudnetservice.cloudnet.v2.master.network.components.ProxyServer;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.services.CloudFlareService;

/**
 * Event handler for when a proxy server is removed.
 * This class instructs {@link CloudFlareService} to remove a proxy by calling {@link CloudFlareService#removeProxy(ProxyServer)}.
 */
public final class ProxyRemoveListener implements EventListener<ProxyRemoveEvent> {

    private final CloudFlareService service;

    public ProxyRemoveListener(final CloudFlareService service) {
        this.service = service;
    }

    @Override
    public void onCall(ProxyRemoveEvent event) {
        NetworkUtils.getExecutor().submit(() -> this.service.removeProxy(event.getProxyServer()));
    }
}
