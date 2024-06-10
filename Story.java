import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Story extends JFrame {
    private JLabel storyLabel;
    private JButton continueButton;
    private Timer timer;
    private int currentImageIndex;
    private static final int TOTAL_IMAGES = 10; // 总共的图片数量

    public Story() {
        setTitle("Story");
        setLayout(new BorderLayout());
        setSize(1000, 600); // 设置窗口大小
        setLocationRelativeTo(null); // 居中窗口
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        storyLabel = new JLabel();
        storyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(storyLabel, BorderLayout.CENTER);

        continueButton = new JButton("Continue");
        continueButton.setVisible(false); // 初始时隐藏按钮
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭窗口
            }
        });
        add(continueButton, BorderLayout.SOUTH);

        currentImageIndex = 1; // 从第一张图片开始
        timer = new Timer(1000, new ActionListener() { // 每1秒切换一张图片
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentImageIndex <= TOTAL_IMAGES) {
                    ImageIcon icon = new ImageIcon("story" + currentImageIndex + ".png");
                    storyLabel.setIcon(icon);
                    currentImageIndex++;
                } else {
                    timer.stop();
                    continueButton.setVisible(true); // 显示继续按钮
                }
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Story story = new Story();
                story.setVisible(true);
            }
        });
    }
}
