package org.example.customer;

public class Customer {
    public void order(String menuName, Menu menu, Cooking cooking){
        MenuItem menuIte = menu.choose(menuName);
        Cook cook = cooking.makeCook(menuIte);

    }
}
