package graphapi.exceptions;

/**
 * Exception to be thrown when BudgetGraph is null.
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 19/11/2014
 * @project BudgetApp
 */
public class BudgetGraphNullException extends Exception {

    public BudgetGraphNullException(String message) {
        super(message);
    }

}
