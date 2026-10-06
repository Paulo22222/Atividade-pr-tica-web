package br.edu.vendas.service;

import br.edu.vendas.model.Marca;
import br.edu.vendas.repository.MarcaRepository;
import br.edu.vendas.util.exception.ErroSistemaException;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MarcaService implements GenericService<Marca> {

    private final MarcaRepository repository = new MarcaRepository();

    @Override
    public void salvar(Marca entidade) throws ErroSistemaException {
        if (entidade.getNome() == null || entidade.getNome().isBlank()) {
            throw new ErroSistemaException("O nome da marca é obrigatório.");
        }
        if (repository.existeComMesmoNome(entidade.getNome(), entidade.getId())) {
            throw new ErroSistemaException("Já existe uma marca cadastrada com este nome.");
        }
        try {
            repository.salvar(entidade);
        } catch (RuntimeException ex) {
            throw new ErroSistemaException("Erro ao salvar marca.", ex);
        }
    }

    @Override
    public void deletar(Marca entidade) throws ErroSistemaException {
        try {
            repository.deletar(entidade.getId());
        } catch (RuntimeException ex) {
            // Provavelmente há produtos vinculados a esta marca (FK)
            throw new ErroSistemaException(
                    "Não é possível excluir esta marca: existem produtos vinculados a ela.", ex);
        }
    }

    @Override
    public List<Marca> listar() {
        return repository.listar();
    }
}
