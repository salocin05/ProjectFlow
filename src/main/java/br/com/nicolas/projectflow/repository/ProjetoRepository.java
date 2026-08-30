package br.com.nicolas.projectflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nicolas.projectflow.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto,Long>{
}
