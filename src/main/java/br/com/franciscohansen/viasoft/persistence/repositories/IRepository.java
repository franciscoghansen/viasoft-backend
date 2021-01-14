package br.com.franciscohansen.viasoft.persistence.repositories;

import br.com.franciscohansen.viasoft.model.AbstractModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IRepository<T extends AbstractModel> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
