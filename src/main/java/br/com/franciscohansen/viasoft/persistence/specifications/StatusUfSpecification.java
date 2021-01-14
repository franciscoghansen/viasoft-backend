package br.com.franciscohansen.viasoft.persistence.specifications;

import br.com.franciscohansen.viasoft.enums.EEstado;
import br.com.franciscohansen.viasoft.model.StatusUF;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Date;
import java.util.Optional;

public class StatusUfSpecification {

    public static Specification<StatusUF> lastStatusSpec(final String autorizador, final String servico) {
        return (root, query, builder) -> {
            Predicate where = builder.isFalse(builder.coalesce(root.get("deleted"), false));
            query.distinct(true);
            return where;
        };
    }


    public static Specification<StatusUF> filterSpec(
            final Boolean byDate,
            final Boolean byUf,
            final Date startDate,
            final Date endDate,
            final String uf
    ) {
        return (root, query, builder) -> {
            Predicate where = builder.isFalse(builder.coalesce(root.get("deleted"), false));
            if (byUf && !Optional.ofNullable(uf).orElse("").isEmpty()) {
                where = builder.and(
                        where,
                        builder.equal(root.get("autorizador"), uf)
                );
            }
            if (byDate && startDate != null && endDate != null) {
                where = builder.and(where,
                        builder.between(root.get("data"), startDate, endDate)
                );
            }
            query.orderBy(builder.desc(root.get("id")));
            return where;
        };
    }


}
