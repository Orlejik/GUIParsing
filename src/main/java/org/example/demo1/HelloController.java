package org.example.demo1;

import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.example.demo1.DBConnection.Connector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.example.demo1.FileCreators.ChooseTheFile.chooseFile;
import static org.example.demo1.Parsing.WebPageParsing.parseWebPage;
import static org.example.demo1.Progress.ProgressBarThread.progressSet;
import static org.example.demo1.Validation.InputsValidation.validateInput;

public class HelloController {
    @FXML
    private Label startErrorLabel;
    @FXML
    private Label endErrorLabel;
    @FXML
    private TextField startInput;
    @FXML
    private TextField endInput;
    @FXML
    private Button proceedBtn;
    @FXML
    private ProgressBar progressBarObject;
    @FXML
    private TextField all_links_file_path;
    @FXML
    private TextField good_links_file_path;
    @FXML
    private TextField bad_links_file_path;
    @FXML
    private TextField parsed_data_file_path;
    @FXML
    private Circle linksall_file_isfilled;
    @FXML
    private Circle linksgood_file_isfilled;
    @FXML
    private Circle linksbad_file_isfilled;
    @FXML
    private Circle parseddata_file_isfilled;
    @FXML
    private Label links_amount;
    @FXML
    private ToggleGroup StoreIn;
    @FXML
    private RadioButton localPane;
    @FXML
    private Pane localStore;
    @FXML
    private Pane dbStore;
    @FXML
    private Button bd_button;
    @FXML
    private ToggleGroup DBName;
    @FXML
    private RadioButton mysqlBD;
    @FXML
    private RadioButton postgresqlDB;
    @FXML
    private RadioButton msslqBD;
    private static String dbDriver;
    private static String classForName;
    @FXML
    private Pane mysqlPane;
    @FXML
    private Pane postgrePane;
    @FXML
    private Pane mssqlPane;
    private List<TextField> inputs = new ArrayList<>();
    double progress = 0.0;

    @FXML
    protected void proceedLocalFilesBtnAction() throws InterruptedException, IOException {

        int diff = Math.abs(Integer.parseInt(startInput.getText()) - Integer.parseInt(endInput.getText()));
        double progress_value = (double) 1 / diff;
//        String goodLinksPath = good_links_file_path.getText();
//        String allLinksPath = all_links_file_path.getText();
//        String badLinksPath = bad_links_file_path.getText();
//        String parsedDataPath = bad_links_file_path.getText();
        String goodLinksPath = "C:\\Users\\artiom.oriol\\Documents\\test\\GoodLinks.txt";
        String allLinksPath = "C:\\Users\\artiom.oriol\\Documents\\test\\AllLinks.txt";
        String badLinksPath = "C:\\Users\\artiom.oriol\\Documents\\test\\BadLinks.txt";
        String parsedDataPath = "C:\\Users\\artiom.oriol\\Documents\\test\\ParsedData.json";

        if (Integer.parseInt(startInput.getText()) > Integer.parseInt(endInput.getText())) {
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {
                    for (int i = Integer.parseInt(endInput.getText()); i <= Integer.parseInt(startInput.getText()); i++) {
                        parseWebPage("https://999.md/ro/" + i + "\n", allLinksPath, goodLinksPath, badLinksPath, parsedDataPath);
                        progress += progress_value;
                        Thread.sleep(1000);

                        if (isCancelled()) {
                            break;
                        }
                        progressBarObject.setProgress(progress);
                    }
                    return null;
                }
            };
            new Thread(task).start();

        } else {
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {
                    for (int i = Integer.parseInt(startInput.getText()); i <= Integer.parseInt(endInput.getText()); i++) {
                        parseWebPage("https://999.md/ro/" + i + "\n", allLinksPath, goodLinksPath, badLinksPath, parsedDataPath);
                        progress += progress_value;
                        Thread.sleep(1000);

                        if (isCancelled()) {
                            break;
                        }
                        progressBarObject.setProgress(progress);
                    }
                    return null;
                }
            };

