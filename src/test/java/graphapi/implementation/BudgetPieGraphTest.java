package graphapi.implementation;

import graphapi.exceptions.BudgetGraphIsInvalidException;
import graphapi.exceptions.BudgetGraphNullException;
import graphapi.properties.GraphProperties;
import org.jfree.chart.ChartFactory;
import org.jfree.data.general.PieDataset;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Test of Pie Graph Implementation
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 21/11/2014
 * @project BudgetApp
 */
public class BudgetPieGraphTest {

    private BudgetPieGraph budgetPieGraph = new BudgetPieGraph();
    private BudgetPieGraph mockBudgetPieGraph;
    private GraphProperties graphProperties;
    private ChartFactory chartFactory;
    private PieDataset pieDataset;

    @Before
    public void setUp() {
        mockBudgetPieGraph = Mockito.mock(BudgetPieGraph.class);
        graphProperties = Mockito.mock(GraphProperties.class);
        chartFactory = Mockito.mock(ChartFactory.class);
        pieDataset = Mockito.mock(PieDataset.class);
    }

    @After
    public void tearDown() {
        graphProperties = null;
    }

    @Test
    public void assertThatBudgetPieGraphTitleIsNotNull() {

        when(graphProperties.getTitle()).thenReturn("");

        budgetPieGraph.initialise(graphProperties);
        assertNotNull("BudgetPieGraph title is not null.", budgetPieGraph.getTitle());

    }

    @Test
    public void assertThatBudgetPieGraphIsEqualToValueSetWithinGraphProperties() {

        when(graphProperties.getTitle()).thenReturn("Graph Test #1");

        budgetPieGraph.initialise(graphProperties);
        assertEquals("BudgetPieGraph title is equal to Graph Test #1", graphProperties.getTitle(),
                budgetPieGraph.getTitle());

    }

    @Test
    public void assertThatBudgetPieGraphRequiredSizeIsNotNull() {

        when(graphProperties.getTitle()).thenReturn("Graph Test #1");
        when(graphProperties.getRequiredSize()).thenReturn("");

        budgetPieGraph.initialise(graphProperties);
        assertEquals("BudgetPieGraph title is equal to Graph Test #1", graphProperties.getTitle(),
                budgetPieGraph.getTitle());
        assertNotNull("BudgetPieGraph requiredSize is not null.", budgetPieGraph.getRequiredSize());
    }

    @Test
    public void assertThatBudgetPieGraphRequiredSizeIsEqualToValueSetWithinGraphProperties() {

        when(graphProperties.getRequiredSize()).thenReturn("SMALL");

        budgetPieGraph.initialise(graphProperties);
        assertEquals("BudgetPieGraph requiredSize is equal to SMALL", graphProperties.getRequiredSize(),
                budgetPieGraph.getRequiredSize());

    }

    @Test
    public void assertThatBudgetPieGraphLegendIsRequiredIsReturnsBooleanValue() {

        budgetPieGraph.isGraphLegendRequired(graphProperties);
        assertEquals("BudgetPieGraph isLegendRequired is equal to default value.", graphProperties.isLegendRequired(),
                budgetPieGraph.isLegendRequired());

    }

    @Test
    public void assertThatBudgetPieGraphLegendIsRequiredIsEqualToGraphPropertiesValue() {

        when(graphProperties.isLegendRequired()).thenReturn(true);

        budgetPieGraph.isGraphLegendRequired(graphProperties);
        assertEquals("BudgetPieGraph isLegendRequired is equal to graphProperties value.", graphProperties.isLegendRequired(),
                budgetPieGraph.isLegendRequired());

    }

    /*
        verify ChartFactory.createPieChart() has been called
        verify ChartUtilities.saveChartAsJPEG has been called
        assert width/height are retruend from Enum based on the requiredSize value

     */
    @Test(expected = BudgetGraphNullException.class)
    public void throwBudgetGraphIsNullExceptionWhenObjectIsPassedInToCreateGraphAsNull() throws Exception {

        BudgetPieGraph parameter = null;

        budgetPieGraph.createGraph(parameter);

    }

    @Test
    public void assertBudgetGraphIsNullExceptionMessageIsValid() throws Exception {

        BudgetPieGraph parameter = null;

        try {
            budgetPieGraph.createGraph(parameter);
            fail("BudgetGraphNullException error message is not valid.");
        } catch (BudgetGraphNullException e) {
            assertEquals("BudgetGraphNullException error message is valid.",
                    "BudgetGraph is null.", e.getMessage());
        }

    }

    @Test
    public void verifyBudgetGraphObjectIsValidated() throws Exception {

        when(mockBudgetPieGraph.getRequiredSize()).thenReturn("SMALL");

        budgetPieGraph.createGraph(mockBudgetPieGraph);
        verify(mockBudgetPieGraph, times(1)).validate(mockBudgetPieGraph);

    }

    @Test(expected = BudgetGraphIsInvalidException.class)
    public void throwExceptionWhenBudgetGraphIsMissingATitle() throws Exception {

        when(mockBudgetPieGraph.getTitle()).thenReturn(null);

        budgetPieGraph.validate(mockBudgetPieGraph);

    }

    @Test(expected = BudgetGraphIsInvalidException.class)
    public void throwExceptionWhenBudgetGraphIsMissingRequiredSize() throws Exception {

        when(mockBudgetPieGraph.getTitle()).thenReturn("title");
        when(mockBudgetPieGraph.getRequiredSize()).thenReturn(null);

        budgetPieGraph.validate(mockBudgetPieGraph);

    }

    @Test(expected = BudgetGraphIsInvalidException.class)
    public void throwExceptionWhenBudgetGraphIsMissingDataset() throws Exception {

        when(mockBudgetPieGraph.getTitle()).thenReturn("title");
        when(mockBudgetPieGraph.getRequiredSize()).thenReturn("SMALL");
        when(mockBudgetPieGraph.getDataset()).thenReturn(null);

        budgetPieGraph.validate(mockBudgetPieGraph);

    }

    @Test
    public void assertThatBudgetPieGraphReturnsAStringURLtoPathOfCreatedGraph() throws Exception {

        when(mockBudgetPieGraph.getRequiredSize()).thenReturn("SMALL");

        String result = budgetPieGraph.createGraph(mockBudgetPieGraph);

        assertNotNull("Result URL is not null", result);
    }

    @Test
    public void assertThatBudgetPieGraphReturnsAStringURLWithSuffixOf_PieChart() throws Exception {

        when(mockBudgetPieGraph.getRequiredSize()).thenReturn("SMALL");

        String result = budgetPieGraph.createGraph(mockBudgetPieGraph);

        assertNotNull("Result URL is not null", result);
        assertTrue("Result URL is suffix is equal to _PieChart.", result.endsWith("_PieChart.jpg"));

    }

}
