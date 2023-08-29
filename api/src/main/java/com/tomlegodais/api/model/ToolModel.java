package com.tomlegodais.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tool")
public class ToolModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ToolTypeModel type;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private ToolBrandModel brand;
}
