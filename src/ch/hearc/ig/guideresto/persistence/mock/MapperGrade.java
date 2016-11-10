/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.CompleteEvaluation;
import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import ch.hearc.ig.guideresto.business.Grade;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public class MapperGrade extends MapperAbstractGrade{
    
    @Override
    public void insert (Grade pGrade){
        String requete = "INSERT INTO NOTES (numero, note, fk_comm, fk_crit) VALUES (?, ?, ?, ?)";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            
            prestmt.setInt(1, pGrade.getId());
            prestmt.setInt(2, pGrade.getGrade());
            prestmt.setInt(3, pGrade.getEvaluation().getId());
            prestmt.setInt(4, pGrade.getCriteria().getId());
            
            prestmt.executeUpdate();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
            
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void update (Grade pGrade){
        Connection cnn = new DbConnection().getConnection();
        String requete = "UPDATE NOTES set note = ?, fk_comm = ?, fk_crit = ? WHERE numero = ?";
        
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setInt(1, pGrade.getGrade());
            prestmt.setInt(2, pGrade.getEvaluation().getId());
            prestmt.setInt(3, pGrade.getCriteria().getId());
            prestmt.setInt(4, pGrade.getId());
            
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public void delete (Grade pGrade){
        Connection cnn = new DbConnection().getConnection();
        String requete = "DELETE FROM NOTES WHERE numero = ?";
        
        try {

            PreparedStatement prestmt = cnn.prepareStatement(requete);

            prestmt.setInt(1, pGrade.getId());
            
            prestmt.executeUpdate();

            prestmt.close();
            cnn.close();
        } catch (Exception e) {

            throw new RuntimeException(e);
        }
        
    }
    
    @Override
    public Set<Grade> find(){
        
        Set<Grade> grades = new HashSet<Grade>();
        Connection cnn = new DbConnection().getConnection();
        String requete = "SELECT numero, note, fk_comm, fk_crit FROM notes";
        
        try {
            
            PreparedStatement prestmt = cnn.prepareStatement(requete);

            ResultSet res = prestmt.executeQuery();
            
            while (res.next()) {
                CompleteEvaluation cEval = new MapperCompleteEvaluation().findById(res.getInt("fk_comm"));
                EvaluationCriteria evCrit = new MapperEvaluationCriteria().findById(res.getInt("fk_crit"));
                Grade grade = new Grade(res.getInt("numero"), res.getInt("note"), cEval, evCrit);
                grades.add(grade);
            }
                    
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return grades;
    }
    
}
