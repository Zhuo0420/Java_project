import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class backpage extends JPanel implements ActionListener{
    private JButton item1,item2;
    public backpage(){
        setFocusable(true);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBackground(Color.GRAY);
        item1 = new JButton(new ImageIcon("green1.gif"));
        item1.setToolTipText("道具一");
        item1.addActionListener(this);
        item1.setBounds(700,500, 10, 10);
        add(item1);

        item2 = new JButton(new ImageIcon("green1.gif"));
        item2.setToolTipText("道具二");
        item2.addActionListener(this);
        item2.setBounds(600,500, 10, 10);
        add(item2);

    }
    public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == item1 ){
            //可以使用道具1，按完後可以選擇使用的地方，使0變1
                
        }
        else if ( evt.getSource() == item2 ){
            //可以使用道具1，按完後可以選擇使用的地方，使1變0
                
        }
     }


}
