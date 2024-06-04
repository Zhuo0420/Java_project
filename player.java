import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//目前只寫到了讓主程式可以讀取這個player程式碼的鍵盤事件
public class player extends JPanel implements KeyListener{
    private JLabel label;
    public player(){
        
        label = new JLabel("");
        add(label);
        setFocusable(true);
        addKeyListener(this);
        

    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_UP){//按鍵盤向上
            label.setText("u");
            
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN){//下面
            label.setText("d");
            
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT){//左邊
            label.setText("l");
            
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT){//右邊
            label.setText("r");
            
        }

    }
    public void keyReleased(KeyEvent e){

    }
    public void keyTyped(KeyEvent e){

    }
}
