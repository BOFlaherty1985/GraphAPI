package graphapi.model;

import java.math.BigDecimal;

/**
 * Graph Data Object
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 24/11/2014
 * @project BudgetApp
 */
public class GraphData {

    private String description;
    private BigDecimal value;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "GraphData{" +
                "description='" + description + '\'' +
                ", value=" + value +
                '}';
    }

}
