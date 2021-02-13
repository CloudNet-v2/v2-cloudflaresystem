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
