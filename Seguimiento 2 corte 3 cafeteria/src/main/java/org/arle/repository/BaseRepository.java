package org.arle.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class BaseRepository<T> {
    private final EntityManager entityManager;
    private final Class<T> entityClass;

    public BaseRepository(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public void save(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
    }

    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }

    public T findById(Long id) {
        return entityManager.find(entityClass, id);
    }
}
