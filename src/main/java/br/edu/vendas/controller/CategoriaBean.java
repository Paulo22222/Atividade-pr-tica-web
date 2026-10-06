package br.edu.vendas.controller;

import br.edu.vendas.model.Categoria;
import br.edu.vendas.service.CategoriaService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ViewScoped
public class CategoriaBean extends GenericCrud<Categoria, CategoriaService> {

    @Inject
    private CategoriaService service;

    @Override
    public Categoria novaEntidade() {
        return new Categoria();
    }

    @Override
    public CategoriaService getService() {
        return service;
    }

    /**
     * Lista usada no <p:selectOneMenu> de categoria pai e também no
     * select hierárquico de Produto - já vem com indentação pronta.
     */
    public List<Categoria> getCategoriasParaSelecao() {
        return service.listarParaSelecao();
    }

    /**
     * Quantidade de "--" a exibir antes do nome, de acordo com a profundidade
     * da categoria na árvore (usado no .xhtml para montar o rótulo indentado).
     */
    public String getRotuloIndentado(Categoria categoria) {
        StringBuilder sb = new StringBuilder();
        Categoria pai = categoria.getCategoriaPai();
        while (pai != null) {
            sb.append("-- ");
            pai = pai.getCategoriaPai();
        }
        sb.append(categoria.getNome());
        return sb.toString();
    }
}
