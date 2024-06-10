import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class store extends JPanel implements ActionListener {
    private JButton storebtn;
    private JDialog dialog=new JDialog();
    private JLabel money = new JLabel("money: ");
    private JButton item1; 
    private JButton item2; 
    private JLabel itemNum = new JLabel("item1: 0" + " item2: 0"); 

    private backpage backpage;      // 道具欄
    private player p;

    public store(backpage backpage,player p){
        this.p=p;
        this.backpage = backpage;    // 道具欄
        setFocusable(true);
        setLayout(new FlowLayout(FlowLayout.RIGHT));        
        storebtn = new JButton(new ImageIcon("yellow1.gif"));       //商店按鈕
        storebtn.setToolTipText("商店");
        storebtn.addActionListener(this);
        storebtn.setBounds(600,10, 10, 10);
        add(storebtn);
        
        // 初始化 JDialog，但不添加到 JPanel 中
        dialog = new JDialog((Frame) null, "store", true);
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JLabel("This is the menu & store dialog."));
        
        //跳出來的視窗裡面有的東西------------------------------------------------------------------
        //金錢
        dialog.add(money, BorderLayout.NORTH);

        //item 數量
        itemNum.setAlignmentX(BOTTOM_ALIGNMENT);
        dialog.add(itemNum, BorderLayout.SOUTH);

        JPanel itemsPanel = new JPanel(); 
        itemsPanel.setLayout(new GridLayout(1, 2));

        item1 = new JButton(new ImageIcon("item1.png")); // Initialize item1
        item1.setToolTipText("0變1");
        item1.addActionListener(this);
        itemsPanel.add(item1);
        item2 = new JButton(new ImageIcon("item2.png"));
        item2.setToolTipText("1變0");
        item2.addActionListener(this);
        itemsPanel.add(item2);

        dialog.add(itemsPanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == storebtn ){
            updateMoneyLabel();
            
            //跳出選單視窗
            dialog.setVisible(true);
        }
        else if(evt.getSource() == item1){
            buyItem1();
            updateMoneyLabel();
        }
        else if(evt.getSource() == item2){
            buyItem2();
            updateMoneyLabel();
        }
    }
    public void updateMoneyLabel(){         // 更新金錢數量
        int coinSum = player.getCoinSum();
        System.out.println("coinSum from player: " + coinSum);
        money.setText("money: " + coinSum);
    }

    public void buyItem1(){
        int coinSum = player.getCoinSum();
        //設 item 1 的價格為 10
        if (coinSum >= 2){                 
            player.setCoinSum(coinSum - 2);
            backpage.item1Num++;    
            p.item1_count++;        
            itemNum.setText("item1: " + backpage.item1Num + " item2: " + backpage.item2Num);
        }
    }
    public void buyItem2(){
        int coinSum = player.getCoinSum();
        //設 item 2 的價格為 10
        if (coinSum >= 2){                 
            player.setCoinSum(coinSum - 2);
            backpage.item2Num++;
            p.item2_count++;
            itemNum.setText("item1: " + backpage.item1Num + " item2: " + backpage.item2Num);
        }
    }
}
