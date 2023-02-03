package com.example.springmvc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;

import com.example.springmvc.dba.DBAConnection;

public class Client {
    Integer idClient;
    String nom;
    String prenom;
    String mdp;
    String email;
    float budget;
    float offre;
    int demande;
    Produit p;

    public Client() {
    }

    public Client(Integer idClient, String nom, String prenom, String mdp, String email, float budget, float offre,
            int demande) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.email = email;
        this.budget = budget;
        this.offre = offre;
        this.demande = demande;
    }

    public Produit getP() {
        return this.p;
    }

    public void setP(Produit p) {
        this.p = p;
    }

    public Integer getIdClient() {
        return this.idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMdp() {
        return this.mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getBudget() {
        return this.budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getOffre() {
        return this.offre;
    }

    public void setOffre(float offre) {
        this.offre = offre;
    }

    public int getDemande() {
        return this.demande;
    }

    public void setDemande(int demande) {
        this.demande = demande;
    }

    @Override
    public String toString() {
        return "{ p='" + getP() + "'" +
                "}";
    }

    public int Login() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idClient = 0;
        try {
            con = DBAConnection.connect();
            ps = con.prepareStatement("select id from Clients where emailclients = ? and mdpclients = ?");

            ps.setString(1, this.getEmail());
            ps.setString(2, this.getMdp());
            rs = ps.executeQuery();

            while (rs.next()) {
                idClient = rs.getInt("id");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            ps.close();
            con.close();
        }
        return idClient;

    }

    public int Inscrisption() throws Exception {
        Connection con = null;
        int mapiditra = 0;
        PreparedStatement ps = null;
        try {
            con = DBAConnection.connect();
            ps = con.prepareStatement(
                    "insert into Clients(nomclients,prenomclients,mdpclients,emailclients, budget) values(?,?,?,?,?)");
            ps.setString(1, this.getNom());
            ps.setString(2, this.getPrenom());
            ps.setString(3, this.getMdp());
            ps.setString(4, this.getEmail());
            ps.setFloat(5, this.getBudget());

            mapiditra = ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            ps.close();
            con.close();
        }
        return mapiditra;
    }

    public ArrayList<Produit> MesEncheres() throws Exception {
        ArrayList<Produit> data = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            String sql = "select * from v_produits where idproprietaire = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, this.getIdClient());

            rs = ps.executeQuery();
            while (rs.next()) {
                Client c = new Client();
                Produit p = new Produit();
                p.setId(rs.getInt("id"));
                p.setNomProduit(rs.getString("produit"));
                p.setDescription(rs.getString("descriptions"));
                p.setImage(rs.getString("image"));
                p.setPrixminimum(rs.getInt("prixminimum"));
                p.setIdProprio(rs.getInt("idproprietaire"));          
                p.setStatut(rs.getInt("statut"));
                p.setDate_depot(rs.getDate("date_depot"));
                c.setP(p);
                c.setNom("proprietaire");

                data.add(p);
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

    public int DemandeRecharge(int demande) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int demandeClient = 0;
        int manova = 0;
        try {

            con = DBAConnection.connect();
            ps = con.prepareStatement("SELECT demande from clients where id = ?");
            ps.setInt(1, this.getIdClient());
            rs = ps.executeQuery();

            while (rs.next()) {
                demandeClient = rs.getInt("demande");

                if (demandeClient == 0) {
                    ps = con.prepareStatement("update clients set demande = ? where id = ?");
                    ps.setFloat(1, demande);
                    ps.setInt(2, this.getIdClient());
                    manova = ps.executeUpdate();
                }
            }

        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
            con.close();
        }
        return manova;
    }

    public Integer ajoutEnchere(Produit produit) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = " ";
        Integer result = 0;
        try {
            con = DBAConnection.connect();
            sql = "INSERT INTO Produit(idTypeProduit,descriptions,image,prixminimum,idproprietaire,duree)values(?,?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, produit.getTp().getId());
            ps.setString(2, produit.getDescription());
            ps.setString(3, produit.getImage());
            ps.setInt(4, produit.getPrixminimum());
            ps.setInt(5, this.getIdClient());
            ps.setInt(6, produit.getDuree());
            result = ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return result;
    }

    public Object getSubstract(int idProduit)throws Exception{
        Object substract = null;
        String sql = "select (date_depot::date + (duree * interval '1 hour')) :: timestamp - (date_trunc('hour',now())) :: timestamp as substract from v_produits where idproprietaire = "+
        this.idClient+" and id="+idProduit;
        Connection con = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            state = con.createStatement();
            rs = state.executeQuery(sql);
            while(rs.next())
               substract = rs.getObject("substract");
        } catch (Exception e) {
            throw e;
        }finally{
            rs.close();
            state.close();
            con.close();
        }
        return substract;
    }
}
