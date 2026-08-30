package br.com.nicolas.projectflow.repository;

import br.com.nicolas.projectflow.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    // Uma funcionalidade bônus: buscar todas as tarefas de um projeto específico!
    List<Tarefa> findByProjetoId(Long projetoId);
}