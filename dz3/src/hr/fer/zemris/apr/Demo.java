package hr.fer.zemris.apr;

import hr.fer.zemris.apr.function.ObjectiveFunction;
import hr.fer.zemris.apr.function.impl.Function1;
import hr.fer.zemris.apr.function.impl.Function2;
import hr.fer.zemris.apr.function.impl.Function3;
import hr.fer.zemris.apr.function.impl.Function4;
import hr.fer.zemris.apr.linear.Vector;
import hr.fer.zemris.apr.optimization.Box;
import hr.fer.zemris.apr.optimization.GradientDescent;
import hr.fer.zemris.apr.optimization.NewtonRaphson;
import hr.fer.zemris.apr.optimization.Transform;
import hr.fer.zemris.apr.util.Interval;

import java.util.ArrayList;
import java.util.List;

public class Demo {

    public static void main(String[] args) {
        first();
        second();
        third();
        fourth();
        fifth();
    }

    private static void first() {
        System.out.println("1. ZADATAK");
        System.out.println("Rezultat uz fiksnu stopu učenja:");
        ObjectiveFunction function = new Function3();
        GradientDescent gradientDescent = new GradientDescent();
        Vector x1 = gradientDescent.calculate(function, function.getX0(), false);
        System.out.println(x1+"\n");
        System.out.println("Rezultat uz adaptivnu stopu učenja:");
        Vector x2 = gradientDescent.calculate(function, function.getX0(), true);
        System.out.println(x2);
        System.out.println("=============================\n");
    }

    private static void second() {
        System.out.println("2. ZADATAK");
        ObjectiveFunction f1 = new Function1();
        ObjectiveFunction f2 = new Function2();
        GradientDescent gradientDescent = new GradientDescent();
        NewtonRaphson newtonRaphson = new NewtonRaphson();
        System.out.println("1. funkcija + gradijentni spust:");
        Vector x1 = gradientDescent.calculate(f1, f1.getX0(), true);
        System.out.println(x1+"\n");
        System.out.println("1. funkcija + Newton-Raphsonov postupak:");
        f1 = new Function1();
        Vector x3 = newtonRaphson.calculate(f1, f1.getX0(), true);
        System.out.println(x3+"\n");
        System.out.println("Broj izračuna funkcije: " + f1.getResultCount());
        System.out.println("Broj izračuna gradijenta: " + f1.getGradientCount());
        System.out.println("Broj izračuna Hesseove matrice: " + f1.getHessianCount());
        System.out.println("2. funkcija + gradijentni spust:");
        Vector x2 = gradientDescent.calculate(f2, f2.getX0(), true);
        System.out.println(x2+"\n");
        System.out.println("2. funkcija + Newton-Raphsonov postupak:");
        f2 = new Function2();
        Vector x4 = newtonRaphson.calculate(f2, f2.getX0(), true);
        System.out.println(x4+"\n");
        System.out.println("Broj izračuna funkcije: " + f2.getResultCount());
        System.out.println("Broj izračuna gradijenta: " + f2.getGradientCount());
        System.out.println("Broj izračuna Hesseove matrice: " + f2.getHessianCount());
        System.out.println("=============================\n");
    }

    private static void third() {
        System.out.println("3. ZADATAK");
        ObjectiveFunction f1 = new Function1();
        ObjectiveFunction f2 = new Function2();
        Interval interval = new Interval(-100, 100);
        ObjectiveFunction g1 = new ObjectiveFunction() {
            @Override
            public double calculate(Vector x) {
                return x.get(1) - x.get(0);
            }
        };
        ObjectiveFunction g2 = new ObjectiveFunction() {
            @Override
            public double calculate(Vector x) {
                return 2 - x.get(0);
            }
        };
        List<Interval> explicit = List.of(interval, interval);
        List<ObjectiveFunction> implicit = List.of(g1, g2);
        Box box = new Box();
        Vector x1 = box.calculate(f1.getX0(), f1, explicit, implicit);
        System.out.println(x1);
        Vector x2 = box.calculate(f2.getX0(), f2, explicit, implicit);
        System.out.println(x2);
        System.out.println("=============================\n");
    }

    private static void fourth() {
        System.out.println("4. ZADATAK");
        ObjectiveFunction f1 = new Function1();
        ObjectiveFunction f2 = new Function2();
        ObjectiveFunction g1 = new ObjectiveFunction() {
            @Override
            public double calculate(Vector x) {
                return x.get(1) - x.get(0);
            }
        };
        ObjectiveFunction g2 = new ObjectiveFunction() {
            @Override
            public double calculate(Vector x) {
                return 2 - x.get(0);
            }
        };
        List<ObjectiveFunction> g = List.of(g1, g2);
        Transform transform = new Transform();
        Vector x1 = transform.transformAndCalculate(f1.getX0(), f1, g, new ArrayList<>());
        System.out.println("1. funkcija + transformacija bez traženja unutarnje početne točke:");
        System.out.println(x1.toString(2));
        Vector x2 = transform.transformAndCalculate(f2.getX0(), f2, g, new ArrayList<>());
        System.out.println("2. funkcija + transformacija bez traženja unutarnje početne točke");
        System.out.println(x2.toString(2));

        Vector x3 = transform.transformAndCalculate(new Vector(new double[] {3, 2}), f1, g, new ArrayList<>());
        System.out.println("1. funkcija + transformacija uz traženje unutarnje početne točke:");
        System.out.println(x3.toString(2));

        Vector x4 = transform.transformAndCalculate(new Vector(new double[] {3, 2}), f2, g, new ArrayList<>());
        System.out.println("2. funkcija + transformacija uz traženje unutarnje početne točke:");
        System.out.println(x4.toString(2) + "\n");
        System.out.println("=============================\n");
    }

    private static void fifth() {
        System.out.println("5. ZADATAK");

        ObjectiveFunction f4 = new Function4();
        ObjectiveFunction g1 = new ObjectiveFunction() {
            @Override
            public double calculate(Vector x) {
                return 3 - x.get(0) - x.get(1);
            }
        };
        ObjectiveFunction g2 = new ObjectiveFunction() {
            @Override
            public double calculate(Vector x) {
                return 3 + 1.5 * x.get(0) - x.get(1);
            }
        };
        ObjectiveFunction h1 = new ObjectiveFunction() {
            @Override
            public double calculate(Vector x) {
                return x.get(1) - 1;
            }
        };

        List<ObjectiveFunction> g = List.of(g1, g2);
        List<ObjectiveFunction> h = List.of(h1);

        Transform transform = new Transform();
        Vector x = transform.transformAndCalculate(new Vector(new double[] {5, 5}), f4, g, h);
        System.out.println("4. funkcija + transformacija uz traženje unutarnje početne točke:");
        System.out.println(x.toString(2));
        System.out.println("=============================\n");

    }

}
