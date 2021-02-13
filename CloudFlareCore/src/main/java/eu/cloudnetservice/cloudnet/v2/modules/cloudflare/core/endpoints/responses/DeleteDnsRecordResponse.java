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

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses;

import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.CloudflareDnsRecord;

import java.util.List;
import java.util.Objects;

public class DeleteDnsRecordResponse extends CloudflareResponse {
    private final CloudflareDnsRecord result;

    public DeleteDnsRecordResponse(final boolean success,
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
        if (!(o instanceof DeleteDnsRecordResponse)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        final DeleteDnsRecordResponse that = (DeleteDnsRecordResponse) o;

        return Objects.equals(result, that.result);
    }

    @Override
    public String toString() {
        return "DeleteDnsRecordResponse{" +
               "result=" + result +
               "} " + super.toString();
    }

    public CloudflareDnsRecord getResult() {
        return result;
    }
}
