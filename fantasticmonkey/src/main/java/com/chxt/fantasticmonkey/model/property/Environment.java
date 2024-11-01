package com.chxt.fantasticmonkey.model.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Environment {
    private Integer id;

    private String namespace;

    private String name;

    private String content;
}
