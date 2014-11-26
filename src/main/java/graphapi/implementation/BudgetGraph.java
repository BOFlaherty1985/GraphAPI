package main.java.graphapi.implementation;

import main.java.graphapi.properties.GraphProperties;
import org.jfree.data.general.AbstractDataset;

/**
 * Budget Graph
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
public interface BudgetGraph {

    // TODO - abstract constructor?
    void initialise(GraphProperties graphProperties);

    // TODO - candidate to be moved into an abstract class?
    void isGraphLegendRequired(GraphProperties graphProperties);

    <T extends AbstractDataset> void setDataset(T dataset);

    <T> String createGraph(T budgetGraph) throws Exception;

}
