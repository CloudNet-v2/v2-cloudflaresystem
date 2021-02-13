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

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module;

import eu.cloudnetservice.cloudnet.v2.lib.NetworkUtils;
import eu.cloudnetservice.cloudnet.v2.master.module.JavaCloudModule;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.config.ConfigCloudFlare;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.listener.ProxyAddListener;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.listener.ProxyRemoveListener;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.services.CloudFlareService;

/**
 * This is the main class of the CloudFlare module for CloudNet
 *
 * Its purpose is to register necessary event handlers in CloudNet and start the verification process of the
 * CloudFlare token in the configurations.
 */
public final class CloudflareModule extends JavaCloudModule {

    private CloudFlareService cloudFlareService;

    public CloudFlareService getCloudFlareService() {
        return cloudFlareService;
    }

    @Override
    public void onEnable() {
        final ConfigCloudFlare config = new ConfigCloudFlare();
        this.cloudFlareService = new CloudFlareService(config.load(), this);
        NetworkUtils.getExecutor().execute(this.cloudFlareService::bootstrap);

        this.getCloud().getEventManager().registerListener(this, new ProxyAddListener(this.cloudFlareService));
        this.getCloud().getEventManager().registerListener(this, new ProxyRemoveListener(this.cloudFlareService));
    }

    @Override
    public void onDisable() {
        try {
            this.cloudFlareService.shutdown();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
