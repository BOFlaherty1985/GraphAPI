package test.graphapi.factory;

import main.java.graphapi.dataset.BudgetCategoryDataset;
import main.java.graphapi.dataset.BudgetPieDataset;
import main.java.graphapi.exceptions.BudgetGraphIsInvalidException;
import main.java.graphapi.exceptions.GraphDatasetNullException;
import main.java.graphapi.exceptions.GraphPropertiesNullException;
import main.java.graphapi.factory.BuildGraph;
import main.java.graphapi.factory.GraphFactory;
import main.java.graphapi.implementation.BudgetCategoryGraph;
import main.java.graphapi.implementation.BudgetPieGraph;
import main.java.graphapi.model.GraphData;
import main.java.graphapi.properties.GraphProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * Build Graph Test
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
public class BuildGraphTest {

    private BuildGraph buildGraph = new BuildGraph();
    private GraphFactory graphFactory;
    private GraphProperties graphProperties;
    private BudgetPieGraph budgetPieGraph;
    private BudgetPieDataset pieDataset;
    private BudgetCategoryGraph budgetCategoryGraph;
    private BudgetCategoryDataset categoryDataset;

    @Before
    public void setUp() {
        graphFactory = Mockito.mock(GraphFactory.class);
        graphProperties = Mockito.mock(GraphProperties.class);
        budgetPieGraph = Mockito.mock(BudgetPieGraph.class);
        budgetCategoryGraph = Mockito.mock(BudgetCategoryGraph.class);
        pieDataset = Mockito.mock(BudgetPieDataset.class);
        categoryDataset = Mockito.mock(BudgetCategoryDataset.class);

        buildGraph.setGraphFactory(graphFactory);
        buildGraph.setPieDataset(pieDataset);
        buildGraph.setCategoryDataset(categoryDataset);
    }

    @After
    public void tearDown() {
        graphFactory = null;
        graphProperties = null;
        budgetPieGraph = null;
        budgetCategoryGraph = null;
    }

    @Test(expected = GraphPropertiesNullException.class)
    public void throwExceptionWhenGraphPropertiesIsNull() throws Exception {

       buildGraph.generateGraph(null);

    }

    @Test
    public void verifyGraphFactoryIsCalled() throws Exception {

        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetPieGraph);

        buildGraph.generateGraph(graphProperties);
        verify(graphFactory, times(1)).buildGraph(graphProperties);

    }

    @Test
    public void verifyPieGraphObjectInitialValuesSetupMethodIsCalled() throws Exception {

        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetPieGraph);

        buildGraph.generateGraph(graphProperties);
        verify(budgetPieGraph, times(1)).initialise(graphProperties);

    }

    @Test(expected = GraphDatasetNullException.class)
    public void throwExceptionIfPieDatasetIsNull() throws Exception {

        buildGraph.setPieDataset(null);
        buildGraph.setCategoryDataset(categoryDataset);
        buildGraph.generateGraph(graphProperties);

    }

    @Test(expected = GraphDatasetNullException.class)
    public void throwExceptionIfCategoryDatasetIsNull() throws Exception {

        buildGraph.setPieDataset(pieDataset);
        buildGraph.setCategoryDataset(null);
        buildGraph.generateGraph(graphProperties);

    }

    @Test
    public void verifyPieDatasetNullExceptionMessageIsValid() throws Exception {

        buildGraph.setPieDataset(null);
        buildGraph.setCategoryDataset(categoryDataset);

        try {
            buildGraph.generateGraph(graphProperties);
            fail("GraphDataSet is null");
        } catch (Exception e) {
            assertEquals("Graph Dataset must not be null.", "Graph Dataset must not be null.", e.getMessage());
        }

    }

    @Test
    public void verifyCategoryDatasetNullExceptionMessageIsValid() throws Exception {

        buildGraph.setPieDataset(pieDataset);
        buildGraph.setCategoryDataset(null);

        try {
            buildGraph.generateGraph(graphProperties);
            fail("GraphDataSet is null");
        } catch (Exception e) {
            assertEquals("Graph Dataset must not be null.", "Graph Dataset must not be null.", e.getMessage());
        }

    }

    @Test
    public void verifyPieDatasetIsCalledWhenTypeOfGraphRequestedIsEqualToP() throws Exception {

        when(graphProperties.getTypeOfGraph()).thenReturn(1);
        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetPieGraph);

        buildGraph.generateGraph(graphProperties);
        verify(pieDataset, times(1)).buildDataset(new ArrayList<GraphData>());

    }

    @Test
    public void verifyCategoryDatasetIsCalledWhenTypeOfGraphRequestedIsEqualToL() throws Exception {

        when(graphProperties.getTypeOfGraph()).thenReturn(2);
        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetCategoryGraph);

        buildGraph.generateGraph(graphProperties);
        verify(categoryDataset, times(1)).buildDataset(new ArrayList<GraphData>());

    }

    @Test
    public void verifyCategoryDatasetIsCalledWhenTypeOfGraphRequestedIsEqualToB() throws Exception {

        when(graphProperties.getTypeOfGraph()).thenReturn(3);
        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetCategoryGraph);

        buildGraph.generateGraph(graphProperties);
        verify(categoryDataset, times(1)).buildDataset(new ArrayList<GraphData>());

    }

    @Test
    public void verifyIsLegendRequiredMethodIsCalledOnBudgetPieGraphImplementation() throws Exception {

        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetPieGraph);

        buildGraph.generateGraph(graphProperties);
        verify(budgetPieGraph, times(1)).isGraphLegendRequired(graphProperties);

    }

    @Test
    public void verifyIsLegendRequiredMethodIsCalledOnBudgetCategoryGraphImplementation() throws Exception {

        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetCategoryGraph);

        buildGraph.generateGraph(graphProperties);
        verify(budgetCategoryGraph, times(1)).isGraphLegendRequired(graphProperties);

    }

    @Test
    public void verifyCreateGraphMethodIsCalledOnBudgetPieGraphImplementation() throws Exception, BudgetGraphIsInvalidException {

        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetPieGraph);

        buildGraph.generateGraph(graphProperties);
        verify(budgetPieGraph, times(1)).createGraph(budgetPieGraph);

    }

    @Test
    public void verifyCreateGraphMethodIsCalledOnBudgetCategoryGraphImplementation() throws Exception {

        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetCategoryGraph);

        buildGraph.generateGraph(graphProperties);
        verify(budgetCategoryGraph, times(1)).createGraph(budgetCategoryGraph);

    }

    @Test
    public void assertBuildGraphMethodReturnsAValidURLForPathOfGraph() throws Exception {

        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetPieGraph);

        GraphProperties props = buildGraph.generateGraph(graphProperties);
        assertNotNull("BudgetGraph return URL is not null.", props);

    }

    @Test
    public void assertGraphPropertiesContainsAValueForGraphURL() throws Exception {

        when(graphFactory.buildGraph(graphProperties)).thenReturn(budgetPieGraph);
        when(buildGraph.generateGraph(graphProperties).getGraphURL()).thenReturn("value");

        GraphProperties props = buildGraph.generateGraph(graphProperties);
        assertNotNull("GraphProperties URL is not null.", props.getGraphURL());

    }

}
