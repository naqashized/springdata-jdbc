package com.springdatajdbc.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("project")
@Getter
@Setter
public class Project {
    @Id
    private int id;
    private String name;
}
