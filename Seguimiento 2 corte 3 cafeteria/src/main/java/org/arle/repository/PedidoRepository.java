package org.arle.repository;

import jakarta.persistence.EntityManager;
import org.arle.entity.Pedido;

public class PedidoRepository extends BaseRepository<Pedido> {
    public PedidoRepository(EntityManager entityManager) {
        super(entityManager, Pedido.class);
    }
}
