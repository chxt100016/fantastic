package com.chxt.fantasticmonkey.infrastructure.env;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chxt.fantasticmonkey.model.property.Environment;
import com.chxt.fantasticmonkey.model.property.TennisProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class EnvRepository {
    @Resource
    private EnvironmentMapper mapper;

    public TennisProperty getTennisReport() {
        List<Environment> environments = this.getNamespace("tennisReport");
        return new TennisProperty(environments);
    }



    private List<Environment> getNamespace(String namespace) {
        LambdaQueryWrapper<Environment> query = new QueryWrapper<Environment>().lambda()
                .eq(Environment::getNamespace, namespace);
        return this.mapper.selectList(query);
    }
}
