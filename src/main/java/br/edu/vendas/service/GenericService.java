package br.edu.vendas.service;

import br.edu.vendas.util.exception.ErroSistemaException;
import java.io.Serializable;
import java.util.List;

public interface GenericService<E> extends Serializable {

    void salvar(E entidade) throws ErroSistemaException;

    void deletar(E entidade) throws ErroSistemaException;

    List<E> listar();
}
