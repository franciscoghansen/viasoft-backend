package br.com.franciscohansen.viasoft.persistence.service;

import br.com.franciscohansen.viasoft.dto.UFCountDTO;
import br.com.franciscohansen.viasoft.dto.UFStatusDTO;
import br.com.franciscohansen.viasoft.enums.EEstado;
import br.com.franciscohansen.viasoft.model.StatusUF;
import br.com.franciscohansen.viasoft.persistence.repositories.StatusUFRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.OrderBy;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("statusUFService")
public class StatusUFService {


    private final StatusUFRepository repository;
    @PersistenceContext
    private EntityManager em;

    @Autowired
    public StatusUFService(StatusUFRepository repository) {
        this.repository = repository;
    }

    public List<UFCountDTO> countMostInactive(String servico) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<StatusUF> root = query.from(StatusUF.class);
        query.groupBy(root.get("autorizador"));
        query.multiselect(root.get("autorizador").alias("autorizador"), builder.count(root).alias("total"));
        Predicate where = builder.and(
                builder.isFalse(builder.coalesce(root.get("deleted"), false)),
                builder.or(
                        builder.equal(root.get(servico), EEstado.VERMELHO),
                        builder.equal(root.get(servico), EEstado.AMARELO)
                )
        );
        query.where(where);
        List<UFCountDTO> response = new ArrayList<>();
        List<Tuple> result = this.em.createQuery(query).getResultList();
        for (Tuple t : result) {
            response.add(
                    UFCountDTO.builder()
                            .autorizador(t.get("autorizador", String.class))
                            .count(t.get("total", Long.class))
                            .build()
            );
        }
        return response.stream()
                .sorted(Comparator.comparing(UFCountDTO::getCount).reversed())
                .collect(Collectors.toList());
    }

    public UFStatusDTO lastStatus(final String autorizador, final String servico) {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<StatusUF> root = query.from(StatusUF.class);
        query.multiselect(root.get("id").alias("id"), root.get(servico).alias(servico));
        Predicate where = builder.and(
                builder.isFalse(builder.coalesce(root.get("deleted"), false)),
                builder.equal(root.get("autorizador"), autorizador)
        );
        query.where(where);
        query.orderBy(builder.desc(root.get("id")));
        List<Tuple> result = this.em.createQuery(query).getResultList();
        if (!result.isEmpty()) {
            return UFStatusDTO.builder()
                    .status(result.get(0).get(servico, EEstado.class))
                    .build();
        } else {
            return UFStatusDTO.builder()
                    .status(EEstado.NONE)
                    .build();
        }
    }

}
