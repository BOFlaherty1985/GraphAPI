package graphapi.implementation;

import graphapi.exceptions.BudgetGraphIsInvalidException;
import graphapi.exceptions.BudgetGraphNullException;
import graphapi.properties.GraphProperties;
import graphapi.properties.GraphSize;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.PieDataset;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Pie Graph Implementation
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
public class BudgetPieGraph implements BudgetGraph {

    private String title;
    private String requiredSize;
    private boolean legendRequired;
    private PieDataset dataset;

    private static final String PATH_TO_FILE = "D:\\Build_WAR\\GraphBuilder\\src\\main\\webapp\\graphs\\pie\\";
    private static final String FILE_SUFFIX = "_PieChart.jpg";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyySS");

    @Override
    public void initialise(GraphProperties graphProperties) {
        this.title = graphProperties.getTitle();
        this.requiredSize = graphProperties.getRequiredSize();
    }

    @Override
    public void isGraphLegendRequired(GraphProperties graphProperties) {
        setLegendRequired(graphProperties.isLegendRequired());
    }

    @Override
    public <T extends AbstractDataset> void setDataset(T dataset) {
        this.dataset = (PieDataset) dataset;
    }

    @Override
    public <T> String createGraph(T budgetGraph) throws Exception {

        BudgetPieGraph pieGraph = (BudgetPieGraph) budgetGraph;

        String graphURL =  PATH_TO_FILE + sdf.format(new Date())+ FILE_SUFFIX;

        if(budgetGraph == null) {
            throw new BudgetGraphNullException("BudgetGraph is null.");
        } else {
            pieGraph.validate(pieGraph);

            JFreeChart pieChart = createPieChart(pieGraph);

            ChartUtilities.saveChartAsJPEG(new File(graphURL),
                    pieChart,
                    determineGraphWidth(pieGraph.getRequiredSize()),
                    determineGraphHeight(pieGraph.getRequiredSize()));
        }

        return graphURL;
    }

    private int determineGraphWidth(String requestedSize) {
        return (requestedSize == "SMALL") ? GraphSize.SMALL.getWidth() :
                (requestedSize == "MEDIUM") ? GraphSize.MEDIUM.getWidth() : GraphSize.LARGE.getWidth();
    }

    private int determineGraphHeight(String requestedSize) {
        return (requestedSize == "SMALL") ? GraphSize.SMALL.getHeight() :
                (requestedSize == "MEDIUM") ? GraphSize.MEDIUM.getHeight() : GraphSize.LARGE.getHeight();

    }

    private JFreeChart createPieChart(BudgetPieGraph pieGraph) {
        return ChartFactory.createPieChart(
                pieGraph.getTitle(),
                pieGraph.getDataset(),
                pieGraph.legendRequired,
                true,
                false);
    }

    public void validate(BudgetPieGraph budgetPieGraph) throws Exception {

        if(budgetPieGraph.getTitle() == null
                || budgetPieGraph.getRequiredSize() == null
                || budgetPieGraph.getDataset() == null)  {
            throw new BudgetGraphIsInvalidException();
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequiredSize() {
        return requiredSize;
    }

    public boolean isLegendRequired() {
        return legendRequired;
    }

    public void setLegendRequired(boolean legendRequired) {
        this.legendRequired = legendRequired;
    }

    @Override
    public String toString() {
        return "BudgetPieGraph{" +
                "title='" + title + '\'' +
                ", requiredSize='" + requiredSize + '\'' +
                ", legendRequired=" + legendRequired +
                ", dataset=" + dataset +
                '}';
    }

    public PieDataset getDataset() {
        return dataset;
    }

    public void setDataset(PieDataset dataset) {
        this.dataset = dataset;
    }

}
