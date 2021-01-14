package br.com.franciscohansen.viasoft.components.schedulres;

import br.com.franciscohansen.viasoft.components.runnables.StatusRunnable;
import br.com.franciscohansen.viasoft.persistence.repositories.StatusUFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class StatusScheduler {
    private final StatusUFRepository repository;

    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    @Autowired
    public StatusScheduler(StatusUFRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void schedule() {
        this.executor.scheduleAtFixedRate(new StatusRunnable(this.repository), 0L, 5L, TimeUnit.MINUTES);
    }
}
