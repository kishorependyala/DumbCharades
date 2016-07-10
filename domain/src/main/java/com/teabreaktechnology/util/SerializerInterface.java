package com.teabreaktechnology.util;

import java.io.Serializable;

/**
 * Created by kishorekpendyala on 2/21/16.
 */
public interface SerializerInterface {

    String getFilePath();

    void setFilePath(String filePath);

    <T extends Serializable> String serialize(T objectToSerialize) throws Exception;

    <T extends Serializable> T deserialize(String fileNameWithPath);
}
