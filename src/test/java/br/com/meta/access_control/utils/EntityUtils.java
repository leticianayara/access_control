package br.com.meta.access_control.utils;

import br.com.meta.dto.VisitorDTO;
import br.com.meta.models.Visitor;

import java.util.ArrayList;
import java.util.List;

public class EntityUtils {

    public static List<Visitor> criarListaAllVisitantes(){
       List<Visitor> list = new ArrayList<>();
        list.add(new Visitor("5a821d65-a145-48fc-bc72-116367e0bfab","Visitante 1","abcdef"));
        list.add(new Visitor("d447d964-58e1-41e6-92e6-71117dfd1403","Visitante 2","ghijkl"));
        list.add(new Visitor("19ff09e2-5825-4738-972c-dd5057d1e4a7","Visitante 3","mnopqr"));
        list.add(new Visitor("5a821d65-a145-48fc-bc72-116367e0bfab","Visitante 4","stuvwxyz"));
        return list;
    }

    public static Visitor criar(){
        return new Visitor("5a821d65-a145-48fc-bc72-116367e0bfab", "Visitante 1", "abcdef");
    }

    public static VisitorDTO criarDTO(){
        return new VisitorDTO("5a821d65-a145-48fc-bc72-116367e0bfab", "Visitante 1", "abcdef");
    }

    public static List<VisitorDTO> criarListaDTO(){
        return criarListaAllVisitantes().stream().map(VisitorDTO::new).toList();
    }

    public static Visitor criarNovo() {
        Visitor v = new Visitor();
        v.setName("Visitante 1");
        v.setDescription("abcdefgh");
        return v;
    }

    public static Visitor criarNovoSemName() {
        Visitor v = new Visitor();
        v.setDescription("abcdefgh");
        return v;
    }
}