import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

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

    private BufferedImage monster_img; // 怪獸圖片
    private BufferedImage monster_img2;
    private int[] monster_x; // 怪獸位置
    private int[] monster_y; // 怪獸位置
    private int targetX, targetY; // 目標位置
    private Timer chaseTimer; // 計時器，用於追蹤玩家

    private GamePanel gamePanel; // 引用GamePanel实例
    private backpage backpage; // 引用backpage实例

    private BufferedImage roadImage;
    private BufferedImage wallImage;
    private BufferedImage backWallImage;
    private BufferedImage doorImage;
    private BufferedImage treasureIMage;
    private BufferedImage treasureOpenIMage;
    private BufferedImage playerIMage;
    private BufferedImage playerDeadIMage;
    private BufferedImage m1_1;
    private BufferedImage m1_2;
    private BufferedImage m1_3;
    private BufferedImage[] monsterImages;

    private BufferedImage m2_1;
    private BufferedImage m2_2;
    private BufferedImage m2_3;
    private BufferedImage[] monsterImages2;

    private int level_conut=1;
    static private int coinSum = 0; // 玩家金幣總數量

    public int item1_count=0;
    public int item2_count=0;

    // 地圖//只是隨便加一個地圖試試
    //0:路  1:牆  2:門  3:寶箱
    private int[][] initialMap = {
        {0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2},
        {0, 1, 3, 0, 0, 3, 1, 0, 1, 1, 0},
        {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
    };
    private int[][] nextLevelMap = {
        {0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0},
        {0, 1, 3, 0, 0, 0, 1, 0, 1, 0, 0},
        {0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0},
        {0, 1, 0, 0, 1, 2, 1, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
        {0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 1, 1, 3, 0, 1, 1, 1}
    };

    private int[][] map;
    private int cellSize = 40; // 每個地圖單元格的大小

    //計時器
    private int timecount;
    private Timer gameTimer;

    //public player(monster m1) {
    //public player() {
    public player(GamePanel gamePanel) {
        this.gamePanel = gamePanel; // 保存GamePanel实例
        //this.backpage = backpage; // 保存backpage实例
        setFocusable(true);// 使面板能夠接收事件
        setLayout(null);        
        //setBackground(Color.GREEN);//只是讓我們方便看現在的地圖範圍而已

        //關於玩家------------------------------------------------
        //this.m1 = m1;
        player_img = new ImageIcon("player1.png");

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
        try {
            m1_1 = ImageIO.read(new File("monster1_1.png"));
            m1_2 = ImageIO.read(new File("monster1_2.png"));
            m1_3 = ImageIO.read(new File("monster1_3.png"));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        monster_img = m1_1; // 加載怪獸圖片
        monsterImages=new BufferedImage[]{m1_1,m1_2,m1_3};

        //-1怪獸------
        try {
            m2_1 = ImageIO.read(new File("monster2_1.png"));
            m2_2 = ImageIO.read(new File("monster2_2.png"));
            m2_3 = ImageIO.read(new File("monster2_3.png"));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        monster_img2 = m2_1; // 加載怪獸圖片
        monsterImages2=new BufferedImage[]{m2_1,m2_2,m2_3};

        //setFocusable(true); // 使面板能夠接收事件

        // 初始化怪獸位置
        //m_x = 300;
        //m_y = 300;
        monster_x = new int[]{7};
        monster_y = new int[]{6};

        // 初始化計時器，每500毫秒執行一次chase方法
        chaseTimer = new Timer(500, e -> chase());

        
        // 地圖圖片
        try {
            roadImage = ImageIO.read(new File("road_0000.png"));
            wallImage = ImageIO.read(new File("wall_0014.png"));
            backWallImage = ImageIO.read(new File("backWall.png"));         // 背景牆
            doorImage = ImageIO.read(new File("door_0045.png"));
            treasureIMage = ImageIO.read(new File("treasure_0089.png"));
            treasureOpenIMage = ImageIO.read(new File("treasureopen_0091.png"));
            playerIMage=ImageIO.read(new File("player1.png"));
            playerDeadIMage=ImageIO.read(new File("player_dead.png"));

            
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        initLevel(initialMap);

        // Initialize game timer 為了讓恐龍動起來!!
        timecount = 0;
        gameTimer = new Timer(500, e -> updateGameTime());
        gameTimer.start();
    }

    //第一關-----------------------------------
    private void initLevel(int[][] newMap) {
        map = new int[newMap.length][newMap[0].length];
        for (int i = 0; i < newMap.length; i++) {
            System.arraycopy(newMap[i], 0, map[i], 0, newMap[i].length);
        }
        x = 0;
        y = 0;

        if (newMap == initialMap) {
            monster_x = new int[]{7};
            monster_y = new int[]{6};
        } else if (newMap == nextLevelMap) {
            monster_x = new int[]{7, 3};
            monster_y = new int[]{6, 2};
        }

        chaseTimer.stop(); // 停止追蹤
        repaint();
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

            // 检查是否到达终点
            if (map[y][x] == 2) {
                if (level_conut==1) {
                    chaseTimer.stop(); 
                    Story story = new Story();
                    story.setVisible(true);
                    initLevel(nextLevelMap); // 进入下一关
                    level_conut++;
                } else {
                    System.out.println("兩關完結");
                    chaseTimer.stop(); 
                    gamePanel.gameWin(); // 通关
                }
            } else if (map[y][x] == 3) {
                // 宝箱触发事件
                treasure_event();
                map[y][x] = 4; // 标记宝箱为开过
            }

            System.out.println("x:" + x + " y:" + y); // 玩家位置
            repaint();
            checkMonsterProximity();
        }
    }

    //寶箱事件
    private void treasure_event(){
        playSoundEffect("openBox.wav");
        Random rand = new Random();
        int outcome = rand.nextInt(10); // 隨機生成0~9
        //if(x==2&& y==1)outcome=0;//為了demo時的寶箱怪設置的

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
    static public void CointoZero(){
        player.coinSum = 0;
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

    
    //計時器--------------------------------------------------------
    private void updateGameTime() {
        timecount++;
        //System.out.println("游戏时间: " + timecount + "秒");
    }

    //畫玩家和怪獸和地圖和寶箱---------------------------------------------------
       // 繪製遊戲地圖和角色
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintBackground(g);
        Graphics2D g2d = (Graphics2D) g;
        int offsetX = (panelWidth - map[0].length * cellSize) / 2; // 計算地圖居中所需的偏移量
        int offsetY = (panelHeight - map.length * cellSize) / 2;
   
        // 繪製地圖
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                int cell = map[row][col];
                switch (cell) {
                    case 0:
                        g2d.drawImage(roadImage, offsetX + col * cellSize, offsetY + row * cellSize, cellSize, cellSize, this);
                        break;
                    case 1:
                        g2d.drawImage(wallImage, offsetX + col * cellSize, offsetY + row * cellSize, cellSize, cellSize, this);
                        break;
                    case 2:
                        g2d.drawImage(doorImage, offsetX + col * cellSize, offsetY + row * cellSize, cellSize, cellSize, this);
                        break;
                    case 3:
                        g2d.drawImage(treasureIMage, offsetX + col * cellSize, offsetY + row * cellSize, cellSize, cellSize, this);
                        break;
                    case 4:
                        g2d.drawImage(treasureOpenIMage, offsetX + col * cellSize, offsetY + row * cellSize, cellSize, cellSize, this);
                        break;
                }
            }
        }   
   
           // 繪製玩家
        g2d.drawImage(player_img.getImage(), offsetX + x * cellSize, offsetY + y * cellSize, cellSize, cellSize, this);
   
           // 繪製怪物
        for (int i = 0; i < monster_x.length; i++) {
            BufferedImage[] monsters=new BufferedImage[]{monster_img,monster_img2};
            monster_img=monsterImages[timecount%3];//怪獸1(紅色
            monster_img2=monsterImages2[timecount%3];//怪獸-1(藍色
            g2d.drawImage(monsters[i], offsetX + monster_x[i] * cellSize, offsetY + monster_y[i] * cellSize, cellSize, cellSize, this);
        }
    }

    // 繪製背景
    public void paintBackground(Graphics g) {    
        System.out.println("paintBackground");                  
        if (backWallImage != null) {        
            System.out.println("paintBackground in if");
            for (int y = 0; y < getHeight(); y += cellSize) {
                for (int x = 0; x < getWidth(); x += cellSize) {
                    g.drawImage(backWallImage, x, y, cellSize, cellSize, this);
                }
            }
        }
    }

    // 方法播放音效
    private void playSoundEffect(String soundFilePath) {
        try {
            File soundFile = new File(getClass().getClassLoader().getResource(soundFilePath).getFile());
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    //怪獸的-------------------------------------------------------------


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
     // 計算怪物到玩家的距離
    private void checkMonsterProximity() {
        boolean isChasing = false;
        for (int i = 0; i < monster_x.length; i++) {
            double distance = Math.sqrt(Math.pow(monster_x[i] - x, 2) + Math.pow(monster_y[i] - y, 2));
            if (distance <= 3.0) {
                isChasing = true;
                break;
            }
        }

        if (isChasing) {
            chaseTimer.start();
        } else {
            chaseTimer.stop();
        }
    }

    // 追蹤方法，更新怪獸位置
    private void chase() {
        for (int i = 0; i < monster_x.length; i++) {
            int o_x=monster_x[i];
            int o_y=monster_y[i];
            if (monster_x[i] < x) {
                monster_x[i]++;
            } else if (monster_x[i] > x) {
                monster_x[i]--;
            } else if (monster_y[i] < y) {
                monster_y[i]++;
            } else if (monster_y[i] > y) {
                monster_y[i]--;
            }

            if(i==0)map[o_y][o_x]=1;//怪獸1
            else  map[o_y][o_x]=0;//怪獸-1
            

            // 當怪物接觸到玩家
            if (monster_x[i] == x && monster_y[i] == y && !gamePanel.getWin()) {
                //player_img = new ImageIcon("player_dead.png");
                gamePanel.gameOver();
                chaseTimer.stop();
                break;
            }
            
        }
        repaint();
    }
    
    
    public void useItem(Object object){
        if (object == null) {
            return;
        }
        if (object instanceof JButton) {            
            JButton btn = (JButton) object;                        
            if (btn.getToolTipText().equals("道具一")) {
                // 道具一                                            
                useItem1();                    
                
            } else if (btn.getToolTipText().equals("道具二")) {
                // 道具二
                System.out.println("使用道具二");
                useItem2();
            }
        }
    }
    

// 在類中定義placeingItem變量
    public boolean placingItem = true;


    public void useItem1() {
        // 使用道具1，按完后可以选择使用的地方，使0变1
        JFrame it1 = new JFrame("道具1");
        ImageIcon item1Icon = new ImageIcon("item1.png");       // 道具1的圖示
        JLabel itemLabel = new JLabel(item1Icon);       // 用JLabel顯示道具1的圖示
        it1.getContentPane().add(itemLabel);            // 將JLabel添加到JFrame中
        it1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        it1.setSize(40, 40); // 大小和cellsize一樣
        it1.setUndecorated(true); // 去掉窗口裝飾
        //it1 的 背景設為透明
        it1.setBackground(new Color(0, 0, 0, 1));           // 設置背景 "幾乎" 為透明
        //it1.setBackground(Color.red);//可以改成item1的圖示，阿不知道為什麼紅色顯示不出來...
        it1.setVisible(true);
        

        // 道具一移動
        java.awt.event.MouseMotionAdapter mouseMotionAdapter = new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                if (placingItem) {
                    System.out.println("道具一移動");
                    // 鼠標相對位置
                    Point mousePoint = evt.getLocationOnScreen();
                    // 設置it1的位置，使其跟随鼠标
                    it1.setLocation(mousePoint.x, mousePoint.y);
                }
            }
        };
        addMouseMotionListener(mouseMotionAdapter);

        // 在it1內滑鼠點擊事件
        it1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (placingItem) {
                    System.out.println("放置道具1");
                    
                    // 印出滑鼠的位置(非map上的位置!!!!)
                    Point clickPoint = evt.getLocationOnScreen();
                    System.out.println("滑鼠點擊位置: " + clickPoint.x/cellSize + ", " + clickPoint.y/cellSize);
                    changetoOne(clickPoint.x/cellSize,clickPoint.y/cellSize);
                    // 用完道具就設為看不到
                    it1.setVisible(false);
                    placingItem = false;  // 放置完成后，設置為false，但我在想 if (placingItem) 這個條件到底是不是必須加的.....
                    // 移除監聽
                    removeMouseListener(this);
                    removeMouseMotionListener(mouseMotionAdapter);
                }
            }
        });
    }

    //更改地圖的資訊(道具1適用)
    private void changetoOne(int x,int y){        
        if(level_conut == 1){//第一關
            if(map[y-4][x-8] != 0){
                JOptionPane.showMessageDialog(this, "這裡不能放置道具");
                return;
            }
            map[y-4][x-8]=1;//(8,4)是map第一格(0,0)在視窗上的位子
            repaint();
        }
        else if(level_conut == 2)       // 第二關
        {
            if(map[y-3][x-8] != 0){
                JOptionPane.showMessageDialog(this, "這裡不能放置道具");
                return;
            }
            map[y-3][x-8] = 1;//(8,3)是map第一格(0,0)在視窗上的位子
            repaint();
        }
        
    }


    
    public void useItem2(){
        // 使用道具2，按完後可以選擇使用的地方，使1變0
        JFrame it2 = new JFrame("道具2");
        ImageIcon item2Icon = new ImageIcon("item2.png");       // 道具2的圖示
        JLabel itemLabel = new JLabel(item2Icon);       // 用JLabel顯示道具2的圖示
        it2.getContentPane().add(itemLabel);            // 將JLabel添加到JFrame中
        it2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        it2.setSize(40, 40); // 大小和cellsize一樣
        it2.setUndecorated(true); // 去掉窗口裝飾
        //it2 的 背景設為透明
        it2.setBackground(new Color(0, 0, 0, 1));           // 設置背景 "幾乎" 為透明
        //it2.setBackground(Color.red);//可以改成item2的圖示，阿不知道為什麼紅色顯示不出來...
        it2.setVisible(true);

        java.awt.event.MouseMotionAdapter mouseMotionAdapter = new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                if (placingItem) {
                    System.out.println("道具二移動");
                    // 鼠標相對位置
                    Point mousePoint = evt.getLocationOnScreen();
                    // 設置it2的位置，使其跟随鼠標
                    it2.setLocation(mousePoint.x, mousePoint.y);
                }
            }
        };
        addMouseMotionListener(mouseMotionAdapter);

        // 在it2內滑鼠點擊事件
        it2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (placingItem) {
                    System.out.println("放置道具2");
                    
                    // 印出滑鼠的位置(非map上的位置!!!!)
                    Point clickPoint = evt.getLocationOnScreen();
                    System.out.println("滑鼠點擊位置: " + clickPoint.x/cellSize + ", " + clickPoint.y/cellSize);
                    changetoZero(clickPoint.x/cellSize,clickPoint.y/cellSize);
                    // 用完道具就設為看不到
                    it2.setVisible(false);
                    placingItem = false;  // 放置完成后，設置為false，但我在想 if (placingItem) 這個條件到底是不是必須加的.....
                    // 移除監聽
                    removeMouseListener(this);
                    removeMouseMotionListener(mouseMotionAdapter);
                }
            }
        });
    }
    private void changetoZero(int x,int y){        
        if(level_conut==1){//第一關
            if(map[y-4][x-8] != 1){
                JOptionPane.showMessageDialog(this, "這裡不能放置道具");
                return;
            }
            map[y-4][x-8] = 0;//(8,4)是map第一格(0,0)在視窗上的位子
            repaint();
        }
        else if(level_conut == 2)       // 第二關
        {
            if(map[y-3][x-8] != 1){
                JOptionPane.showMessageDialog(this, "這裡不能放置道具");
                return;
            }
            map[y-3][x-8] = 0;//(8,3)是map第一格(0,0)在視窗上的位子
            repaint();
        }
        

    }
    
}
