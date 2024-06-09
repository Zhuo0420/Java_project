import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    private ImageIcon startIcon1;
    private ImageIcon startIcon2;
    private boolean toggleIcon = true;

    private ImageIcon[] animationIcons;
    private JLabel backgroundLabel;
    private JButton startButton,title;
    private Timer animationTimer;
    private int currentIconIndex = 0;

    public HomePanel(GamePanel gamePanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(118, 59, 54)); // 设置背景颜色为咖啡色

        JLayeredPane layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);
        

        // 動畫
        animationIcons = new ImageIcon[11];
        for (int i = 0; i < 11; i++) {
            animationIcons[i] = new ImageIcon(new ImageIcon(i + ".png").getImage().getScaledInstance(1000, 600, Image.SCALE_SMOOTH));
        }

        // 背景標籤
        backgroundLabel = new JLabel(animationIcons[0]);
        backgroundLabel.setBounds(0, 0, 1000, 600);
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);

        //標題
        title = new JButton(new ImageIcon(new ImageIcon("name.png").getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH)));
        title.setToolTipText("標題");
        title.setPreferredSize(new Dimension(40, 40));
        title.setBorderPainted(false); // 移除按鈕邊框
        title.setContentAreaFilled(false); // 移除按鈕填充區域
        
        // start按鈕
        startIcon1 = new ImageIcon(new ImageIcon("startbtn.png").getImage().getScaledInstance(100, 48, Image.SCALE_SMOOTH));
        startIcon2 = new ImageIcon(new ImageIcon("startbtn2.png").getImage().getScaledInstance(100, 48, Image.SCALE_SMOOTH));
        startButton = new JButton(startIcon1);
        //ImageIcon startIcon = new ImageIcon(new ImageIcon("startbtn.png").getImage().getScaledInstance(100, 48, Image.SCALE_SMOOTH));
        //startButton = new JButton(startIcon);
        startButton.setToolTipText("start");
        startButton.setBorderPainted(false); // 移除按钮边框
        startButton.setContentAreaFilled(false); // 移除按钮填充区域
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationTimer.stop(); // 按下按鈕停止動畫
                gamePanel.startGame();
            }
        });
        
        // 右半部
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false); // 使面板透明
        rightPanel.add(Box.createVerticalGlue()); // 添加垂直胶水以将组件推到面板顶部

        title.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        rightPanel.add(title);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // 添加固定间距

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 居中对齐
        rightPanel.add(startButton);
        rightPanel.add(Box.createVerticalGlue()); // 添加垂直胶水以将组件推到面板顶部

        layeredPane.add(rightPanel, JLayeredPane.PALETTE_LAYER);
        rightPanel.setBounds(1000, 0, 200, 600); // 设置右侧面板的位置和大小


        // 居中對期
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // 使面板透明
        centerPanel.setBounds(0, 0, 1000, 600);
        //centerPanel.add(startButton);
        layeredPane.add(centerPanel, JLayeredPane.PALETTE_LAYER);

        // 切換動畫+按鈕動畫
        animationTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (toggleIcon) {
                    startButton.setIcon(startIcon2);
                } else {
                    startButton.setIcon(startIcon1);
                }
                toggleIcon = !toggleIcon;
                
                currentIconIndex = (currentIconIndex + 1) % animationIcons.length;
                backgroundLabel.setIcon(animationIcons[currentIconIndex]);
            }
        });

        animationTimer.start(); // 启动动画
    }

    // 背景顏色
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(118, 59, 54));//棕
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
