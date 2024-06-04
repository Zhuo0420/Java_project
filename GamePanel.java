import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel  extends JFrame {
    public GamePanel(){
        super("某某遊戲");//名字...?
        setLayout(new BorderLayout());
        player character =new player();
        MenueTable m=new MenueTable();
        add(character, BorderLayout.CENTER);
        add(m, BorderLayout.NORTH);

        // 在所有元件新增完畢後請求焦點
        character.setFocusable(true);
        character.requestFocusInWindow();
    }
    
    public static void main(String[] args)  {
        GamePanel  app = new GamePanel ();

        //空白的視窗
        app.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        app.setSize(1200,600);  
        app.setVisible(true); 

        // 确保窗口可见后请求焦点 =>增加下面這幾行才角色和menue才都動的了
        SwingUtilities.invokeLater(() -> {
            app.requestFocusInWindow();
            app.getContentPane().getComponent(0).requestFocusInWindow(); // 直接请求Player组件的焦点
        });
    }
  
}