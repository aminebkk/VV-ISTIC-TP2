# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

A refresher on TCC and LCC is available in the [course notes](https://oscarlvp.github.io/vandv-classes/#cohesion-graph).

## Answer

  First this is a small definition of the TCC and the LCC 
 ==> a Tight Class Cohesion (TCC) measures how much a class's methods are used together (high TCC means methods are closely related).
 ==> a Loose Class Cohesion (LCC) measures how much a class's methods use each other (high LCC means the methods don't depend on each other much).
 
this is an example of a Calculator class :

public class Calculator {

    private int a;
    private int b;

    public Calculator(int a, int b) {
        this.a = a;
        this.b = b;
    }

    // Method 1: Adds the two fields a and b 
    public int add() {
        return a + b;
    }

    // Method 2: Multiplies the two fields a and b
    public int multiply() {
        return a * b;
    }

    // Method 3: Prints the sum of the fields a and b
    public void printSum() {
        System.out.println("Sum: " + add());
    }

    // Method 4: Prints the product of the fields a and b
    public void printProduct() {
        System.out.println("Product: " + multiply());
    }

    public static void main(String[] args) {
        ExampleClass example = new ExampleClass(5, 10);
        example.printSum();
        example.printProduct();
    }
}

here the value of TCC and LCC will produce the same value : 
  ==> would measure the interactions between the methods. Since all the methods (add(), multiply(), printSum(), printProduct()) only call each other and don't interact with any external methods, TCC will be high.
  ==> LCC measures how much a method depends on methods from other classes. Since this class doesn't call methods from other classes, LCC will also be high.

 ++++ Can LCC be Lower than TCC :
Yes, LCC can be lower than TCC in some situations 
 for example Imagine we have a class that represents a Car.
TCC (Tight Class Cohesion) is high if all the methods in the class work closely together, like one method that starts the car, another that accelerates it, and another that applies the brakes. These methods rely on each other to make the car function smoothly.

LCC (Loose Class Cohesion) is lower if, for example, the car class needs to call an external GPS service to find directions or an external payment system to pay for fuel. These methods don't really rely on each other inside the class; they are using things from outside the class.

So, in this case, TCC is high, but LCC is lower because the methods in the class are depending on things outside the class (like the GPS or payment system).



