package my.edu.um.fsktm.spendwise;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseFunction {
    public DatabaseFunction(){

    }

    public void writeToFile(String filename, String data, Context context){
        File path = context.getFilesDir();
        Log.d("Path", String.valueOf(path));
        File file = new File(path, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public ArrayList<String> readTransFromFile(String filename, Context context){
        File path = context.getFilesDir();
        File file = new File(path, filename);
        Log.d("Path", String.valueOf(file));
        ArrayList<String> al = new ArrayList<>();
        try {
            Log.d("Info", "Enter");
            FileInputStream fis = new FileInputStream(file);
            Scanner sc = new Scanner(fis);
            while(sc.hasNextLine()){
                Log.d("Info", "Enter2");
                al.add(sc.nextLine());
            }


        } catch (FileNotFoundException e) {
            Log.d("Error", "No file found.");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("Error", "Error?");
            e.printStackTrace();
        }
        Log.d("Info", "Enter3");
        for(int i = 0; i < al.size(); i++){
            Log.d("Array", al.get(i));
        }

        return al;
    }



    public ArrayList<String> readBudgFromFile(String filename, Context context){
        File path = context.getFilesDir();
        File file = new File(path, filename);
        Log.d("Path", String.valueOf(file));
        String line = null;
        ArrayList<String> al = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            Scanner sc = new Scanner(fis);
            line = sc.nextLine();

            String temp[] = line.split(",");
            for(int i = 0; i < temp.length; i++){
                Log.d("Temp[i]", temp[i]);
                al.add(temp[i]);
            }

        } catch (FileNotFoundException e) {
            Log.d("Error", "No file found.");
            e.printStackTrace();

            return null;
        } catch (IOException e) {
            Log.d("Error", "Error?");
            e.printStackTrace();
            return null;
        }


        return al;
    }








}
