package com.victorbarreto.msclientes.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.victorbarreto.msclientes.domain.Cliente;
import com.victorbarreto.msclientes.infra.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente){
        return repository.save(cliente);
    }

    public Optional<Cliente> getByCPF(String cpf){
        return repository.findByCpf(cpf);
    }
}
