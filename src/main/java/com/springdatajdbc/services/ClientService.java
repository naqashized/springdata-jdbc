package com.springdatajdbc.services;

import com.springdatajdbc.dtos.AddClientDTO;
import com.springdatajdbc.dtos.ClientDTO;
import com.springdatajdbc.exceptions.RecordNotFoundException;

import java.util.List;

public interface ClientService {
    void addClient(AddClientDTO addClientDTO);
    List<ClientDTO> getAllClients();
    ClientDTO getClientById(int id) throws RecordNotFoundException;

}
