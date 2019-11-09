/*
 * Copyright (c) Tarek Hosni El Alaoui 2017
 */

package eu.cloudnetservice.cloudflare.module.config;

import com.google.gson.reflect.TypeToken;
import de.dytanic.cloudnet.lib.utility.document.Document;
import de.dytanic.cloudnetcore.config.ConfigAbstract;
import de.dytanic.cloudnetcore.config.ILoader;
import eu.cloudnetservice.cloudflare.core.models.CloudFlareConfig;
import eu.cloudnetservice.cloudflare.core.models.CloudFlareProxyGroup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Tareko on 26.08.2017.
 */
public class ConfigCloudFlare extends ConfigAbstract implements ILoader<Collection<CloudFlareConfig>> {

    public ConfigCloudFlare() {
        super(new Document("configurations",
                Collections.singletonList(new CloudFlareConfig(false,
                    "example@gmail.com",
                    "token",
                    "example.com",
                    "zone",
                    Collections.singletonList(new CloudFlareProxyGroup("Bungee",
                        "server"))))),
            Paths.get("local/cloudflare_cfg.json"));
    }

    @Override
    public Collection<CloudFlareConfig> load() {
        File old = new File("local/cloudflare.json");

        if (old.exists()) {

            CloudFlareConfig cloudFlareConfig = Document.loadDocument(old).getObject("cloudflare",
                TypeToken.get(CloudFlareConfig.class).getType());

            new Document().append("configurations",
                Document.GSON.toJsonTree(new CloudFlareConfig[] {cloudFlareConfig},
                    TypeToken.get(CloudFlareConfig[].class).getType())).saveAsConfig(path);
            try {
                Files.deleteIfExists(old.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return Document.loadDocument(path).getObject("configurations",
            TypeToken.getParameterized(Collection.class, CloudFlareConfig.class).getType());
    }

}
