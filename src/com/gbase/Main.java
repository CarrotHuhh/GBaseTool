package com.gbase;


import com.gbase.mode.Mode1;
import com.gbase.mode.Mode2;

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
            }
        } else {
            Mode2 mode2 = new Mode2();
        }
    }
}
