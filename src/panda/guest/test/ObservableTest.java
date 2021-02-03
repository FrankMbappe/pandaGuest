package panda.guest.test;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ObservableTest {
    static ObservableList<String> fruitsObservable;
    static ObservableList<String> currentFruitsObservable;

    public static void main(String[] args) {
        System.out.println("Test started");

        ArrayList<String> fruits = new ArrayList<>(){{
            add("Banana");
            add("Mango");
            add("Apple");
            add("Pineapple");
        }};

        fruitsObservable = FXCollections.observableArrayList(fruits);

        currentFruitsObservable = FXCollections.observableArrayList(fruitsObservable);

        fruitsObservable.addListener((InvalidationListener) observable -> {
            if (! currentFruitsObservable.containsAll(fruitsObservable)){
                System.out.println("A change has been made.");
                currentFruitsObservable.setAll(fruitsObservable);
            }
        });

        ArrayList<String> fruits2 = new ArrayList<>(){{
            add("Banana");
            add("Mango");
            add("Apple");
            add("Pineapple");
            add("Orange");
        }};

        fruitsObservable.setAll(fruits);

        fruitsObservable.setAll(fruits2);

        fruitsObservable.setAll(fruits2);


        System.out.println("Test ended");
    }
}


