package org.vktask.vkrestapitask.exception;

public class TokenNotFoundException extends Exception {

    public TokenNotFoundException() {
        super("Token not found in Headers, please provide your validation token in the headlines ");
    }

}
