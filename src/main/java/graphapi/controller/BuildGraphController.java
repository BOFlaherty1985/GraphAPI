package graphapi.controller;

import graphapi.factory.BuildGraph;
import graphapi.properties.GraphProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Build Graph Rest Controller
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 24/11/2014
 * @project GraphAPI
 */
@RestController
public class BuildGraphController {

    @Autowired
    private BuildGraph buildGraph;

    @RequestMapping(value="/generateGraph", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GraphProperties> buildGraph(@RequestBody GraphProperties graphProperties) throws Exception {

        // TODO - Complete validation
        if(graphProperties.getTitle() == null || graphProperties.getRequiredSize() == null) {
            return new ResponseEntity<GraphProperties>(graphProperties, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<GraphProperties>(buildGraph.generateGraph(graphProperties), HttpStatus.CREATED);
    }

}
