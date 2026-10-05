package com.exemplo.fornecedoresservice.client;

import com.exemplo.fornecedoresservice.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Cliente Feign do produtos-service. O "name" e' o nome registrado no Eureka;
 * o Feign descobre sozinho o endereco e a porta, sem URL escrita a mao.
 */
@FeignClient(name = "produtos-service")
public interface ProdutoClient {

    @GetMapping("/produtos")
    List<ProdutoDTO> listarProdutos();
}
