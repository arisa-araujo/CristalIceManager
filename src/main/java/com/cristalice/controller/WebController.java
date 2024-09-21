package com.cristalice.controller;

import com.cristalice.model.Cliente;
import com.cristalice.model.Pedido;
import com.cristalice.model.Produto;
import com.cristalice.service.ClienteService;
import com.cristalice.service.PedidoService;
import com.cristalice.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/clientes")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.listarClientes();
        model.addAttribute("clientes", clientes);
        return "clientes";
    }

    @GetMapping("/pedidos")
    public String listarPedidos(Model model) {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        model.addAttribute("pedidos", pedidos);
        return "pedidos";
    }

    @GetMapping("/pedidosDoDia")
    public String listarPedidosDoDia(Model model) {
        List<Pedido> pedidosDoDia = pedidoService.listarPedidosDoDia();
        model.addAttribute("pedidos", pedidosDoDia);
        return "pedidos"; // Redireciona para a mesma página de pedidos
    }

    @GetMapping("/pedidosDoMes")
    public String listarPedidosDoMes(Model model) {
        List<Pedido> pedidosDoMes = pedidoService.listarPedidosDoMes();
        model.addAttribute("pedidos", pedidosDoMes);
        return "pedidos"; // Redireciona para a mesma página de pedidos
    }

    @GetMapping("/cadastrarCliente")
    public String cadastrarClienteForm() {
        return "cadastrarCliente";
    }

    @PostMapping("/cadastrarCliente")
    public ResponseEntity<String> cadastrarCliente(
            @RequestParam("nome") String nome,
            @RequestParam("telefone") String telefone,
            @RequestParam("endereco") String endereco)  {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        clienteService.cadastrarCliente(cliente);
        return ResponseEntity.ok("Cliente cadastrado com sucesso!");
    }

    @GetMapping("/cadastrarPedido")
    public String cadastrarPedidoForm(Model model) {
        model.addAttribute("clientes", clienteService.listarClientes());
        model.addAttribute("produtos", produtoService.listarProdutos());
        return "cadastrarPedido";
    }


    @PostMapping("/cadastrarPedido")
    public ResponseEntity<String> cadastrarPedido(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("produtoId") Long produtoId,
            @RequestParam("quantidade") Integer quantidade) {
        Pedido pedido = new Pedido();
        Cliente cliente = clienteService.buscarClientePorId(clienteId);
        Produto produto = produtoService.buscarProdutoPorId(produtoId);
        pedido.setProduto(produto);
        pedido.setCliente(cliente);
        pedido.setQuantidade(quantidade);
        pedido.setStatus("Pendente");
        pedidoService.cadastrarPedido(pedido);
        return ResponseEntity.ok("Pedido cadastrado com sucesso!");
    }


    @PostMapping("/atualizarStatus")
    public String atualizarStatus(@RequestParam Long pedidoId, @RequestParam String status) {
        Pedido pedido = pedidoService.buscarPedidoPorId(pedidoId);
        if (pedido != null) {
            pedido.setStatus(status);
            pedidoService.atualizarStatusPedido(pedidoId, status);
        }
        return "redirect:/pedidos";
    }
}

