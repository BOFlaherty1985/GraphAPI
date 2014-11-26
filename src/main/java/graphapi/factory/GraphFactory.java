package main.java.graphapi.factory;


import main.java.graphapi.exceptions.GraphPropertiesNullException;
import main.java.graphapi.implementation.BudgetCategoryGraph;
import main.java.graphapi.implementation.BudgetGraph;
import main.java.graphapi.implementation.BudgetPieGraph;
import main.java.graphapi.properties.GraphProperties;
import org.springframework.stereotype.Component;

/**
 * Graph Factory
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
@Component
public class GraphFactory {

    public static final int PIE_GRAPH = 1;
    public static final int LINE_GRAPH = 2;
    public static final int BAR_GRAPH = 3;

    public BudgetGraph buildGraph(GraphProperties graphProperties) throws Exception {

        validate(graphProperties);

        return determineTypeOfGraph(graphProperties);
    }

    private void validate(GraphProperties graphProperties) throws GraphPropertiesNullException {
        if(graphProperties == null) {
            throw new GraphPropertiesNullException("GraphProperties must not be null.");
        }
    }

    private BudgetGraph determineTypeOfGraph(GraphProperties graphProperties) {

        BudgetGraph graph = null;

        switch (graphProperties.getTypeOfGraph()) {
            case PIE_GRAPH:
                graph = new BudgetPieGraph();
            break;
            case LINE_GRAPH:
                graph = new BudgetCategoryGraph();
                break;
            case BAR_GRAPH:
                graph = new BudgetCategoryGraph();
        }

        return graph;
    }

}