import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.applet.*;
import javax.sound.sampled.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GamePanel extends JFrame {
    private Clip clip;
    private JPanel homePanel;
    private JPanel gamePanel;
    private CardLayout cardLayout;

    public GamePanel() {
        super("某某游戏");
        cardLayout = new CardLayout();
        setLayout(cardLayout);

        homePanel = new HomePanel(this);
        gamePanel = new JPanel(new BorderLayout());

        add(homePanel, "Home");
        add(gamePanel, "Game");

        // 初始化游戏面板
        initGamePanel();
    }

    private void initGamePanel() {
        // monster m1 = new monster();
        // player character = new player(m1);
        backpage b = new backpage();
        player character = new player(this, b);
        MenueTable m = new MenueTable(this,character);
        store s = new store(b);
        

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1100, 600));
        character.setBounds(0, 0, 1100, 600);
        // m1.setBounds(0, 0, 800, 600);

        layeredPane.add(character, JLayeredPane.DEFAULT_LAYER);
        // layeredPane.add(m1, JLayeredPane.PALETTE_LAYER);

        gamePanel.add(layeredPane, BorderLayout.CENTER);
        gamePanel.add(m, BorderLayout.NORTH);
        gamePanel.add(s, BorderLayout.EAST);
        gamePanel.add(b, BorderLayout.SOUTH);

        // 确保窗口可见后请求焦点
        SwingUtilities.invokeLater(() -> {
            character.requestFocusInWindow();
        });

        // 音乐
        // 目前随便抓的
        playBackgroundMusic("spacemusic.wav");

        // 当窗口关闭时，停止播放背景音乐
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (clip != null) {
                    clip.stop();
                    clip.close();
                }
                System.exit(0); // 确保程序退出
            }
        });
    }

    public void startGame() {
        cardLayout.show(getContentPane(), "Game");
    }

    //在menue按下home按鈕
    public void showHomePanel() {
        cardLayout.show(getContentPane(), "Home");
    }

    //在menue按下retry按鈕
    public void restartGame() {
        gamePanel.removeAll();
        initGamePanel();
        cardLayout.show(getContentPane(), "Game");
        revalidate();
        repaint();
    }

    private void playBackgroundMusic(String musicFilePath) {
        try {
            File musicFile = new File(musicFilePath);
            if (musicFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // 设置循环播放
            } else {
                System.err.println("找不到音樂文件: " + musicFilePath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //玩家被怪獸追到後要跳出GAME OVER視窗
    public void gameOver() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); 
    }

    public void gameWin() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
        JOptionPane.showMessageDialog(this, "You Win!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0); // 确保程序退出
    }

    public static void main(String[] args) {
        GamePanel app = new GamePanel();
        app.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        app.setSize(1200, 600);
        app.setVisible(true);
    }
}
