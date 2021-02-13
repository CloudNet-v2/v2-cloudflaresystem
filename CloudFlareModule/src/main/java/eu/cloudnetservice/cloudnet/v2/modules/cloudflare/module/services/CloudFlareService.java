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

package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.services;

import com.google.common.collect.ImmutableSet;
import eu.cloudnetservice.cloudnet.v2.master.CloudNet;
import eu.cloudnetservice.cloudnet.v2.master.network.components.ProxyServer;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.CreateDnsRecord;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.DeleteDnsRecord;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.ListDnsRecords;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.Verify;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.CloudflareDnsRecord;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.SrvRecord;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.CreateDnsRecordResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.DeleteDnsRecordResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.ListDnsRecordsResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.VerifyResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.exception.CloudFlareDNSRecordException;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models.CloudFlareConfig;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.module.CloudflareModule;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * This class represents the controlling instance of the CloudFlare module.
 * It offers the functionality to add and remove DNS records, as well as verifying given configurations.
 */
public final class CloudFlareService {

    private static final Set<String> A_RECORD_TYPES = ImmutableSet.of("A", "AAAA");
    private final List<CloudFlareConfig> cloudFlareConfigs;
    private final CloudflareModule module;

    /**
     * Constructs a new CloudFlare service instance with a given list of configurations.
     *
     * @param cloudFlareConfigs the configurations for this instance.
     * @param module            the CloudFlare module instance.
     */
    public CloudFlareService(List<CloudFlareConfig> cloudFlareConfigs,
                             final CloudflareModule module) {
        this.module = module;
        this.cloudFlareConfigs = cloudFlareConfigs;
    }

