/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.Restaurant;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public abstract class MapperAbstractRestaurant {
    
    public abstract void insert(Restaurant pRest);

    public abstract void update(Restaurant pRest);

    public abstract void delete(Restaurant pRest);

    public abstract Set<Restaurant> find();
    
    public abstract Restaurant findById(int pId);
}
