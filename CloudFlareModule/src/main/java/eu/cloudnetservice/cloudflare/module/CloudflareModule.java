package eu.cloudnetservice.cloudflare.module;

import de.dytanic.cloudnet.lib.service.SimpledWrapperInfo;
import de.dytanic.cloudnetcore.api.CoreModule;
import eu.cloudnetservice.cloudflare.module.config.ConfigCloudFlare;
import eu.cloudnetservice.cloudflare.module.database.CloudFlareDatabase;
import eu.cloudnetservice.cloudflare.module.listener.ProxyAddListener;
import eu.cloudnetservice.cloudflare.module.listener.ProxyRemoveListener;
import eu.cloudnetservice.cloudflare.module.services.CloudFlareService;

import java.util.Map;
import java.util.stream.Collectors;

public final class CloudflareModule extends CoreModule {

	private static CloudflareModule instance;
	private ConfigCloudFlare configCloudFlare;
	private CloudFlareDatabase cloudFlareDatabase;

	public static CloudflareModule getInstance() {
		return instance;
	}

	public ConfigCloudFlare getConfigCloudFlare() {
		return configCloudFlare;
	}

	public CloudFlareDatabase getCloudFlareDatabase() {
		return cloudFlareDatabase;
	}

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onBootstrap() {
		configCloudFlare = new ConfigCloudFlare();
		cloudFlareDatabase = new CloudFlareDatabase(getCloud().getDatabaseManager().getDatabase("cloudnet_internal_cfg"));
		try {

			CloudFlareService cloudFlareAPI = new CloudFlareService(configCloudFlare.load());
			cloudFlareAPI.bootstrap(getCloud().getWrappers().entrySet().stream()
							.collect(Collectors.toMap(Map.Entry::getKey, e ->
									new SimpledWrapperInfo(e.getValue().getServerId(),
											e.getValue().getNetworkInfo().getHostName()))),
					getCloud().getProxyGroups(), cloudFlareDatabase);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		getCloud().getEventManager().registerListener(this, new ProxyAddListener());
		getCloud().getEventManager().registerListener(this, new ProxyRemoveListener());
	}

	@Override
	public void onShutdown() {
		try {
			CloudFlareService.getInstance().shutdown(cloudFlareDatabase);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
