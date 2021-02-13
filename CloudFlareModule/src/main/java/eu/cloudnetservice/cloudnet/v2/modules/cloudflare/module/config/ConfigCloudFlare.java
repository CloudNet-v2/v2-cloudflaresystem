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

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.config;

import com.google.gson.reflect.TypeToken;
import eu.cloudnetservice.cloudnet.v2.lib.utility.document.Document;
import eu.cloudnetservice.cloudnet.v2.master.config.ConfigAbstract;
import eu.cloudnetservice.cloudnet.v2.master.config.ILoader;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models.CloudFlareConfig;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models.CloudFlareProxyGroup;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * The loader class for the CloudFlare module's configuration.
 * This class takes care of creating and loading the configuration of the CloudFlare module.
 */
public final class ConfigCloudFlare extends ConfigAbstract implements ILoader<List<CloudFlareConfig>> {

    public static final Document DEFAULTS = new Document("configurations",
        Collections.singletonList(new CloudFlareConfig(false,
            "token", "example.com",
            "zone",
            Collections.singletonList(new CloudFlareProxyGroup("Bungee",
                "server")))));

    public ConfigCloudFlare() {
        super(DEFAULTS, Paths.get("local/cloudflare_cfg.json"));
    }

    @Override
    public List<CloudFlareConfig> load() {
        return Document.loadDocument(this.path).getObject("configurations",
            TypeToken.getParameterized(List.class, CloudFlareConfig.class).getType());
    }

}
