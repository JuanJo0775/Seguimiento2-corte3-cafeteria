package org.arle;

import org.arle.entity.EstadoPedido;
import org.arle.entity.Pedido;
import org.arle.entity.Plato;
import org.arle.repository.PedidoRepository;
import org.arle.service.PedidoService;
import org.arle.service.ProductoService;
import org.arle.Thread.PrepararPedidoThread;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("cafeteriaPU");
        EntityManager em = emf.createEntityManager();

        PedidoRepository pedidoRepository = new PedidoRepository(em);
        PedidoService pedidoService = new PedidoService(pedidoRepository);
        ProductoService productoService = new ProductoService(em);

        // Agregar platos predefinidos
        productoService.addPlato(new Plato("Café", 3.00));
        productoService.addPlato(new Plato("Galletas", 1.50));
        productoService.addPlato(new Plato("Té", 2.50));

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú Cafetería ---");
            System.out.println("1. Realizar Pedido");
            System.out.println("2. Ver Pedidos");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1 -> realizarPedido(productoService, pedidoService, pedidoRepository);
                case 2 -> mostrarPedidos(pedidoRepository);
                case 3 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 3);

        em.close();
        emf.close();
    }

    private static void realizarPedido(ProductoService productoService, PedidoService pedidoService, PedidoRepository pedidoRepository) {
        List<Plato> productos = productoService.getAllPlatos();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Realizar Pedido ---");
        List<Plato> platosSeleccionados = new ArrayList<>();

        int opcion;
        do {
            System.out.println("Seleccione un producto para agregar al pedido:");
            for (int i = 0; i < productos.size(); i++) {
                System.out.println((i + 1) + ". " + productos.get(i).getNombre() + " - $" + productos.get(i).getPrecio());
            }
            System.out.println((productos.size() + 1) + ". Terminar pedido");

            opcion = scanner.nextInt();

            if (opcion > 0 && opcion <= productos.size()) {
                platosSeleccionados.add(productos.get(opcion - 1));
                System.out.println("Producto agregado al pedido.");
            } else if (opcion != productos.size() + 1) {
                System.out.println("Opción no válida.");
            }
        } while (opcion != productos.size() + 1);

        if (!platosSeleccionados.isEmpty()) {
            Pedido pedido = new Pedido(platosSeleccionados);
            pedidoRepository.save(pedido);

            PrepararPedidoThread prepararPedido = new PrepararPedidoThread(pedido, pedidoService);
            prepararPedido.start();

            try {
                prepararPedido.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Pedido vacío, no se ha realizado ningún pedido.");
        }
    }

    private static void mostrarPedidos(PedidoRepository pedidoRepository) {
        List<Pedido> pedidos = pedidoRepository.findAll();
        System.out.println("\n--- Lista de Pedidos ---");
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println("Pedido ID: " + pedido.getId() + ", Estado: " + pedido.getEstado());
                System.out.println("Productos:");
                for (Plato plato : pedido.getPlatos()) {
                    System.out.println("- " + plato.getNombre() + " - $" + plato.getPrecio());
                }
                System.out.println();
            }
        }
    }
}
