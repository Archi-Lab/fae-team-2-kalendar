package de.th.koeln.fae.microservice_kalendar.kalender.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    @Getter
    @Setter
    private String name;

    public Name(){

    }

    public Name(String name){

        String expression = "^[a-zA-Z\\s]+";
        if(!name.matches(expression)){
            throw new IllegalArgumentException("Ein Kalendername darf nur aus Groß- bzw. Kleinbuchstaben bestehen.");
        }
        this.name = name;

    }


}
