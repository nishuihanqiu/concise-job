package com.lls.api.concise.rmi.serialize;

/************************************
 * SerializationFactory
 * @author liliangshan
 * @date 2018/12/25
 ************************************/
public class SerializationFactory {

    private static Serialization newSerialization() {
        return new HessianSerialization();
    }

    public static Serialization newSerialization(int version) {
        Serialization serialization;
        SerializationVersion serializationVersion = SerializationVersion.match(version);
        if (serializationVersion == null) {
            return newSerialization();
        }
        switch (serializationVersion) {
            case HESSIAN_1:
                serialization = new HessianSerialization();
                break;
            case HESSIAN_2:
                serialization = new Hessian2Serialization();
                break;
            case JACK_SON:
                serialization = new JacksonSerialization();
                break;
            default:
                serialization = newSerialization();
        }
        return serialization;
    }
}
