/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public class MapperEvaluationCriteria extends MapperAbstractEvaluationCriteria{
    
    @Override
    public void insert (EvaluationCriteria eval){
        String requete = "INSERT INTO CRITERES_EVALUATION (numero, nom, description) VALUES (?, ?, ?)";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            prestmt.setLong(1, eval.getId());
            prestmt.setString(2, eval.getName());
            prestmt.setString(3, eval.getDescription());
            
            prestmt.executeUpdate();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void update (EvaluationCriteria eval){
        
        String requete = "UPDATE CRITERES_EVALUATION set nom = ?, description = ? WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setString(1, eval.getName());
            prestmt.setString(2, eval.getDescription());
            prestmt.setInt(3, eval.getId());
            
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public void delete (EvaluationCriteria eval){
        
        String requete = "DELETE FROM CRITERES_EVALUATION WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setInt(1, eval.getId());
            
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public Set<EvaluationCriteria> find(){
        
        Set<EvaluationCriteria> evaCrits = new HashSet<EvaluationCriteria>();
        Connection cnn = new DbConnection().getConnection();
        String requete = "SELECT numero, nom, description FROM CRITERES_EVALUATION";
        
        try {
            
            PreparedStatement prestmt = cnn.prepareStatement(requete);

            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                EvaluationCriteria evaCrit = new EvaluationCriteria(res.getInt("numero"), res.getString("nom"), res.getString("description"));
                evaCrits.add(evaCrit);
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return evaCrits;
    }
    
    @Override
    public EvaluationCriteria findById(int pId){
        EvaluationCriteria evalCriter = null;
        
        String requete = "SELECT numero, nom, description FROM CRITERES_EVALUATION WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {
            
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            prestmt.setInt(1, pId);

            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                evalCriter = new EvaluationCriteria(res.getInt("numero"), res.getString("nom"), res.getString("description"));
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return evalCriter;
    }
}
