package com.exemplo.fornecedoresservice.service;

import com.exemplo.fornecedoresservice.client.ProdutoClient;
import com.exemplo.fornecedoresservice.dto.ProdutoDTO;
import com.exemplo.fornecedoresservice.model.Fornecedor;
import com.exemplo.fornecedoresservice.repository.FornecedorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Regra de negocio de Fornecedor. O controller nao fala direto com o repository
 * nem com o Feign, fala com este service.
 */
@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final ProdutoClient produtoClient;

    public FornecedorService(FornecedorRepository fornecedorRepository, ProdutoClient produtoClient) {
        this.fornecedorRepository = fornecedorRepository;
        this.produtoClient = produtoClient;
    }

    public List<Fornecedor> listarTodos() {
        return fornecedorRepository.findAll();
    }

    public Optional<Fornecedor> buscarPorId(Long id) {
        return fornecedorRepository.findById(id);
    }

    public Fornecedor salvar(Fornecedor fornecedor) {
        // nome e cnpj sao obrigatorios: sem isso o banco lancaria erro e a API devolveria 500
        if (fornecedor.getNome() == null || fornecedor.getNome().isBlank()
                || fornecedor.getCnpj() == null || fornecedor.getCnpj().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "nome e cnpj sao obrigatorios");
        }

        // cnpj e' unico: devolve 409 em vez de deixar estourar a constraint do banco
        if (fornecedorRepository.existsByCnpj(fornecedor.getCnpj())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "ja existe fornecedor com o cnpj " + fornecedor.getCnpj());
        }

        // o id e' sempre gerado pelo banco; um id vindo no JSON nao pode sobrescrever outro registro
        fornecedor.setId(null);
        return fornecedorRepository.save(fornecedor);
    }

    public List<ProdutoDTO> listarProdutos() {
        return produtoClient.listarProdutos();
    }
}
