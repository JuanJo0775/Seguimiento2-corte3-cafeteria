package org.arle.Thread;

import org.arle.entity.Pedido;
import org.arle.entity.EstadoPedido;
import org.arle.service.PedidoService;

public class PrepararPedidoThread extends Thread {
    private Pedido pedido;
    private PedidoService pedidoService;

    public PrepararPedidoThread(Pedido pedido, PedidoService pedidoService) {
        this.pedido = pedido;
        this.pedidoService = pedidoService;
    }

    @Override
    public void run() {
        try {
            System.out.println("Pedido ID " + pedido.getId() + " - Estado: " + pedido.getEstado());
            pedidoService.actualizarEstado(pedido, EstadoPedido.EN_PREPARACION);
            Thread.sleep(2000);
            pedidoService.actualizarEstado(pedido, EstadoPedido.LISTO);
            System.out.println("Pedido ID " + pedido.getId() + " - Estado: " + pedido.getEstado());
            Thread.sleep(1000);
            pedidoService.actualizarEstado(pedido, EstadoPedido.ENTREGADO);
            System.out.println("Pedido ID " + pedido.getId() + " - Estado: " + pedido.getEstado());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
