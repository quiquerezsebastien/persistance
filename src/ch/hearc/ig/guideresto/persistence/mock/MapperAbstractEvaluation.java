/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.ig.guideresto.persistence.mock;

import ch.hearc.ig.guideresto.business.Evaluation;
import java.util.Set;

/**
 *
 * @author sebastie.quiquere
 */
public abstract class MapperAbstractEvaluation {

    public abstract void insert(Evaluation pEval);

    public abstract void update(Evaluation pEval);

    public abstract void delete(Evaluation pEval);

    public abstract Set<Evaluation> find();

}
