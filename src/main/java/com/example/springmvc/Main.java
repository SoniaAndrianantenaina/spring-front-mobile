package com.example.springmvc;

import java.sql.Date;

public class Main {
    public static void main(String[] args) throws Exception {
        Client cl = new Client();
        cl.setIdClient(1);
        TypeProduit tp = new TypeProduit();

        Produit p = new Produit();
        tp.setId(1);
        p.setTp(tp);
        p.setDescription("blablabla");
        p.setImage("blablabla");
        p.setPrixminimum(2500000);
        p.setIdProprio(cl.getIdClient());
        p.setDuree(5);
        
         System.out.println(cl.ajoutEnchere(p));

        System.out.println(Produit.getLastId()); 
    }
}
