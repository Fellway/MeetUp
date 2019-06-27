package com.skrzypczyk.meetings.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public String getNameWithDashes() {
        return name.replaceAll(" ", "-");
    }
}
