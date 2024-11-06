package org.arle.service;

import org.arle.entity.Plato;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ProductoService {
    private EntityManager entityManager;

    public ProductoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addPlato(Plato plato) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(plato);
        transaction.commit();
    }

    public List<Plato> getAllPlatos() {
        return entityManager.createQuery("SELECT p FROM Plato p", Plato.class).getResultList();
    }
}
