package test.graphapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.graphapi.controller.BuildGraphController;
import main.java.graphapi.factory.BuildGraph;
import main.java.graphapi.factory.GraphFactory;
import main.java.graphapi.model.GraphData;
import main.java.graphapi.properties.GraphProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Build Graph Controller Test
 *
 * @author Benjamin O'Flaherty
 * @date Created on: 25/11/2014
 * @project GraphAPI
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:main/webapp/WEB-INF/GraphBuilder-servlet.xml")
@WebAppConfiguration
public class BuildGraphControllerTest {

    private MockMvc mockMvc;

    private static final String REST_URL = "http://localhost:8080/GraphBuilder/generateGraph";

    @Autowired
    private BuildGraphController buildGraphController;

    @Autowired
    private BuildGraph buildGraph;

    @Autowired
    private GraphFactory graphFactory;

    @Autowired
    private RestTemplate restTemplate;

    private ObjectMapper jsonMapper = new ObjectMapper();

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(buildGraphController).build();

        buildGraph = Mockito.mock(BuildGraph.class);

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Test
    public void isGenerateGraphRequestMappingValidAndReturnsOKStatus() throws Exception {

        GraphProperties graphProperties = mockGraphProperties("Test 1 Graph", "SMALL", false);
        graphProperties.setGraphDataList(mockDataList());

        mockMvc.perform(post("/generateGraph")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonMapper.writeValueAsString(graphProperties)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title", is("Test 1 Graph")))
            .andExpect(jsonPath("$.requiredSize", is("SMALL")))
            .andExpect(jsonPath("$.legendRequired", is(false)));
    }

    @Test
    public void setHTTPStatusCodeToBadRequestWheNGraphPropertiesIsMissingATitleAndNotValid() throws Exception {

        GraphProperties graphProperties = mockGraphProperties(null, "LARGE", false);
        graphProperties.setGraphDataList(mockDataList());

        mockMvc.perform(post("/generateGraph")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(graphProperties)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void setHTTPStatusCodeToBadRequestWheNGraphPropertiesIsMissingGraphSizeAndNotValid() throws Exception {

        GraphProperties graphProperties = mockGraphProperties("Title Goes here", null, false);
        graphProperties.setGraphDataList(mockDataList());

        mockMvc.perform(post("/generateGraph")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(graphProperties)))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void assertGraphPropertiesObjectValuesAreCorrectWhenUsingTheRestTemplate() throws JsonProcessingException {

        GraphProperties graphProperties = mockGraphProperties("RestTemplate Test1", "MEDIUM", true);
        graphProperties.setTypeOfGraph('p');
        graphProperties.setGraphDataList(mockDataList());

        HttpEntity request = setupRestTemplate(graphProperties);

        ResponseEntity<GraphProperties> response = restTemplate.postForEntity(REST_URL, request,
                GraphProperties.class);

        assertEquals("Response Title is equal to response RestTemplate Test1", "RestTemplate Test1", response.getBody().getTitle());
        assertEquals("Response RequiredSize is equal to MEDIUM", "MEDIUM", response.getBody().getRequiredSize());
        assertNotNull("Response GraphDataList should not be null.", response.getBody().getGraphDataList());
        assertNotNull("Response GraphURL should not be null.", response.getBody().getGraphURL());
    }

    @Test
    public void assertGraphPropertiesObjectGraphURLIsValidAndSuffixEndsIn_PieChartWhenGraphIsOfTypePie() {

        GraphProperties graphProperties = mockGraphProperties("RestTemplate Test1", "MEDIUM", true);
        graphProperties.setTypeOfGraph('p');
        graphProperties.setGraphDataList(mockDataList());

        HttpEntity request = setupRestTemplate(graphProperties);

        ResponseEntity<GraphProperties> response = restTemplate.postForEntity(REST_URL, request,
                GraphProperties.class);

        assertTrue("Response GraphURL suffix is _PieChart When typeOfGraph equals PIE", response.getBody().getGraphURL().endsWith("_PieChart.jpg"));
    }

    @Test
    public void assertGraphPropertiesObjectGraphURLIsValidAndSuffixEndsIn_CategoryChartWhenGraphIsOfTypeCategory() {

        GraphProperties graphProperties = mockGraphProperties("RestTemplate Test1", "LARGE", true);
        graphProperties.setTypeOfGraph('b');
        graphProperties.setGraphDataList(mockDataList());

        HttpEntity request = setupRestTemplate(graphProperties);

        ResponseEntity<GraphProperties> response = restTemplate.postForEntity(REST_URL, request,
                GraphProperties.class);

        assertTrue("Response GraphURL suffix is _CategoryChart When typeOfGraph equals Bar Chart", response.getBody().getGraphURL().endsWith("_CategoryChart.jpg"));

    }

    private HttpEntity setupRestTemplate(GraphProperties graphProperties) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        return new HttpEntity( graphProperties, headers );
    }

//    @Test
//    public void testFootballApi() {
//
//        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity("http://football-api.com/api/?Action=standings&APIKey=eedee853-c3f8-a783-f10599bf8217&comp_id=[2]", Object[].class);
//        System.out.println(responseEntity.toString());
//
//    }

    private GraphProperties mockGraphProperties(String title, String sizeOfGraph, boolean isLegendRequired) {

        GraphProperties graphProperties = new GraphProperties();
        graphProperties.setTitle(title);
        graphProperties.setRequiredSize(sizeOfGraph);
        graphProperties.setLegendRequired(isLegendRequired);

        return graphProperties;
    }

    private List<GraphData> mockDataList() {

        List<GraphData> graphDataList = new ArrayList<GraphData>();

        GraphData category_one = new GraphData();
        category_one.setDescription("Description 1");
        category_one.setValue(new BigDecimal("40"));

        graphDataList.add(category_one);

        GraphData category_two = new GraphData();
        category_two.setDescription("Description 2");
        category_two.setValue(new BigDecimal("60"));

        graphDataList.add(category_two);

        return graphDataList;
    }

}
