package br.com.nicolas.projectflow.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.nicolas.projectflow.model.Projeto;
import br.com.nicolas.projectflow.model.Tarefa;
import br.com.nicolas.projectflow.repository.ProjetoRepository;
import br.com.nicolas.projectflow.repository.TarefaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final TarefaRepository tarefaRepository;
    private final ProjetoRepository projetoRepository;

    // Construtor (o novo padrão ninja sem @Autowired)
    public TarefaController(TarefaRepository tarefaRepository, ProjetoRepository projetoRepository) {
        this.tarefaRepository = tarefaRepository;
        this.projetoRepository = projetoRepository;
    }

    // 1. Criar uma tarefa e vinculá-rad a um Projeto
    @PostMapping("/projeto/{projetoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Tarefa criarTarefa(@PathVariable Long projetoId, @Valid @RequestBody Tarefa tarefa) {
        // Primeiro, verificamos se o projeto existe
        Projeto projetoEncontrado = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));
        
        // Colocamos o projeto dentro da tarefa e salvamos
        tarefa.setProjeto(projetoEncontrado);
        return tarefaRepository.save(tarefa);
    }

    // 2. Listar TODAS as tarefas de UM projeto específico
    @GetMapping("/projeto/{projetoId}")
    public List<Tarefa> listarTarefasDoProjeto(@PathVariable Long projetoId) {
        return tarefaRepository.findByProjetoId(projetoId);
    }

    // 3. Atualizar (ou concluir) uma tarefa
    @PutMapping("/{id}")
    public Tarefa atualizarTarefa(@PathVariable Long id, @Valid @RequestBody Tarefa tarefaAtualizada) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setTitulo(tarefaAtualizada.getTitulo());
            tarefa.setDescricao(tarefaAtualizada.getDescricao());
            tarefa.setConcluida(tarefaAtualizada.isConcluida());
            return tarefaRepository.save(tarefa);
        }).orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));
    }

    // 4. Deletar uma tarefa
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarTarefa(@PathVariable Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada!");
        }
        tarefaRepository.deleteById(id);
    }
}