    /**
     * Initializes the CloudFlare service and verifies its configurations by calling the verification endpoint of the CloudFlare API.
     * Configurations with invalid API tokens are temporarily disabled.
     */
    public void bootstrap() {
        for (CloudFlareConfig cloudFlareConfig : this.cloudFlareConfigs) {
            if (cloudFlareConfig.isEnabled()) {

                final Verify verify = new Verify();
                try {
                    final VerifyResponse response = verify.call(cloudFlareConfig);
                    if (response.isSuccess()) {
                        this.module.getModuleLogger().info(String.format("Configuration for %s verified and enabled!",
                            cloudFlareConfig.getDomainName()));
                    } else {
                        this.module.getModuleLogger().warning(String.format("Configuration for %s could not be verified! Disabling...",
                            cloudFlareConfig.getDomainName()));
                        cloudFlareConfig.setEnabled(false);
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<CloudflareDnsRecord> records = null;
                try {
                    final ListDnsRecords request = new ListDnsRecords();
                    final ListDnsRecordsResponse response = request.call(cloudFlareConfig);
                    if (response.isSuccess()) {
                        records = response.getResult();
                    } else {
                        this.module.getModuleLogger().warning(String.format("Error(s) when listing DNS records: %s", response.getErrors()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (records != null) {
                    CloudNet.getInstance().getProxys().forEach((proxyId, proxyServer) -> this.addProxy(proxyServer));
                }
            }
        }
    }

    /**
     * Adds a new proxy to this service and updates or creates necessary DNS records for it.
     * This is done by first listing all DNS records and searching for one with a matching hostname.
     *
     * If the content (target) of the A or AAAA record doesn't match the address of {@code proxyServer}, it is deleted and a new
     * record is created.
     * Then a new SRV record is added for the proxy.
     *
     * @param proxyServer        the proxy server to create DNS records for.
     */
    public void addProxy(ProxyServer proxyServer) {
        for (CloudFlareConfig cloudFlareConfig : this.cloudFlareConfigs) {
            if (cloudFlareConfig.isEnabled()) {
                cloudFlareConfig.getGroups()
                                .stream()
                                .filter(value -> value.getName().equals(proxyServer.getServiceId().getGroup()))
                                .findFirst()
                                .ifPresent(proxyGroup -> {

                                    final ListDnsRecords listRecords = new ListDnsRecords();
                                    try {
                                        final ListDnsRecordsResponse response = listRecords.call(cloudFlareConfig);
                                        response.getResult()
                                                .stream()
                                                .filter(record -> A_RECORD_TYPES.contains(record.getType()))
                                                .filter(record -> record.getName().equals(makeRecordName(proxyServer, cloudFlareConfig)))
                                                .filter(record -> !record.getContent().equals(proxyServer.getNetworkInfo().getHostName().getHostAddress()))
                                                .forEach(record -> this.deleteRecord(cloudFlareConfig, record.getId()));

                                        final InetAddress host = proxyServer.getNetworkInfo().getHostName();
                                        final CloudflareDnsRecord.Builder builder = CloudflareDnsRecord.Builder
                                            .create()
                                            .name(makeRecordName(proxyServer, cloudFlareConfig))
                                            .content(host.getHostAddress());


                                        if (host instanceof Inet4Address) {
                                            builder.type("A");
                                        } else if (host instanceof Inet6Address) {
                                            builder.type("AAAA");
                                        }

                                        final CreateDnsRecord create = new CreateDnsRecord(cloudFlareConfig, builder.build());

                                        try {
                                            create.call(cloudFlareConfig);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        return;
                                    }


                                    final SrvRecord.Builder builder = SrvRecord.Builder
                                        .create()
                                        .service("_minecraft")
                                        .proto("_tcp")
                                        .priority(1)
                                        .weight(1)
                                        .port(proxyServer.getNetworkInfo().getPort())
                                        .target(makeRecordName(proxyServer, cloudFlareConfig));

                                    if (proxyGroup.getSubdomain().startsWith("@")) {
                                        builder.name(cloudFlareConfig.getDomainName());
                                    } else {
                                        builder.name(proxyGroup.getSubdomain());
                                    }

                                    final SrvRecord record = builder.build();

                                    this.createRecord(cloudFlareConfig, record);
                                });
            }
        }
    }

    /**
     * Constructs a name for a record for the given {@code proxyServer} instance and {@code cloudFlareConfig}.
     * This method essentially concatenates the server ID and the configured domain name with a dot (.) in between.
     *
     * @param proxyServer      the given proxy server; acts as the subdomain.
     * @param cloudFlareConfig the given configuration; the domain name is used.
     * @return the constructed record name (server-id.domainname).
     */
    @NotNull
    private static String makeRecordName(final ProxyServer proxyServer, final CloudFlareConfig cloudFlareConfig) {
        return proxyServer.getServerId().toLowerCase(Locale.ROOT) + '.' + cloudFlareConfig.getDomainName();
    }

    /**
     * Deletes the DNS record with the given ID.
     * @param cloudFlareConfig the configuration to use.
     * @param id               the ID of the record to delete.
     *
     * @throws CloudFlareDNSRecordException when the record couldn't be deleted.
     */
    private void deleteRecord(final CloudFlareConfig cloudFlareConfig, final String id) {

        final DeleteDnsRecord request = new DeleteDnsRecord(cloudFlareConfig, id);
        try {
            final DeleteDnsRecordResponse response = request.call(cloudFlareConfig);
            if (response.isSuccess()) {
                this.module.getModuleLogger().info(String.format("DNSRecord [%s] was removed%n", id));
            } else {
                throw new CloudFlareDNSRecordException(String.format("Failed to delete DNSRecord%n%s", response.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new DNS record in the configured zone.
     *
     * @param dnsRecord the record to create
     *
     * @throws CloudFlareDNSRecordException when the requested record could not be created.
     *
     */
    private void createRecord(CloudFlareConfig cloudFlareConfig, CloudflareDnsRecord dnsRecord) {

        final CreateDnsRecord request = new CreateDnsRecord(cloudFlareConfig, dnsRecord);

        try {
            final CreateDnsRecordResponse response = request.call(cloudFlareConfig);

            if (response.isSuccess()) {
                this.module.getModuleLogger().info(String.format("DNSRecord [%s/%s] was created%n",
                    response.getResult().getName(),
                    response.getResult().getType()));
            } else {
                throw new CloudFlareDNSRecordException(String.format("Failed to create DNSRecord %n %s", response.toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        CloudNet.getInstance().getProxys().forEach((key, proxy) -> this.removeProxy(proxy));
    }

    /**
     * Removes a given {@code proxyServer} and its associated DNS records from CloudFlare for all valid configurations.
     * This method removes both the A/AAAA and the SRV record.
     *
     * @param proxyServer        the proxy server to remove.
     */
    public void removeProxy(ProxyServer proxyServer) {
        for (CloudFlareConfig cloudFlareConfig : this.cloudFlareConfigs) {
            if (cloudFlareConfig.isEnabled()) {
                cloudFlareConfig.getGroups()
                                .stream()
                                .filter(value -> value.getName().equals(proxyServer.getServiceId().getGroup()))
                                .findFirst()
                                .ifPresent(proxyGroup -> {

                                    final ListDnsRecords listRecords = new ListDnsRecords();
                                    try {
                                        final ListDnsRecordsResponse response = listRecords.call(cloudFlareConfig);
                                        response.getResult()
                                                .stream()
                                                .filter(record -> A_RECORD_TYPES.contains(record.getType()))
                                                .filter(record -> record.getName().equals(makeRecordName(proxyServer, cloudFlareConfig)))
                                                .filter(record -> record.getContent().equals(proxyServer.getNetworkInfo().getHostName().getHostAddress()))
                                                .forEach(record -> this.deleteRecord(cloudFlareConfig, record.getId()));

                                        response.getResult()
                                                .stream()
                                                .filter(record -> record.getType().equals("SRV"))
                                                .filter(record -> record.getData().get("service").equals("_minecraft"))
                                                .filter(record -> record.getData().get("proto").equals("_tcp"))
                                                .filter(record -> {
                                                    if (proxyGroup.getSubdomain().startsWith("@")) {
                                                        return record.getData().get("name").equals(cloudFlareConfig.getDomainName());
                                                    } else {
                                                        return record.getData().get("name").equals(proxyGroup.getSubdomain() + '.' + cloudFlareConfig.getDomainName());
                                                    }
                                                })
                                                .filter(record -> record.getData().get("target").equals(makeRecordName(proxyServer,
                                                    cloudFlareConfig)))
                                                .filter(record -> {
                                                    Object portObject = record.getData().get("port");
                                                    if (portObject instanceof Number) {
                                                        final int port = ((Number) portObject).intValue(); // Gson deserializes any number into a Double by default
                                                        return port == proxyServer.getNetworkInfo().getPort();
                                                    } else {
                                                        return false;
                                                    }
                                                })
                                                .forEach(record -> this.deleteRecord(cloudFlareConfig, record.getId()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
            }
        }
    }

}
