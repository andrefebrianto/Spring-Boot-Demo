package com.example.demo.repository;

import com.example.demo.model.entity._BaseEntityWithAudit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface _BaseEntityWithAuditRepository<T extends _BaseEntityWithAudit, ID>
        extends JpaRepository<T, ID> {

    @Override
    default void delete(T entity) {
        entity.setIsDeleted(Boolean.TRUE);
        this.save(entity);
    }

    @Override
    default void deleteById(ID id) {
        Optional<T> entity = this.findById(id);
        entity.ifPresent(this::delete);
    }

    @Override
    default void deleteAll() {
        for (T element : this.findAll()) {
            this.delete(element);
        }
    }

    @Override
    default void deleteAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            this.delete(entity);
        }
    }

    @Override
    default void deleteAllById(Iterable<? extends ID> ids) {
        for (ID id : ids) {
            this.deleteById(id);
        }
    }
}
