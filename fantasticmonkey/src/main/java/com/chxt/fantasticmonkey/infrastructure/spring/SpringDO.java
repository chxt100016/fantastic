package com.chxt.fantasticmonkey.infrastructure.spring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "springs")
public class SpringDO {

    @Id
    private String id;
    private String title;
    private String name;

    private String type;

    private String url;

    private List<Binary> images;

    private Integer imageSize;

    private List<String> torrents;

    private List<String> magnets;

    private String tags;

    private String status;
}
