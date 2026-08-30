package br.com.nicolas.projectflow.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "projetos")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do projeto não pode ser vazio")
    private String nome;

    private String descricao;

    private LocalDate dataInicio = LocalDate.now();

    // Construtor vazio obrigatório do Spring
    public Projeto() {}

    // --- DAQUI PARA BAIXO SÃO OS MÉTODOS QUE ESTAVAM FALTANDO! ---

    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getNome() { 
        return nome; 
    }
    public void setNome(String nome) { 
        this.nome = nome; 
    }
    
    public String getDescricao() { 
        return descricao; 
    }
    public void setDescricao(String descricao) { 
        this.descricao = descricao; 
    }
    
    public LocalDate getDataInicio() { 
        return dataInicio; 
    }
    public void setDataInicio(LocalDate dataInicio) { 
        this.dataInicio = dataInicio; 
    }
}