package com.chxt.fantasticmonkey.infrastructure.spring;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SpringRepository extends MongoRepository<SpringDO, String> {


    @Query(value = "{'status': ?0, 'type': ?1}", fields = "{'id': 1, 'title': 1, 'name': 1, 'type': 1, 'url': 1, 'tags': 1, 'status': 1, 'imageSize': 1}")
    List<SpringDO> findByStatusAndType(String status, String type);

    @Query(value = "{'status': ?0}", fields = "{'id': 1, 'title': 1, 'name': 1, 'type': 1, 'url': 1, 'tags': 1, 'status': 1, 'imageSize':  1}")
    List<SpringDO> findByStatus(String status);


    @Query(value = "{'title': ?0}")
    List<SpringDO> findByTitle(String title);



}
