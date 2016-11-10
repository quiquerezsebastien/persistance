/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.City;
import ch.hearc.ig.guideresto.business.Evaluation;
import ch.hearc.ig.guideresto.business.Localisation;
import ch.hearc.ig.guideresto.business.Restaurant;
import ch.hearc.ig.guideresto.business.RestaurantType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public class MapperRestaurant extends MapperAbstractRestaurant{
    
    @Override
    public void insert (Restaurant pRest){
        String requete = "INSERT INTO RESTAURANTS (numero, nom, adresse, description, site_web, fk_type, fk_vill) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            prestmt.setInt(1, pRest.getId());
            prestmt.setString(2, pRest.getName());
            prestmt.setString(3, pRest.getAddress().getStreet());
            prestmt.setString(4, pRest.getDescription());
            prestmt.setString(5, pRest.getWebsite());
            prestmt.setInt(6, pRest.getType().getId());
            prestmt.setInt(7, pRest.getAddress().getCity().getId());
            
            prestmt.executeUpdate();
            prestmt.close();
            
            for (Evaluation eval : pRest.getEvaluations()) {
                new MapperCompleteEvaluation().insert(eval);
            }
            cnn.close();
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void update (Restaurant pRest){
        
        String requete = "UPDATE RESTAURANTS set nom = ?, adresse = ?, descrption = ?, site_web = ?, fk_type = ?, fk_vill = ? WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setString(1, pRest.getName());
            prestmt.setString(2, pRest.getAddress().getStreet());
            prestmt.setString(3, pRest.getDescription());
            prestmt.setString(4, pRest.getWebsite());
            prestmt.setInt(5, pRest.getType().getId());
            prestmt.setInt(6, pRest.getAddress().getCity().getId());
            prestmt.setInt(7, pRest.getId());
            
            
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public void delete (Restaurant pRest){
        
        String requete = "DELETE FROM RESTAURANTS WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setInt(1, pRest.getId());
            
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
            
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public Set<Restaurant> find(){
        
        Connection cnn = new DbConnection().getConnection();
        Set<Restaurant> restaurants = new HashSet<Restaurant>();
        
        String requete = "SELECT rest.numero NumRest, nom, adresse, rest.description DescrRest, site_web, restType.numero NumType, libelle, restType.description DescrType, v.numero NumVille, code_postal, nom_ville\n" +
                        "FROM RESTAURANTS rest INNER JOIN TYPES_GASTRONOMIQUES restType ON rest.fk_type = restType.numero\n" +
                        "INNER JOIN VILLES v ON rest.fk_vill = v.numero";
        
        try {
            
            PreparedStatement prestmt = cnn.prepareStatement(requete);

            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                Restaurant rest = new Restaurant(res.getInt("NumRest"), res.getString("nom"), res.getString("DescrRest"), res.getString("site_web"),
                        new Localisation(res.getString("adresse"), new City(res.getInt("NumVille"), res.getString("code_postal"), res.getString("nom_ville"))),
                        new RestaurantType(res.getInt("NumType"), res.getString("libelle"), res.getString("DescrType")));
                restaurants.add(rest);
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return restaurants;
    }
    
    @Override
    public Restaurant findById(int pId){
        Restaurant restau = null;
        Connection cnn = new DbConnection().getConnection();
        String requete = "SELECT numero, nom, adresse, description, site_web, fk_type, fk_vill FROM RESTAURANTS WHERE numero = ?";
        
        try {
            
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            prestmt.setInt(1, pId);

            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                Localisation localisation = new Localisation(res.getString("adresse"), new MapperCity().findById(res.getInt("fk_vill")));
                RestaurantType restType = new MapperRestaurantType().findById(res.getInt("fk_type"));
                restau = new Restaurant(res.getInt("numero"), res.getString("nom"),res.getString("description"), res.getString("site_web"), localisation, restType);
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return restau;
    }
}
