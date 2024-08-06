import connection.ClientManager;
import connection.User;
import controller.GameManager;

public class ClientMain {
    public static void main(String[] args) {
        User.getInstance();
        ClientManager.getInstance().makeSocket();
        ClientManager.getInstance().start();
        GameManager.getInstance();
    }
}
//private void setStartButton() {
//    JButton startButton = new JButton("Start");
//    startButton.setBounds(730, 130, 150, 80);
//    startButton.setFont(new Font("", Font.BOLD, 20));
//    startButton.addActionListener(e -> {
//        setVisible(false);
//        MyFrame.getInstance().remove(this);
//        MyFrame.getInstance().revalidate();
//        FirstPanel.getInstance().openGame();
//        Collision.startCheckCollision();
//        EnemyBuilder.build(1);
//        Update.updatePaint();
//    });
//    add(startButton);
//}
//
//private void setSettingButton() {
//    JButton settingButton = new JButton("Settings");
//    settingButton.setBounds(530, 280, 150, 80);
//    settingButton.setFont(new Font("", Font.BOLD, 20));
//    settingButton.addActionListener(e -> {
//        setVisible(false);
//        MyFrame.getInstance().remove(this);
//        MyFrame.getInstance().revalidate();
//        MyFrame.getInstance().add(SettingsPanel.getInstance());
//        SettingsPanel.getInstance().setVisible(true);
//    });
//    add(settingButton);
//}
//
//private void setTutorialButton() {
//    JButton tutorialButton = new JButton("Tutorial");
//    tutorialButton.setBounds(330, 430, 150, 80);
//    tutorialButton.setFont(new Font("", Font.BOLD, 20));
//    tutorialButton.addActionListener(e -> {
//        String tutorial = """
//                    use "WASD" to move epsilon
//                    use "LMB" to shoot at enemies
//                    use "P" to open store""";
//        JOptionPane.showMessageDialog(this, tutorial, "tutorial", JOptionPane.PLAIN_MESSAGE);
//    });
//    add(tutorialButton);
//}
//
//private void setSkillTreeButton() {
//    JButton skillTreeButton = new JButton("Skill Tree");
//    skillTreeButton.setBounds(130, 580, 150, 80);
//    skillTreeButton.setFont(new Font("", Font.BOLD, 20));
//    skillTreeButton.addActionListener(e -> System.out.println("doesn't implement"));
//    add(skillTreeButton);
//}
//
//private void setSquadModeButton() {
//    JButton squadMode = new JButton("Squad Mode");
//    squadMode.setBounds(730, 580, 150, 80);
//    squadMode.setFont(new Font("", Font.BOLD, 20));
//    squadMode.addActionListener(e -> {
//        if (ClientManager.getInstance().isConnecting()) {
//            setVisible(false);
//            MyFrame.getInstance().remove(this);
//            MyFrame.getInstance().revalidate();
//            MyFrame.getInstance().add(SquadPanel.getInstance());
//            SquadPanel.getInstance().setVisible(true);
//        } else {
//            JOptionPane.showMessageDialog(MyFrame.getInstance(),
//                    "Connection refused!",
//                    "Connection Status",
//                    JOptionPane.ERROR_MESSAGE);
//        }
//    });
//    add(squadMode);
//}