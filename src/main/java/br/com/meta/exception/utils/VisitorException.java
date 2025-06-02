package br.com.meta.exception.utils;

public class VisitorException extends RuntimeException{

    public VisitorException(String id){
        super("Visitor not found with id "+id);
    }
}
