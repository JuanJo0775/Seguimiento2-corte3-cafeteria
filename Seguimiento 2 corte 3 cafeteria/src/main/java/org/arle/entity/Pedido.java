
package org.arle.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Plato> platos;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    public Pedido() {}

    public Pedido(List<Plato> platos) {
        this.platos = platos;
        this.estado = EstadoPedido.PENDIENTE;
    }

    public Long getId() { return id; }
    public List<Plato> getPlatos() { return platos; }
    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
}
