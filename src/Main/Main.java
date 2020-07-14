package Main;

import HUD.LoadingScence;
import HUD.Menu;
import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Menu menu=new Menu();
        LoadingScence loadingScence=new LoadingScence(menu.getPrimaryWindow());


        primaryStage=loadingScence.getLoadingStage();

        primaryStage.setResizable(false);
        primaryStage.show();





    }


    public static void main(String[] args) {
        launch(args);
    }
}
