import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class player extends JPanel {
    private ImageIcon player_img;
    private int x, y;//玩家位置
    private int panelWidth = 1100;
    private int panelHeight = 480;
    //private monster m1;

    private ImageIcon monster_img; // 怪獸圖片
    private int m_x, m_y; // 怪獸位置
    private int targetX, targetY; // 目標位置
    private Timer chaseTimer; // 計時器，用於追蹤玩家

    //public player(monster m1) {
    public player() {
        setFocusable(true);// 使面板能夠接收事件
        setLayout(null);
        setBackground(Color.GREEN);//只是讓我們方便看現在的地圖範圍而已

        //關於玩家------------------------------------------------
        //this.m1 = m1;
        player_img = new ImageIcon("blue1.gif");

        // 檢查玩家圖片是否加載成功
        if (player_img.getIconWidth() == -1) {
            System.err.println("玩家图像加载失败！");
        }

        //玩家的初始位置:右下角
        x = 10;
        y = 460;

        // 使用KeyBindings处理键盘事件
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "moveUp");
        getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                y -= 5;
                updatePosition();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                y += 5;
                updatePosition();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x -= 5;
                updatePosition();
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                x += 5;
                updatePosition();
            }
        });

        //關於怪獸-------------------------------------------

        monster_img = new ImageIcon("monster1.gif"); // 加載怪獸圖片

        // 檢查圖片是否加載成功
        if (monster_img.getIconWidth() == -1) {
            System.err.println("怪獸圖像加載失敗！");
        }

        //setFocusable(true); // 使面板能夠接收事件

        // 初始化怪獸位置
        m_x = 300;
        m_y = 300;

        // 初始化計時器，每100毫秒執行一次chase方法
        chaseTimer = new Timer(100, e -> chase());
    }

    //玩家------------------------------------------------------
    private void updatePosition() {
        // 確保玩家在範圍內
        x = Math.max(0, Math.min(x, panelWidth - player_img.getIconWidth()));
        y = Math.max(0, Math.min(y, panelHeight - player_img.getIconHeight()));

        System.out.println("x:" + x + " y:" + y);//玩家位置
        repaint();
        checkMonsterProximity();
    }

    //畫玩家和怪獸---------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Insets ins = getInsets();
        player_img.paintIcon(this, g, x + ins.left, y + ins.top);
        monster_img.paintIcon(this, g, m_x + ins.left, m_y + ins.top);
    }

    //怪獸的-------------------------------------------------------------

    private void checkMonsterProximity() {
        // 檢查玩家位置是否在怪獸的位置+-50，如果是就追
        System.out.println("monster x:"+m_y+"y:"+m_x);//怪獸位置
        if (Math.abs(x - m_x) <= 50 && Math.abs(y - m_y) <= 50) {
            startChasing(x, y);
        } else {
            stopChasing();
        }
    }


    // 開始追蹤玩家
    public void startChasing(int playerX, int playerY) {
        targetX = playerX; // 設定目標位置
        targetY = playerY;
        chaseTimer.start(); // 啟動計時器
    }

    // 停止追蹤玩家
    public void stopChasing() {
        chaseTimer.stop(); // 停止計時器
    }

    // 追蹤方法，更新怪獸位置
    private void chase() {
        if (m_x < targetX) {
            m_x += 5;
        } else if (m_x > targetX) {
            m_x -= 5;
        }

        if (m_y < targetY) {
            m_y += 5;
        } else if (m_y > targetY) {
            m_y -= 5;
        }

        repaint(); // 重畫面板
    }

    
}
