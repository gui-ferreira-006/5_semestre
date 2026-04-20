package Persistencia;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Seguranca {
    public static String criptografasSenha(String senhaPura ) {
        try{
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte [] messageDigest = algorithm.digest(senhaPura.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Erro ao criptografar senha!");
            return senhaPura;
        }
    }
}
