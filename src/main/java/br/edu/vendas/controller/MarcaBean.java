package br.edu.vendas.controller;

import br.edu.vendas.model.Marca;
import br.edu.vendas.service.MarcaService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class MarcaBean extends GenericCrud<Marca, MarcaService> {

    @Inject
    private MarcaService service;

    @Override
    public Marca novaEntidade() {
        return new Marca();
    }

    @Override
    public MarcaService getService() {
        return service;
    }
}
