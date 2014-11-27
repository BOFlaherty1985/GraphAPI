package graphapi.dataset;

import graphapi.model.GraphData;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * Pie Dataset Implementation Test
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
public class BudgetPieDatasetTest {

    /*
        buildDataset(List<GraphData> dataSetList)

        GraphData Object
            - String category
            - BigDecimal value

        1. loop through each GraphData object in dataSetList
        2. calculate totalValue from 'value' fields of each GraphData object
        3. divide original GraphData value by the totalValue field (provides % for Pie Chart)
        4. convert percentage value to Double
        5. create instance of PieDataSet
        6. call setValue method on dataset and pass in category and value (%)
        7. return dataset object

    */

    private BudgetPieDataset pieDataset = new BudgetPieDataset();

    @Test
    public void assertPieDataSetIsNotNull() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertNotNull("PieDataSet is not null.", pieDataSet);

    }

    @Test
    public void assertReturnedDatasetIsAnInstanceOfDefaultPieDataset() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertTrue("Dataset is an instance of DefaultPieDataset.", pieDataSet instanceof DefaultPieDataset);

    }

    @Test
    public void assertDatasetSizeIsGreaterThanZeroWhenGraphDataListHasValues() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();
        graphDataList.add(setupGraphData("Description1", new BigDecimal("100")));

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertTrue("PieDataSet has a size greater than Zero.", pieDataSet.getKeys().size() > 0);

    }

    @Test
    public void assertDatasetSizeIsEqualToTheSizeOfGraphDataListInputTestOne() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();
        graphDataList.add(setupGraphData("Description1", new BigDecimal("100")));

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertEquals("PieDataSet size is equal to size of input graphDataList", graphDataList.size(),
                pieDataSet.getKeys().size());

    }

    @Test
    public void assertDatasetSizeIsEqualToTheSizeOfGraphDataListInputTestTwo() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();
        graphDataList.add(setupGraphData("Description1", new BigDecimal("100")));
        graphDataList.add(setupGraphData("Description2", new BigDecimal("200")));

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertEquals("PieDataSet size is equal to size of input graphDataList", graphDataList.size(),
                pieDataSet.getKeys().size());

    }

    @Test
    public void assertDatasetSizeIsEqualToTheSizeOfGraphDataListInputTestThree() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();
        graphDataList.add(setupGraphData("Description1", new BigDecimal("100")));
        graphDataList.add(setupGraphData("Description2", new BigDecimal("200")));
        graphDataList.add(setupGraphData("Description3", new BigDecimal("300")));

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertEquals("PieDataSet size is equal to size of input graphDataList", graphDataList.size(),
                pieDataSet.getKeys().size());

    }

    // validate GraphData object, contains description and value
    @Test
    public void throwExceptionIfGraphDataObjectDoesNotContainAValidDescription() {

        List<GraphData> graphDataList = new ArrayList<GraphData>();

        GraphData graphData = new GraphData();
        graphData.setDescription(null);
        graphData.setValue(new BigDecimal("100"));

        graphDataList.add(graphData);

        try {
            pieDataset.buildDataset(graphDataList);
            fail("GraphData Description is null.");
        } catch (Exception e) {
            System.out.println("Exception Thrown");
        }

    }

    @Test
    public void throwExceptionIfGraphDataObjectDoesNotContainAValidValue() {

        List<GraphData> graphDataList = new ArrayList<GraphData>();

        GraphData graphData = new GraphData();
        graphData.setDescription("Description1");
        graphData.setValue(null);

        graphDataList.add(graphData);

        try {
            pieDataset.buildDataset(graphDataList);
            fail("GraphData Value is null.");
        } catch (Exception e) {
            System.out.println("Exception Thrown");
        }

    }

    @Test
    public void assertGraphDataExceptionErrorMessageIsValid() {

        List<GraphData> graphDataList = new ArrayList<GraphData>();

        GraphData graphData = new GraphData();
        graphData.setDescription("Description1");
        graphData.setValue(null);

        graphDataList.add(graphData);

        try {
            pieDataset.buildDataset(graphDataList);
            fail("GraphData Value is null.");
        } catch (Exception e) {
            assertEquals("GraphData is invalid exception message displayed.", "GraphData is invalid.", e.getMessage());
        }

    }

    @Test
    public void assertPieDatasetDescriptionIsEqualToGraphDataObjectValue() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();
        graphDataList.add(setupGraphData("Description1", new BigDecimal("100")));

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertEquals("PieDataset description is equal to Description1", "Description1", pieDataSet.getKey(0));

    }

    @Test
    public void assertPieDataSetValueIsEqualToGraphDataObjectValue() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();
        graphDataList.add(setupGraphData("Description1", new BigDecimal("50")));

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertEquals("PieDataset value is equal to 100.0", 100.0D, pieDataSet.getValue(0));

    }

    @Test
    public void assertPieDatasetPercentageValueIsCorrectTestOne() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();
        graphDataList.add(setupGraphData("Description1", new BigDecimal("45")));
        graphDataList.add(setupGraphData("Description2", new BigDecimal("85")));

        // TotalGraphDataValues = 130
        // 45/130 = 0.346
        // 85/130 = 0.653

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertEquals("PieDataset value is equal to 34.6", 34.6D, pieDataSet.getValue(0));
        assertEquals("PieDataset value is equal to 65.4", 65.4D, pieDataSet.getValue(1));

    }

    @Test
    public void assertPieDatasetPercentageValueIsCorrectTestTwo() throws Exception {

        List<GraphData> graphDataList = new ArrayList<GraphData>();
        graphDataList.add(setupGraphData("Description1", new BigDecimal("65")));
        graphDataList.add(setupGraphData("Description2", new BigDecimal("150")));

        // TotalGraphDataValues = 215
        // 65/215 = 0.302
        // 150/215 = 0.697

        PieDataset pieDataSet = (PieDataset) pieDataset.buildDataset(graphDataList);
        assertEquals("PieDataset value is equal to 34.6", 30.2D, pieDataSet.getValue(0));
        assertEquals("PieDataset value is equal to 65.4", 69.8D, pieDataSet.getValue(1));

    }

    public GraphData setupGraphData(String description, BigDecimal value) {

        GraphData graphData = new GraphData();
        graphData.setDescription(description);
        graphData.setValue(value);

        return graphData;
    }

}
