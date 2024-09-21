package com.cristalice.service;

import com.cristalice.model.Pedido;
import com.cristalice.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido cadastrarPedido(Pedido pedido) {
        pedido.setStatus("Pendente");
        pedido.setValor(calcularValorDoPedido(pedido));
        pedido.setData(LocalDate.now());
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPedidosDoDia() {
        LocalDate hoje = LocalDate.now();
        List<Pedido> pedidosDoDia = pedidoRepository.findByData(hoje);

        // Ordenar para manter os não entregues no topo
        pedidosDoDia.sort(Comparator.comparing(pedido ->
                pedido.getStatus().equals("Entregue") ? 1 : 0)); // 0 para Pendente, 1 para Entregue

        return pedidosDoDia;
    }

    public List<Pedido> listarPedidosDoMes() {
        LocalDate primeiroDiaMes = LocalDate.now().withDayOfMonth(1);
        LocalDate ultimoDiaMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        return pedidoRepository.findByDataBetween(primeiroDiaMes, ultimoDiaMes);
    }


    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public void atualizarStatusPedido(Long id, String status) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null) {
            pedido.setStatus(status);
            pedidoRepository.save(pedido);
        }
    }

    public void deletarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    public double calcularFaturamento(List<Pedido> pedidos) {
        return pedidos.stream().mapToDouble(Pedido::getValor).sum();
    }

    private double calcularValorDoPedido(Pedido pedido) {
        return pedido.getProduto().getPreco() * pedido.getQuantidade();
    }
}
