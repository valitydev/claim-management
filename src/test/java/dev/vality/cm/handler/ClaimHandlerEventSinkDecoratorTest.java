package dev.vality.cm.handler;

import dev.vality.cm.AbstractKafkaIntegrationTest;
import dev.vality.cm.serde.ClaimManagementEventDeserializer;
import dev.vality.cm.service.ConversionWrapperService;
import dev.vality.cm.util.MockUtil;
import dev.vality.damsel.claim_management.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

import static dev.vality.cm.util.ServiceUtils.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ClaimHandlerEventSinkDecoratorTest extends AbstractKafkaIntegrationTest {

    public static final String PARTY_ID_2 = "party_id_2";

    @Autowired
    private ClaimManagementSrv.Iface client;

    @Autowired
    private ConversionWrapperService conversionWrapperService;

    @Test
    public void testCreateClaimAndGet() {
        Claim claim = createClaim(client, PARTY_ID_2, generateModifications(conversionWrapperService,
                () -> MockUtil.generateTBaseList(Modification.party_modification(new PartyModification()), 5)));

        Consumer<String, Event> consumer = createConsumer(ClaimManagementEventDeserializer.class);
        consumer.subscribe(List.of(Initializer.CLAIM_EVENT_SINK));

        consumer.poll(Duration.ofSeconds(5))
                .forEach(event -> {
                    Event value = event.value();
                    assertEquals(EMAIL, value.getUserInfo().getEmail());
                    assertEquals(claim.getPartyId(), event.key());
                    assertEquals(claim.getId(), value.getChange().getCreated().getId());
                    assertEquals(5, value.getChange().getCreated().getChangesetSize());
                });

        runService(() -> client.updateClaim(PARTY_ID_2, claim.getId(), 0,
                generateModifications(conversionWrapperService, () -> MockUtil
                        .generateTBaseList(Modification.claim_modification(new ClaimModification()), 5))));

        consumer.poll(Duration.ofSeconds(5))
                .forEach(event -> {
                    Event value = event.value();
                    assertEquals(EMAIL, value.getUserInfo().getEmail());
                    assertEquals(claim.getPartyId(), event.key());
                    assertEquals(claim.getId(), value.getChange().getUpdated().getId());
                    assertEquals(5, value.getChange().getUpdated().getChangesetSize());
                });

        runService(() -> client.requestClaimReview(PARTY_ID_2, claim.getId(), 1));

        consumer.poll(Duration.ofSeconds(5))
                .forEach(event -> {
                    Event value = event.value();
                    assertEquals(EMAIL, value.getUserInfo().getEmail());
                    assertEquals(claim.getPartyId(), event.key());
                    assertEquals(claim.getId(), value.getChange().getStatusChanged().getId());
                    assertTrue(value.getChange().getStatusChanged().getStatus().isSetReview());
                });

        runService(() -> client.requestClaimChanges(PARTY_ID_2, claim.getId(), 2));

        consumer.poll(Duration.ofSeconds(5))
                .forEach(event -> {
                    Event value = event.value();
                    assertEquals(EMAIL, value.getUserInfo().getEmail());
                    assertEquals(claim.getPartyId(), event.key());
                    assertEquals(claim.getId(), value.getChange().getStatusChanged().getId());
                    assertTrue(value.getChange().getStatusChanged().getStatus().isSetPending());
                });

        consumer.close();
    }
}
