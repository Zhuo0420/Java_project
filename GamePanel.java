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
    public GamePanel() {
        super("某某遊戲");
        setLayout(new BorderLayout());

        //monster m1 = new monster();
        //player character = new player(m1);
        player character = new player();
        MenueTable m = new MenueTable();
        store s = new store();
        backpage b = new backpage();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1100, 600));
        character.setBounds(0, 0, 1100, 600);
        //m1.setBounds(0, 0, 800, 600);

        layeredPane.add(character, JLayeredPane.DEFAULT_LAYER);
        //layeredPane.add(m1, JLayeredPane.PALETTE_LAYER);

        add(layeredPane, BorderLayout.CENTER);
        add(m, BorderLayout.NORTH);
        add(s, BorderLayout.EAST);
        add(b, BorderLayout.SOUTH);

        // 确保窗口可见后请求焦点-------------------------------------------
         SwingUtilities.invokeLater(() -> {
            character.requestFocusInWindow();
        });


        //音樂------------------------------------------------
        //目前隨便抓的
        playBackgroundMusic("spacemusic.wav");

        //當是窗關閉時，停止播放背景音樂
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

    private void playBackgroundMusic(String musicFilePath) {
        try {
            File musicFile = new File(musicFilePath);
            if (musicFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // 设置循环播放
            } else {
                System.err.println("找不到音乐文件: " + musicFilePath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GamePanel app = new GamePanel();
        app.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        app.setSize(1200, 600);
        app.setVisible(true);
    }
}
