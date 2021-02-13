package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints;

import com.google.gson.reflect.TypeToken;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.CloudflareDnsRecord;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.ListDnsRecordsResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models.CloudFlareConfig;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents the API endpoint for listing DNS records at CloudFlare.
 */
public class ListDnsRecords extends CloudflareEndpoint<Void, ListDnsRecordsResponse> {

    public static final Type TYPE = TypeToken.get(ListDnsRecordsResponse.class).getType();

    @Override
    public synchronized ListDnsRecordsResponse call(final CloudFlareConfig config) throws IOException {
        ListDnsRecordsResponse response = new InternalListDnsRecords(config.getZoneId(), 1).call(config);
        if (response != null && response.getResultInfo() != null) {
            final List<CloudflareDnsRecord> records =
                IntStream.range(response.getResultInfo().getPage() + 1, response.getResultInfo().getTotalPages() + 1)
                         .parallel()
                         .mapToObj(page -> new InternalListDnsRecords(config.getZoneId(), page))
                         .map(listDnsRecords -> {
                             try {
                                 return listDnsRecords.call(config);
                             } catch (IOException e) {
                                 e.printStackTrace();
                                 return null;
                             }
                         })
                         .filter(Objects::nonNull)
                         .map(ListDnsRecordsResponse::getResult)
                         .flatMap(List::stream)
                         .collect(Collectors.toList());
            response.getResult().addAll(records);
        }
        return response;
    }

    @Override
    protected String providePath() {
        throw new IllegalArgumentException();
    }

    @Override
    protected String provideMethod() {
        throw new IllegalArgumentException();
    }

    @Override
    protected Void provideRequest() {
        throw new IllegalArgumentException();
    }

    @Override
    protected Type provideResponseType() {
        throw new IllegalArgumentException();
    }

    private static class InternalListDnsRecords extends CloudflareEndpoint<Void, ListDnsRecordsResponse> {

        private final String zoneId;
        private final int currentPage;

        private InternalListDnsRecords(final String zoneId, final int currentPage) {
            this.zoneId = zoneId;
            this.currentPage = currentPage;
        }

        @Override
        protected String providePath() {
            return String.format("zones/%s/dns_records?per_page=100&page=%d", this.zoneId, this.currentPage);
        }

        @Override
        protected String provideMethod() {
            return "GET";
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
}
