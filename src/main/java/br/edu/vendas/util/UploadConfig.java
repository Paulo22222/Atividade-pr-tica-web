package br.edu.vendas.util;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Centraliza onde as imagens de produto são salvas em disco. Fica fora da
 * pasta do WAR de propósito, para que o arquivo sobreviva a um novo deploy.
 */
public class UploadConfig {

    private UploadConfig() {
    }

    public static Path getDiretorioProdutos() {
        Path dir = Paths.get(System.getProperty("user.home"), "uploads-vendas", "produtos");
        dir.toFile().mkdirs();
        return dir;
    }
}
