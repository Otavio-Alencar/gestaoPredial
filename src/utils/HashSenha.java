package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSenha {
    public static String gerarHash(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b)); // converte para hexadecimal
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo SHA-256 não disponível", e);
        }
    }
}
