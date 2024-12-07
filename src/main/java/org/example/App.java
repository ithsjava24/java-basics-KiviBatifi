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
            System.out.println("2. Lägsta, Högsta och Medel");
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
            System.out.print("Ange elpris för timme " + i + "-" + (i + 1) + "- ");
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

        System.out.println("Lägsta pris: " + String.format("%02d-%02d", minIndex, 3) + " - " + min + " öre/kWh");
        System.out.println("Högsta pris: " + String.format("%02d-%02d", maxIndex, 1) + " - " + max + " öre/kWh");
        System.out.println("Medelpris: " + String.format("%.2f", medel) + " öre/kWh");
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
        if (priser.isEmpty()) {
            System.out.println("Inga priser inmatade.");
            return;
        }

        final int width = 76;
        double maxPris = Collections.max(priser);
        double minPris = Collections.min(priser);
        double skala = width / (maxPris - minPris);

        int antalTimmar = 24;
        double stepSize = (maxPris - minPris) / antalTimmar;

        for (int y = 0; y <= antalTimmar; y++) {
            double currentLevel = maxPris - y * stepSize;
            System.out.printf("%2d | ", Math.round(currentLevel));

            for (int i = 0; i < priser.size(); i++) {
                double pris = priser.get(i);
                if (pris >= currentLevel) {
                    System.out.print("X ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println(" ");
        }

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
