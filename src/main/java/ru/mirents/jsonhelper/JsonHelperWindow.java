package ru.mirents.jsonhelper;

import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonHelperWindow extends JFrame implements ActionListener {
    private final JSplitPane mainPane;
    /**
     * Исходный текст в формате Json.
     */
    private JEditorPane inputJsonText = new JEditorPane();
    /**
     * Текст запроса выборки из исходного Json-файла.
     */
    private JTextField queryText = new JTextField("", 1);
    /**
     * Результат выборки по запросу.
     */
    private JEditorPane resultText = new JEditorPane();
    /**
     * Рабочая панель для запроса и результата.
     */
    JPanel outputPanel = new JPanel();
    /**
     * Рабочая панель для запроса и результата.
     */
    JPanel inputPanel = new JPanel();
    
    private JButton prettyflyJsonButton;
    private static final String TYPE_LABEL = "Тип данных результата: ";
    private JLabel labelType = new JLabel(TYPE_LABEL);
    
    JsonHelperWindow() {
        super("JSON Helper");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        prettyflyJsonButton = new JButton("Prettyfly Json");
        prettyflyJsonButton.addActionListener(this);
        queryText.addActionListener(this);

        outputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        outputPanel.setLayout(new BorderLayout());
        Box box1 = Box.createHorizontalBox();
        box1.add(new JLabel("Запрос: "));
        box1.add(new JScrollPane(queryText));
        outputPanel.add(box1, BorderLayout.NORTH);
        
        Box box2 = Box.createHorizontalBox();
        box2.add(new JLabel("Результат запроса"));
        Box box3 = Box.createVerticalBox();
        box3.add(box2);
        box3.add(new JScrollPane(resultText));
        outputPanel.add(box3, BorderLayout.CENTER);
        outputPanel.add(labelType, BorderLayout.SOUTH);

        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(new JScrollPane(inputJsonText));
        inputPanel.add(prettyflyJsonButton);
        mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, outputPanel);
        mainPane.setOneTouchExpandable(true);
        mainPane.setDividerLocation(0.5);

        this.add(mainPane);
        this.setPreferredSize(new Dimension(800, 600));
        this.setResizable(true);
        this.setVisible(true);
        this.pack();
        restoreDefaults();
    }
    
    private void restoreDefaults() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainPane.setDividerLocation((int)(mainPane.getSize().width / 3) * 2);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(prettyflyJsonButton)) {
            if (!inputJsonText.getText().isEmpty()) {
                try {
                    JsonPath jsonPath = new JsonPath(inputJsonText.getText());
                    inputJsonText.setText(jsonPath.prettyPrint());
                } catch(JsonPathException e) {
                    JOptionPane.showMessageDialog(JsonHelperWindow.this, 
                               "Ошибка преобразования:\n" + e.getLocalizedMessage());
                }
            }
        } else if (actionEvent.getSource().equals(queryText)) {
            try {
                JsonPath jsonPath = new JsonPath(inputJsonText.getText());
                Object object = jsonPath.get(queryText.getText());
                if (object != null) {
                    resultText.setText(object.toString());
                    labelType.setText(TYPE_LABEL + "'" + object.getClass().getName() + "'");
                } else {
                    JOptionPane.showMessageDialog(JsonHelperWindow.this, 
                        "Ошибка получения значения:\n" +
                        "результат получения значения равен null");
                }
            } catch(Exception e) {
                labelType.setText(TYPE_LABEL);
                resultText.setText(e.getMessage());
                JOptionPane.showMessageDialog(JsonHelperWindow.this, 
                               "Ошибка получения значения:\n" + e.toString());
            }
        }
    }
}
