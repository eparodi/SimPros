package GraphicInterface;

import Interpreter.*;
import sun.plugin.dom.exception.InvalidStateException;
import tp2.Scheduler;
import tp2.TraceManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class SwingContainer {

    private String[][] data;
    private int index = 0;
    private String[] algorithms = {"FIFO","RR"};
    private final static JFileChooser fc = new JFileChooser();
    private JTextField quantum = new JTextField(2);
    private JPanel buttons = new JPanel(new FlowLayout());
    private DefaultTableModel tableModel;
    private JComboBox<String> algorithmsList;
    private TraceManager tManager;

    void createAndShowGUI() {
        //Create and set up the window.
        JFrame mainFrame = new JFrame("SimPros");
        mainFrame.setSize(600,300);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Welcome to SimPros, the process simulator!");
        mainFrame.getContentPane().add(label, BorderLayout.PAGE_START);

        tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane pane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JPanel tablePanel= new JPanel(new GridLayout());
        tablePanel.add(pane);
        mainFrame.getContentPane().add(tablePanel, BorderLayout.CENTER);

        Border tableBorder = BorderFactory.createLineBorder(Color.gray);
        tableBorder = BorderFactory.createTitledBorder(tableBorder, "GANTT");
        tablePanel.setBorder(tableBorder);

        mainFrame.getContentPane().add(buttons, BorderLayout.PAGE_END);

        buttons.add(addButton("Images/PreviousButton.png", e -> previous()));
        buttons.add(addButton("Images/PlayButton.png", e -> play()));
        buttons.add(addButton("Images/NextButton.png", e -> next()));

//        JPanel options = new JPanel(new FlowLayout());
//        mainFrame.getContentPane().add(options, BorderLayout.LINE_END);
        algorithmsList = new JComboBox<>(algorithms);
        algorithmsList.addActionListener(e -> setAlgorithms(algorithmsList));

        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> loadButton(loadButton));
        buttons.add(loadButton);
        buttons.add(algorithmsList);
        buttons.add(quantum);
        quantum.setEditable(false);
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

    private void loadButton(Component component){
        int returnVal = fc.showOpenDialog(component);
        if (returnVal == JFileChooser.APPROVE_OPTION){
            try {
                File f = fc.getSelectedFile();
                Scheduler scheduler;
                if (quantum.isEditable()){
                    scheduler = Interpreter.jsonToProcess(f.getAbsolutePath(), Integer.parseInt(quantum.getText()));
                }else{
                    scheduler = Interpreter.jsonToProcess(f.getAbsolutePath(), null);
                }
                tManager = new TraceManager();
                scheduler.run(tManager);
                tManager.setInfo(scheduler.getInfoMatrix());
                tManager.setGantt(scheduler.getInfoMatrix().size(), scheduler.getCores(), scheduler.getPositionsMap());
                data = transformMatrix(tManager.gBuilder.getGantt());
                tableModel.setColumnCount(0);
                tableModel.addColumn("Process", data[0]);
                tableModel.addColumn("KLT", data[1]);
                tableModel.addColumn("ULT", data[2]);
                index = 3;
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(component, "File format not recognized.");
            } catch (NumberFormatException e){
                JOptionPane.showMessageDialog(component, "Write a number please.");
            }
        }
    }

    private String[][] transformMatrix(String[][] matrix){
        String[][] transformed = new String[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix[0].length ; i++ ){
            for ( int k = 0 ; k < matrix.length ; k++ ){
                transformed[i][k] = matrix[k][i];
            }
        }

        return transformed;
    }

    private void setAlgorithms(JComboBox<String> component){
        String option = (String) component.getSelectedItem();
        if (option.equals("RR")){
            quantum.setEditable(true);
        }else{
            quantum.setEditable(false);
        }
    }

    private void next(){
        if ( data != null ){
            if ( index < data.length ) {
                tableModel.addColumn(Integer.toString(index-3), data[index]);
                tManager.printGanttLine(index - 3);
                index++;
            }
        }else{
            JOptionPane.showMessageDialog(fc, "Load a file please.");
        }
    }

    private void previous(){
        if ( data != null ){
            if ( index > 3 ){
                index--;
                tManager.printGanttLine(index - 3);
                tableModel.setColumnCount(index);
            }
        }else{
            JOptionPane.showMessageDialog(fc, "Load a file please.");
        }
    }

    private void play(){
        if ( data != null ){
            for (int i = index ; i < data.length ; i++ ){
                tableModel.addColumn(Integer.toString(i-3),data[i]);
            }
            index = data.length;
        }else{
            JOptionPane.showMessageDialog(fc, "Load a file please.");
        }
    }

}
