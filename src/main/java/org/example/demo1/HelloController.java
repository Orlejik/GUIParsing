package org.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Circle;

import java.io.IOException;
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

    private double progress;
    private List<TextField> inputs = new ArrayList<>();


    @FXML
    protected void proceedBtnAction() throws InterruptedException, IOException {

        int diff = Math.abs(Integer.parseInt(startInput.getText()) - Integer.parseInt(endInput.getText()));
        double progress_value = (double) 1 / diff;
        String goodLinksPath = good_links_file_path.getText();
        String allLinksPath = all_links_file_path.getText();
        String badLinksPath = bad_links_file_path.getText();
        String parsedDataPath = bad_links_file_path.getText();

        if (Integer.parseInt(startInput.getText()) > Integer.parseInt(endInput.getText())) {

            for (int i = Integer.parseInt(endInput.getText()); i <= Integer.parseInt(startInput.getText()); i++) {
                Thread thread = new Thread();
                parseWebPage("https://999.md/ro/" + i, allLinksPath, goodLinksPath, badLinksPath, parsedDataPath);
                thread.join(progressSet(progress_value));
                Thread.sleep(1000);
            }
        } else {
            proceedBtn.setDisable(false);
            for (int i = Integer.parseInt(startInput.getText()); i <= Integer.parseInt(endInput.getText()); i++) {
                Thread thread = new Thread();
                parseWebPage("https://999.md/ro/" + i, allLinksPath, goodLinksPath, badLinksPath, parsedDataPath);
                thread.join(progressSet(progress_value));
                Thread.sleep(1000);
            }
        }
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
        links_amount.setText(String.valueOf(returnDiff()));
    }


}