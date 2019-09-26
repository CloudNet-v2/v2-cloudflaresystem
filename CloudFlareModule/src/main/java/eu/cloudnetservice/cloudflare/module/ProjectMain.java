package eu.cloudnetservice.cloudflare.module;

import de.dytanic.cloudnet.lib.service.SimpledWrapperInfo;
import de.dytanic.cloudnet.lib.utility.Catcher;
import de.dytanic.cloudnet.lib.utility.MapWrapper;
import de.dytanic.cloudnetcore.api.CoreModule;
import de.dytanic.cloudnetcore.network.components.Wrapper;
import eu.cloudnetservice.cloudflare.module.config.ConfigCloudFlare;
import eu.cloudnetservice.cloudflare.module.database.CloudFlareDatabase;
import eu.cloudnetservice.cloudflare.module.listener.ProxyAddListener;
import eu.cloudnetservice.cloudflare.module.listener.ProxyRemoveListener;
import eu.cloudnetservice.cloudflare.module.services.CloudFlareService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ProjectMain extends CoreModule {
	private static ProjectMain instance;
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	private ConfigCloudFlare configCloudFlare;
	private CloudFlareDatabase cloudFlareDatabase;

	public static ProjectMain getInstance() {
		return instance;
	}

	public ConfigCloudFlare getConfigCloudFlare() {
		return configCloudFlare;
	}

	public ExecutorService getExecutor() {
		return executor;
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
			cloudFlareAPI.bootstrap(MapWrapper.transform(getCloud().getWrappers(), new Catcher<String, String>() {
				@Override
				public String doCatch(String key) {
					return key;
				}
			}, new Catcher<SimpledWrapperInfo, Wrapper>() {
				@Override
				public SimpledWrapperInfo doCatch(Wrapper key) {
					return new SimpledWrapperInfo(key.getServerId(), key.getNetworkInfo().getHostName());
				}
			}), getCloud().getProxyGroups(), cloudFlareDatabase);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		getCloud().getEventManager().registerListener(this, new ProxyAddListener());
		getCloud().getEventManager().registerListener(this, new ProxyRemoveListener());
	}

	@Override
	public void onShutdown() {

		executor.shutdownNow();

		try {
			CloudFlareService.getInstance().shutdown(cloudFlareDatabase);
		} catch (Exception ignored) {
		}
	}
}
