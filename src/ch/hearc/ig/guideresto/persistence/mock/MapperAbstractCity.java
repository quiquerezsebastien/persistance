/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.City;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public abstract class MapperAbstractCity {
    
    public abstract void insert(City pCity);

    public abstract void update(City pCity);

    public abstract void delete(City pCity);

    public abstract Set<City> find();
    
    public abstract City findById(int pId);
}
