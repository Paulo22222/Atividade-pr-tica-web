package br.edu.vendas.service;

import br.edu.vendas.model.Categoria;
import br.edu.vendas.repository.CategoriaRepository;
import br.edu.vendas.util.exception.ErroSistemaException;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CategoriaService implements GenericService<Categoria> {

    private final CategoriaRepository repository = new CategoriaRepository();

    @Override
    public void salvar(Categoria entidade) throws ErroSistemaException {
        if (entidade.getNome() == null || entidade.getNome().isBlank()) {
            throw new ErroSistemaException("O nome da categoria é obrigatório.");
        }
        if (formariaCiclo(entidade)) {
            throw new ErroSistemaException(
                    "Seleção de categoria pai inválida: isso criaria um ciclo na árvore de categorias.");
        }
        try {
            repository.salvar(entidade);
        } catch (RuntimeException ex) {
            throw new ErroSistemaException("Erro ao salvar categoria.", ex);
        }
    }

    @Override
    public void deletar(Categoria entidade) throws ErroSistemaException {
        if (entidade.getSubcategorias() != null && !entidade.getSubcategorias().isEmpty()) {
            throw new ErroSistemaException(
                    "Não é possível excluir: esta categoria possui subcategorias. Exclua-as primeiro.");
        }
        long produtosVinculados = repository.contarProdutosVinculados(entidade.getId());
        if (produtosVinculados > 0) {
            throw new ErroSistemaException(
                    "Não é possível excluir: existem " + produtosVinculados + " produto(s) vinculado(s) a esta categoria.");
        }
        try {
            repository.deletar(entidade.getId());
        } catch (RuntimeException ex) {
            throw new ErroSistemaException("Erro ao excluir categoria.", ex);
        }
    }

    @Override
    public List<Categoria> listar() {
        return repository.listar();
    }

    /**
     * Retorna a árvore pronta para exibir num selectOneMenu, com indentação
     * ("-- ") de acordo com a profundidade. Só inclui raízes e desce
     * recursivamente pelos filhos.
     */
    public List<Categoria> listarParaSelecao() {
        List<Categoria> todas = repository.listarOrdenadoPorNome();
        List<Categoria> raizes = todas.stream()
                .filter(c -> c.getCategoriaPai() == null)
                .toList();
        List<Categoria> resultado = new java.util.ArrayList<>();
        for (Categoria raiz : raizes) {
            adicionarComFilhos(raiz, resultado);
        }
        return resultado;
    }

    private void adicionarComFilhos(Categoria categoria, List<Categoria> resultado) {
        resultado.add(categoria);
        if (categoria.getSubcategorias() != null) {
            for (Categoria filha : categoria.getSubcategorias()) {
                adicionarComFilhos(filha, resultado);
            }
        }
    }

    /**
     * Impede que uma categoria seja definida como pai dela mesma ou de um
     * de seus próprios descendentes (o que criaria um ciclo).
     */
    private boolean formariaCiclo(Categoria entidade) {
        if (entidade.getId() == null || entidade.getCategoriaPai() == null) {
            return false;
        }
        Categoria pai = entidade.getCategoriaPai();
        while (pai != null) {
            if (pai.getId() != null && pai.getId().equals(entidade.getId())) {
                return true;
            }
            pai = pai.getCategoriaPai();
        }
        return false;
    }
}
