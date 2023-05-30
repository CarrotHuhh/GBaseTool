package com.gbase;


import com.gbase.mode.Mode1;
import com.gbase.mode.Mode2;
import com.gbase.mode.Mode3;

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
            }
        } else {
            Mode2 mode2 = new Mode2();
        }
    }
}
