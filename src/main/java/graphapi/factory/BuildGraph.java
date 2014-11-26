package main.java.graphapi.factory;

import main.java.graphapi.dataset.BudgetCategoryDataset;
import main.java.graphapi.dataset.BudgetPieDataset;
import main.java.graphapi.exceptions.BudgetGraphNullException;
import main.java.graphapi.exceptions.GraphDatasetNullException;
import main.java.graphapi.exceptions.GraphPropertiesNullException;
import main.java.graphapi.implementation.BudgetGraph;
import main.java.graphapi.properties.GraphProperties;
import org.jfree.data.general.AbstractDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Build Graph
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
@Component
public class BuildGraph {

    public static final char PIE_GRAPH = 1;
    public static final char LINE_GRAPH = 2;
    public static final char BAR_GRAPH = 3;

    @Autowired
    private GraphFactory graphFactory;

    @Autowired
    private BudgetPieDataset pieDataset;

    @Autowired
    private BudgetCategoryDataset categoryDataset;

    /**
     * build an implementation of BudgetGraph
     *
     * @param graphProperties
     * @return
     * @throws Exception
     */
    public GraphProperties generateGraph(GraphProperties graphProperties) throws Exception {

        boolean result;

        // validate
        validate(graphProperties);

        // 1. use GraphFactory to build initial Graph object
        BudgetGraph budgetGraph = graphFactory.buildGraph(graphProperties);

        if(budgetGraph != null) {

            // 2. set initial values on the object
            budgetGraph.initialise(graphProperties);
            // 3. Based on the value of graphProperties.getTypeOfGraph decide which DataSource class to use
            // 4. verify that the call to the DataSource.buildDataSource has been made
            budgetGraph.setDataset(determineDataset(graphProperties));
            // 5. graphLegendRequired() has been called
            budgetGraph.isGraphLegendRequired(graphProperties);
            //  6. verify that CreateGraph has been called
            graphProperties.setGraphURL(budgetGraph.createGraph(budgetGraph));

        } else {
            throw new BudgetGraphNullException("BudgetGraph is null.");
        }

        // TODO - return GraphProperties object (with URL) instead of a boolean flag.
        return graphProperties;
    }

    private void validate(GraphProperties graphProperties) throws GraphPropertiesNullException, GraphDatasetNullException {
        if(graphProperties == null) {
            throw new GraphPropertiesNullException("GraphProperties is null.");
        }

        if(pieDataset == null || categoryDataset == null) {
            throw new GraphDatasetNullException("Graph Dataset must not be null.");
        }
    }

    private AbstractDataset determineDataset(GraphProperties graphProperties) throws Exception {
        return (graphProperties.getTypeOfGraph() == PIE_GRAPH) ? pieDataset.buildDataset(graphProperties.getGraphDataList()) :
                ((graphProperties.getTypeOfGraph() == LINE_GRAPH || graphProperties.getTypeOfGraph() == BAR_GRAPH) ?
                        categoryDataset.buildDataset(graphProperties.getGraphDataList()) : null);
    }


    public void setGraphFactory(GraphFactory graphFactory) {
        this.graphFactory = graphFactory;
    }

    public void setPieDataset(BudgetPieDataset pieDataset) {
        this.pieDataset = pieDataset;
    }

    public void setCategoryDataset(BudgetCategoryDataset categoryDataset) {
        this.categoryDataset = categoryDataset;
    }

}