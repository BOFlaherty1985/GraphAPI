package graphapi.dataset;

import graphapi.model.GraphData;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

/**
 * Build a Dataset for Pie Graph
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
@Component
public class BudgetPieDataset implements BudgetDataset {

    @Override
    public <T extends AbstractDataset> T buildDataset(List<GraphData> graphDataList) throws Exception {

        DefaultPieDataset dataset = new DefaultPieDataset();

        BigDecimal totalPie = new BigDecimal(BigInteger.ZERO);
        totalPie = calculateTotalPie(graphDataList, totalPie);

        for(GraphData graphData : graphDataList) {
            dataset.setValue(graphData.getDescription(), calculatePercentageValue(totalPie, graphData));
        }


        return (T) dataset;
    }

    private void validateGraphData(GraphData graphData) throws Exception {
        if(graphData.getDescription() == null || graphData.getValue() == null) {
            throw new Exception("GraphData is invalid.");
        }
    }

    private BigDecimal calculateTotalPie(List<GraphData> graphDataList, BigDecimal totalPie) throws Exception {
        for(GraphData graphData : graphDataList) {

            validateGraphData(graphData);

            totalPie = totalPie.add(graphData.getValue());
        }

        return totalPie;
    }

    private Double calculatePercentageValue(BigDecimal totalPie, GraphData graphData) {
        return graphData.getValue().divide(totalPie, 3, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100")).doubleValue();
    }

}
