package com.example.minecraftbo3;

import net.sandrohc.schematic4j.SchematicLoader;
import net.sandrohc.schematic4j.schematic.Schematic;

import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        File schemFile = new File("C:/Users/killi/Desktop/test/bo3/McMaps/angel.litematic");
        try {
            Schematic schematic = SchematicLoader.load(schemFile);
            System.out.println("Width: " + schematic.width());
            System.out.println("Height: " + schematic.height());
            System.out.println("Length: " + schematic.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
