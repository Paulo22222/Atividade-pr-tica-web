package br.edu.vendas.service;

import br.edu.vendas.model.Produto;
import br.edu.vendas.repository.ProdutoRepository;
import br.edu.vendas.util.exception.ErroSistemaException;
import jakarta.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProdutoService implements GenericService<Produto> {

    private final ProdutoRepository repository = new ProdutoRepository();

    @Override
    public void salvar(Produto entidade) throws ErroSistemaException {
        if (entidade.getNome() == null || entidade.getNome().isBlank()) {
            throw new ErroSistemaException("O nome do produto é obrigatório.");
        }
        if (entidade.getMarca() == null || entidade.getMarca().getId() == null) {
            throw new ErroSistemaException("Selecione uma marca.");
        }
        if (entidade.getCategoria() == null || entidade.getCategoria().getId() == null) {
            throw new ErroSistemaException("Selecione uma categoria.");
        }
        if (entidade.getPreco() == null || entidade.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ErroSistemaException("O preço deve ser maior que zero.");
        }
        if (entidade.getQuantidadeEstoque() == null || entidade.getQuantidadeEstoque() < 0) {
            throw new ErroSistemaException("A quantidade em estoque não pode ser negativa.");
        }
        try {
            repository.salvar(entidade);
        } catch (RuntimeException ex) {
            throw new ErroSistemaException("Erro ao salvar produto.", ex);
        }
    }

    @Override
    public void deletar(Produto entidade) throws ErroSistemaException {
        try {
            repository.deletar(entidade.getId());
        } catch (RuntimeException ex) {
            throw new ErroSistemaException("Erro ao excluir produto.", ex);
        }
    }

    @Override
    public List<Produto> listar() {
        return repository.listar();
    }
}
