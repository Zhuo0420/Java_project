import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class monster extends JPanel {
    private ImageIcon player_img;
    private int x,y;//怪獸位置
    private int Mapheight=450;//不知道大小是多少.....
    private int Mapwidth;
    public monster(){
        player_img = new ImageIcon("monster1.gif");
        setFocusable(true);//呼叫setFocusable(true)使得Player面板能夠接收鍵盤事件。
        

        //初始位子:
        x=300;
        y=300;
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        Insets ins = getInsets(); // 取得邊線尺寸
        // 繪出ImageIcon
        player_img.paintIcon(this, g, x + ins.left, y + ins.top);
    }
}
