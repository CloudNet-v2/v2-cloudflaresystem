/*
 * Copyright 2017 Tarek Hosni El Alaoui
 * Copyright 2021 CloudNetService
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
