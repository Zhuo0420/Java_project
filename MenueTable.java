import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenueTable extends JPanel implements ActionListener {
    private JButton menuebtn,retrybtn,homebtn;
    private JDialog dialog=new JDialog();
    public MenueTable(){
        setFocusable(true);
        setLayout(new FlowLayout(FlowLayout.RIGHT));

        setBackground(Color.BLACK);//只是隨便設個顏色，方便觀察

        //btn------------------------------------------------------
        menuebtn = new JButton(new ImageIcon(new ImageIcon("menuebtn_0802.png").getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH)));
        menuebtn.setToolTipText("選單");
        menuebtn.addActionListener(this);
        menuebtn.setPreferredSize(new Dimension(40, 40));
        menuebtn.setBorderPainted(false); // 移除按鈕邊框
        menuebtn.setContentAreaFilled(false); // 移除按鈕填充區域
        add(menuebtn);
        
        // 初始化 JDialog，但不添加到 JPanel 中--------------------------
        dialog = new JDialog((Frame) null, "Menue & store", true);
        dialog.setSize(200, 100);
        dialog.setBackground(Color.BLACK);
        //dialog.setLayout(new BorderLayout());
        dialog.setLayout(new FlowLayout());
        //dialog.add(new JLabel("retry."));

        //代做:重新按紐/退出........
        retrybtn=new JButton(new ImageIcon(new ImageIcon("retry_0673.png").getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH)));
        //retrybtn = new JButton(new ImageIcon("retry_0673.png"));
        retrybtn.setToolTipText("重新");
        retrybtn.addActionListener(this);
        retrybtn.setPreferredSize(new Dimension(40, 40));
        retrybtn.setBorderPainted(false); // 移除按鈕邊框
        retrybtn.setContentAreaFilled(false); // 移除按鈕填充區域
        //retrybtn.setBounds(700,500, 10, 10);
        //dialog.add(retrybtn,BorderLayout.WEST);
        dialog.add(retrybtn);

        //退出:homebtn
        homebtn=new JButton(new ImageIcon(new ImageIcon("home_0683.png").getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH)));
        homebtn.setToolTipText("退出");
        homebtn.addActionListener(this);
        homebtn.setPreferredSize(new Dimension(40, 40));
        homebtn.setBorderPainted(false); // 移除按鈕邊框
        homebtn.setContentAreaFilled(false); // 移除按鈕填充區域
        //dialog.add(retrybtn,BorderLayout.WEST);
        dialog.add(homebtn);

        
        


    }
    public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == menuebtn ){
            //跳出選單視窗
                dialog.setVisible(true);
        }
     }

}
