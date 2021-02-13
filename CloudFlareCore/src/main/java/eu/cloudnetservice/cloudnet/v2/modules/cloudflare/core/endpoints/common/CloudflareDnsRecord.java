package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.Objects;

public class CloudflareDnsRecord {
    private final String id;
    private final String type;
    private final String name;
    private final String content;
    private final boolean proxiable;
    private final boolean proxied;
    private final int ttl;
    private final boolean locked;

    @SerializedName("zone_id")
    private final String zoneId;

    @SerializedName("zone_name")
    private final String zoneName;

    @SerializedName("created_on")
    private final String createdOn;

    @SerializedName("modified_on")
    private final String modifiedOn;

    private final Map<String, Object> data;
    private final Map<String, Object> meta;


    public CloudflareDnsRecord(final String id,
                               final String type,
                               final String name,
                               final String content,
                               final boolean proxiable,
                               final boolean proxied,
                               final int ttl,
                               final boolean locked,
                               final String zoneId,
                               final String zoneName,
                               final String createdOn,
                               final String modifiedOn,
                               final Map<String, Object> data,
                               final Map<String, Object> meta) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.content = content;
        this.proxiable = proxiable;
        this.proxied = proxied;
        this.ttl = ttl;
        this.locked = locked;
        this.zoneId = zoneId;
        this.zoneName = zoneName;
        this.createdOn = createdOn;
        this.modifiedOn = modifiedOn;
        this.data = data;
        this.meta = meta;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (proxiable ? 1 : 0);
        result = 31 * result + (proxied ? 1 : 0);
        result = 31 * result + ttl;
        result = 31 * result + (locked ? 1 : 0);
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        result = 31 * result + (zoneName != null ? zoneName.hashCode() : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (modifiedOn != null ? modifiedOn.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (meta != null ? meta.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CloudflareDnsRecord)) {
            return false;
        }

        final CloudflareDnsRecord that = (CloudflareDnsRecord) o;

        if (proxiable != that.proxiable) {
            return false;
        }
        if (proxied != that.proxied) {
            return false;
        }
        if (ttl != that.ttl) {
            return false;
        }
        if (locked != that.locked) {
            return false;
        }
        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!Objects.equals(type, that.type)) {
            return false;
        }
        if (!Objects.equals(name, that.name)) {
            return false;
        }
        if (!Objects.equals(content, that.content)) {
            return false;
        }
        if (!Objects.equals(zoneId, that.zoneId)) {
            return false;
        }
        if (!Objects.equals(zoneName, that.zoneName)) {
            return false;
        }
        if (!Objects.equals(createdOn, that.createdOn)) {
            return false;
        }
        if (!Objects.equals(modifiedOn, that.modifiedOn)) {
            return false;
        }
        if (!Objects.equals(data, that.data)) {
            return false;
        }
        return Objects.equals(meta, that.meta);
    }

    @Override
    public String toString() {
        return "CloudflareDnsRecord{" +
               "id='" + id + '\'' +
               ", type='" + type + '\'' +
               ", name='" + name + '\'' +
               ", content='" + content + '\'' +
               ", proxiable=" + proxiable +
               ", proxied=" + proxied +
               ", ttl=" + ttl +
               ", locked=" + locked +
               ", zoneId='" + zoneId + '\'' +
               ", zoneName='" + zoneName + '\'' +
               ", createdOn='" + createdOn + '\'' +
               ", modifiedOn='" + modifiedOn + '\'' +
               ", data=" + data +
               ", meta=" + meta +
               '}';
    }

    public String getContent() {
        return content;
    }

    public boolean isProxiable() {
        return proxiable;
    }

    public boolean isProxied() {
        return proxied;
    }

    public int getTtl() {
        return ttl;
    }

    public boolean isLocked() {
        return locked;
    }

    public String getZoneId() {
        return zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public static final class Builder {
        private String id;
        private String type;
        private String name;
        private String content;
        private boolean proxiable;
        private boolean proxied;
        private int ttl;
        private boolean locked;
        private String zoneId;
        private String zoneName;
        private String createdOn;
        private String modifiedOn;
        private Map<String, Object> data;
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

        public Builder type(String type) {
            this.type = type;
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

        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }

        public Builder meta(Map<String, Object> meta) {
            this.meta = meta;
            return this;
        }

        public CloudflareDnsRecord build() {
            return new CloudflareDnsRecord(id,
                type,
                name,
                content,
                proxiable,
                proxied,
                ttl,
                locked,
                zoneId,
                zoneName,
                createdOn,
                modifiedOn,
                data,
                meta);
        }
    }
}
