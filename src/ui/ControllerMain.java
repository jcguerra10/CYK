package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    void doCYK(ActionEvent event) {
        try {
            String inital = s.getText();
            String w = word.getText();
            String[] terArr = terminals.getText().split(" ");
            ArrayList<String> ter = new ArrayList<>();
            ter.addAll(Arrays.asList(terArr));
            String[] nonterArr = nonterminals.getText().split(" ");
            ArrayList<String> nonter = new ArrayList<>();
            nonter.addAll(Arrays.asList(nonterArr));
            File f = new File("src/savedata/file.txt");
            f.createNewFile();
            FileWriter fw = new FileWriter(f.getAbsolutePath());
            fw.write(tall.getText());
            fw.close();
            Controller c = new Controller(f.getAbsolutePath(), w, inital, ter, nonter);
            String[][] res = c.doit();
            show(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void show(String[][] res) {
        int i = 0;
        for (i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++){
                System.out.print(" ||"+res[i][j] + "|| ");
            }
            System.out.println();
        }
        res[i-1][0] = "B S";
        if (res[i-1][0].contains("S")) {
            System.out.println("(w) if it belongs to grammar (G)");
        } else {
            System.out.println("(w) does not belong to grammar (G)");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
