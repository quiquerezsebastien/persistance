/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.BasicEvaluation;
import ch.hearc.ig.guideresto.business.Evaluation;
import ch.hearc.ig.guideresto.business.Restaurant;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public class MapperBasicEvaluation extends MapperAbstractEvaluation {

    @Override
    public void insert(Evaluation pEval) {

        String requete = "INSERT INTO LIKES (numero, appreciation, date_eval, adresse_ip, fk_rest) VALUES (?, ?, ?, ?, ?)";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            BasicEvaluation bEval = (BasicEvaluation) pEval;

            prestmt.setInt(1, bEval.getId());
            prestmt.setBoolean(2, bEval.getLikeRestaurant());
            prestmt.setDate(3, (Date) pEval.getVisitDate());
            prestmt.setString(4, bEval.getIpAddress());
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

        String requete = "UPDATE LIKES set appreciation = ?, date_eval = ?, adresse_ip = ?, fk_rest = ? WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            BasicEvaluation bEval = (BasicEvaluation) pEval;

            prestmt.setBoolean(1, bEval.getLikeRestaurant());
            prestmt.setDate(2, (Date) pEval.getVisitDate());
            prestmt.setString(3, bEval.getIpAddress());
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

        String requete = "DELETE FROM LIKES WHERE numero = ?";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);
            BasicEvaluation bEval = (BasicEvaluation) pEval;

            prestmt.setInt(1, bEval.getId());

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
        String requete = "SELECT numero, appreciation, date_eval, adresse_ip, fk_rest FROM likes";
        Connection cnn = new DbConnection().getConnection();
        try {
            PreparedStatement prestmt = cnn.prepareStatement(requete);

            ResultSet res = prestmt.executeQuery();

            while (res.next()) {
                Restaurant rest = new MapperRestaurant().findById(res.getInt("fk_rest"));
                BasicEvaluation bEval = new BasicEvaluation(res.getInt("numero"), res.getDate("date_eval"), rest, res.getBoolean("appreciation"), res.getString("adresse_ip"));
                evals.add(bEval);
            }
            res.close();
            prestmt.close();
            cnn.close();
        } catch (Exception e) {
                
            throw new RuntimeException(e);
        }

        return evals;
    }
}
