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

import java.util.List;
import java.util.Objects;

public class CloudflareResponse {

    private final boolean success;
    private final List<CloudFlareError> errors;
    private final List<CloudFlareMessage> messages;

    public CloudflareResponse(final boolean success,
                              final List<CloudFlareError> errors,
                              final List<CloudFlareMessage> messages) {
        this.success = success;
        this.errors = errors;
        this.messages = messages;
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (errors != null ? errors.hashCode() : 0);
        result = 31 * result + (messages != null ? messages.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CloudflareResponse)) {
            return false;
        }

        final CloudflareResponse that = (CloudflareResponse) o;

        if (success != that.success) {
            return false;
        }
        if (!Objects.equals(errors, that.errors)) {
            return false;
        }
        return Objects.equals(messages, that.messages);
    }

    @Override
    public String toString() {
        return "CloudflareResponse{" +
               "success=" + success +
               ", errors=" + errors +
               ", messages=" + messages +
               '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public List<CloudFlareError> getErrors() {
        return errors;
    }

    public List<CloudFlareMessage> getMessages() {
        return messages;
    }
}
