import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenueTable extends JPanel implements ActionListener {
    private JButton menuebtn,retrybtn;
    private JDialog dialog=new JDialog();
    public MenueTable(){
        setFocusable(true);
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        menuebtn = new JButton(new ImageIcon("green1.gif"));
        menuebtn.setToolTipText("選單");
        menuebtn.addActionListener(this);
        menuebtn.setBounds(700,5, 10, 10);
        add(menuebtn);
        
        // 初始化 JDialog，但不添加到 JPanel 中
        dialog = new JDialog((Frame) null, "Menue & store", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JLabel("retry."));

        //代做:重新按紐/退出........
        retrybtn = new JButton(new ImageIcon("retry.png"));//痾目前的圖片超大.....
        retrybtn.setToolTipText("重新");
        retrybtn.addActionListener(this);
        //retrybtn.setBounds(700,500, 10, 10);
        dialog.add(retrybtn,BorderLayout.WEST);
        
        


    }
    public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == menuebtn ){
            //跳出選單視窗
                dialog.setVisible(true);
        }
     }

}
