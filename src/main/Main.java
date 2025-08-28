package main;


import iu.login.Login;
import iu.main.Menu;



public class Main {

    public static void main(String[] args) {
        Login login = new Login();
        login.menu();
        Menu menu = new Menu();
        menu.menuPrincipal();
    }
}
