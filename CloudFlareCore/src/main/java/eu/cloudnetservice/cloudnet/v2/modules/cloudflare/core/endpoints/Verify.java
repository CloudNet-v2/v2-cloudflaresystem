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

import com.google.gson.reflect.TypeToken;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.VerifyResponse;

import java.lang.reflect.Type;

/**
 * Represents the API endpoint for verifying the configured token at CloudFlare.
 */
public class Verify extends CloudflareEndpoint<Void, VerifyResponse> {

    public static final Type TYPE = TypeToken.get(VerifyResponse.class).getType();

    @Override
    protected String providePath() {
        return "user/tokens/verify";
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
