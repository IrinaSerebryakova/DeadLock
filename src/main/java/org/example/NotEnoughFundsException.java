package org.example;

public class NotEnoughFundsException extends RuntimeException{
    public NotEnoughFundsException(){
        super("На вашем счёте недостаточно средств");
    }
}
