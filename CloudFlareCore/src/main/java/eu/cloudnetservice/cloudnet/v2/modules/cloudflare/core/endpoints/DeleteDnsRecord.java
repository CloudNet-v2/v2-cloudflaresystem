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

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints;

import com.google.gson.reflect.TypeToken;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.DeleteDnsRecordResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models.CloudFlareConfig;

import java.lang.reflect.Type;

/**
 * Represents the API endpoint for deleting DNS records at CloudFlare.
 */
public class DeleteDnsRecord extends CloudflareEndpoint<Void, DeleteDnsRecordResponse> {

    public static final Type TYPE = TypeToken.get(DeleteDnsRecordResponse.class).getType();

    private final CloudFlareConfig config;
    private final String identifier;

    /**
     * Constructs a new DNS record deletion request for a given DNS record.
     * @param config     the configuration to use.
     * @param identifier the ID of the DNS record that should be deleted.
     */
    public DeleteDnsRecord(final CloudFlareConfig config, final String identifier) {
        this.config = config;
        this.identifier = identifier;
    }


    @Override
    protected String providePath() {
        return String.format("zones/%s/dns_records/%s", this.config.getZoneId(), this.identifier);
    }

    @Override
    protected String provideMethod() {
        return "DELETE";
    }

    @Override
    protected Void provideRequest() {
        return null;
    }

    @Override
    protected Type provideResponseType() {
        return TYPE;
    }
}
