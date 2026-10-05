package com.exemplo.fornecedoresservice.config;

import com.exemplo.fornecedoresservice.model.Fornecedor;
import com.exemplo.fornecedoresservice.repository.FornecedorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Popula o banco H2 em memoria com fornecedores de teste assim que a aplicacao sobe.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final FornecedorRepository fornecedorRepository;

    public DataInitializer(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @Override
    public void run(String... args) {
        fornecedorRepository.save(new Fornecedor("Tech Componentes Ltda", "12.345.678/0001-95"));
        fornecedorRepository.save(new Fornecedor("Mega Perifericos SA", "23.456.789/0001-95"));
        fornecedorRepository.save(new Fornecedor("Distribuidora Horizonte", "34.567.890/0001-30"));
        fornecedorRepository.save(new Fornecedor("Eletro Brasil Importadora", "45.678.901/0001-75"));
        fornecedorRepository.save(new Fornecedor("Office Mais Suprimentos", "56.789.012/0001-00"));
    }
}
