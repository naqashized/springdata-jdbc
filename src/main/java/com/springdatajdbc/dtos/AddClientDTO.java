package com.springdatajdbc.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddClientDTO {
    private String name;
    private List<AddProjectDTO> projects;

    @Getter
    @Setter
    public static class AddProjectDTO{
        private String name;
    }
}
