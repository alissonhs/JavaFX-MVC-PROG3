/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.mvc.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author lukas
 */
public class HashSHA2 {

    public static String hashSHA2(String texto) {
        String hashF = "";
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            final byte[] hash = md.digest(texto.getBytes());
            hashF = toHexFormat(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashF;
    }

    private static String toHexFormat(final byte[] bytes) {
        final StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
