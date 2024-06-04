import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel  extends JFrame {
    public GamePanel(){
        super("某某遊戲");//名字...?
        player character =new player();
        add(character);
    }
    
    public static void main(String[] args)  {
        GamePanel  app = new GamePanel ();

        //空白的視窗
        app.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        app.setSize(1200,600);  
        app.setVisible(true); 
    }
  
}