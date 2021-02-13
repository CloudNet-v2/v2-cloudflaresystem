package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.listener;

import eu.cloudnetservice.cloudnet.v2.event.EventListener;
import eu.cloudnetservice.cloudnet.v2.lib.NetworkUtils;
import eu.cloudnetservice.cloudnet.v2.master.api.event.server.ProxyAddEvent;
import eu.cloudnetservice.cloudnet.v2.master.network.components.ProxyServer;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.services.CloudFlareService;

/**
 * Event handler for when a proxy server is added.
 * This class instructs {@link CloudFlareService} to add a proxy by calling {@link CloudFlareService#addProxy(ProxyServer)}.
 */
public final class ProxyAddListener implements EventListener<ProxyAddEvent> {

    private final CloudFlareService service;

    public ProxyAddListener(final CloudFlareService service) {
        this.service = service;
    }

    @Override
    public void onCall(ProxyAddEvent event) {
        NetworkUtils.getExecutor().submit(() -> this.service.addProxy(event.getProxyServer()));
    }
}
