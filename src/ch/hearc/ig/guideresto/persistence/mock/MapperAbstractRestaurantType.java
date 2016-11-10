/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.RestaurantType;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public abstract class MapperAbstractRestaurantType {
    
    public abstract void insert(RestaurantType pRestType);

    public abstract void update(RestaurantType pRestType);

    public abstract void delete(RestaurantType pRestType);

    public abstract Set<RestaurantType> find();
    
    public abstract RestaurantType findById(int pId);
    
}
