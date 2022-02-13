package com.springdatajdbc.controllers;

import com.springdatajdbc.dtos.AddClientDTO;
import com.springdatajdbc.dtos.ClientDTO;
import com.springdatajdbc.dtos.ResponseDTO;
import com.springdatajdbc.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addClient(@RequestBody AddClientDTO addClientDTO){
        clientService.addClient(addClientDTO);
        ResponseDTO responseDTO = new ResponseDTO(true, "Client added successfully.");
        return new ResponseEntity<> (responseDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<ClientDTO> getAllClients(){
        return clientService.getAllClients();
    }
}
