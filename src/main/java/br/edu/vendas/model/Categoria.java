package br.edu.vendas.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_categoria")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres")
    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoriaPai; // nulo para categorias raiz

    @OneToMany(mappedBy = "categoriaPai", cascade = CascadeType.ALL)
    private List<Categoria> subcategorias = new ArrayList<>();

    /**
     * Monta o caminho hierárquico completo, ex: "Eletrônicos > Celulares > Smartphones Android"
     */
    public String getCaminhoHierarquico() {
        if (categoriaPai == null) {
            return nome;
        }
        return categoriaPai.getCaminhoHierarquico() + " > " + nome;
    }
}
