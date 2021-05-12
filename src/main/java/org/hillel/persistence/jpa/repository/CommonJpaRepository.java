package org.hillel.persistence.jpa.repository;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface CommonJpaRepository<E extends Persistable<ID>, ID extends Serializable> extends JpaRepository<E, ID> {

    @Query("select e from #{#entityName} e where e.active = true")
    List<E> findOnlyActive();

    @Modifying
    @Query("update #{#entityName} e set e.active = false where e.id = :id")
    void disableById(@Param("id") ID id);

}
