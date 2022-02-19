package com.springdatajdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdatajdbc.dtos.AddClientDTO;
import com.springdatajdbc.models.Client;
import com.springdatajdbc.models.Project;
import com.springdatajdbc.repositories.ClientRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ClientControllerTests {
//    @Autowired
//    private WebApplicationContext applicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private ClientRepository clientRepository;
    //@Container
    private static MySQLContainer mySQLContainer = (MySQLContainer) new MySQLContainer("mysql:8.0.26")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true);

    @DynamicPropertySource
    public static  void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);
    }
    @BeforeAll
    public static void start() {
        mySQLContainer.withInitScript("schema.sql");
        mySQLContainer.start();
    }
    @AfterAll
    public static void stop(){
        mySQLContainer.stop();
    }


    @Test
    public void addProduct() throws Exception {
        AddClientDTO addClientDTO = new AddClientDTO();
        addClientDTO.setName("Test Client");
        AddClientDTO.AddProjectDTO addProjectDTO1 = new AddClientDTO.AddProjectDTO();
        addProjectDTO1.setName("CRM");
        AddClientDTO.AddProjectDTO addProjectDTO2 = new AddClientDTO.AddProjectDTO();
        addProjectDTO2.setName("ERP");
        addClientDTO.setProjects(Arrays.asList(addProjectDTO1,addProjectDTO2));
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        String request = objectMapper.writeValueAsString(addClientDTO);
        mockMvc.perform(post("/client/add").contentType(MEDIA_TYPE_JSON_UTF8)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath( "$.errorMessage").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Client added successfully."))
                .andDo(print());
    }

    private Client setupClient(){
        Client client = new Client();
        client.setName("DL Services");
        Project project = new Project();
        project.setName("Poverty Alleviation");
        client.addProject(project);
        return clientRepository.save(client);
    }

    @Test
    public void getAllClients() throws Exception {
        setupClient();
        mockMvc.perform(get("/client/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    public void getClientById() throws Exception {
        Client client = setupClient();
        mockMvc.perform(get("/client/"+client.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("DL Services"))
                .andDo(print());
    }
}
