import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenueTable extends JPanel implements ActionListener {
    private JButton menuebtn;
    public MenueTable(){
        setFocusable(true);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        menuebtn = new JButton(new ImageIcon("green1.gif"));
        menuebtn.setToolTipText("選單");
        menuebtn.addActionListener(this);
        menuebtn.setBounds(700,5, 10, 10);
        add(menuebtn);
    }
    public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == menuebtn ){
            //跳出選單視窗
                System.out.println("Menue");
        }
     }

}
