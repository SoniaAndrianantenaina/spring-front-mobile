package com.example.springmvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.springmvc.dba.DBAConnection;

public class TypeProduit {
    int id;
    String nomTypep;
    int idcategories;

    public TypeProduit() {
    }

    public TypeProduit(int id, String nomTypep, int idcategories) {
        this.id = id;
        this.nomTypep = nomTypep;
        this.idcategories = idcategories;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomTypep() {
        return this.nomTypep;
    }

    public void setNomTypep(String nomTypep) {
        this.nomTypep = nomTypep;
    }

    public int getIdcategories() {
        return this.idcategories;
    }

    public void setIdcategories(int idcategories) {
        this.idcategories = idcategories;
    }

    public TypeProduit id(int id) {
        setId(id);
        return this;
    }

    public TypeProduit nomTypep(String nomTypep) {
        setNomTypep(nomTypep);
        return this;
    }

    public TypeProduit idcategories(int idcategories) {
        setIdcategories(idcategories);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nomTypep='" + getNomTypep() + "'" +
                ", idcategories='" + getIdcategories() + "'" +
                "}";
    }

    public ArrayList<TypeProduit> ListTypeProduit() throws Exception {
        ArrayList<TypeProduit> data = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            String sql = "select * from typeproduit ";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TypeProduit tp = new TypeProduit();
                tp.setId(rs.getInt("id"));
                tp.setNomTypep(rs.getString("nomtypep"));
                tp.setIdcategories(rs.getInt("idcategories"));
                data.add(tp);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            ps.close();
            con.close();
        }
        return data;
    }

}
