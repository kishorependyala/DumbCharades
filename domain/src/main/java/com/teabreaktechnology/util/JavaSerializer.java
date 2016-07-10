package com.teabreaktechnology.util;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by kishorekpendyala on 12/13/15.
 */
public class JavaSerializer implements SerializerInterface {


    private String filePath = "/tmp/";

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName(String filePath, String fileName) {
        return filePath + fileName;
    }

    /**
     * @param objectToSerialize
     * @param <T>               can be any class that implements {@link Serializable}
     * @return Path of serialized file
     * @throws Exception
     */
    @Override
    public <T extends Serializable> String serialize(T objectToSerialize) throws Exception {

        String fileNameWithPath = getFileName(filePath, "cacheStateAt");

        FileOutputStream fileOut =
                new FileOutputStream(fileNameWithPath);

        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(objectToSerialize);
        out.close();
        fileOut.close();

        return fileNameWithPath;
    }

    /**
     * @param fileNameWithPath
     * @param <T>              Return type of the deserialized object
     * @return Deserialized object
     */
    @Override
    public <T extends Serializable> T deserialize(String fileNameWithPath) {
        T e = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileNameWithPath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (T) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return e;
        } catch (ClassNotFoundException c) {
            System.out.println("Issue in deserealizing " + fileNameWithPath);
            c.printStackTrace();
            return e;
        }
        return e;
    }


}
