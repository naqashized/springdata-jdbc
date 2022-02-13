package com.springdatajdbc.repositories;

import com.springdatajdbc.models.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {
}
