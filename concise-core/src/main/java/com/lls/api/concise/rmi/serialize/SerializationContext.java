package com.lls.api.concise.rmi.serialize;

import java.io.IOException;

/************************************
 * SerializationContext
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public class SerializationContext implements Serialization {

    private Serialization serialization;

    public SerializationContext(Serialization serialization) {
        this.serialization = serialization;
    }


    @Override
    public byte[] serialize(Object obj) throws IOException {
        return serialization.serialize(obj);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException {
        return serialization.deserialize(bytes, clz);
    }

    @Override
    public byte[] batchSerialize(Object[] data) throws IOException {
        return serialization.batchSerialize(data);
    }

    @Override
    public Object[] batchDeserialize(byte[] data, Class<?>[] classes) throws IOException {
        return serialization.batchDeserialize(data, classes);
    }

    @Override
    public int getSerializeVersion() {
        return serialization.getSerializeVersion();
    }

}
