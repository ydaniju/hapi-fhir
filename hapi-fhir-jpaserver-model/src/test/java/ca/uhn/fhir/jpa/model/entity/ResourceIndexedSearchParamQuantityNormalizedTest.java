package ca.uhn.fhir.jpa.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import ca.uhn.fhir.jpa.model.config.PartitionSettings;
import org.junit.jupiter.api.Test;

public class ResourceIndexedSearchParamQuantityNormalizedTest {

    @Test
    public void testEquals() {
        BaseResourceIndexedSearchParamQuantity val1 =
                new ResourceIndexedSearchParamQuantityNormalized()
                        .setValue(Double.parseDouble("123"));
        val1.setPartitionSettings(new PartitionSettings());
        val1.calculateHashes();
        BaseResourceIndexedSearchParamQuantity val2 =
                new ResourceIndexedSearchParamQuantityNormalized()
                        .setValue(Double.parseDouble("123"));
        val2.setPartitionSettings(new PartitionSettings());
        val2.calculateHashes();
        assertEquals(val1, val1);
        assertEquals(val1, val2);
        assertNotEquals(val1, null);
        assertNotEquals(val1, "");
    }
}
