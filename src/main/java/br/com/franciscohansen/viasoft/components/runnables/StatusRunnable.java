package br.com.franciscohansen.viasoft.components.runnables;

import br.com.franciscohansen.viasoft.components.downloader.NFeStatusDownloader;
import br.com.franciscohansen.viasoft.model.StatusUF;
import br.com.franciscohansen.viasoft.persistence.repositories.StatusUFRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatusRunnable implements Runnable {
    private static final Logger LOG = Logger.getLogger(StatusRunnable.class.getSimpleName());

    private final StatusUFRepository repository;

    public StatusRunnable(StatusUFRepository repository) {
        this.repository = repository;
    }


    @Override
    public void run() {
        try {
            List<StatusUF> list = new NFeStatusDownloader().read();
            this.repository.saveAll(list);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error Downloading Data", e);
        }
    }
}
