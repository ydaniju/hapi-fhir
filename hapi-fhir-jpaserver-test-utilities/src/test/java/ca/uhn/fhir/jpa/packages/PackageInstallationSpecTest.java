package ca.uhn.fhir.jpa.packages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import ca.uhn.fhir.util.JsonUtil;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class PackageInstallationSpecTest {

    @Test
    public void testExampleSupplier() throws IOException {
        PackageInstallationSpec output = new PackageInstallationSpec.ExampleSupplier().get();
        String json = JsonUtil.serialize(output);
        assertThat(json, containsString("\"name\" : \"hl7.fhir.us.core\""));

        output = new PackageInstallationSpec.ExampleSupplier2().get();
        json = JsonUtil.serialize(output);
        assertThat(json, containsString("\"packageUrl\" : \"classpath:/my-resources.tgz\""));
    }
}
