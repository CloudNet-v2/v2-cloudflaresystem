package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses;

import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.CloudflareDnsRecord;

import java.util.List;
import java.util.Objects;

public class CreateDnsRecordResponse extends CloudflareResponse {

    private final CloudflareDnsRecord result;

    public CreateDnsRecordResponse(final boolean success,
                                   final List<CloudFlareError> errors,
                                   final List<CloudFlareMessage> messages,
                                   final CloudflareDnsRecord result) {
        super(success, errors, messages);
        this.result = result;
    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateDnsRecordResponse)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final CreateDnsRecordResponse that = (CreateDnsRecordResponse) o;

        return Objects.equals(result, that.result);
    }

    @Override
    public String toString() {
        return "CreateDnsRecordResponse{" +
               "result=" + result +
               "} " + super.toString();
    }

    public CloudflareDnsRecord getResult() {
        return result;
    }
}
