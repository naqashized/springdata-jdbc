package com.springdatajdbc.services;

import com.springdatajdbc.dtos.AddClientDTO;
import com.springdatajdbc.dtos.ClientDTO;
import static com.springdatajdbc.dtos.AddClientDTO.AddProjectDTO;
import static com.springdatajdbc.dtos.ClientDTO.ProjectDTO;
import com.springdatajdbc.models.Client;
import com.springdatajdbc.models.Project;
import com.springdatajdbc.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;

    @Override
    public void addClient(AddClientDTO addClientDTO) {
        Client client = new Client();
        client.setName(addClientDTO.getName());
        for(AddProjectDTO addProjectDTO: addClientDTO.getProjects()){
            Project project = new Project();
            project.setName(addProjectDTO.getName());
            client.addProject(project);
        }
        clientRepository.save(client);
    }

    @Override
    public List<ClientDTO> getAllClients() {
        List<Client> clients = (List<Client>) clientRepository.findAll();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        for(Client client: clients){
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setId(client.getId());
            clientDTO.setName(client.getName());
            List<ProjectDTO> projectDTOs = client.getProjects().stream().map(project -> convertProjectToProjectDTO(project)).collect(Collectors.toList());
            clientDTO.setProjects(projectDTOs);
            clientDTOList.add(clientDTO);
        }
        return clientDTOList;
    }
    private ProjectDTO convertProjectToProjectDTO(Project project){
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        return projectDTO;
    }

    @Override
    public ClientDTO getClientById(int id) {
        return null;
    }
}
