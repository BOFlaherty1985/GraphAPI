package graphapi.implementation;

import graphapi.properties.GraphProperties;
import org.jfree.data.general.AbstractDataset;

/**
 *  Category Graph Implementation
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
public class BudgetCategoryGraph implements BudgetGraph {

    @Override
    public void initialise(GraphProperties graphProperties) {

    }

    @Override
    public void isGraphLegendRequired(GraphProperties graphProperties) {

    }

    @Override
    public <T extends AbstractDataset> void setDataset(T dataset) {

    }

    @Override
    public <T> String createGraph(T budgetGraph) {
        return null;
    }


}
