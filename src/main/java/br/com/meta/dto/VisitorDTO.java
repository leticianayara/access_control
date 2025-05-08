package br.com.meta.dto;

import br.com.meta.models.Visitor;

import java.io.Serial;
import java.io.Serializable;

public class VisitorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;

    public VisitorDTO() {
    }

    public VisitorDTO(Visitor visitor){
        id = visitor.getId();
        name = visitor.getName();
        description = visitor.getDescription();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
