package eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints;

import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.CloudflareDnsRecord;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.SrvRecord;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.common.TxtRecord;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.CreateDnsRecordResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.DeleteDnsRecordResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.ListDnsRecordsResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.endpoints.responses.VerifyResponse;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models.CloudFlareConfig;
import eu.cloudnetservice.cloudnet.v2.modules.cloudflare.core.models.CloudFlareProxyGroup;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EndpointsTest {

    private final CloudFlareConfig testConfig = new CloudFlareConfig(
        true,
        System.getenv("CF_TOKEN"),
        System.getenv("CF_DOMAIN"),
        System.getenv("CF_ZONE_ID"),
        Collections.singletonList(
            new CloudFlareProxyGroup(
                "test",
                ""
            )
        )
    );

    private final List<CloudflareDnsRecord> records = new ArrayList<>();


    @TestFactory
    Collection<DynamicTest> cloudflareTests() {
        if (this.testConfig.getToken() != null) {
            return Arrays.asList(
                DynamicTest.dynamicTest("Verify token", this::verify),
                DynamicTest.dynamicTest("List records", this::list),
                DynamicTest.dynamicTest("Create record", this::create),
                DynamicTest.dynamicTest("Delete record", this::delete)
            );
        } else {
            return Collections.emptyList();
        }
    }


    void verify() {
        final Verify verify = new Verify();
        assertDoesNotThrow(() -> {
            final VerifyResponse response = verify.call(this.testConfig);
            System.out.println(response);
            assertTrue(response.isSuccess());
        });
    }

    void list() {
        final ListDnsRecords list = new ListDnsRecords();
        assertDoesNotThrow(() -> {
            final ListDnsRecordsResponse response = list.call(this.testConfig);
            System.out.println(response);
            assertTrue(response.isSuccess());
            records.clear();
        });
    }

    void create() {
        final CreateDnsRecord record = new CreateDnsRecord(this.testConfig,
            TxtRecord.Builder
                .create()
                .name("test")
                .content("test")
                .ttl(1)
                .build()
        );
        assertDoesNotThrow(() -> {
            final CreateDnsRecordResponse response = record.call(this.testConfig);
            System.out.println(response);
            assertTrue(response.isSuccess());
            this.records.add(response.getResult());
        });
        final CreateDnsRecord srvRecord = new CreateDnsRecord(this.testConfig,
            SrvRecord.Builder
                .create()
                .name("test-srv")
                .service("_minecraft")
                .proto("_tcp")
                .name(this.testConfig.getDomainName())
                .priority(1)
                .weight(1)
                .port(25565)
                .target("test." + this.testConfig.getDomainName())
                .build()
        );
        assertDoesNotThrow(() -> {
            final CreateDnsRecordResponse response = srvRecord.call(this.testConfig);
            System.out.println(response);
            assertTrue(response.isSuccess());
            this.records.add(response.getResult());
        });
    }

    void delete() {
        for (final CloudflareDnsRecord record : this.records) {
            final DeleteDnsRecord delete = new DeleteDnsRecord(this.testConfig, record.getId());
            assertDoesNotThrow(() -> {
                final DeleteDnsRecordResponse response = delete.call(this.testConfig);
                System.out.println(response);
                assertNotNull(response);
            });
        }
        records.clear();
    }

}
