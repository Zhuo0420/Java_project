import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//目前只寫到了讓主程式可以讀取這個player程式碼的鍵盤事件
public class player extends JPanel implements KeyListener{
    private ImageIcon player_img;
    private int x,y;
    private int Mapheight=450;//不知道大小是多少.....
    private int Mapwidth;
    public player(){
        player_img = new ImageIcon("blue1.gif");
        setFocusable(true);//呼叫setFocusable(true)使得Player面板能夠接收鍵盤事件。
        addKeyListener(this);

        //初始位子:右下
        x=5;
        y=Mapheight;
       
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        Insets ins = getInsets(); // 取得邊線尺寸
        // 繪出ImageIcon
        player_img.paintIcon(this, g, x + ins.left, y + ins.top);
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_UP){//按鍵盤向上
            y-=5;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN){//下面
            y+=5;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT){//左邊
            x-=5;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT){//右邊
            x+=5;
        }
        repaint();//重畫

    }
    public void keyReleased(KeyEvent e){

    }
    public void keyTyped(KeyEvent e){

    }
}
