package com.cristalice.controller;

import com.cristalice.model.Pedido;
import com.cristalice.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> cadastrarPedido(@RequestBody Pedido pedido) {
        Pedido novoPedido = pedidoService.cadastrarPedido(pedido);
        return ResponseEntity.ok(novoPedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPedidoPorId(id);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dia")
    public ResponseEntity<List<Pedido>> getPedidosDoDia() {
        List<Pedido> pedidos = pedidoService.listarPedidosDoDia();
        double faturamento = pedidoService.calcularFaturamento(pedidos);

        Map<String, Object> response = new HashMap<>();
        response.put("pedidos", pedidos);
        response.put("faturamento", faturamento);
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/mes")
    public ResponseEntity<List<Pedido>> getPedidosDoMes() {
        List<Pedido> pedidos = pedidoService.listarPedidosDoMes();
        return ResponseEntity.ok(pedidos);
    }


    @PutMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatusPedido(@PathVariable Long id, @RequestParam String status) {
        pedidoService.atualizarStatusPedido(id, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
