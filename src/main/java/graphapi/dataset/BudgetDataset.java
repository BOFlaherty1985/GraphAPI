package graphapi.dataset;

import graphapi.model.GraphData;
import org.jfree.data.general.AbstractDataset;

import java.util.List;

/**
 * Budget Dataset interface
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
public interface BudgetDataset {

    <T extends AbstractDataset> T buildDataset(List<GraphData> graphDataList) throws Exception;

}
