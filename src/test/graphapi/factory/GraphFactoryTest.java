package test.graphapi.factory;

import main.java.graphapi.exceptions.GraphPropertiesNullException;
import main.java.graphapi.factory.GraphFactory;
import main.java.graphapi.implementation.BudgetCategoryGraph;
import main.java.graphapi.implementation.BudgetGraph;
import main.java.graphapi.implementation.BudgetPieGraph;
import main.java.graphapi.properties.GraphProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Build Graph Factory Test
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
public class GraphFactoryTest {

    private GraphFactory factory = new GraphFactory();

    private GraphProperties graphProperties;

    @Before
    public void setUp() {
        this.graphProperties = Mockito.mock(GraphProperties.class);
    }

    @After
    public void tearDown() {
        graphProperties = null;
    }

    @Test
    public void throwExceptionIfGraphPropertiesIsNull() {

        try {
            factory.buildGraph(null);
            fail("GraphProperties is null");
        } catch (Exception e) {
            System.out.println("exception thrown if GraphProperties is null.");
        }

    }

    @Test
    public void throwGraphPropertiesNullExceptionIfGraphPropertiesIsNull() throws Exception {

        try {
            factory.buildGraph(null);
            fail("GraphProperties is null");
        } catch (GraphPropertiesNullException e) {
            assertEquals("Exception is of type GraphPropertiesNullException",
                    "GraphPropertiesNullException", e.getClass().getSimpleName());
        }

    }

    @Test
    public void verifyGraphPropertiesNullExceptionErrorMessageIsValid() throws Exception {

        try {
            factory.buildGraph(null);
            fail("GraphProperties is null");
        } catch (GraphPropertiesNullException e) {
            assertEquals("GraphProperties error message is valid.", "GraphProperties must not be null."
                    , e.getMessage());
        }

    }

    @Test
    public void assertGraphPropertiesContainsUserInputTypeOfGraphRequested() throws Exception {

        when(graphProperties.getTypeOfGraph()).thenReturn(8);

        factory.buildGraph(graphProperties);
        assertTrue("GraphProperties contains a char value.", graphProperties.getTypeOfGraph() != ' ');

    }

    @Test
    public void assertPieGraphImplementationIsReturnedWheNTypeOfGraphIsEqualToP() throws Exception {

        when(graphProperties.getTypeOfGraph()).thenReturn(1);

        BudgetGraph result = factory.buildGraph(graphProperties);
        assertTrue("Result is of type BudgetPieGraph.", result instanceof BudgetPieGraph);

    }

    @Test
    public void assertCategoryGraphImplementationIsReturnedWheNTypeOfGraphIsEqualToL() throws Exception {

        when(graphProperties.getTypeOfGraph()).thenReturn(2);

        BudgetGraph result = factory.buildGraph(graphProperties);
        assertTrue("Result is of type BudgetCategoryGraph.", result instanceof BudgetCategoryGraph);

    }

    @Test
    public void assertCategoryGraphImplementationIsReturnedWheNTypeOfGraphIsEqualToB() throws Exception {

        when(graphProperties.getTypeOfGraph()).thenReturn(3);

        BudgetGraph result = factory.buildGraph(graphProperties);
        assertTrue("Result is of type BudgetCategoryGraph.", result instanceof BudgetCategoryGraph);

    }

    @Test
    public void assertNullIsReturnedWhenTypeOfGraphIsNotValidTestOne() throws Exception {

        BudgetGraph result = factory.buildGraph(graphProperties);
        assertTrue("Result is equal to null", result == null);

    }

    @Test
    public void assertNullIsReturnedWhenTypeOfGraphIsNotValidTestTwo() throws Exception {

        when(graphProperties.getTypeOfGraph()).thenReturn(10);

        BudgetGraph result = factory.buildGraph(graphProperties);
        assertTrue("Result is equal to null", result == null);

    }

    @Test
    public void assertNullIsReturnedWhenTypeOfGraphIsNotValidTestThree() throws Exception {

        when(graphProperties.getTypeOfGraph()).thenReturn(100);

        BudgetGraph result = factory.buildGraph(graphProperties);
        assertTrue("Result is equal to null", result == null);

    }


}
