package br.edu.vendas.converter;

import br.edu.vendas.model.Categoria;
import br.edu.vendas.repository.CategoriaRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = Categoria.class, managed = true)
public class CategoriaConverter implements Converter<Categoria> {

    private final CategoriaRepository repository = new CategoriaRepository();

    @Override
    public Categoria getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return repository.buscarPorId(Long.valueOf(value));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Categoria value) {
        if (value == null || value.getId() == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}
