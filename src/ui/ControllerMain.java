package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ControllerMain implements Initializable {
    @FXML
    private TextField s;

    @FXML
    private TextField word;

    @FXML
    private TextField terminals;

    @FXML
    private TextField nonterminals;

    @FXML
    private TextArea tall;

    @FXML
    private VBox vert;

    private Controller c;

    private final String[][] res;


    public ControllerMain(String[][] res, Controller c) {
        this.res = res;
        this.c = c;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @FXML
    void doCYK() {
        try {
            String inital = s.getText();
            String w = word.getText();
            String[] terArr = terminals.getText().split(" ");
            ArrayList<String> ter = new ArrayList<>(Arrays.asList(terArr));
            String[] nonterArr = nonterminals.getText().split(" ");
            ArrayList<String> nonter = new ArrayList<>(Arrays.asList(nonterArr));
            File f = new File("src/savedata/file.txt");
            f.createNewFile();
            FileWriter fw = new FileWriter(f.getAbsolutePath());
            fw.write(tall.getText());
            fw.close();
            c = new Controller(f.getAbsolutePath(), w, inital, ter, nonter);
            String[][] res = c.doit();
            clean();
            show(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clean() {
        s.clear();
        word.clear();
        terminals.clear();
        nonterminals.clear();
        tall.clear();
    }

    private void show(String[][] res) {
        try {
            FXMLLoader fl = new FXMLLoader(getClass().getResource("res.fxml"));
            fl.setController(new ControllerMain(res, c));
            Parent p = fl.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(p));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnusedAssignment")
    private void show1(String[][] res) {
        int i;
        HBox newhb = new HBox();
        for (i = 0; i < res.length; i++) {
            newhb = new HBox();
            newhb.setMaxHeight(20.0);
            for (int j = 0; j < res[i].length; j++) {
                Label l = new Label(res[i][j]);
                l.setMinWidth(45.0);
                l.setAlignment(Pos.CENTER);
                l.setFont(Font.font(17));
                newhb.getChildren().add(l);
            }
            vert.getChildren().add(newhb);
        }
        Label l;
        newhb = new HBox();
        if (res[i-1][0].contains(c.getInitS())) {
            l = new Label("(w) if it belongs to grammar (G)");
        } else {
            l = new Label("(w) does not belong to grammar (G)");
        }
        l.setMinWidth(35.0);
        l.setAlignment(Pos.CENTER);
        l.setFont(Font.font(17));
        newhb.getChildren().add(l);
        vert.getChildren().add(newhb);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (vert != null) {
            show1(res);
        }
    }
}
