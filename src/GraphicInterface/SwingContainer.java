package GraphicInterface;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

class SwingContainer {

    private String[] columns = {};
    private String[][] data = {};
    private String[] algorithms = {"FIFO","HRRN"};

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

        buttons.add(addButton("src/GraphicInterface/Images/NextButton.png"));
        buttons.add(addButton("src/GraphicInterface/Images/PlayButton.png"));
        buttons.add(addButton("src/GraphicInterface/Images/PreviousButton.png"));

//        JPanel options = new JPanel(new FlowLayout());
//        mainFrame.getContentPane().add(options, BorderLayout.LINE_END);
        JComboBox<String> algorithmsList = new JComboBox<>(algorithms);

        buttons.add(new JButton("Load"));
        buttons.add(new JButton("Generate GANTT"));
        buttons.add(algorithmsList);

        //Display the window.
        mainFrame.setVisible(true);
    }

    private JButton addButton(String directory){
        ImageIcon icon = new ImageIcon(directory);
        JButton button = new JButton(icon);
        button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
        button.addActionListener(e -> System.out.print("Hola"));
        return button;
    }

}
