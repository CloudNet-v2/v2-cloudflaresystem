package eu.cloudnetservice.cloudflare.module.database;

import com.google.gson.reflect.TypeToken;
import de.dytanic.cloudnet.database.DatabaseUsable;
import de.dytanic.cloudnet.lib.MultiValue;
import de.dytanic.cloudnet.lib.database.Database;
import de.dytanic.cloudnet.lib.database.DatabaseDocument;
import de.dytanic.cloudnet.lib.utility.document.Document;
import eu.cloudnetservice.cloudflare.core.models.CloudFlareConfig;
import eu.cloudnetservice.cloudflare.core.models.PostResponse;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Database to store information about the CloudFlare requests and cache.
 */
public class CloudFlareDatabase extends DatabaseUsable {

    private static final String CLOUDFLARE_CACHE = "cloudflare_cache", CLOUDFLARE_CACHE_REQ = "cloudflare_cache_dnsreq";

    public CloudFlareDatabase(Database database) {
        super(database);
        if (database.getDocument(CLOUDFLARE_CACHE) == null) {
            database.insert(new DatabaseDocument(CLOUDFLARE_CACHE));
        }

        if (database.getDocument(CLOUDFLARE_CACHE_REQ) == null) {
            database.insert(new DatabaseDocument(CLOUDFLARE_CACHE_REQ));
        }
    }

    public Collection<String> getAll() {
        Collection<String> collection = database.getDocument(CLOUDFLARE_CACHE).keys();
        collection.remove(Database.UNIQUE_NAME_KEY);
        return collection;
    }

    public void putPostResponse(MultiValue<PostResponse, String> postResponse) {
        Document document = database.getDocument(CLOUDFLARE_CACHE);
        document.append(postResponse.getFirst().getId(),
            Document.GSON.toJsonTree(postResponse,
                TypeToken.getParameterized(MultiValue.class, PostResponse.class, String.class).getType()));
        database.insert(document);
    }

    public boolean contains(CloudFlareConfig cloudFlareConfig, String wrapper) {
        Document document = database.getDocument(CLOUDFLARE_CACHE);
        for (String key : document.keys()) {
            if (!key.equalsIgnoreCase(Database.UNIQUE_NAME_KEY)) {

                MultiValue<PostResponse, String> value = document.getObject(key,
                    TypeToken.getParameterized(MultiValue.class, PostResponse.class, String.class).getType());

                if (value != null && value.getSecond().equalsIgnoreCase(wrapper) && value.getFirst()
                                                                                         .getCloudFlareConfig()
                                                                                         .getDomainName()
                                                                                         .equalsIgnoreCase(cloudFlareConfig.getDomainName())) {
                    return true;
                }
            }
        }

        return false;
        //return document.contains(wrapper);
    }

    public void remove(String wrapper) {
        database.getDocument(CLOUDFLARE_CACHE).remove(wrapper);
    }

    public PostResponse getResponse(String wrapper) {

        return database.getDocument(CLOUDFLARE_CACHE).getObject(wrapper, TypeToken.get(PostResponse.class).getType());
    }

    public void add(PostResponse postResponse) {
        if (postResponse == null) {
            return;
        }
        Document document = database.getDocument(CLOUDFLARE_CACHE_REQ);
        Map<String, PostResponse> responses = new HashMap<>();
        Type t = TypeToken.getParameterized(Map.class, String.class, PostResponse.class).getType();
        if (document.contains("requests")) {
            responses = document.getObject("requests", t);
            responses.put(postResponse.getId(), postResponse);
            document.append("requests", Document.GSON.toJsonTree(responses, t));
        } else {
            responses.put(postResponse.getId(), postResponse);
            document.append("requests", Document.GSON.toJsonTree(responses, t));
        }
        database.insert(document);
    }

    public void remove(PostResponse postResponse) {
        Document document = database.getDocument(CLOUDFLARE_CACHE_REQ);
        Type t = TypeToken.getParameterized(Map.class, String.class, PostResponse.class).getType();
        Map<String, PostResponse> responses = new HashMap<>();
        if (document.contains("requests")) {
            responses = document.getObject("requests",
                TypeToken.getParameterized(Map.class, String.class, PostResponse.class).getType());
            responses.remove(postResponse.getId());
            document.append("requests", Document.GSON.toJsonTree(responses, t));
        } else {
            document.append("requests", Document.GSON.toJsonTree(responses, t));
        }

        database.insert(document);
    }

    public Map<String, MultiValue<PostResponse, String>> getAndRemove() {
        Document document = database.getDocument(CLOUDFLARE_CACHE_REQ);
        if (document.contains("requests")) {

            Map<String, MultiValue<PostResponse, String>> responses = document.getObject("requests",
                TypeToken.getParameterized(Map.class,
                    String.class,
                    TypeToken.getParameterized(MultiValue.class, PostResponse.class, String.class).getType()).getType());
            document.append("requests", Document.GSON.toJsonTree(new HashMap<>(0), TypeToken.get(HashMap.class).getType()));
            database.insert(document);
            return responses;
        }
        return new HashMap<>(0);
    }
}
