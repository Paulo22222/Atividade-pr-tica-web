package br.edu.vendas.controller;

import br.edu.vendas.model.Produto;
import br.edu.vendas.service.MarcaService;
import br.edu.vendas.service.CategoriaService;
import br.edu.vendas.service.ProdutoService;
import br.edu.vendas.util.UploadConfig;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@Named
@ViewScoped
public class ProdutoBean extends GenericCrud<Produto, ProdutoService> {

    @Inject
    private ProdutoService service;

    @Inject
    private MarcaService marcaService;

    @Inject
    private CategoriaService categoriaService;

    @Override
    public Produto novaEntidade() {
        return new Produto();
    }

    @Override
    public ProdutoService getService() {
        return service;
    }

    public List<br.edu.vendas.model.Marca> getMarcas() {
        return marcaService.listar();
    }

    public List<br.edu.vendas.model.Categoria> getCategorias() {
        return categoriaService.listarParaSelecao();
    }

    /**
     * Listener do <p:fileUpload> (modo avançado). Salva o arquivo em disco
     * com um nome único e grava apenas o nome do arquivo na entidade.
     */
    public void handleFileUpload(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String nomeOriginal = uploadedFile.getFileName();
            String extensao = nomeOriginal.contains(".")
                    ? nomeOriginal.substring(nomeOriginal.lastIndexOf('.'))
                    : "";
            String nomeArquivo = UUID.randomUUID() + extensao;

            Path destino = UploadConfig.getDiretorioProdutos().resolve(nomeArquivo);
            try (var input = uploadedFile.getInputStream()) {
                Files.copy(input, destino);
            }

            getEntidade().setCaminhoImagem(nomeArquivo);
            addInfo("Imagem", "Imagem enviada com sucesso.");
        } catch (IOException ex) {
            addErro("Erro ao enviar imagem.");
            System.getLogger(ProdutoBean.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
