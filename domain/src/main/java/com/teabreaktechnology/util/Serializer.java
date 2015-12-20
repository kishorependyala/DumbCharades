package com.teabreaktechnology.util;


import com.teabreaktechnology.dumcharades.bean.Player;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by kishorekpendyala on 12/13/15.
 */
public class Serializer {

    public static void main(String[] args) throws Exception {


        Player player = new Player.Builder().playerId(1).playerName("Avinash").build();
        Serializer serializer = new Serializer();
        serializer.serialize(player);
        System.out.println("Before " + player);
        Player newPlayer = serializer.deserialize();
        System.out.println("After " + newPlayer);

    }

    public void serialize(Player player) throws Exception {


        FileOutputStream fileOut =
                new FileOutputStream("/tmp/employee.ser");

        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(player);
        out.close();
        fileOut.close();
    }

    public Player deserialize() {
        Player e = null;
        try {
            FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Player) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return e;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return e;
        }
        return e;
    }


}
