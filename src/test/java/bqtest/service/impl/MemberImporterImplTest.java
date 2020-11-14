package bqtest.service.impl;

import bqtest.service.MemberImporter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.nio.file.NoSuchFileException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class MemberImporterImplTest {

    @TestConfiguration
    public static class MemberFileProcessorBeanConfig {

        @Bean
        public MemberImporter makeImporterBean(){
            return new MemberImporterImpl();
        }
    }

    @Autowired
    private MemberImporter memberImporter;

    @Test
    void importMembers() throws Exception {
        Exception exception = assertThrows(NoSuchFileException.class, () -> {
            memberImporter.importMembers(new File("NoSuchFile.txt"));
        });

        assertThat(exception).isInstanceOf(NoSuchFileException.class);
    }
}