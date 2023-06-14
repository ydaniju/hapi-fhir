package ca.uhn.fhir.jpa.embedded;

import static ca.uhn.fhir.jpa.embedded.HapiEmbeddedDatabasesExtension.FIRST_TESTED_VERSION;
import static ca.uhn.fhir.jpa.migrate.SchemaMigrator.HAPI_FHIR_MIGRATION_TABLENAME;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.uhn.fhir.jpa.migrate.DriverTypeEnum;
import ca.uhn.fhir.jpa.migrate.HapiMigrationStorageSvc;
import ca.uhn.fhir.jpa.migrate.MigrationTaskList;
import ca.uhn.fhir.jpa.migrate.SchemaMigrator;
import ca.uhn.fhir.jpa.migrate.dao.HapiMigrationDao;
import ca.uhn.fhir.jpa.migrate.tasks.HapiFhirJpaMigrationTasks;
import ca.uhn.fhir.system.HapiSystemProperties;
import ca.uhn.fhir.util.VersionEnum;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HapiSchemaMigrationTest {

    private static final Logger ourLog = LoggerFactory.getLogger(HapiSchemaMigrationTest.class);
    public static final String TEST_SCHEMA_NAME = "test";

    @RegisterExtension
    static HapiEmbeddedDatabasesExtension myEmbeddedServersExtension =
            new HapiEmbeddedDatabasesExtension();

    @AfterEach
    public void afterEach() {
        myEmbeddedServersExtension.clearDatabases();
        HapiSystemProperties.enableUnitTestMode();
    }

    @ParameterizedTest
    @ArgumentsSource(HapiEmbeddedDatabasesExtension.DatabaseVendorProvider.class)
    public void testMigration(DriverTypeEnum theDriverType) {
        // ensure all migrations are run
        HapiSystemProperties.disableUnitTestMode();

        ourLog.info("Running hapi fhir migration tasks for {}", theDriverType);

        myEmbeddedServersExtension.initializePersistenceSchema(theDriverType);
        myEmbeddedServersExtension.insertPersistenceTestData(theDriverType);

        DataSource dataSource = myEmbeddedServersExtension.getDataSource(theDriverType);
        HapiMigrationDao hapiMigrationDao =
                new HapiMigrationDao(dataSource, theDriverType, HAPI_FHIR_MIGRATION_TABLENAME);
        HapiMigrationStorageSvc hapiMigrationStorageSvc =
                new HapiMigrationStorageSvc(hapiMigrationDao);

        VersionEnum[] allVersions = VersionEnum.values();

        int fromVersion = FIRST_TESTED_VERSION.ordinal() - 1;
        VersionEnum from = allVersions[fromVersion];

        int lastVersion = allVersions.length - 1;
        VersionEnum to = allVersions[lastVersion];

        MigrationTaskList migrationTasks =
                new HapiFhirJpaMigrationTasks(Collections.emptySet()).getTaskList(from, to);
        SchemaMigrator schemaMigrator =
                new SchemaMigrator(
                        TEST_SCHEMA_NAME,
                        HAPI_FHIR_MIGRATION_TABLENAME,
                        dataSource,
                        new Properties(),
                        migrationTasks,
                        hapiMigrationStorageSvc);
        schemaMigrator.setDriverType(theDriverType);
        schemaMigrator.createMigrationTableIfRequired();
        schemaMigrator.migrate();
    }

    @Test
    public void testCreateMigrationTableIfRequired() throws SQLException {
        // Setup
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:h2:mem:no-tables");
        dataSource.setUsername("SA");
        dataSource.setPassword("SA");
        dataSource.start();

        MigrationTaskList migrationTasks =
                new HapiFhirJpaMigrationTasks(Collections.emptySet())
                        .getTaskList(VersionEnum.V6_0_0, VersionEnum.V6_4_0);
        HapiMigrationDao hapiMigrationDao =
                new HapiMigrationDao(
                        dataSource, DriverTypeEnum.H2_EMBEDDED, HAPI_FHIR_MIGRATION_TABLENAME);
        HapiMigrationStorageSvc hapiMigrationStorageSvc =
                new HapiMigrationStorageSvc(hapiMigrationDao);
        SchemaMigrator schemaMigrator =
                new SchemaMigrator(
                        TEST_SCHEMA_NAME,
                        HAPI_FHIR_MIGRATION_TABLENAME,
                        dataSource,
                        new Properties(),
                        migrationTasks,
                        hapiMigrationStorageSvc);
        schemaMigrator.setDriverType(DriverTypeEnum.H2_EMBEDDED);

        // Test & Validate
        assertTrue(schemaMigrator.createMigrationTableIfRequired());
        assertFalse(schemaMigrator.createMigrationTableIfRequired());
        assertFalse(schemaMigrator.createMigrationTableIfRequired());
    }
}
