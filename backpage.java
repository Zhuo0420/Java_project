import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class backpage extends JPanel implements ActionListener{
    private JButton item1,item2;

    int item1Num = 0;
    int item2Num = 0;

    boolean item1Used = false;
    boolean item2Used = false;

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
            if(item1Num > 0){
                item1Used = true;                
            }
        }
        else if ( evt.getSource() == item2 ){
            //可以使用道具1，按完後可以選擇使用的地方，使1變0
            if(item2Num > 0){                
                item2Used = true;
            }
        }
        
     }
    public void buyItem1(){
        item1Num++;
    }
    public void buyItem2(){
        item2Num++;
    }
    public void itemToZero(){
        item1Num = 0;
        item2Num = 0;
    }
}
