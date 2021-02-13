package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common;

import java.util.Map;

public class TxtRecord extends CloudflareDnsRecord {
    public TxtRecord(final String id,
                     final String name,
                     final String content,
                     final int ttl,
                     final boolean locked,
                     final String zoneId,
                     final String zoneName,
                     final String createdOn,
                     final String modifiedOn,
                     final Map<String, Object> meta) {
        super(id, "TXT", name, content, false, false, ttl, locked, zoneId, zoneName, createdOn, modifiedOn, null, meta);
    }


    public static final class Builder {
        private String id;
        private String name;
        private String content;
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

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
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

        public TxtRecord build() {
            return new TxtRecord(id, name, content, ttl, locked, zoneId, zoneName, createdOn, modifiedOn, meta);
        }
    }
}
