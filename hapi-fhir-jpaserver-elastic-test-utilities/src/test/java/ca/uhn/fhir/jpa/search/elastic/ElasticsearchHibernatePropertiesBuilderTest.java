package ca.uhn.fhir.jpa.search.elastic;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.spy;

import ca.uhn.fhir.context.ConfigurationException;
import ca.uhn.fhir.i18n.Msg;
import java.util.Properties;
import org.hibernate.search.backend.elasticsearch.cfg.ElasticsearchBackendSettings;
import org.hibernate.search.engine.cfg.BackendSettings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ElasticsearchHibernatePropertiesBuilderTest {

    ElasticsearchHibernatePropertiesBuilder myPropertiesBuilder =
            spy(ElasticsearchHibernatePropertiesBuilder.class);

    @Test
    public void testHostsCannotContainProtocol() {
        String host = "localhost:9200";
        String protocolHost = "https://" + host;
        String failureMessage =
                "Elasticsearch URLs cannot include a protocol, that is a separate property. Remove"
                        + " http:// or https:// from this URL.";

        myPropertiesBuilder.setProtocol("https").setUsername("whatever").setPassword("whatever");

        // SUT
        try {
            myPropertiesBuilder.setHosts(protocolHost).apply(new Properties());
            fail();
        } catch (ConfigurationException e) {
            assertThat(e.getMessage(), is(equalTo(Msg.code(2139) + failureMessage)));
        }

        Properties properties = new Properties();
        myPropertiesBuilder.setHosts(host).apply(properties);

        assertThat(
                properties.getProperty(
                        BackendSettings.backendKey(ElasticsearchBackendSettings.HOSTS)),
                is(equalTo(host)));
    }
}
