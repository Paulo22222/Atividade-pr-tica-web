package br.edu.vendas.converter;

import br.edu.vendas.model.Marca;
import br.edu.vendas.repository.MarcaRepository;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = Marca.class, managed = true)
public class MarcaConverter implements Converter<Marca> {

    private final MarcaRepository repository = new MarcaRepository();

    @Override
    public Marca getAsObject(FacesContext context, UIComponent component, String value) {
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
    public String getAsString(FacesContext context, UIComponent component, Marca value) {
        if (value == null || value.getId() == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}
