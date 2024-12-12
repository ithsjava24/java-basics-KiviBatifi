package org.example;

import java.util.Locale;
import java.util.Scanner;
import java.util.Arrays;


public class App {
    private static int[] priser = new int[24];

    public static void main(String[] args) {
        Locale.setDefault(Locale.of("SE", "sv"));
        Scanner sc = new Scanner(System.in);


        boolean runProgram = true;

        while (runProgram) {
            displayMenu();

            String userImp = sc.nextLine();

            switch (userImp.toLowerCase()) {


                case "1":
                    inmatning(sc);
                    System.out.println("Inmatning\n");
                    break;

                case "2":
                    calcMinMaxAvg(App.priser);
                    //("Min Max Medel\n");
                    break;

                case "3":
                    Sortera();
                    System.out.println("Sortera\n");
                    break;

                case "4":
                    BestCharge();
                    System.out.println();
                    break;

                case "5":
                    //Visualisering, jag tog bort med VG uppgiften;
                    System.out.println("Visualisering\n");
                    break;

                case "e":
                    runProgram = false;
                    System.out.println("Avsluta\n");
                    break;

                default:
                    System.out.println("Ogiltig inmatning\n");
                    break;

            }
        }
        sc.close();
    }

    private static void displayMenu() {
        String[] menu = {
                """
                Elpriser
                ========
                1. Inmatning
                2. Min, Max och Medel
                3. Sortera
                4. Bästa Laddningstid (4h)
                5. Visualisering
                e. Avsluta
                """};
        for (String s : menu) {
            System.out.println(s);
        }

    }

    private static void inmatning(Scanner sc) {
        //System.out.println("Skriv in dina värden här\n");
        for (int i = 0; i < 24; i++) {
            if (i == 23) {
                System.out.printf("Ange priset för timme %02d-%02d: \n", 23, 24);
            } else {
                System.out.printf("Ange priset för timme %02d-%02d: \n", i, i + 1);
            }

            priser[i] = Integer.parseInt(sc.nextLine());

        }
        System.out.println("Inmatning Klar\n");

    }

    private static void calcMinMaxAvg(int[] priser) {
        int min = priser[0];
        int max = priser[0];
        int sum = 0;
        int minHour = 0;
        int maxHour = 0;


        for (int i = 0; i < 24; i++) {
            sum += priser[i];

            if (priser[i] < min) {
                min = priser[i];
                minHour = i;
            }

            if (priser[i] > max) {
                max = priser[i];
                maxHour = i;
            }

        }
        double medel = (double) sum / priser.length;

        System.out.printf("Lägsta pris: %02d-%02d, %d öre/kWh\n"
                        + "Högsta pris: %02d-%02d, %d öre/kWh\n"
                        + "Medelpris: %.2f öre/kWh\n",
                minHour, minHour + 1, min,
                maxHour, maxHour + 1, max,
                medel);


    }


    private static void Sortera() {
        String[] tider = new String[24];
        for (int i = 0; i < 24; i++) {
            tider[i] = String.format("%02d-%02d", i, (i + 1));
        }
        int[] sorterbaraPriser = Arrays.copyOf(priser, priser.length);

        for (int i = 0; i < sorterbaraPriser.length; i++) {
            for (int j = 0; j < sorterbaraPriser.length - 1 - i; j++) {
                if (sorterbaraPriser[j] < sorterbaraPriser[j + 1]) {
                    int tempPrice = sorterbaraPriser[j];
                    sorterbaraPriser[j] = sorterbaraPriser[j + 1];
                    sorterbaraPriser[j + 1] = tempPrice;

                    String tempTime = tider[j];
                    tider[j] = tider[j + 1];
                    tider[j + 1] = tempTime;
                }
            }

        }

        for (int i = 0; i < sorterbaraPriser.length; i++) {
            System.out.print(tider[i] + " " + sorterbaraPriser[i] + " öre\n");
        }
    }

    private static void BestCharge() {
        String[] tider = new String[24];
        for (int i = 0; i < 24; i++) {
            tider[i] = String.format("%02d", i);
        }

        double minSum = Float.MAX_VALUE;
        int bestStartIndex = 0;

        for (int i = 0; i <= 20; i++) {
            double sum = priser[i] + priser[i + 1] + priser[i + 2] + priser[i + 3];

            if (sum < minSum) {
                minSum = sum;
                bestStartIndex = i;
            }

        }

        double avg = minSum / 4;

        String startTid = tider[bestStartIndex];

        System.out.print("Påbörja laddning klockan " + startTid + "\n");
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n", avg);

    }


}