package main.java.graphapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.graphapi.model.GraphData;
import main.java.graphapi.properties.GraphProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Client Test
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 25/11/2014
 * @project GraphBuilder
 */
public class Client {

    public static void main(String[] args) throws IOException {

        ObjectMapper jsonMapper = new ObjectMapper();

        GraphProperties graphProperties = new GraphProperties();
        graphProperties.setTitle("RestTemplate Test1");
        graphProperties.setRequiredSize("MEDIUM");
        graphProperties.setLegendRequired(true);
        graphProperties.setTypeOfGraph('p');

        List<GraphData> graphDataList = new ArrayList<GraphData>();

        GraphData category_one = new GraphData();
        category_one.setDescription("Description 1");
        category_one.setValue(new BigDecimal("40"));

        graphDataList.add(category_one);

        GraphData category_two = new GraphData();
        category_two.setDescription("Description 2");
        category_two.setValue(new BigDecimal("60"));

        graphDataList.add(category_two);

        graphProperties.setGraphDataList(graphDataList);

        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        String jsonStringUser = "{"
                + "\"id\":\"\"" + ","
                + "\"firstName\":\"nicolas\"" + ","
                + "\"lastName\":\"loriente\""
                + "}";

        HttpEntity request= new HttpEntity( jsonStringUser, headers );

        System.out.println(jsonMapper.writeValueAsString(graphProperties));

        String url = "http://localhost:8080/GraphBuilder/generateGraph";

        GraphProperties test = template.postForObject(url, request, GraphProperties.class);
        System.out.println(test);

    }

}
