
package org.arle.service;

import org.arle.entity.Pedido;
import org.arle.entity.EstadoPedido;
import org.arle.repository.PedidoRepository;

public class PedidoService {
    private PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void actualizarEstado(Pedido pedido, EstadoPedido estado) {
        pedido.setEstado(estado);
        pedidoRepository.save(pedido);
    }
}
