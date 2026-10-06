package br.edu.vendas.servlet;

import br.edu.vendas.util.UploadConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Serve as imagens de produto que foram salvas fora do diretório do WAR
 * (veja UploadConfig). Mapeado em /imagens/produtos/{nomeArquivo}.
 */
@WebServlet(name = "ImagemProdutoServlet", urlPatterns = {"/imagens/produtos/*"})
public class ImagemProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomeArquivo = request.getPathInfo();
        if (nomeArquivo == null || nomeArquivo.isBlank() || nomeArquivo.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        nomeArquivo = nomeArquivo.substring(1); // remove a barra inicial

        Path arquivo = UploadConfig.getDiretorioProdutos().resolve(nomeArquivo).normalize();
        if (!arquivo.startsWith(UploadConfig.getDiretorioProdutos()) || !Files.exists(arquivo)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = Files.probeContentType(arquivo);
        response.setContentType(contentType != null ? contentType : "application/octet-stream");
        response.setContentLengthLong(Files.size(arquivo));
        Files.copy(arquivo, response.getOutputStream());
    }
}
