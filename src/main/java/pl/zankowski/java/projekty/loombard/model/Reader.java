package pl.zankowski.java.projekty.loombard.model;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Zano on 2015-03-26.
 */
public class Reader {

    public static ArrayList<String> read(String filePath)
            throws FileNotFoundException, UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
        ArrayList<String> list = new ArrayList<String>();
        String line;
        try {
            try {
                while((line = reader.readLine()) != null) {
                    list.add(line);
                }
            } finally {
                reader.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String readBlock(String filePath)
            throws FileNotFoundException, UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-16"));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            try {
                while((line = reader.readLine()) != null) {
                    builder.append(line+"\n");
                }
            } finally {
                reader.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
