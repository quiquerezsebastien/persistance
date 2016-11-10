/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.City;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public class MapperCity extends MapperAbstractCity{
    
    @Override
    public void insert (City pCity){
        String requete = "INSERT INTO VILLES (numero, code_postal, nom_ville) VALUES (?, ?, ?)";
        
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            prestmt.setInt(1, pCity.getId());
            prestmt.setString(2, pCity.getZipCode());
            prestmt.setString(3, pCity.getCityName());
            
            prestmt.executeUpdate();
            prestmt.close();
            cnn.close();
            
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void update (City pCity){
        
        String requete = "UPDATE VILLES set code_postal = ?, nom_ville = ? WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setString(1, pCity.getZipCode());
            prestmt.setString(2, pCity.getCityName());
            prestmt.setInt(3, pCity.getId());
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public void delete (City pCity){
        
        String requete = "DELETE FROM VILLES WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setInt(1, pCity.getId());
            
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public Set<City> find(){
        
        Set<City> cities = new HashSet<City>();
        
        String requete = "SELECT numero, code_postal, nom_ville FROM VILLES";
        Connection cnn = new DbConnection().getConnection();
        try {
            
            PreparedStatement prestmt = cnn.prepareStatement(requete);

            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                City city = new City(res.getInt("numero"), res.getString("code_postal"), res.getString("nom_ville"));
                cities.add(city);
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return cities;
    }
    
    @Override
    public City findById(int pId){
        City city = null;
        
        String requete = "SELECT numero, code_postal, nom_ville FROM VILLES WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {
            
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            prestmt.setInt(1, pId);

            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                city = new City(res.getInt("numero"), res.getString("code_postal"), res.getString("nom_ville"));
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return city;
    }
    
}
