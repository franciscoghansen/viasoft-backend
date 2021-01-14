package br.com.franciscohansen.viasoft.controllers;

import br.com.franciscohansen.viasoft.model.AbstractModel;
import br.com.franciscohansen.viasoft.persistence.repositories.IRepository;
import br.com.franciscohansen.viasoft.persistence.specifications.StatusUfSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

public abstract class AbstractController<T extends AbstractModel, U extends IRepository<T>> {

    protected abstract U repository();

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public T save(@RequestBody T obj) {
        return repository().save(obj);
    }


}
