package br.com.franciscohansen.viasoft;

import br.com.franciscohansen.viasoft.components.downloader.NFeStatusDownloader;
import br.com.franciscohansen.viasoft.components.schedulres.StatusScheduler;
import br.com.franciscohansen.viasoft.model.StatusUF;
import br.com.franciscohansen.viasoft.persistence.repositories.StatusUFRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootTest
class ViasoftApplicationTests {
    private static final Logger LOG = Logger.getLogger(ViasoftApplicationTests.class.getSimpleName());
    @Autowired
    private StatusUFRepository repository;

    @Test
    void testDownloadData() {
        try {
            List<StatusUF> list = new NFeStatusDownloader().read();
            Assertions.assertFalse(list.isEmpty());
            this.repository.saveAll(list);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
            Assertions.fail(e.getMessage());
        }
    }


}
