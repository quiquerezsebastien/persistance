/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.EvaluationCriteria;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public abstract class MapperAbstractEvaluationCriteria {
    
    public abstract void insert(EvaluationCriteria pEvalCrit);

    public abstract void update(EvaluationCriteria pEvalCrit);

    public abstract void delete(EvaluationCriteria pEvalCrit);

    public abstract Set<EvaluationCriteria> find();
    
    public abstract EvaluationCriteria findById(int pId);
    
}
