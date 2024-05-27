package com.kidsbank.util;

import com.kidsbank.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class PageLoader {
    private Pane view;

    // 把指定的FXML文件装载到Pane里
    public Pane getPage(String fxmlFile){
        try {
            URL fileUrl = Main.class.getResource("view/" + fxmlFile);

            if (fileUrl == null){
                throw new java.io.FileNotFoundException("FXML file cannot be found");
            }
            view = new FXMLLoader().load(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    return view;
    }

}
