import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.*;

public class Controller {

    @FXML
    GridPane A, B, C, D, E, F, G, H, I;
    @FXML
    JFXTextField A1, A2, A3, A4, A5, A6, A7, A8, A9;
    @FXML
    JFXTextField B1, B2, B3, B4, B5, B6, B7, B8, B9;
    @FXML
    JFXTextField C1, C2, C3, C4, C5, C6, C7, C8, C9;
    @FXML
    JFXTextField D1, D2, D3, D4, D5, D6, D7, D8, D9;
    @FXML
    JFXTextField E1, E2, E3, E4, E5, E6, E7, E8, E9;
    @FXML
    JFXTextField F1, F2, F3, F4, F5, F6, F7, F8, F9;
    @FXML
    JFXTextField G1, G2, G3, G4, G5, G6, G7, G8, G9;
    @FXML
    JFXTextField H1, H2, H3, H4, H5, H6, H7, H8, H9;
    @FXML
    JFXTextField I1, I2, I3, I4, I5, I6, I7, I8, I9;
    @FXML
    JFXTextField info;
    @FXML
    JFXButton generate, check, number1, number2, number3, number4, number5, number6, number7, number8, number9;

    private List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    private List<JFXTextField> tile1 = new ArrayList<>();
    private List<JFXTextField> tile2 = new ArrayList<>();
    private List<JFXTextField> tile3 = new ArrayList<>();
    private List<JFXTextField> tile4 = new ArrayList<>();
    private List<JFXTextField> tile5 = new ArrayList<>();
    private List<JFXTextField> tile6 = new ArrayList<>();
    private List<JFXTextField> tile7 = new ArrayList<>();
    private List<JFXTextField> tile8 = new ArrayList<>();
    private List<JFXTextField> tile9 = new ArrayList<>();
    private List<JFXTextField> fields = new ArrayList<>();
    private JFXTextField[][] array = new JFXTextField[9][9];
    private String[][] filledArray = new String[9][9];

    private int level = 35;

    @FXML
    void onClick() {
        info.setVisible(false);
        clearSudoku();
        defineArray();
        defineTiles();
        createFieldsList();
        setDefaultNames();
        setColoringActionsForFields();
        setNumberHighlighting();
        fillSudoku();
        fillStringArray();
        removeText(level);
        disableFilled();
    }

    // set difficulty
    @FXML
    void setEasy() {
        level = 35;
        onClick();
    }

    @FXML
    void setNormal() {
        level = 45;
        onClick();
    }

    @FXML
    void setHard() {
        level = 55;
        onClick();
    }

    // define lists, arrays, tiles
    private void defineArray() {
        array = new JFXTextField[][]{
                {A1, A2, A3, A4, A5, A6, A7, A8, A9},
                {B1, B2, B3, B4, B5, B6, B7, B8, B9},
                {C1, C2, C3, C4, C5, C6, C7, C8, C9},
                {D1, D2, D3, D4, D5, D6, D7, D8, D9},
                {E1, E2, E3, E4, E5, E6, E7, E8, E9},
                {F1, F2, F3, F4, F5, F6, F7, F8, F9},
                {G1, G2, G3, G4, G5, G6, G7, G8, G9},
                {H1, H2, H3, H4, H5, H6, H7, H8, H9},
                {I1, I2, I3, I4, I5, I6, I7, I8, I9}
        };
    }

    private void defineTiles() {
        tile1 = Arrays.asList(A1, A2, A3, B1, B2, B3, C1, C2, C3);
        tile2 = Arrays.asList(A4, A5, A6, B4, B5, B6, C4, C5, C6);
        tile3 = Arrays.asList(A7, A8, A9, B7, B8, B9, C7, C8, C9);
        tile4 = Arrays.asList(D1, D2, D3, E1, E2, E3, F1, F2, F3);
        tile5 = Arrays.asList(D4, D5, D6, E4, E5, E6, F4, F5, F6);
        tile6 = Arrays.asList(D7, D8, D9, E7, E8, E9, F7, F8, F9);
        tile7 = Arrays.asList(G1, G2, G3, H1, H2, H3, I1, I2, I3);
        tile8 = Arrays.asList(G4, G5, G6, H4, H5, H6, I4, I5, I6);
        tile9 = Arrays.asList(G7, G8, G9, H7, H8, H9, I7, I8, I9);

    }

    private void createFieldsList() {
        fields.addAll(tile1);
        fields.addAll(tile2);
        fields.addAll(tile3);
        fields.addAll(tile4);
        fields.addAll(tile5);
        fields.addAll(tile6);
        fields.addAll(tile7);
        fields.addAll(tile8);
        fields.addAll(tile9);
    }

    // Sudoku
    private void fillSudoku() {
        fillSection(0);
        fillSection(3);
        fillSection(6);
    }

    private void fillSection(int i) {
        while (fillRows(i, i + 3) > 9) {
            fillRows(i, i + 3);
        }
    }

