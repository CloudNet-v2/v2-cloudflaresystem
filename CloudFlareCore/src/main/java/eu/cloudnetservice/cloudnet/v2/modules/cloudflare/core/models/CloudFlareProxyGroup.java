package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models;

import java.util.Objects;

/**
 * Container for a CloudFlare proxy group.
 */
public class CloudFlareProxyGroup {
    /**
     * Name of the BungeeCord group
     */
    private final String name;
    /**
     * Name of the sub-domain
     */
    private final String subdomain;

    public CloudFlareProxyGroup(String name, String subdomain) {
        this.name = name;
        this.subdomain = subdomain;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (subdomain != null ? subdomain.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CloudFlareProxyGroup)) {
            return false;
        }

        final CloudFlareProxyGroup that = (CloudFlareProxyGroup) o;

        if (!Objects.equals(name, that.name)) {
            return false;
        }
        return Objects.equals(subdomain, that.subdomain);
    }

    @Override
    public String toString() {
        return "CloudFlareProxyGroup{" +
               "name='" + name + '\'' +
               ", subdomain='" + subdomain + '\'' +
               '}';
    }

    public String getName() {
        return name;
    }

    public String getSubdomain() {
        return subdomain;
    }
}
