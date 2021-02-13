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

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common;

import java.util.HashMap;
import java.util.Map;

public class SrvRecord extends CloudflareDnsRecord {

    public SrvRecord(final String id,
                     final String content,
                     final boolean proxiable,
                     final boolean proxied,
                     final int ttl,
                     final boolean locked,
                     final String zoneId,
                     final String zoneName,
                     final String createdOn,
                     final String modifiedOn,

                     final String service,
                     final String proto,
                     final String name,
                     final int priority,
                     final int weight,
                     final int port,
                     final String target,

                     final Map<String, Object> meta) {
        super(id,
            "SRV",
            null,
            content,
            proxiable,
            proxied,
            ttl,
            locked,
            zoneId,
            zoneName,
            createdOn,
            modifiedOn,
            makeDataMap(service, proto, name, priority, weight, port, target),
            meta);
    }

    private static Map<String, Object> makeDataMap(final String service,
                                                   final String proto,
                                                   final String name,
                                                   final int priority,
                                                   final int weight,
                                                   final int port,
                                                   final String target) {
        final Map<String, Object> map = new HashMap<>();
        map.put("service", service);
        map.put("proto", proto);
        map.put("name", name);
        map.put("priority", priority);
        map.put("weight", weight);
        map.put("port", port);
        map.put("target", target);
        return map;
    }

    public static final class Builder {
        String service;
        String proto;
        String name;
        int priority;
        int weight;
        int port;
        String target;
        private String id;
        private String content;
        private boolean proxiable;
        private boolean proxied;
        private int ttl;
        private boolean locked;
        private String zoneId;
        private String zoneName;
        private String createdOn;
        private String modifiedOn;
        private Map<String, Object> meta;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder proxiable(boolean proxiable) {
            this.proxiable = proxiable;
            return this;
        }

        public Builder proxied(boolean proxied) {
            this.proxied = proxied;
            return this;
        }

        public Builder ttl(int ttl) {
            this.ttl = ttl;
            return this;
        }

        public Builder locked(boolean locked) {
            this.locked = locked;
            return this;
        }

        public Builder zoneId(String zoneId) {
            this.zoneId = zoneId;
            return this;
        }

        public Builder zoneName(String zoneName) {
            this.zoneName = zoneName;
            return this;
        }

        public Builder createdOn(String createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public Builder modifiedOn(String modifiedOn) {
            this.modifiedOn = modifiedOn;
            return this;
        }

        public Builder meta(Map<String, Object> meta) {
            this.meta = meta;
            return this;
        }

        public Builder service(String service) {
            this.service = service;
            return this;
        }

        public Builder proto(String proto) {
            this.proto = proto;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder target(String target) {
            this.target = target;
            return this;
        }

        public SrvRecord build() {
            return new SrvRecord(id,
                content,
                proxiable,
                proxied,
                ttl,
                locked,
                zoneId,
                zoneName,
                createdOn,
                modifiedOn,
                service,
                proto,
                name,
                priority,
                weight,
                port,
                target,
                meta);
        }
    }
}
