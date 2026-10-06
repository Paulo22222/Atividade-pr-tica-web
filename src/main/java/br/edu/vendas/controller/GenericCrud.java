package br.edu.vendas.controller;

import br.edu.vendas.service.GenericService;
import br.edu.vendas.util.JSFUtil;
import br.edu.vendas.util.exception.ErroSistemaException;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericCrud<E, S extends GenericService<E>> extends JSFUtil {

    private E entidade;
    private List<E> entidades = new ArrayList<>();

    private EstadoCrud estadoCrud = EstadoCrud.LISTAR;

    public enum EstadoCrud {
        CRIAR,
        ATUALIZAR,
        LISTAR
    }

    public void novo() {
        entidade = novaEntidade();
        estadoCrud = EstadoCrud.CRIAR;
    }

    public void editar(E entidade) {
        this.entidade = entidade;
        estadoCrud = EstadoCrud.ATUALIZAR;
    }

    public void salvar() {
        try {
            getService().salvar(entidade);
            addInfo("Salvo com sucesso.");
            estadoCrud = EstadoCrud.LISTAR;
            listar();
        } catch (ErroSistemaException ex) {
            addErro(ex);
        } catch (Exception ex) {
            addErro("Erro geral no sistema.");
            System.getLogger(GenericCrud.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void excluir(E entidade) {
        try {
            getService().deletar(entidade);
            addInfo("Excluído com sucesso.");
            listar();
        } catch (ErroSistemaException ex) {
            addErro(ex);
        } catch (Exception ex) {
            addErro("Erro geral no sistema.");
            System.getLogger(GenericCrud.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void listar() {
        estadoCrud = EstadoCrud.LISTAR;
        entidades = getService().listar();
        if (entidades == null || entidades.isEmpty()) {
            addAviso("Nenhum item encontrado.");
        }
    }

    public E getEntidade() {
        return entidade;
    }

    public List<E> getEntidades() {
        return entidades;
    }

    public EstadoCrud getEstadoCrud() {
        return estadoCrud;
    }

    public abstract E novaEntidade();

    public abstract S getService();
}
