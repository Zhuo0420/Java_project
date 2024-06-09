import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

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

    private GamePanel gamePanel; // 引用GamePanel实例

    private BufferedImage roadImage;
    private BufferedImage wallImage;
    private BufferedImage doorImage;
    private BufferedImage treasureIMage;
    private BufferedImage treasureOpenIMage;

    static private int coinSum = 0; // 玩家金幣總數量

    // 地圖//只是隨便加一個地圖試試
    //0:路  1:牆  2:門  3:寶箱
    private int[][] map = {
        {0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2},
        {0, 1, 3, 0, 0, 3, 1, 0, 1, 1, 0},
        {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
    };
    private int cellSize = 40; // 每個地圖單元格的大小

    //public player(monster m1) {
    //public player() {
    public player(GamePanel gamePanel) {
        this.gamePanel = gamePanel; // 保存GamePanel实例
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
        //x = 10;
        //y = 460;
        // 玩家初始位置:地圖的左上角
        x = 0;
        y = 0;

        // 使用KeyBindings处理键盘事件
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "moveUp");
        getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //y -= 5;
                movePlayer(0, -1);
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //y += 5;
                movePlayer(0, 1);
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //x -= 5;
                movePlayer(-1, 0);
            }
        });

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //x += 5;
                movePlayer(1, 0);
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
        //m_x = 300;
        //m_y = 300;
        m_x=7;
        m_y=6;

        // 初始化計時器，每500毫秒執行一次chase方法
        chaseTimer = new Timer(500, e -> chase());

        
        // 地圖圖片
        try {
            roadImage = ImageIO.read(new File("road_0000.png"));
            wallImage = ImageIO.read(new File("wall_0014.png"));
            doorImage = ImageIO.read(new File("door_0045.png"));
            treasureIMage = ImageIO.read(new File("treasure_0089.png"));
            treasureOpenIMage = ImageIO.read(new File("treasureopen_0091.png"));
            
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //玩家------------------------------------------------------
      // 移動玩家
      private void movePlayer(int dx, int dy) {
        int newX = x + dx ;
        int newY = y + dy ;

        // 確保玩家在範圍內並且不會走到牆上
        if (newX >= 0 && newX < map[0].length && newY >= 0 && newY < map.length && map[newY][newX] != 1) {
            x = newX;
            y = newY;

            // 檢查是否到達終點
            if (map[y][x] == 2) {
                gamePanel.gameWin();
            }

            //寶箱觸發事件
            if (map[y][x] == 3) {
                treasure_event();
                map[y][x] = 4;//標記寶箱為開過
            }

            System.out.println("x:" + x + " y:" + y); // 玩家位置
            repaint();
            checkMonsterProximity();
        }
    }

    //寶箱事件
    private void treasure_event(){
        Random rand = new Random();
        int outcome = rand.nextInt(10); // 隨機生成0~9
        if(x==2&& y==1)outcome=0;//為了demo時的寶箱怪設置的

        if (outcome == 0) {
            // 10%的概率出現寶箱怪
            showGameOverAnimation();//寶箱怪小動畫和Gameover提示
        } else {
            // 獲得1~20元
            int coins = rand.nextInt(20) + 1;
            coinSum += coins;
            JOptionPane.showMessageDialog(this, "你獲得了 " + coins + " 個金幣！");
            System.out.println("coinSum: " + coinSum);
        }
    }
    static public int getCoinSum() {         // 獲取玩家金幣數量
        System.out.println("get coinSum: " + coinSum);
        return coinSum;
    }
    static public void setCoinSum(int coinSum) {         // 設置玩家金幣數量
        player.coinSum = coinSum;
    }
    //寶箱怪動畫-----------------------------------------------------------
    private void showGameOverAnimation() {
        JDialog dialog = new JDialog((Frame) null, "遊戲結束", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout());
        JLabel msg=new JLabel("好黑~好可怕\n這裡面是珍貴的魔導書(才怪");

        JPanel animationPanel = new JPanel() {
            private ImageIcon[] icons = {
                new ImageIcon("treasure_0089.png"),
                new ImageIcon("treasure_0090.png"),
                new ImageIcon("treasureopen_0091.png"),
                new ImageIcon("treasure_0092.png")
            };
            private int currentFrame = 0;

            {
                Timer timer = new Timer(200, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currentFrame = (currentFrame + 1) % icons.length;
                        repaint();
                    }
                });
                timer.start();
            }

            @Override
            protected void paintComponent(Graphics g) {
                Image img = icons[currentFrame].getImage();
                g.drawImage(img, 80, 30, 50, 50, this);
                
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 100);
            }
        };
        
        dialog.add(animationPanel, BorderLayout.WEST);
        dialog.add(msg,BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        gamePanel.gameOver(); // 调用gameOver方法结束游戏
    }

    //----------------------------------------
    private void updatePosition() {
        // 確保玩家在範圍內
        //x = Math.max(0, Math.min(x, panelWidth - player_img.getIconWidth()));
        //y = Math.max(0, Math.min(y, panelHeight - player_img.getIconHeight()));

        //System.out.println("x:" + x + " y:" + y);//玩家位置
        repaint();
        checkMonsterProximity();
    }

    //畫玩家和怪獸和地圖和寶箱---------------------------------------------------
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Insets ins = getInsets();

        // 繪製地圖
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                BufferedImage img;
                if (map[i][j] == 1) {
                    img = wallImage;
                } else if (map[i][j] == 2) {//門
                    img=doorImage;
                } else if (map[i][j] == 3) {//還沒開過的寶箱
                    img=treasureIMage;
                }else if (map[i][j] == 4) {//開過的寶箱
                    img=treasureOpenIMage;
                } else {
                    img = roadImage;
                }
                g.drawImage(img, j * cellSize, i * cellSize, cellSize, cellSize, null);
            }
        }

        player_img.paintIcon(this, g, x*cellSize + ins.left, y*cellSize + ins.top);
        monster_img.paintIcon(this, g, m_x*cellSize + ins.left, m_y*cellSize + ins.top);
    }

    //怪獸的-------------------------------------------------------------

    private void checkMonsterProximity() {
        // 檢查玩家位置是否在怪獸的位置+-2，如果是就追
        System.out.println("monster x:"+m_y+"y:"+m_x);//怪獸位置
        if (Math.abs(x - m_x) <= 2 && Math.abs(y - m_y) <= 2) {
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
        int orginal_m_x=m_x;
        int orginal_m_y=m_y;
        if (m_x < targetX) {
            m_x += 1;
        } else if (m_x > targetX) {
            m_x -= 1;
        }
        else if (m_y < targetY) {
            m_y += 1;
        } else if (m_y > targetY) {
            m_y -= 1;
        }

        
       
         // 檢查怪獸是否抓住玩家
        if (m_x == x && m_y == y) {
            repaint();
            gamePanel.gameOver(); // 调用GamePanel中的gameOver方法
        }


        //路變牆
        map[orginal_m_y][orginal_m_x]=1;

        repaint(); // 重畫面板
    }

    
}
