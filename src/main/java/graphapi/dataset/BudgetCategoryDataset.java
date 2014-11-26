package main.java.graphapi.dataset;

import main.java.graphapi.model.GraphData;
import org.jfree.data.general.AbstractDataset;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description Here
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
@Component
public class BudgetCategoryDataset implements BudgetDataset {

    @Override
    public <T extends AbstractDataset> T buildDataset(List<GraphData> graphDataList) {
        return null;
    }
}