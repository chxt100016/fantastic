package com.chxt.fantasticmonkey.util.crawler;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class CrawlerFactory implements PooledObjectFactory<Crawler> {
    @Override
    public PooledObject<Crawler> makeObject() throws Exception {
        return new DefaultPooledObject<>(Crawler.getLocal(true));
    }

    @Override
    public void destroyObject(PooledObject<Crawler> pooledObject) throws Exception {
        pooledObject.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<Crawler> pooledObject) {
        return true;
    }

    @Override
    public void activateObject(PooledObject<Crawler> pooledObject) throws Exception {

    }

    @Override
    public void passivateObject(PooledObject<Crawler> pooledObject) throws Exception {

    }
}
