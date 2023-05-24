package com.gbase;


import com.gbase.mode.*;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            String mode = args[0].toLowerCase();
            switch (mode) {
                case "mode1":
                    Mode1 mode1 = new Mode1();
                    break;
                case "mode2":
                    Mode2 mode2 = new Mode2();
                    break;
                case "mode3":
                    Mode3 mode3 = new Mode3();
                    break;
                case "mode4":
                    Mode4 mode4 = new Mode4();
                    break;
                case "mode5":
                    Mode5 mode5 = new Mode5();
                    break;
            }
        } else {
            Mode1 mode2 = new Mode1();
        }
    }
}
