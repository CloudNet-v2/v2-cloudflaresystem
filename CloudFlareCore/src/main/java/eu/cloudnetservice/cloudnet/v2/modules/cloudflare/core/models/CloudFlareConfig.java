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

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models;

import java.util.List;
import java.util.Locale;

/**
 * Container for the CloudFlare configuration.
 */
public class CloudFlareConfig {
    /**
     * The token to authenticate at the CloudFlare API with.
     */
    private final String token;
    /**
     * The domain to create records for.
     */
    private final String domainName;
    /**
     * The internal zone ID at CloudFlare.
     */
    private final String zoneId;
    /**
     * All configured BungeeCord groups and their sub-domains.
     */
    private final List<CloudFlareProxyGroup> groups;
    /**
     * Whether or not this configuration is enabled.
     */
    private boolean enabled;

    public CloudFlareConfig(boolean enabled,
                            final String token, String domainName,
                            String zoneId,
                            List<CloudFlareProxyGroup> groups) {
        this.enabled = enabled;
        this.token = token;
        this.domainName = domainName;
        this.zoneId = zoneId;
        this.groups = groups;
    }

    public List<CloudFlareProxyGroup> getGroups() {
        return groups;
    }

    public String getDomainName() {
        return domainName.toLowerCase(Locale.ROOT); // Make sure we keep consistency because CloudFlare will lowercase all domains
    }

    public String getToken() {
        return token;
    }

    public String getZoneId() {
        return zoneId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
