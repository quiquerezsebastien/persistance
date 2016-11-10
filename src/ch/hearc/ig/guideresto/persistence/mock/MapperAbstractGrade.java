/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.Grade;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public abstract class MapperAbstractGrade {
    
    public abstract void insert(Grade pGrade);

    public abstract void update(Grade pGrade);

    public abstract void delete(Grade pGrade);

    public abstract Set<Grade> find();
    
}
