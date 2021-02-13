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

import java.util.Objects;

class CloudFlareMessage {
    private final int code;
    private final String message;
    private final String type;

    private CloudFlareMessage(final int code, final String message, final String type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CloudFlareMessage)) {
            return false;
        }

        final CloudFlareMessage that = (CloudFlareMessage) o;

        if (code != that.code) {
            return false;
        }
        if (!Objects.equals(message, that.message)) {
            return false;
        }
        return Objects.equals(type, that.type);
    }

    @Override
    public String toString() {
        return "CloudFlareMessage{" +
               "code=" + code +
               ", message='" + message + '\'' +
               ", type='" + type + '\'' +
               '}';
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
