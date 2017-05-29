package GraphicInterface;

import Interpreter.Interpreter;
import tp2.Scheduler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

class SwingContainer {

    private String[] columns = {};
    private String[][] data = {};
    private String[] algorithms = {"FIFO","HRRN"};
    private final static JFileChooser fc = new JFileChooser();

    void createAndShowGUI() {
        //Create and set up the window.
        JFrame mainFrame = new JFrame("SimPros");
        mainFrame.setSize(600,300);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to SimPros, the process simulator!");
        mainFrame.getContentPane().add(label, BorderLayout.PAGE_START);

        JTable table = new JTable(data, columns);
        JPanel tablePanel= new JPanel(new GridLayout());
        tablePanel.add(table);
        mainFrame.getContentPane().add(tablePanel, BorderLayout.CENTER);

        Border tableborder = BorderFactory.createLineBorder(Color.gray);
        tableborder = BorderFactory.createTitledBorder(tableborder, "GANTT");
        tablePanel.setBorder(tableborder);

        JPanel buttons = new JPanel(new FlowLayout());
        mainFrame.getContentPane().add(buttons, BorderLayout.PAGE_END);

        buttons.add(addButton("src/GraphicInterface/Images/NextButton.png", e -> {
            System.out.println("Hello");
        }));
        buttons.add(addButton("src/GraphicInterface/Images/PlayButton.png", e -> {
            System.out.println("Hello");
        }));
        buttons.add(addButton("src/GraphicInterface/Images/PreviousButton.png", e -> {
            System.out.println("Hello");
        }));

//        JPanel options = new JPanel(new FlowLayout());
//        mainFrame.getContentPane().add(options, BorderLayout.LINE_END);
        JComboBox<String> algorithmsList = new JComboBox<>(algorithms);

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadButton(loadButton));
        buttons.add(loadButton);
        buttons.add(algorithmsList);

        //Display the window.
        mainFrame.setVisible(true);
    }

    private JButton addButton(String directory, ActionListener runnable){
        ImageIcon icon = new ImageIcon(directory);
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        button.addActionListener(runnable);
        return button;
    }

    private static void loadButton(Component component){
        int returnVal = fc.showOpenDialog(component);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            try {
                File f = fc.getSelectedFile();
                Scheduler scheduler = Interpreter.jsonToProcess(f.getAbsolutePath());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(component, "File format not recognized.");
            }
        }
    }

}
