package hr.fer.zemris.apr;

import hr.fer.zemris.apr.functions.ObjectiveFunction;
import hr.fer.zemris.apr.functions.impl.*;
import hr.fer.zemris.apr.vector.Vector;
import hr.fer.zemris.apr.optimization.CoordinateSearch;
import hr.fer.zemris.apr.optimization.HookeJeeves;
import hr.fer.zemris.apr.optimization.Simplex;

public class Demo {

    public static void main(String[] args) {
        first();
        second();
        third();
        fourth();
        fifth();
    }

    private static void first() {
        System.out.println("PRVI ZADATAK\n-----------------");
        ObjectiveFunction f = new Function6();
        double x0 = 10;
        Vector x = new Vector(true, new double[]{x0});

        f.setCallsCount(0);
        CoordinateSearch search = new CoordinateSearch();
        Vector result = search.coordinateSearch(f, x);
        System.out.println(result);
        System.out.println(f.getCallsCount());

        System.out.println();

        f.setCallsCount(0);
        f.resetMap();
        HookeJeeves hj = new HookeJeeves();
        result = hj.hookeJeeves(f, false, x, 10E-6);
        System.out.println(result);
        System.out.println(f.getCallsCount());

        System.out.println();

        f.setCallsCount(0);
        f.resetMap();
        Simplex simplex = new Simplex();
        result = simplex.simplex(f, false, x);
        System.out.println(result);
        System.out.println(f.getCallsCount() + "\n");
    }

    private static void second() {
        System.out.println("DRUGI ZADATAK\n-----------------");

        ObjectiveFunction f1 = new Function1();
        System.out.println("Funkcija 1:");
        CoordinateSearch search = new CoordinateSearch();
        System.out.println(search.coordinateSearch(f1, f1.getX0()));
        System.out.println(f1.getCallsCount() + "\n");
        f1.resetMap();

        f1.setCallsCount(0);
        HookeJeeves hj = new HookeJeeves();
        System.out.println(hj.hookeJeeves(f1, f1.getX0()));
        System.out.println(f1.getCallsCount() + "\n");
        f1.resetMap();

        f1.setCallsCount(0);
        Simplex simplex = new Simplex();
        System.out.println(simplex.simplex(f1, false, f1.getX0()));
        System.out.println(f1.getCallsCount() + "\n");

        System.out.println("Funkcija 2:");
        ObjectiveFunction f2 = new Function2();
        System.out.println(search.coordinateSearch(f2, f2.getX0()));
        System.out.println(f2.getCallsCount() + "\n");
        f2.setCallsCount(0);
        f2.resetMap();

        System.out.println(hj.hookeJeeves(f2, f2.getX0()));
        System.out.println(f2.getCallsCount() + "\n");
        f2.resetMap();

        f2.setCallsCount(0);
        System.out.println(simplex.simplex(f2, false, f2.getX0()));
        System.out.println(f2.getCallsCount() + "\n");

        System.out.println("Funkcija 3:");
        ObjectiveFunction f3 = new Function3();
        System.out.println(search.coordinateSearch(f3, f3.getX0()));
        System.out.println(f3.getCallsCount() + "\n");
        f3.resetMap();

        f3.setCallsCount(0);
        System.out.println(hj.hookeJeeves(f3, f3.getX0()));
        System.out.println(f3.getCallsCount() + "\n");
        f3.resetMap();

        f3.setCallsCount(0);
        System.out.println(simplex.simplex(f3, false, f3.getX0()));
        System.out.println(f3.getCallsCount() + "\n");

        System.out.println("Funkcija 4:");
        ObjectiveFunction f4 = new Function4();
        System.out.println(search.coordinateSearch(f4, f4.getX0()));
        System.out.println(f4.getCallsCount() + "\n");

        f4.resetMap();
        f4.setCallsCount(0);
        System.out.println(hj.hookeJeeves(f4, f4.getX0()));
        System.out.println(f4.getCallsCount() + "\n");

        f4.resetMap();
        f4.setCallsCount(0);
        System.out.println(simplex.simplex(f4, false, f4.getX0()));
        System.out.println(f4.getCallsCount() + "\n");
    }

    private static void third() {
        System.out.println("TRECI ZADATAK\n-----------------");
        Vector x0 = new Vector(true, new double[]{5, 5});
        ObjectiveFunction f = new Function4();
        HookeJeeves hj = new HookeJeeves();
        System.out.println(hj.hookeJeeves(f, x0));
        f.resetMap();
        f.setCallsCount(0);
        Simplex simplex = new Simplex();
        System.out.println(simplex.simplex(f, false, x0) + "\n");
    }

    private static void fourth() {
        System.out.println("CETVRTI ZADATAK\n-----------------");
        System.out.println("Početna točka: [0.5, 0.5]");
        ObjectiveFunction f = new Function1();
        Vector x0 = new Vector(true, new double[]{0.5, 0.5});
        Simplex simplex = new Simplex();
        for (int step = 1; step < 20; step += 2) {
            System.out.println("step = " + step + ":\t" + simplex.simplex(f, false, x0, step) + "\t" + f.getCallsCount());
            f.setCallsCount(0);
            f.resetMap();
        }

        System.out.println();
        System.out.println("Početna točka: [20, 20]");
        x0 = new Vector(true, new double[]{20, 20});
        for (int step = 1; step < 20; step += 2) {
            System.out.println("step = " + step + ":\t" + simplex.simplex(f, false, x0, step) + "\t" + f.getCallsCount());
            f.setCallsCount(0);
            f.resetMap();
        }
        System.out.println();
    }

    private static void fifth() {
        System.out.println("PETI ZADATAK\n-----------------");
        ObjectiveFunction f = new Function5();
        HookeJeeves hj = new HookeJeeves();
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            Vector x0 = new Vector(true, new double[]{-50 + Math.random() * 100, -50 + Math.random() * 100});
            Vector x = hj.hookeJeeves(f, x0);
            double res = Math.abs(f.calculate(x));
            if (res <= 10E-4) {
                count++;
            }
            f.setCallsCount(0);
            f.resetMap();
        }

        System.out.println(count / (double) 10000 * 100 + "%");
    }
}
