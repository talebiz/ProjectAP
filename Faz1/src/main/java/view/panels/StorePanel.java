package view.panels;

import controller.Collision;
import model.Epsilon;

import javax.swing.*;
import java.awt.*;

import static controller.Util.Constant.*;

public class StorePanel extends MyPanel {
    private static StorePanel storePanel;
    private JButton hephaestusBanish, athenaEmpower, apolloHeal, back;
    private JLabel XP;

    private StorePanel() {
        super();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setSize(STORE_PANEL_SIZE);
        this.setLocation(STORE_PANEL_LOCATION);
        this.setContent();
    }

    public static StorePanel getInstance() {
        if (storePanel == null) storePanel = new StorePanel();
        return storePanel;
    }

    @Override
    protected void setContent() {
        setXpLabel();
        setApolloHealButton();
        setAthenaEmpowerButton();
        setHephaestusBanishButton();
        setBackButton();
    }

    private void setXpLabel() {
        XP = new JLabel();
        XP.setForeground(Color.WHITE);
        XP.setFont(new Font("", Font.BOLD, 25));
        XP.setText("☘︎ : " + Epsilon.getInstance().getXP());
        XP.setBounds(350, 150, 100, 50);
        add(XP);
    }

    private void setApolloHealButton() {
        apolloHeal = new JButton("Apollo Heal");
        apolloHeal.setBounds(50, 300, 200, 50);
        apolloHeal.setFont(new Font("", Font.BOLD, 15));
        apolloHeal.addActionListener(e -> {
            if (Epsilon.getInstance().getXP() >= 50) {
                Epsilon.getInstance().takeXP(-50);
                Epsilon.getInstance().damage(-10);
                this.setVisible(false);
                GamePanel.getInstance().setBounds(GAME_PANEL_X, GAME_PANEL_Y, GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
                GamePanel.getInstance().resumeGamePanel();
            }
        });
        add(apolloHeal);
    }

    private void setAthenaEmpowerButton() {
        athenaEmpower = new JButton("Athena Empower");
        athenaEmpower.setBounds(300, 300, 200, 50);
        athenaEmpower.setFont(new Font("", Font.BOLD, 15));
        athenaEmpower.addActionListener(e -> {
            if (Epsilon.getInstance().getXP() >= 75) {
                Epsilon.getInstance().takeXP(-75);
                GamePanel.getInstance().setAthenaEmpower(true);
                Timer timer = new Timer(10000, a -> GamePanel.getInstance().setAthenaEmpower(false));
                timer.setRepeats(false);
                timer.start();
                this.setVisible(false);
                GamePanel.getInstance().setBounds(GAME_PANEL_X, GAME_PANEL_Y, GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
                GamePanel.getInstance().resumeGamePanel();
            }
        });
        add(athenaEmpower);
    }

    private void setHephaestusBanishButton() {
        hephaestusBanish = new JButton("Hephaestus Banish");
        hephaestusBanish.setBounds(550, 300, 200, 50);
        hephaestusBanish.setFont(new Font("", Font.BOLD, 15));
        hephaestusBanish.addActionListener(e -> {
            if (Epsilon.getInstance().getXP() >= 100) {
                Epsilon.getInstance().takeXP(-100);
                makeImpact();
                this.setVisible(false);
                GamePanel.getInstance().setBounds(GAME_PANEL_X, GAME_PANEL_Y, GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
                GamePanel.getInstance().resumeGamePanel();
            }
        });
        add(hephaestusBanish);
    }

    private static void makeImpact() {
        Timer timer = new Timer(100, a -> {
            Collision.makeImpact(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), null);
            Collision.makeImpact(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), null);
            Collision.makeImpact(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), null);
            Collision.makeImpact(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), null);
            Collision.makeImpact(Epsilon.getInstance().getX(), Epsilon.getInstance().getY(), null);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void setBackButton() {
        back = new JButton("back");
        back.setBounds(40, 40, 80, 50);
        back.setFont(new Font("", Font.BOLD, 20));
        back.addActionListener(e -> {
            this.setVisible(false);
            GamePanel.getInstance().setBounds(GAME_PANEL_X, GAME_PANEL_Y, GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
            GamePanel.getInstance().resumeGamePanel();
        });
        add(back);
    }
}
