package com.springdatajdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springdatajdbc.dtos.AddClientDTO;
import com.springdatajdbc.models.Client;
import com.springdatajdbc.models.Project;
import com.springdatajdbc.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class ClientControllerTests {
    @Autowired
    private WebApplicationContext applicationContext;
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private ClientRepository clientRepository;

    @BeforeAll
    public void init() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
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

    private void setupClient(){
        Client client = new Client();
        client.setName("DL Services");
        Project project = new Project();
        project.setName("Poverty Alleviation");
        client.addProject(project);
        clientRepository.save(client);
    }

    @Test
    public void getAllClients() throws Exception {
        setupClient();
        mockMvc.perform(get("/client/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath( "$",hasSize(1)))
                .andDo(print());
    }
}
