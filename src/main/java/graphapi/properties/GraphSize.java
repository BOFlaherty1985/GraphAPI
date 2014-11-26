package main.java.graphapi.properties;

/**
 * Description Here
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 21/11/2014
 * @project BudgetApp
 */
public enum GraphSize {

    SMALL(250, 250),
    MEDIUM(300, 300),
    LARGE(400, 400);

    private int width;
    private int height;

    private GraphSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
