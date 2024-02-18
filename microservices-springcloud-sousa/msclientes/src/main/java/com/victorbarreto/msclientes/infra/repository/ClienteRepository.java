package com.victorbarreto.msclientes.infra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.victorbarreto.msclientes.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpf(String cpf);
}