import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class store extends JPanel implements ActionListener {
    private JButton storebtn;
    private JDialog dialog=new JDialog();
    public store(){
        setFocusable(true);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        storebtn = new JButton(new ImageIcon("yellow1.gif"));
        storebtn.setToolTipText("商店");
        storebtn.addActionListener(this);
        storebtn.setBounds(600,10, 10, 10);
        add(storebtn);
        
        // 初始化 JDialog，但不添加到 JPanel 中
        dialog = new JDialog((Frame) null, "store", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JLabel("This is the menu & store dialog."));
        
        //跳出來的視窗裡面有的東西------------------------------------------------------------------
        JLabel money=new JLabel("money:");
        dialog.add(money,BorderLayout.NORTH);

        //道具
        JButton item1=new JButton(new ImageIcon("yellow1.gif"));
        item1.setToolTipText("0變1");
        dialog.add(item1,BorderLayout.CENTER);
        JButton item2=new JButton(new ImageIcon("yellow1.gif"));
        item2.setToolTipText("1變0");
        dialog.add(item2,BorderLayout.SOUTH);


    }
    public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == storebtn ){
            //跳出選單視窗
                dialog.setVisible(true);
        }
     }
    

}
