/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

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
public class MapperRestaurantType {
    
    public void insert (RestaurantType pRestType){
        String requete = "INSERT INTO TYPES_GASTRONOMIQUES (numero, libelle, description) VALUES (?, ?, ?)";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            prestmt.setLong(1, pRestType.getId());
            prestmt.setString(2, pRestType.getLabel());
            prestmt.setString(3, pRestType.getDescription());
            
            prestmt.executeUpdate();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
    }
    
    public void update (RestaurantType pRestType){
        
        String requete = "UPDATE TYPES_GASTRONOMIQUES set libelle = ?, description = ? WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setString(1, pRestType.getLabel());
            prestmt.setString(2, pRestType.getDescription());
            prestmt.setInt(3, pRestType.getId());
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    public void delete (RestaurantType pRestType){
        
        String requete = "DELETE FROM TYPES_GASTRONOMIQUES WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setLong(1, pRestType.getId());
            
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
            
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    public Set<RestaurantType> find(){
        
        Set<RestaurantType> restaurantTypes = new HashSet<RestaurantType>();
        
        String requete = "SELECT numero, libelle, description FROM TYPES_GASTRONOMIQUES";
        Connection cnn = new DbConnection().getConnection();
        try {
            
            PreparedStatement prestmt = cnn.prepareStatement(requete);

            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                RestaurantType restType = new RestaurantType(res.getInt("numero"), res.getString("libelle"), res.getString("description"));
                restaurantTypes.add(restType);
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return restaurantTypes;
    }
    
    public RestaurantType findById(int pId){
        RestaurantType restType = null;
        
        String requete = "SELECT numero, libelle, description FROM TYPES_GASTRONOMIQUES WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {
            
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            prestmt.setInt(1, pId);

            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                restType = new RestaurantType(res.getInt("numero"), res.getString("libelle"), res.getString("description"));
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return restType;
    }
}
