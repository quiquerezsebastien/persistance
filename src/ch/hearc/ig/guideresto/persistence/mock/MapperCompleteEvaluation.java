/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.Evaluation;
import ch.hearc.ig.guideresto.business.Restaurant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public class MapperCompleteEvaluation extends MapperAbstractEvaluation{

    @Override
    public void insert(Evaluation pEval) {
        
        String requete = "INSERT INTO COMMENTAIRES (numero, date_eval, commentaire, nom_utilisateur, fk_rest) VALUES (?, ?, ?, ?, ?)";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            CompleteEvaluation cEval = (CompleteEvaluation) pEval;
            
            prestmt.setInt(1, cEval.getId());
            prestmt.setDate(2, (Date) pEval.getVisitDate());
            prestmt.setString(3, cEval.getComment());
            prestmt.setString(4, cEval.getUsername());
            prestmt.setInt(5, pEval.getRestaurant().getId());
            
            prestmt.executeUpdate();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public void update(Evaluation pEval) {
        
        String requete = "UPDATE COMMENTAIRES set date_eval = ? commentaire = ?, nom_utilisateur = ?, fk_rest = ? WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            CompleteEvaluation cEval = (CompleteEvaluation) pEval;
            
            prestmt.setDate(1, (Date) pEval.getVisitDate());
            prestmt.setString(2, cEval.getComment());
            prestmt.setString(3, cEval.getUsername());
            prestmt.setInt(4, pEval.getRestaurant().getId());
            prestmt.setInt(5, pEval.getId());
            
            prestmt.executeUpdate();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public void delete(Evaluation pEval) {
        
        String requete = "DELETE FROM COMMENTAIRES WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            CompleteEvaluation cEval = (CompleteEvaluation) pEval;
            
            prestmt.setInt(1, cEval.getId());
            
            prestmt.executeUpdate();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Set<Evaluation> find() {
        
        Set<Evaluation> evals = new HashSet<Evaluation>();
        String requete = "SELECT numero, date_eval, commentaire, nom_utilisateur, fk_rest FROM commentaires";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                Restaurant rest = new MapperRestaurant().findById(res.getInt("fk_rest"));
                CompleteEvaluation cEval = new CompleteEvaluation(res.getInt("numero"), res.getDate("date_eval"), rest, res.getString("commentaire"), res.getString("nom_utilisateur"));
                evals.add(cEval);
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
        
        return evals;
    }
    
    public CompleteEvaluation findById(int pId) {
        
        CompleteEvaluation cEval = null;
        String requete = "SELECT numero, date_eval, commentaire, nom_utilisateur, fk_rest FROM commentaires WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            prestmt.setInt(1, pId);
            
            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                Restaurant rest = new MapperRestaurant().findById(res.getInt("fk_rest"));
                cEval = new CompleteEvaluation(res.getInt("numero"), res.getDate("date_eval"), rest, res.getString("commentaire"), res.getString("nom_utilisateur"));
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
        
        return cEval;
    }
}