    private int fillRows(int one, int two) {
        int count = 0;
        for (int i = one; i < two; i++) {
            List<Integer> nums = new ArrayList<>(numbers);
            for (int j = 0; j < array.length; j++) {
                Set<JFXTextField> forbidden = getForbidden(array[i][j]);
                forbidden.remove(array[i][j]);
                int index = generateNum(nums.size());
                while (reduceListToString(forbidden).contains(String.valueOf(nums.get(index)))) {
                    index = generateNum(nums.size());
                    count++;
                    if (count > 9) {
                        return count;
                    }
                }
                count = 0;
                array[i][j].setText(String.valueOf(nums.get(index)));
                nums.remove(index);
            }
        }
        return count;
    }

    private void clearSudoku() {
        for (JFXTextField field : fields) {
            field.setText("");
            field.setEditable(true);
            field.getStyleClass().remove("initial");
            field.getStyleClass().remove("wrong");
        }
        List<JFXButton> numberFields = Arrays.asList(number1, number2, number3, number4,
                number5, number6, number7, number8, number9);
        for (JFXButton button : numberFields) {
            button.getStyleClass().remove("highlighted-number");
        }
    }

    @FXML
    void verifySudoku() {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                array[i][j].getStyleClass().remove("wrong");
                if (!array[i][j].getText().equals("") && !filledArray[i][j].equals(array[i][j].getText())) {
                    array[i][j].getStyleClass().add("wrong");
                    count++;
                }
            }
        }
        if (count > 0) {
            info.setText("Incorrect");
        } else {
            info.setText("Congratulations!!");
        }
        info.setVisible(true);
    }

    private void setDefaultNames() {
        A1.setPromptText("A1");
        A2.setPromptText("A2");
        A3.setPromptText("A3");
        A4.setPromptText("A4");
        A5.setPromptText("A5");
        A6.setPromptText("A6");
        A7.setPromptText("A7");
        A8.setPromptText("A8");
        A9.setPromptText("A9");
        B1.setPromptText("B1");
        B2.setPromptText("B2");
        B3.setPromptText("B3");
        B4.setPromptText("B4");
        B5.setPromptText("B5");
        B6.setPromptText("B6");
        B7.setPromptText("B7");
        B8.setPromptText("B8");
        B9.setPromptText("B9");
        C1.setPromptText("C1");
        C2.setPromptText("C2");
        C3.setPromptText("C3");
        C4.setPromptText("C4");
        C5.setPromptText("C5");
        C6.setPromptText("C6");
        C7.setPromptText("C7");
        C8.setPromptText("C8");
        C9.setPromptText("C9");
        D1.setPromptText("D1");
        D2.setPromptText("D2");
        D3.setPromptText("D3");
        D4.setPromptText("D4");
        D5.setPromptText("D5");
        D6.setPromptText("D6");
        D7.setPromptText("D7");
        D8.setPromptText("D8");
        D9.setPromptText("D9");
        E1.setPromptText("E1");
        E2.setPromptText("E2");
        E3.setPromptText("E3");
        E4.setPromptText("E4");
        E5.setPromptText("E5");
        E6.setPromptText("E6");
        E7.setPromptText("E7");
        E8.setPromptText("E8");
        E9.setPromptText("E9");
        F1.setPromptText("F1");
        F2.setPromptText("F2");
        F3.setPromptText("F3");
        F4.setPromptText("F4");
        F5.setPromptText("F5");
        F6.setPromptText("F6");
        F7.setPromptText("F7");
        F8.setPromptText("F8");
        F9.setPromptText("F9");
        G1.setPromptText("G1");
        G2.setPromptText("G2");
        G3.setPromptText("G3");
        G4.setPromptText("G4");
        G5.setPromptText("G5");
        G6.setPromptText("G6");
        G7.setPromptText("G7");
        G8.setPromptText("G8");
        G9.setPromptText("G9");
        H1.setPromptText("H1");
        H2.setPromptText("H2");
        H3.setPromptText("H3");
        H4.setPromptText("H4");
        H5.setPromptText("H5");
        H6.setPromptText("H6");
        H7.setPromptText("H7");
        H8.setPromptText("H8");
        H9.setPromptText("H9");
        I1.setPromptText("I1");
        I2.setPromptText("I2");
        I3.setPromptText("I3");
        I4.setPromptText("I4");
        I5.setPromptText("I5");
        I6.setPromptText("I6");
        I7.setPromptText("I7");
        I8.setPromptText("I8");
        I9.setPromptText("I9");
    }

    private void fillStringArray() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                filledArray[i][j] = array[i][j].getText();
            }
        }
    }

    private Set<JFXTextField> chooseFieldsToRemoveText(int number) {
        Set<JFXTextField> set = new HashSet<>();
        while (set.size() < number) {
            set.add(fields.get(generateNum(fields.size())));
        }
        return set;
    }

    private void removeText(int number) {
        Set<JFXTextField> set = chooseFieldsToRemoveText(number);
        for (JFXTextField field : set) {
            field.setText("");
        }
    }

    private void disableFilled() {
        for (JFXTextField[] jfxTextFields : array) {
            for (int j = 0; j < array.length; j++) {
                if (!jfxTextFields[j].getText().equals("")) {
                    jfxTextFields[j].setEditable(false);
                    jfxTextFields[j].getStyleClass().add("initial");
                }
            }
        }
    }

    // forbidden fields
    private Set<JFXTextField> getForbidden(JFXTextField field) {
        Set<JFXTextField> forbidden = new HashSet<>();
        for (JFXTextField[] jfxTextFields : array) {
            for (int j = 0; j < array.length; j++) {
                if (jfxTextFields[j].getPromptText().contains(field.getPromptText().substring(0, 1))) {
                    forbidden.add(jfxTextFields[j]);
                } else if (jfxTextFields[j].getPromptText().contains(field.getPromptText().substring(1))) {
                    forbidden.add(jfxTextFields[j]);
                }
            }
        }
        forbidden.addAll(checkTile(field));
        forbidden.remove(field);
        return forbidden;
    }

    private List<JFXTextField> checkTile(JFXTextField field) {
        List<JFXTextField> list = new ArrayList<>();
        String[] idArray = field.getPromptText().split("");
        int value = Integer.parseInt(idArray[1]);
        if ("ABC".contains(idArray[0]) && value >= 1 && value <= 3) {
            list.addAll(tile1);
        } else if ("ABC".contains(idArray[0]) && value >= 4 && value <= 6) {
            list.addAll(tile2);
        } else if ("ABC".contains(idArray[0]) && value >= 7 && value <= 9) {
            list.addAll(tile3);
        } else if ("DEF".contains(idArray[0]) && value >= 1 && value <= 3) {
            list.addAll(tile4);
        } else if ("DEF".contains(idArray[0]) && value >= 4 && value <= 6) {
            list.addAll(tile5);
        } else if ("DEF".contains(idArray[0]) && value >= 7 && value <= 9) {
            list.addAll(tile6);
        } else if ("GHI".contains(idArray[0]) && value >= 1 && value <= 3) {
            list.addAll(tile7);
        } else if ("GHI".contains(idArray[0]) && value >= 4 && value <= 6) {
            list.addAll(tile8);
        } else if ("GHI".contains(idArray[0]) && value >= 7 && value <= 9) {
            list.addAll(tile9);
        }
        return list;
    }

    private String reduceListToString(Set<JFXTextField> list) {
        StringBuilder sb = new StringBuilder();
        for (JFXTextField x : list) {
            sb.append(x.getText());
        }
        return sb.toString();
    }

    // numbers generation
    private int generateNum(int bound) {
        return new Random().nextInt(bound);
    }

    // mouse events
    private void onField(javafx.scene.input.MouseEvent e) {
        Set<JFXTextField> list = getForbidden((JFXTextField) e.getSource());
        for (JFXTextField field : list) {
            field.getStyleClass().add("highlighted");
        }
    }

    private void offField(javafx.scene.input.MouseEvent e) {
        for (JFXTextField field : fields) {
            field.getStyleClass().remove("highlighted");
            field.getStyleClass().remove("highlighted-number");
        }
    }

    private void setColoringActionsForFields() {
        for (JFXTextField field : fields) {
            field.setOnMouseEntered(this::onField);
            field.setOnMouseExited(this::offField);
            field.setOnMouseClicked(this::clickOnField);
        }
    }

    private void onNumber(javafx.scene.input.MouseEvent e) {
        String text = ((JFXButton) e.getSource()).getText();
        int i = 0;
        for (JFXTextField[] jfxTextFields : array) {
            for (int j = 0; j < array.length; j++) {
                if (jfxTextFields[j].getText().equals(text)) {
                    jfxTextFields[j].getStyleClass().add("highlighted-number");
                    i++;
                }
            }
        }
        if (i == 9) {
            ((JFXButton) e.getSource()).getStyleClass().add("highlighted-number");
        }
    }

    private void setNumberHighlighting() {
        number1.setOnMouseEntered(this::onNumber);
        number2.setOnMouseEntered(this::onNumber);
        number3.setOnMouseEntered(this::onNumber);
        number4.setOnMouseEntered(this::onNumber);
        number5.setOnMouseEntered(this::onNumber);
        number6.setOnMouseEntered(this::onNumber);
        number7.setOnMouseEntered(this::onNumber);
        number8.setOnMouseEntered(this::onNumber);
        number9.setOnMouseEntered(this::onNumber);
        number1.setOnMouseExited(this::offField);
        number2.setOnMouseExited(this::offField);
        number3.setOnMouseExited(this::offField);
        number4.setOnMouseExited(this::offField);
        number5.setOnMouseExited(this::offField);
        number6.setOnMouseExited(this::offField);
        number7.setOnMouseExited(this::offField);
        number8.setOnMouseExited(this::offField);
        number9.setOnMouseExited(this::offField);
    }

    private void clickOnField(javafx.scene.input.MouseEvent e) {
        info.setVisible(false);
    }
}
