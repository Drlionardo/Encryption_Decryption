package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
       Operator op = new Operator(args);
       op.operate();
    }
}
class Operator {
    /**
     * If there is no -mode, the program should work in enc mode.
     * If there is no -key, the program should consider that key = 0.
     * If there is no -data,  the program should assume that the data is an empty string.
     * -alg unicode/shift shift is default
     */
    private String mode = "enc";
    private String alg = "shift";
    private int key = 0;
    private String data="";
    private String in="";
    private String out="";

    public Operator(String[] args) {
        //Read args:
        for(int i =0; i < args.length;i++){
            switch (args[i]){
                case "-mode":
                    this.mode=args[i+1];
                break;
                case "-alg":
                    this.alg=args[i+1];
                    break;
                case "-key":
                    this.key= Integer.parseInt(args[i+1]);
                    break;
                case"-data":
                    this.data=args[i+1];
                    break;
                case"-in":
                    this.in=args[i+1];
                    break;
                case"-out":
                    this.out=args[i+1];
                    break;
            }
        }
    }
    public void readData() {
        //Read from console
        Scanner scan = null;
        if (in.isEmpty()) {
            scan = new Scanner(System.in);
        } else {
            File inputFile = new File(data);
            try {
                scan = new Scanner(inputFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //Reading data using scanner:
        data=scan.next();
    }
    private void writeData() {
        //If no output file in args -> to console
        if(out.isEmpty()){
            System.out.println(data);
        } else{ // Write -> output File
            try {
                File output = new File(this.out);
                if (output.createNewFile()) {
                    FileWriter fw = new FileWriter(output);
                    fw.write(data);
                    fw.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void operate() {
        if(data.isEmpty()) readData();
        operateData();
        writeData();
        }

    private void operateData() throws NullPointerException {
        Algorithm a = null;
        if(alg.equals("shift")){
            a=new ShiftAlg();
    }
        else if(alg.equals("unicode")){
            a=new UnicodeAlg();
        }
        assert a != null;
        if(mode.equals("enc")) data = a.Encrypt(data,key);
        else if(mode.equals("dec")) data = a.Decrypt(data,key);
}

}
interface Algorithm{
    String Encrypt(String data, int key);
    String Decrypt(String data, int key);
}
class UnicodeAlg implements Algorithm{
    @Override
    public String Encrypt(String data, int key) {
        char[] a = data.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] += key;
        }
        return String.valueOf(a);
    }

    @Override
    public String Decrypt(String data, int key) {
        char[] a = data.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i]-=key;
        }
        return String.valueOf(a);
    }
}
class ShiftAlg implements Algorithm{
    @Override
    public String Encrypt(String data, int key) {
        char[] a = data.toCharArray();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String alphabetCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < a.length; i++) {
            if(Character.isAlphabetic(a[i])){
                for (int j = 0; j < alphabet.length(); j++) {
                    if(a[i]==alphabet.charAt(j)) a[i]=alphabet.charAt((j+key)%26);
                    else if(a[i]==alphabetCaps.charAt(j)) a[i]=alphabetCaps.charAt((j+key)%26);
                }
            }
        }
        return String.valueOf(a);
    }

    @Override
    public String Decrypt(String data, int key) {
        char[] a = data.toCharArray();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String alphabetCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < a.length; i++) {
            if(Character.isAlphabetic(a[i])){
                for (int j = 0; j < alphabet.length(); j++) {
                    if(a[i]==alphabet.charAt(j)){
                        a[i]=alphabet.charAt((j-key+26)%26);
                        break;
                    }
                    else if(a[i]==alphabetCaps.charAt(j)){
                        a[i]=alphabetCaps.charAt((j-key+26)%26);
                        break;
                    }
                }
            }
        }
        return String.valueOf(a);
    }
}