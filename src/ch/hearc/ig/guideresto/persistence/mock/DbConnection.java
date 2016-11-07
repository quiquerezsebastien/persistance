/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.sql.PooledConnection;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import oracle.jdbc.pool.OracleDataSource;


/**
 *
 * @author sebastie.quiquere
 */
public class DbConnection {
    
    public java.sql.Connection getConnection(){
        

        try {
            
            DataSource ds = new OracleConnectionPoolDataSource();
            
            ((OracleDataSource)ds).setDriverType("thin");
            ((OracleDataSource)ds).setServerName("db.ig.he-arc.ch");
            ((OracleDataSource)ds).setDatabaseName("ens2");
            ((OracleDataSource)ds).setPortNumber(1521);
            ((OracleDataSource)ds).setUser("sebastie_quiquere");
            ((OracleDataSource)ds).setPassword("sebastie_quiquere");
            
            
            //création pool de connexions
            PooledConnection poolConn = ((OracleConnectionPoolDataSource)ds).getPooledConnection();            
            
            // récupération de la connexion
            Connection cnn = poolConn.getConnection();
            cnn.setAutoCommit(false);
            
            return cnn;
            

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
