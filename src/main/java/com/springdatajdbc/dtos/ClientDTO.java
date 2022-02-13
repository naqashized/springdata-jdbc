package com.springdatajdbc.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientDTO {
    private int id;
    private String name;
    private List<ProjectDTO> projects;

    @Getter
    @Setter
    public static class ProjectDTO{
        private int id;
        private String name;
    }
}
