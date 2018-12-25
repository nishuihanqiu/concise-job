package com.lls.api.concise.rmi.serialize;

/************************************
 * SerializationVersion
 * @author liliangshan
 * @date 2018/12/23
 ************************************/
public enum SerializationVersion {

    HESSIAN_1(0),
    HESSIAN_2(1),
    JACK_SON(2);
    private final int version;

    SerializationVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public static SerializationVersion match(int version) {
        for (SerializationVersion item : SerializationVersion.values()) {
            if (item.getVersion() == version) {
                return item;
            }
        }
        return null;
    }
}
