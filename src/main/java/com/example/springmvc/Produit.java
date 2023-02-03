package com.example.springmvc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.springmvc.dba.DBAConnection;

public class Produit {
    int id;
    String nomProduit;
    String description;
    String image;
    int prixminimum;
    int idProprio;
    int statut;
    Date date_depot;
    int duree;
    TypeProduit tp;
    

    public Produit(int id, String nomProduit, String descrition, String image, int prixminimum, int idProprio,
            int statut, Date date_depot, int duree, TypeProduit tp) {}

    public Produit(int id, String nomProduit, String description, String image, int prixminimum, int idProprio,
            int statut) {
        this.id = id;
        this.nomProduit = nomProduit;
        this.description = description;
        this.image = image;
        this.prixminimum = prixminimum;
        this.idProprio = idProprio;
        this.statut = statut;
        this.date_depot = date_depot;
        this.duree = duree;
        this.tp = tp;
    }

    public TypeProduit getTp() {
        return this.tp;
    }

    public void setTp(TypeProduit tp) {
        this.tp = tp;
    }

    public Produit tp(TypeProduit tp) {
        setTp(tp);
        return this;
    }

    public Produit() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomProduit() {
        return this.nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrixminimum() {
        return this.prixminimum;
    }

    public void setPrixminimum(int prixminimum) {
        this.prixminimum = prixminimum;
    }

    public int getIdProprio() {
        return this.idProprio;
    }

    public void setIdProprio(int idProprio) {
        this.idProprio = idProprio;
    }

    public int getStatut() {
        return this.statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public Date getDate_depot() {
        return this.date_depot;
    }

    public void setDate_depot(Date date_depot) {
        this.date_depot = date_depot;
    }

    public int getDuree() {
        return this.duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public Produit id(int id) {
        setId(id);
        return this;
    }

    public Produit nomProduit(String nomProduit) {
        setNomProduit(nomProduit);
        return this;
    }

    public Produit description(String description) {
        setDescription(description);
        return this;
    }

    public Produit image(String image) {
        setImage(image);
        return this;
    }

    public Produit prixminimum(int prixminimum) {
        setPrixminimum(prixminimum);
        return this;
    }

    public Produit idProprio(int idProprio) {
        setIdProprio(idProprio);
        return this;
    }

    public Produit statut(int statut) {
        setStatut(statut);
        return this;
    }

    public Produit date_depot(Date date_depot) {
        setDate_depot(date_depot);
        return this;
    }

    public Produit duree(int duree) {
        setDuree(duree);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nomProduit='" + getNomProduit() + "'" +
                ", description='" + getDescription() + "'" +
                ", image='" + getImage() + "'" +
                ", prixminimum='" + getPrixminimum() + "'" +
                ", idProprio='" + getIdProprio() + "'" +
                ", statut='" + getStatut() + "'" +
                "}";
    }

    public int InsertPhotos() throws Exception{
        Connection con = null;
        int mapiditra = 0;
        PreparedStatement ps = null;
        try {
            con = DBAConnection.connect();
            ps = con.prepareStatement(
                    "insert into EncheresImages(idProduit,image) values(?,?)");
            ps.setInt(1, this.getId());
            ps.setString(2, this.getImage());
            mapiditra = ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            ps.close();
            con.close();
        }
        return mapiditra;
    }

    public static int getLastId() throws Exception{
        Connection con = null;
        int idProduit = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            con.setAutoCommit(false);
            ps = con.prepareStatement(
                    "select max(id) from produit");
           
            rs = ps.executeQuery();
            while(rs.next()){
                idProduit = rs.getInt("max");
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            ps.close();
            con.close();
        }
        return idProduit;

    }
}