package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints;

import com.google.gson.reflect.TypeToken;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.CloudflareDnsRecord;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.CreateDnsRecordResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models.CloudFlareConfig;

import java.lang.reflect.Type;

/**
 * Represents the API endpoint for creating DNS records at CloudFlare.
 */
public class CreateDnsRecord extends CloudflareEndpoint<CloudflareDnsRecord, CreateDnsRecordResponse> {

    public static final Type TYPE = TypeToken.get(CreateDnsRecordResponse.class).getType();

    private final CloudflareDnsRecord record;
    private final CloudFlareConfig config;

    /**
     * Constructs a new DNS record creation request for a given DNS record.
     * @param config the configuration to use.
     * @param record the DNS record that should be created.
     */
    public CreateDnsRecord(final CloudFlareConfig config, final CloudflareDnsRecord record) {
        this.config = config;
        this.record = record;
    }

    @Override
    protected String providePath() {
        return String.format("zones/%s/dns_records", this.config.getZoneId());
    }

    @Override
    protected String provideMethod() {
        return "POST";
    }

    @Override
    protected CloudflareDnsRecord provideRequest() {
        return record;
    }

    @Override
    protected Type provideResponseType() {
        return TYPE;
    }
}
