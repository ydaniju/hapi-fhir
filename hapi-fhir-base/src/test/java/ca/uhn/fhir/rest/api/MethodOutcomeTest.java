package ca.uhn.fhir.rest.api;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MethodOutcomeTest {

    private MethodOutcome myMethodOutcome;

    @BeforeEach
    void setUp() {
        myMethodOutcome = new MethodOutcome();
        myMethodOutcome.setResponseHeaders(new HashMap<>());
    }

    @Test
    void getFirstHeader_withNoHeaders_empty() {

        Optional<String> firstHeader = myMethodOutcome.getFirstResponseHeader("some-header");

        assertTrue(firstHeader.isEmpty());
    }

    @Test
    void getFirstHeader_withTwoHeaders_returnsFirst() {
        myMethodOutcome.getResponseHeaders().put("some-header", Arrays.asList("value1", "value2"));

        Optional<String> firstHeader = myMethodOutcome.getFirstResponseHeader("some-header");

        assertTrue(firstHeader.isPresent());
        assertEquals("value1", firstHeader.get());
    }
}
