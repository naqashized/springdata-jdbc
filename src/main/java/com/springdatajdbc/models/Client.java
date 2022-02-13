package com.springdatajdbc.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("client")
@Getter
@Setter
public class Client {
    @Id
    private int id;
    private String name;
    private Set<Project> projects = new HashSet<>();

    public void addProject(Project project){
        projects.add(project);
    }
}
