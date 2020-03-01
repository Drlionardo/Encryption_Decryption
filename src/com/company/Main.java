package com.company;

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
            }
        }

    }
    public String readData(){
        return this.data=data;
    }
    private void writeData() {
        System.out.println(data);
    }

    public void operate() {
        readData();
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
        if(mode.equals("encrypt")) data = a.Encrypt(data);
        else if(mode.equals("decrypt")) data = a.Decrypt(data);
}


}
interface Algorithm{
    String Encrypt(String data);
    String Decrypt(String data);
}
class ShiftAlg implements Algorithm{
    @Override
    public String Encrypt(String data) {
        return null;
    }

    @Override
    public String Decrypt(String data) {
        return null;
    }
}
class UnicodeAlg implements Algorithm{
    @Override
    public String Encrypt(String data) {
        return null;
    }

    @Override
    public String Decrypt(String data) {
        return null;
    }
}