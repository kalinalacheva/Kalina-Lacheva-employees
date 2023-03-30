import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.CountDownLatch;

public class MyFrame extends JFrame implements ActionListener {

    JButton btn;
    File file_path;
    private CountDownLatch latch;

    MyFrame() {
        this.setSize(500, 500);
        latch = new CountDownLatch(1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());

        btn= new JButton("Upload file");
        btn.setFocusable(false);

        btn.addActionListener(this);
        this.add(btn);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btn){
            JFileChooser file_upload = new JFileChooser();
            int res = file_upload.showOpenDialog(null);

            if(res == JFileChooser.APPROVE_OPTION){
                this.file_path = new File(file_upload.getSelectedFile().getAbsolutePath());
                latch.countDown();
                dispose();
            }
        }
    }
    public String getPath(){
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.valueOf(this.file_path).replace("\\","\\\\");
    }
}
