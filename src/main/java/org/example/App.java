package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class App {
    private static ArrayList<Double> priser = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char val;

        do {
            System.out.println("Elpriser");
            System.out.println("========");
            System.out.println("1. Inmatning");
            System.out.println("2. Min, Max och Medel");
            System.out.println("3. Sortera");
            System.out.println("4. Bästa Laddningstid (4h)");
            System.out.println("5. Visualisering");
            System.out.println("e. Avsluta");
            System.out.print("Välj ett alternativ: ");
            val = scanner.next().charAt(0);

            switch (val) {
                case '1':
                    inmatning(scanner);
                    break;
                case '2':
                    minMaxMedel();
                    break;
                case '3':
                    sortera();
                    break;
                case '4':
                    bastaLaddningstid();
                    break;
                case '5':
                    visualisering();
                    break;
                case 'e':
                case 'E':
                    System.out.println("Avslutar programmet.");
                    break;
                default:
                    System.out.println("Ogiltigt val, försök igen.");
            }
        } while (val != 'e' && val != 'E');

        scanner.close();
    }

    private static void inmatning(Scanner scanner) {
        priser.clear();
        for (int i = 0; i < 24; i++) {
            System.out.print("Ange elpris för timme " + i + "-" + (i + 1) + ": ");
            double pris = scanner.nextDouble();
            priser.add(pris);
        }
    }

    private static void minMaxMedel() {
        if (priser.isEmpty()) {
            System.out.println("Inga priser inmatade.");
            return;
        }

        double min = Collections.min(priser);
        double max = Collections.max(priser);
        double medel = priser.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        int minIndex = priser.indexOf(min);
        int maxIndex = priser.indexOf(max);

        System.out.println("Min: " + min + " öre (timme " + minIndex + "-" + (minIndex + 1) + ")");
        System.out.println("Max: " + max + " öre (timme " + maxIndex + "-" + (maxIndex + 1) + ")");
        System.out.println("Medel: " + medel + " öre");
    }

    private static void sortera() {
        if (priser.isEmpty()) {
            System.out.println("Inga priser inmatade.");
            return;
        }

        ArrayList<Double> sorteradePriser = new ArrayList<>(priser);
        Collections.sort(sorteradePriser, Collections.reverseOrder());

        for (double pris : sorteradePriser) {
            int index = priser.indexOf(pris);
            System.out.println(String.format("%02d-%02d %d öre", index, index + 1, (int) pris));
        }
    }

    private static void bastaLaddningstid() {
        if (priser.size() < 4) {
            System.out.println("Minst 4 priser krävs för att beräkna bästa laddningstid.");
            return;
        }

        double minSum = Double.MAX_VALUE;
        int startIndex = 0;

        for (int i = 0; i <= priser.size() - 4; i++) {
            double sum = 0;
            for (int j = i; j < i + 4; j++) {
                sum += priser.get(j);
            }
            if (sum < minSum) {
                minSum = sum;
                startIndex = i;
            }
        }

        double medelPris = minSum / 4;
        System.out.println("Bästa laddningstid är från timme " + startIndex + " till " + (startIndex + 3) + " med en total kostnad av " + minSum + " öre och ett medelpris av " + medelPris + " öre.");
    }

    private static void visualisering() {
//        ArrayList<Integer> priser = new ArrayList<>();
//
//        // Populate the ArrayList with numbers from 2 to 25
//        for (int i = 1; i <= 24; i++) {
//            priser.add(i);
//        }

        if (priser.isEmpty()) {
            System.out.println("Inga priser inmatade.");
            return;
        }

        final int width = 76;
        double maxPris = Collections.max(priser);
        double minPris = Collections.min(priser);
        double skala = width / (maxPris - minPris);


        // Define the number of Y-axis steps (resolution of Y-axis)
        int antalTimmar = 24; // Adjust this for more or fewer Y-axis steps
        double stepSize = (maxPris - minPris) / antalTimmar;  // Calculate step size

        // Print Y-axis from maxPris to minPris
        for (int y = 0; y <= antalTimmar; y++) {
            // Calculate the current Y-axis level
            double currentLevel = maxPris - y * stepSize;

            // Print Y-axis label (rounded to integer)
            System.out.printf("%2d | ", Math.round(currentLevel));

            // Print histogram for each time step
            for (int i = 0; i < priser.size(); i++) {
                double pris = priser.get(i);

                // If the price at this time step is close to the current Y-axis level
                if (pris >= currentLevel) {
                    System.out.print("X ");  // Plot the price
                } else {
                    System.out.print("  ");  // Empty space
                }
            }
            System.out.println(" ");  // New line after each Y-axis level
        }


//        for (int i = 0; i < priser.size(); i++) {
//            double pris = priser.get(i);
//            // Antal X behöver rättas.
//            int antalX = (int) ((pris - minPris) * skala);
//            System.out.print(String.format("%03d| ", (int) pris));
//            for (int j = 0; j < antalX; j++) {
//                System.out.print("x");
//            }
//            System.out.println();
//        }

        System.out.print("   |");
        for (int i = 0; i < 76; i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.print("   |");
        for (int i = 0; i < 24; i++) {
            System.out.print(String.format(" %02d", i));
        }
        System.out.println();
    }
}

