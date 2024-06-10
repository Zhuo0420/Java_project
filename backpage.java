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
    player p;

    public backpage(player p){
        this.p=p;
        item1Num=p.item1_count;
        item2Num=p.item2_count;
        setFocusable(true);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        setBackground(Color.GRAY);
        item1 = new JButton(new ImageIcon(new ImageIcon("item1.png").getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH)));
        item1.setToolTipText("道具一");
        item1.addActionListener(this);
        item1.setBounds(700,500, 10, 10);
        add(item1);

        item2 = new JButton(new ImageIcon(new ImageIcon("item2.png").getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH)));
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
                System.out.println("使用item1");
                p.placingItem = true;
                p.useItem1();          
                item1Num--;
            }

        }
        else if ( evt.getSource() == item2 ){
            //可以使用道具1，按完後可以選擇使用的地方，使1變0
            if(item2Num > 0){    
                item2Used = true;
                System.out.println("使用item2");
                p.placingItem = true;
                //p.useItem2();            
                item2Num--;
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
