package graphapi.properties;


import graphapi.model.GraphData;

import java.util.List;

/**
 * Graph Properties
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
public class GraphProperties {

    private int typeOfGraph;
    private String title;
    private String requiredSize;
    private boolean legendRequired;
    private List<GraphData> graphDataList;
    private String graphURL;

    public void setTypeOfGraph(int typeOfGraph) {
        this.typeOfGraph = typeOfGraph;
    }

    public int getTypeOfGraph() {
        return typeOfGraph;
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

    public void setRequiredSize(String requiredSize) {
        this.requiredSize = requiredSize;
    }

    public boolean isLegendRequired() {
        return legendRequired;
    }

    public void setLegendRequired(boolean legendRequired) {
        this.legendRequired = legendRequired;
    }

    public List<GraphData> getGraphDataList() {
        return graphDataList;
    }

    public void setGraphDataList(List<GraphData> graphDataList) {
        this.graphDataList = graphDataList;
    }

    public String getGraphURL() {
        return graphURL;
    }

    public void setGraphURL(String graphURL) {
        this.graphURL = graphURL;
    }

    @Override
    public String toString() {
        return "GraphProperties{" +
                "typeOfGraph=" + typeOfGraph +
                ", title='" + title + '\'' +
                ", requiredSize='" + requiredSize + '\'' +
                ", legendRequired=" + legendRequired +
                ", graphDataList=" + graphDataList +
                ", graphURL='" + graphURL + '\'' +
                '}';
    }

}
