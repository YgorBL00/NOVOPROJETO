package com.example.fluxo_de_cliente.util;

import java.security.MessageDigest;

public class HashUtil {

    // Agora o método se chama gerarHash
    public static String gerarHash(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(texto.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash", e);
        }
    }

    // Verifica se a senha digitada confere com o hash armazenado
    public static boolean verificarHash(String senhaDigitada, String hashArmazenado) {
        String hashSenha = gerarHash(senhaDigitada); // usa gerarHash
        return hashSenha != null && hashSenha.equals(hashArmazenado);
    }
}
