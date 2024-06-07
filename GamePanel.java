import javax.swing.*;
import java.awt.*;

public class GamePanel extends JFrame {
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

        // 确保窗口可见后请求焦点
        SwingUtilities.invokeLater(() -> {
            character.requestFocusInWindow();
        });
    }

    public static void main(String[] args) {
        GamePanel app = new GamePanel();
        app.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        app.setSize(1200, 600);
        app.setVisible(true);
    }
}
