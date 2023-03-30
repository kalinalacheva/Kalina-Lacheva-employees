import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.CountDownLatch;

public class SecondFrame extends JFrame {

    public static JPanel jPanel = new JPanel();

    SecondFrame() {
        this.setSize(500, 500);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());

        BoxLayout layout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(layout);
        this.add(jPanel);

        JLabel data_label = new JLabel("Table");
        jPanel.add(data_label);
        getContentPane().setPreferredSize(new Dimension(500, 500));
        this.setVisible(true);

        this.pack();

    }
    public void showTable(String[][] data, String[] columns){
        JTable jTable = new JTable(data,columns);
        jPanel.add(jTable);
        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.getViewport().add(jTable);
        jPanel.add(jScrollPane);
        setVisible(true);

    }
}
