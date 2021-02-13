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

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models.CloudFlareConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class CloudflareEndpoint<REQUEST, RESPONSE> {

    /**
     * The endpoint base URL for the CloudFlare API.
     */
    private static final String ENDPOINT_HOST = "https://api.cloudflare.com/client/v4/";

    private static final Gson GSON = new GsonBuilder()
        .setLenient()
        .create();

    /**
     * Configures and call the requested API endpoint with the given configuration.
     *
     * Utilizes {@link #provideMethod()}, {@link #providePath()}, {@link #provideRequest()} and {@link #provideResponseType()}.
     *
     * @param config the CloudFlare configuration to use.
     * @return the response (if present) as a deserialized Object of the given generic type {@link RESPONSE}.
     * @throws IOException when an error during the network interaction occurs.
     */
    public RESPONSE call(CloudFlareConfig config) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(ENDPOINT_HOST + this.providePath()).openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod(this.provideMethod());
        connection.setRequestProperty("Authorization", "Bearer " + config.getToken());
        connection.setRequestProperty("Accept", "application/json");

        final Object request = this.provideRequest();
        if (request != null) {
            final String requestString = GSON.toJson(request);
            final int requestLength = requestString.getBytes(StandardCharsets.UTF_8).length;
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(requestLength));
            connection.setRequestProperty("User-Agent", "CloudNet CloudFlare-Module/1.0.0");


            //            System.out.printf("Request: %s%n", requestString);

            try (final OutputStream stream = connection.getOutputStream()) {
                stream.write(requestString.getBytes(StandardCharsets.UTF_8));
            }
        }

        connection.connect();


        try (final Reader reader = new InputStreamReader(connection.getResponseCode() < 400
                                                         ? connection.getInputStream()
                                                         : connection.getErrorStream(), StandardCharsets.UTF_8)) {
            StringBuilder builder = new StringBuilder();

            char[] buffer = new char[1024];
            int read;
            while ((read = reader.read(buffer)) > 0) {
                builder.append(buffer, 0, read);
            }

            //            System.out.printf("Response: %s%n", builder.toString());

            return GSON.fromJson(builder.toString(), this.provideResponseType());
        } finally {
            connection.disconnect();
        }
    }

    /**
     * @return the path after the endpoint base URL.
     */
    protected abstract String providePath();

    /**
     * @return the HTTP method, that is to be used by this endpoint (e.g. GET, POST etc)
     */
    protected abstract String provideMethod();

    /**
     * @return the object representing the request payload of the generic type {@link REQUEST}. Will be serialized when calling the endpoint.
     */
    protected abstract REQUEST provideRequest();

    /**
     * @return the type of the response for proper deserialization to the generic type {@link RESPONSE}.
     */
    protected abstract Type provideResponseType();
}
