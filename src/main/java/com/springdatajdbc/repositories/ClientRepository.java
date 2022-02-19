package com.springdatajdbc.repositories;

import com.springdatajdbc.models.Client;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ClientRepository extends PagingAndSortingRepository<Client, Integer> {
}
