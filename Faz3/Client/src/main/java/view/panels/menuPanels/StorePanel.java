package view.panels.menuPanels;

import controller.Collision;
import model.Epsilon;
import view.panels.MyPanel;
import view.panels.gamePanels.FirstPanel;

import javax.swing.*;
import java.awt.*;

import static controller.Util.Constant.*;

public final class StorePanel extends MyPanel {
    private final static StorePanel storePanel = new StorePanel();

    private StorePanel() {
        super();
        this.setSize(STORE_PANEL_SIZE);
        this.setLocation(STORE_PANEL_LOCATION);
        this.setContent();
    }

    public static StorePanel getInstance() {
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
        JLabel XP = new JLabel();
        XP.setForeground(Color.WHITE);
        XP.setFont(new Font("", Font.BOLD, 25));
        XP.setText("☘︎ : " + Epsilon.getInstance().getXP());
        XP.setBounds(350, 150, 100, 50);
        add(XP);
    }

    private void setApolloHealButton() {
        JButton apolloHeal = new JButton("Apollo Heal");
        apolloHeal.setBounds(50, 300, 200, 50);
        apolloHeal.setFont(new Font("", Font.BOLD, 15));
        apolloHeal.addActionListener(e -> {
            if (Epsilon.getInstance().getXP() >= 50) {
                Epsilon.getInstance().changeXP(-50);
                Epsilon.getInstance().damage(-10);
                setVisible(false);
                FirstPanel.getInstance().resumeGamePanel();
            }
        });
        add(apolloHeal);
    }

    private void setAthenaEmpowerButton() {
        JButton athenaEmpower = new JButton("Athena Empower");
        athenaEmpower.setBounds(300, 300, 200, 50);
        athenaEmpower.setFont(new Font("", Font.BOLD, 15));
        athenaEmpower.addActionListener(e -> {
            if (Epsilon.getInstance().getXP() >= 75) {
                Epsilon.getInstance().changeXP(-75);
                FirstPanel.getInstance().setAthenaEmpower(true);
                Timer timer = new Timer(10000, a -> FirstPanel.getInstance().setAthenaEmpower(false));
                timer.setRepeats(false);
                timer.start();
                setVisible(false);
                FirstPanel.getInstance().resumeGamePanel();
            }
        });
        add(athenaEmpower);
    }

    private void setHephaestusBanishButton() {
        JButton hephaestusBanish = new JButton("Hephaestus Banish");
        hephaestusBanish.setBounds(550, 300, 200, 50);
        hephaestusBanish.setFont(new Font("", Font.BOLD, 15));
        hephaestusBanish.addActionListener(e -> {
            if (Epsilon.getInstance().getXP() >= 100) {
                Epsilon.getInstance().changeXP(-100);
                makeImpact();
                setVisible(false);
                FirstPanel.getInstance().resumeGamePanel();
            }
        });
        add(hephaestusBanish);
    }

    private static void makeImpact() {
        Timer timer = new Timer(500, a -> {
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
        JButton back = new JButton("back");
        back.setBounds(40, 40, 80, 50);
        back.setFont(new Font("", Font.BOLD, 20));
        back.addActionListener(e -> {
            setVisible(false);
            FirstPanel.getInstance().resumeGamePanel();
        });
        add(back);
    }
}
