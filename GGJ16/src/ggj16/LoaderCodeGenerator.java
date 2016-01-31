/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Pattern;

/**
 *
 * @author Jonathon
 */
public class LoaderCodeGenerator {
    
    public static void main(String[] args) {
        File code = new File("loader_code.txt");
        try {
            PrintStream ps = new PrintStream(code);
            printFiles("assets/images", "loadImage", new File("assets/images"), ps);
            printFiles("assets/sounds", "loadSoundEffect", new File("assets/sounds"), ps);
            printFiles("assets/music", "loadSoundEffect", new File("assets/music"), ps);
            ps.flush();
            ps.close();
        } catch(Exception e) { }
        
    }
    
    public static void printFiles(String currentFilePath, String function, File inDirectory, PrintStream ps) throws IOException {
        File[] list = inDirectory.listFiles();
        for (int i=0; i<list.length; i++) {
            if (list[i].isDirectory()) {
                printFiles(currentFilePath + "/" + list[i].getName(), function, list[i], ps);
            } else {
                ps.println("assetManager." + function + "(\"" + currentFilePath + "/" + list[i].getName() + "\", \"" + (list[i].getName().split(Pattern.quote("."))[0]) + "\");");
            }
        }
    }
}
