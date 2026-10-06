package br.edu.vendas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_marca")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Marca implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "O nome da marca é obrigatório")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    @Column(name = "nome", nullable = false, unique = true, length = 150)
    private String nome;

    @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = Boolean.TRUE;
}
