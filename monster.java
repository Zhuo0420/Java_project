//無論怎麼修改，都無法讓玩家和怪獸同時出現在同個畫面，且能夠執行追逐的功能，所以把玩家和怪獸都移到player.java檔中了

/*import javax.swing.*;
import java.awt.*;

public class monster extends JPanel {
    private ImageIcon monster_img; // 怪獸圖片
    private int x, y; // 怪獸位置
    private int targetX, targetY; // 目標位置
    private Timer chaseTimer; // 計時器，用於追蹤玩家

    public monster() {
        monster_img = new ImageIcon("monster1.gif"); // 加載怪獸圖片

        // 檢查圖片是否加載成功
        if (monster_img.getIconWidth() == -1) {
            System.err.println("怪獸圖像加載失敗！");
        }

        setFocusable(true); // 使面板能夠接收事件

        x = 300; // 初始化怪獸位置
        y = 300;

        // 初始化計時器，每100毫秒執行一次chase方法
        chaseTimer = new Timer(100, e -> chase());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Insets ins = getInsets(); // 取得邊線尺寸
        monster_img.paintIcon(this, g, x + ins.left, y + ins.top); // 繪出怪獸圖片
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
        if (x < targetX) {
            x += 5;
        } else if (x > targetX) {
            x -= 5;
        }

        if (y < targetY) {
            y += 5;
        } else if (y > targetY) {
            y -= 5;
        }

        repaint(); // 重畫面板
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}*/
