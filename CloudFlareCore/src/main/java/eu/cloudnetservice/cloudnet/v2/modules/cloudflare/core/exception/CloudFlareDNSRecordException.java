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

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.exception;

/**
 * Thrown when errors regarding DNS records occur.
 */
public class CloudFlareDNSRecordException extends RuntimeException {

    /**
     * Constructs a new exception with {@code message} as its detailed message.
     *
     * @param message the detailed message of this exception.
     */
    public CloudFlareDNSRecordException(String message) {
        super(message);
    }

    public CloudFlareDNSRecordException(String message, Throwable cause) {
        super(message, cause);
    }
}