            new Thread(task).start();

        }
    }

    @FXML
    protected void proceedBDBtnAction() {
    }

    private int returnDiff() {
        int startInputValue = Integer.parseInt(startInput.getText());
        int endInputValue = Integer.parseInt(endInput.getText());

        return Math.abs(startInputValue - endInputValue);
    }

    @FXML
    protected void getPathForAllLinks() {
        if (all_links_file_path.getLength() < 1) {
            chooseFile(all_links_file_path, "AllLinks", "txt", linksall_file_isfilled);
            inputs.add(all_links_file_path);
        } else {
            return;
        }
    }

    @FXML
    protected void getPathForGoodLinks() {
        if (good_links_file_path.getLength() < 1) {
            chooseFile(good_links_file_path, "GoodLinks", "txt", linksgood_file_isfilled);
            inputs.add(good_links_file_path);
        } else {
            return;
        }
    }

    @FXML
    protected void getPathForBadLinks() {
        if (bad_links_file_path.getLength() < 1) {
            chooseFile(bad_links_file_path, "BadLinks", "txt", linksbad_file_isfilled);
            inputs.add(bad_links_file_path);
        } else {
            return;
        }
    }

    @FXML
    protected void getPathForParsedData() {
        if (parsed_data_file_path.getLength() < 1) {
            chooseFile(parsed_data_file_path, "ParsedData", "json", parseddata_file_isfilled);
            inputs.add(parsed_data_file_path);
        } else {
            return;
        }
    }

    @FXML
    protected void validateStart() {
        validateInput(startInput, startErrorLabel, proceedBtn, inputs);
    }

    @FXML
    protected void validateEnd() {
        validateInput(endInput, endErrorLabel, proceedBtn, inputs);
    }

    @FXML
    protected void showDifference() {
        links_amount.setText(String.valueOf(returnDiff()));
    }

    @FXML
    protected void showSelected() {
        RadioButton selectedRadioButton = (RadioButton) StoreIn.getSelectedToggle();

        if (selectedRadioButton.equals(localPane)) {
            localStore.setVisible(true);
            proceedBtn.setVisible(true);
            dbStore.setVisible(false);
            bd_button.setVisible(false);
        } else {
            localStore.setVisible(false);
            proceedBtn.setVisible(false);
            dbStore.setVisible(true);
            bd_button.setVisible(true);
        }
        System.out.println(selectedRadioButton.getText());
    }

    @FXML
    protected RadioButton selectDBDriver() {
        RadioButton selectDbDriver = (RadioButton) DBName.getSelectedToggle();
        if (selectDbDriver.equals(mysqlBD)) {
            mysqlPane.setVisible(true);
            postgrePane.setVisible(false);
            mssqlPane.setVisible(false);
        } else if (selectDbDriver.equals(postgresqlDB)) {
            mysqlPane.setVisible(false);
            postgrePane.setVisible(true);
            mssqlPane.setVisible(false);
        } else if (selectDbDriver.equals(msslqBD)) {
            mysqlPane.setVisible(false);
            postgrePane.setVisible(false);
            mssqlPane.setVisible(true);
        } else {
            throw new RuntimeException("No such driver!");
        }
        return selectDbDriver;
    }

    private String setDefaultValue(TextField textField, String defaultValue) {
        if (textField.getText().isEmpty()) {
            return defaultValue;
        }
        return textField.getText();
    }

    @FXML
    private Button testMysqlBtn;
    @FXML
    private Circle connectionMysqlStatus;
    @FXML
    private Button testpostGREBtn;
    @FXML
    private Circle connectionpostGREStatus;
    @FXML
    private Button testmssqlBtn;
    @FXML
    private Circle connectionmssqlStatus;

    private void setConnection(String classF, String dbdriver, String bdAddress, String dbPort, String dbName, String username, String Password, Button button, Circle circle) throws SQLException, InterruptedException {
        Connector connector = new Connector(classF, dbdriver, bdAddress, dbPort, dbName, username, Password);
        System.out.println(connector.url());
        try (Connection connection = connector.connect(circle, button)) {

            if (connection == null) {
                System.out.println("Connection was not established");
                System.out.printf("username : %s", connector.getUsername());
                System.out.printf("username : %s", connector.getPassword());
                circle.setFill(Color.RED);
                button.setText("NO!");
                Thread.sleep(5000);
                button.setText("Test");
            } else {
                System.out.printf("Connected to DB : %s", connector.getDbName());
                System.out.printf("username : %s", connector.getUsername());
                System.out.printf("username : %s", connector.getPassword());
                System.out.printf("Connected to DB : %s", connector.getDbName());
                circle.setFill(Color.GREEN);
                button.setText("YES");
                Thread.sleep(5000);
                button.setText("Test");
            }
        }
    }

    @FXML
    private TextField mysqlAddress;
    @FXML
    private PasswordField mysqlPasswordPF;
    @FXML
    private TextField mysqlUsernameTF;
    @FXML
    private TextField mysqlPort;
    @FXML
    private TextField mysqlName;
    @FXML
    private TextField mssqlAddress;
    @FXML
    private TextField mssqlPort;
    @FXML
    private TextField mssqlName;
    @FXML
    private TextField mssqlusernameTF;
    @FXML
    private TextField mssqlpasswordPF;
    @FXML
    private TextField postGREAddress;
    @FXML
    private TextField postGREPort;
    @FXML
    private TextField postGREName;
    @FXML
    private TextField postGREusernameTF;
    @FXML
    private TextField postGREpasswordPF;

    @FXML
    protected void checkConnectionToDB() throws SQLException, InterruptedException {
        RadioButton selectDbDriverBtn = selectDBDriver();
        if (selectDbDriverBtn.equals(mysqlBD)) {
            classForName = "com.mysql.cj.jdbc.Driver";
            dbDriver = "jdbc:mysql://";
//    private Button testMysqlBtn;
//    private Circle connectionMysqlStatus;
            setConnection(classForName,
                    dbDriver,
                    setDefaultValue(mysqlAddress, "localhost"),
                    setDefaultValue(mysqlPort, "3306"),
                    setDefaultValue(mysqlName, "parsedData"),
                    mysqlUsernameTF.getText(),
                    mysqlPasswordPF.getText(),
                    testMysqlBtn,
                    connectionMysqlStatus);
        } else if (selectDbDriverBtn.equals(postgresqlDB)) {
            classForName = "org.postgresql.Driver";
            dbDriver = "jdbc:postgresql://";
//    private Button testpostGREBtn;
//    private Circle connectionpostGREStatus;
            setConnection(classForName,
                    dbDriver,
                    setDefaultValue(postGREAddress, "localhost"),
                    setDefaultValue(postGREPort, "5432"),
                    setDefaultValue(postGREName, "parsedData"),
                    postGREusernameTF.getText(),
                    postGREpasswordPF.getText(),
                    testpostGREBtn,
                    connectionpostGREStatus);
        } else if (selectDbDriverBtn.equals(msslqBD)) {
            classForName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            dbDriver = "jdbc:sqlserver://";
//    private Button testmssqlBtn
//    private Circle connectionmssqlStatus
            setConnection(classForName,
                    dbDriver,
                    setDefaultValue(mssqlAddress, "localhost"),
                    setDefaultValue(mssqlPort, "1434"),
                    setDefaultValue(mssqlName, "parsedData"),
                    mssqlusernameTF.getText(),
                    mssqlpasswordPF.getText(),
                    testmssqlBtn,
                    connectionmssqlStatus);
        } else {
            throw new RuntimeException("No such driver!");
        }
    }
}