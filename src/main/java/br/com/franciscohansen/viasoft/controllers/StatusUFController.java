package br.com.franciscohansen.viasoft.controllers;

import br.com.franciscohansen.viasoft.dto.UFCountDTO;
import br.com.franciscohansen.viasoft.dto.UFStatusDTO;
import br.com.franciscohansen.viasoft.enums.EEstado;
import br.com.franciscohansen.viasoft.model.StatusUF;
import br.com.franciscohansen.viasoft.persistence.repositories.StatusUFRepository;
import br.com.franciscohansen.viasoft.persistence.service.StatusUFService;
import br.com.franciscohansen.viasoft.persistence.specifications.StatusUfSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/status-uf")
public class StatusUFController extends AbstractController<StatusUF, StatusUFRepository> {


    private final StatusUFRepository repository;
    private final StatusUFService service;

    @Autowired
    public StatusUFController(StatusUFRepository repository, StatusUFService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/most-inactive/{servico}")
    public UFCountDTO mostInactive(@PathVariable String servico) {
        if (Optional.ofNullable(servico).orElse("").isEmpty()) {
            return null;
        }
        List<UFCountDTO> list = this.service.countMostInactive(servico);
        return list.isEmpty() ? null : list.get(0);
    }

    @GetMapping(value = "/last-status", produces = MediaType.APPLICATION_JSON_VALUE)
    public UFStatusDTO lastStatus(@RequestParam("autorizador") String autorizador, @RequestParam("servico") String servico) {
        return this.service.lastStatus(autorizador, servico);
    }


    @GetMapping("/status/{start}/{length}")
    public List<StatusUF> consultaStatus(
            @RequestParam(value = "by-date", defaultValue = "false") Boolean byDate,
            @RequestParam(value = "by-uf", defaultValue = "false") Boolean byUf,
            @RequestParam(value = "start-date", required = false)
            @DateTimeFormat(pattern = "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\"") Date startDate,
            @RequestParam(value = "end-date", required = false)
            @DateTimeFormat(pattern = "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\"", iso= DateTimeFormat.ISO.DATE_TIME) Date endDate,
            @RequestParam(value = "uf", required = false) String uf,
            @PathVariable int start,
            @PathVariable int length
    ) {

        return repository().findAll(
                Specification.where(
                        StatusUfSpecification.filterSpec(
                                byDate,
                                byUf,
                                startDate,
                                endDate,
                                uf
                        )
                ), PageRequest.of(start, length)
        ).toList();
    }

    @GetMapping("/status-count")
    public Long consultaStatusCount(
            @RequestParam(value = "by-date", defaultValue = "false") Boolean byDate,
            @RequestParam(value = "by-uf", defaultValue = "false") Boolean byUf,
            @RequestParam(value = "start-date", required = false)
            @DateTimeFormat(pattern = "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\"") Date startDate,
            @RequestParam(value = "end-date", required = false)
            @DateTimeFormat(pattern = "\"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'\"", iso= DateTimeFormat.ISO.DATE_TIME) Date endDate,
            @RequestParam(value = "uf", required = false) String uf
    ) {

        return repository().count(
                Specification.where(
                        StatusUfSpecification.filterSpec(
                                byDate,
                                byUf,
                                startDate,
                                endDate,
                                uf
                        )
                )
        );
    }

    @Override
    protected StatusUFRepository repository() {
        return this.repository;
    }
}
