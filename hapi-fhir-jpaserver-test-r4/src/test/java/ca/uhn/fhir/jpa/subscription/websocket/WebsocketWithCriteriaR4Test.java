package ca.uhn.fhir.jpa.subscription.websocket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import ca.uhn.fhir.jpa.provider.BaseResourceProviderR4Test;
import ca.uhn.fhir.jpa.subscription.FhirR4Util;
import ca.uhn.fhir.jpa.util.WebsocketSubscriptionClient;
import ca.uhn.fhir.rest.api.MethodOutcome;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Patient;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Subscription;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;

// This is currently disabled as the criteria mechanism was a non-standard experiment
@Disabled
public class WebsocketWithCriteriaR4Test extends BaseResourceProviderR4Test {

    private static final Logger ourLog =
            org.slf4j.LoggerFactory.getLogger(WebsocketWithCriteriaR4Test.class);

    @RegisterExtension
    private final WebsocketSubscriptionClient myWebsocketClientExtension =
            new WebsocketSubscriptionClient(() -> myServer, () -> myStorageSettings);

    private String myPatientId;
    private String mySubscriptionId;

    @Override
    @AfterEach
    public void after() throws Exception {
        super.after();
    }

    @Override
    @BeforeEach
    public void before() throws Exception {
        super.before();

        /*
         * Create patient
         */

        Patient patient = FhirR4Util.getPatient();
        MethodOutcome methodOutcome = myClient.create().resource(patient).execute();
        myPatientId = methodOutcome.getId().getIdPart();

        /*
         * Create subscription
         */
        Subscription subscription = new Subscription();
        subscription.setReason(
                "Monitor new neonatal function (note, age will be determined by the monitor)");
        subscription.setStatus(Subscription.SubscriptionStatus.ACTIVE);
        // subscription.setCriteria("Observation?subject=Patient/" + PATIENT_ID);
        subscription.setCriteria("Observation?code=SNOMED-CT|82313006&_format=xml");

        Subscription.SubscriptionChannelComponent channel =
                new Subscription.SubscriptionChannelComponent();
        channel.setType(Subscription.SubscriptionChannelType.WEBSOCKET);
        channel.setPayload("application/json");
        subscription.setChannel(channel);

        methodOutcome = myClient.create().resource(subscription).execute();
        mySubscriptionId = methodOutcome.getId().getIdPart();

        /*
         * Attach websocket
         */

        myWebsocketClientExtension.bind(mySubscriptionId);
    }

    @Test
    public void createObservation() throws Exception {
        Observation observation = new Observation();
        CodeableConcept codeableConcept = new CodeableConcept();
        observation.setCode(codeableConcept);
        Coding coding = codeableConcept.addCoding();
        coding.setCode("82313006");
        coding.setSystem("SNOMED-CT");
        Reference reference = new Reference();
        reference.setReference("Patient/" + myPatientId);
        observation.setSubject(reference);
        observation.setStatus(Observation.ObservationStatus.FINAL);

        MethodOutcome methodOutcome2 = myClient.create().resource(observation).execute();
        String observationId = methodOutcome2.getId().getIdPart();
        observation.setId(observationId);

        ourLog.info("Observation id generated by server is: " + observationId);

        ourLog.info("WS Messages: {}", myWebsocketClientExtension.getMessages());
        waitForSize(2, myWebsocketClientExtension.getMessages());
        assertThat(
                myWebsocketClientExtension.getMessages(),
                contains("bound " + mySubscriptionId, "ping " + mySubscriptionId));
    }

    @Test
    public void createObservationThatDoesNotMatch() throws Exception {
        Observation observation = new Observation();
        CodeableConcept codeableConcept = new CodeableConcept();
        observation.setCode(codeableConcept);
        Coding coding = codeableConcept.addCoding();
        coding.setCode("8231");
        coding.setSystem("SNOMED-CT");
        Reference reference = new Reference();
        reference.setReference("Patient/" + myPatientId);
        observation.setSubject(reference);
        observation.setStatus(Observation.ObservationStatus.FINAL);

        MethodOutcome methodOutcome2 = myClient.create().resource(observation).execute();
        String observationId = methodOutcome2.getId().getIdPart();
        observation.setId(observationId);

        ourLog.info("Observation id generated by server is: " + observationId);

        ourLog.info("WS Messages: {}", myWebsocketClientExtension.getMessages());
        waitForSize(2, myWebsocketClientExtension.getMessages());
        assertThat(myWebsocketClientExtension.getMessages(), contains("bound " + mySubscriptionId));
    }
}
