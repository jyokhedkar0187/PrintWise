package com.projectf.Controller;

import com.projectf.AdminHome;
import com.projectf.configuration.FirebaseInit;
import com.projectf.Controller.FirebaseConfig;
import com.projectf.view.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        FirebaseConfig.initializeFirebase();
        this.primaryStage = stage;
        showLoginView();
        primaryStage.show();
    }

    public void showLoginView() {
        LoginView loginView = new LoginView();
        loginView.start(primaryStage, this);
    }
    public void showSignUpPage() {
    LoginView loginView = new LoginView();
    loginView.start(primaryStage, this);
    loginView.initializeSignUpPage();
    primaryStage.setScene(loginView.getSignupScene());
}


    public void showMyOrdersPage() {
    MyOrdersScreen screen = new MyOrdersScreen();
    screen.start(primaryStage, this);
}

    public void showHomepage() {
        Home homepage = new Home();
        homepage.start(primaryStage, this);
    }

    public void showAdmin() {
        AdminHome adminHome = new AdminHome();
        adminHome.start(primaryStage, this);
    }

    public void showOrdersPage() {
        MyOrdersScreen screen = new MyOrdersScreen();
        screen.start(primaryStage, this);
    }

    public void showExplorePage() {
        Explore explore = new Explore();
          FirebaseConfig.initializeFirebase();
        explore.start(primaryStage, this);
    }

    public void showReferPage() {
        ReferPage referPage = new ReferPage();
        referPage.start(primaryStage, this);
    }

    public void showProfilePage() {
        // Uncomment and implement when ProfileScreen is available
        // ProfileScreen profileScreen = new ProfileScreen();
        // profileScreen.start(primaryStage, this);
    }

}