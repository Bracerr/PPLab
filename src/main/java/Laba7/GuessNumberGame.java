package Laba7;

import javax.swing.*;
import java.awt.*;

public class GuessNumberGame extends JFrame {

    private JTextField minField, maxField, guessField;
    private JButton guessButton;
    private JButton higherButton, lowerButton, correctButton;
    private JLabel infoLabel;
    private JRadioButton metalButton, nimbusButton, windowsButton, classicButton;

    private int min, max;
    private int currentGuess;
    private int lowerBound, upperBound;
    private boolean cheating = false;

    public GuessNumberGame() {
        setTitle("Игра в 'Больше-меньше'");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        initComponents();

        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Минимальное значение:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        minField = new JTextField(10);
        panel.add(minField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Максимальное значение:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        maxField = new JTextField(10);
        panel.add(maxField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Загаданное число:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        guessField = new JTextField(10);
        panel.add(guessField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        guessButton = new JButton("Попробовать угадать");
        guessButton.addActionListener(e -> startGuessing());
        panel.add(guessButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        higherButton = new JButton("Больше");
        higherButton.addActionListener(e -> adjustBounds("higher"));
        higherButton.setEnabled(false);
        panel.add(higherButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        lowerButton = new JButton("Меньше");
        lowerButton.addActionListener(e -> adjustBounds("lower"));
        lowerButton.setEnabled(false);
        panel.add(lowerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        correctButton = new JButton("Верно!");
        correctButton.addActionListener(e -> {
            infoLabel.setText("Правильно! Хотите сыграть еще раз?");
            disableGuessing();
        });
        correctButton.setEnabled(false);
        panel.add(correctButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        infoLabel = new JLabel("Введите диапазон и предположение");
        panel.add(infoLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        ButtonGroup lafGroup = new ButtonGroup();
        metalButton = new JRadioButton("Metal");
        nimbusButton = new JRadioButton("Nimbus");
        windowsButton = new JRadioButton("Windows");
        classicButton = new JRadioButton("Classic", true);

        metalButton.addActionListener(e -> changeLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel", new Color(255, 228, 181))); // PeachPuff
        nimbusButton.addActionListener(e -> changeLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel", new Color(240, 255, 240))); // Honeydew
        windowsButton.addActionListener(e -> changeLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel", new Color(230, 230, 250))); // Lavender
        classicButton.addActionListener(e -> changeLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName(), new Color(255, 250, 240))); // Ivory


        lafGroup.add(metalButton);
        lafGroup.add(nimbusButton);
        lafGroup.add(windowsButton);
        lafGroup.add(classicButton);

        panel.add(new JLabel("Выберите тему:"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(metalButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(nimbusButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        panel.add(windowsButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(classicButton, gbc);

        add(panel);
    }

    private void startGuessing() {
        try {
            min = Integer.parseInt(minField.getText());
            max = Integer.parseInt(maxField.getText());
            lowerBound = min;
            upperBound = max;
            currentGuess = (lowerBound + upperBound) / 2;

            infoLabel.setText("Это число " + currentGuess + "?");
            enableGuessing();
        } catch (NumberFormatException e) {
            infoLabel.setText("Введите корректные значения");
        }
    }

    private void adjustBounds(String adjustment) {
        if (adjustment.equals("higher")) {
            lowerBound = currentGuess + 1;
        } else if (adjustment.equals("lower")) {
            upperBound = currentGuess - 1;
        }
        if (lowerBound <= upperBound) {
            currentGuess = (lowerBound + upperBound) / 2;
            infoLabel.setText("Это число " + currentGuess + "?");
        } else {
            infoLabel.setText("Жульничество! Загаданное число вне диапазона");
            cheating = true;
            disableGuessing();
        }
    }

    private void enableGuessing() {
        guessField.setEnabled(false);
        guessButton.setEnabled(false);
        higherButton.setEnabled(true);
        lowerButton.setEnabled(true);
        correctButton.setEnabled(true);
    }

    private void disableGuessing() {
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
        higherButton.setEnabled(false);
        lowerButton.setEnabled(false);
        correctButton.setEnabled(false);
    }

    private void changeLookAndFeel(String lafClassName, Color bgColor) {
        try {
            UIManager.setLookAndFeel(lafClassName);
            getContentPane().setBackground(bgColor);
            System.out.println(bgColor);
            SwingUtilities.updateComponentTreeUI(this); // Обновляем UI

            updateComponentTreeUI(getContentPane(), bgColor);

            pack();
            setSize(1920, 1080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateComponentTreeUI(Component component, Color bgColor) {
        if (component instanceof Container) {
            Container container = (Container) component;
            for (Component comp : container.getComponents()) {
                if (!(comp instanceof JTextField) && !(comp instanceof JButton)) {
                    comp.setBackground(bgColor);
                }
                updateComponentTreeUI(comp, bgColor);
            }
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuessNumberGame());
    }
}
