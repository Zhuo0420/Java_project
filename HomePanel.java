import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    private ImageIcon startIcon1;
    private ImageIcon startIcon2;
    private JButton startButton;
    private Timer animationTimer;
    private boolean toggleIcon = true;

    public HomePanel(GamePanel gamePanel) {
        setLayout(new GridBagLayout());

        // 加载按钮图像
        startIcon1 = new ImageIcon(new ImageIcon("startbtn.png").getImage().getScaledInstance(100, 48, Image.SCALE_SMOOTH));
        startIcon2 = new ImageIcon(new ImageIcon("startbtn2.png").getImage().getScaledInstance(100, 48, Image.SCALE_SMOOTH));

        startButton = new JButton(startIcon1);
        startButton.setToolTipText("start");
        startButton.setBorderPainted(false); // 移除按钮边框
        startButton.setContentAreaFilled(false); // 移除按钮填充区域

        startButton.setPreferredSize(new Dimension(200, 50));

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationTimer.stop(); // 停止动画
                gamePanel.startGame();
            }
        });

        add(startButton);

        // 创建计时器来切换按钮图像
        animationTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggleIcon) {
                    startButton.setIcon(startIcon2);
                } else {
                    startButton.setIcon(startIcon1);
                }
                toggleIcon = !toggleIcon;
            }
        });

        animationTimer.start(); // 启动动画
    }
}
