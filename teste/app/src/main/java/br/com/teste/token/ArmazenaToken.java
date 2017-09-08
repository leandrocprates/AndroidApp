package br.com.teste.token;

/**
 * Created by lprates on 07/09/2017.
 */

public class ArmazenaToken {

    public static String tokenArmazenado;

    public static String getTokenArmazenado() {
        return tokenArmazenado;
    }

    public static void setTokenArmazenado(String tokenArmazenado) {
        ArmazenaToken.tokenArmazenado = tokenArmazenado;
    }
}
