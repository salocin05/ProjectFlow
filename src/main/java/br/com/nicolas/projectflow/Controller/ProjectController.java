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
import br.com.nicolas.projectflow.repository.ProjetoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/projetos")
public class ProjectController {

    private final ProjetoRepository repository;

    public ProjectController(ProjetoRepository repository) {
        this.repository = repository;
    }

    // Rota para LISTAR todos os projetos
    @GetMapping
    public List<Projeto> listarTodos() {
        return repository.findAll();
    }

    // Rota para CRIAR um novo projeto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Projeto criarProjeto(@Valid @RequestBody Projeto projeto) {
        return repository.save(projeto);
    }
    // Rota para BUSCAR um projeto específico pelo ID
    @GetMapping("/{id}")
    public Projeto buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));
    }

    // Rota para ATUALIZAR um projeto existente
    @PutMapping("/{id}")
    public Projeto atualizarProjeto(@PathVariable Long id, @Valid @RequestBody Projeto projetoAtualizado) {
        return repository.findById(id).map(projeto -> {
            projeto.setNome(projetoAtualizado.getNome());
            projeto.setDescricao(projetoAtualizado.getDescricao());
            // A dataInicio nós não mexemos, para manter a data de criação original!
            return repository.save(projeto);
        }).orElseThrow(() -> new RuntimeException("Projeto não encontrado!"));
    }

    // Rota para DELETAR um projeto
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProjeto(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Projeto não encontrado!");
        }
        repository.deleteById(id);
    }
}