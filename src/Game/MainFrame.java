package Game;
import Game.Characters.Enemy;
import Game.Characters.Character;
import Game.Characters.Warrior;
import Game.Encounters.FightEnemy;
import Game.Encounters.GeneralEncounter;
import Game.Spells.Empty;
import Game.Spells.Fireball;
import Game.Spells.Lesser_heal;
import Game.Spells.Spell;


import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Objects;


public class MainFrame implements ActionListener {


    private JFrame frame;
    private JLabel chooseAttackMenu;
    private JLabel chooseSpellTargetMenu;
    private JLabel chooseSpellsMenu;
    private JButton Spell0;
    private JButton Spell1;
    private JButton Spell2;
    private JButton Spell3;
    private JButton Spell4;
    private JButton Spell5;
    private JButton Spell6;
    private JButton Spell7;
    private JButton Spell8;
    private JButton Spell9;
    private JButton attackMonster1;
    private JButton attackMonster2;
    private JButton attackMonster3;
    private JButton spellMonster1;
    private JButton spellMonster2;
    private JButton spellMonster3;
    private int castedSpell;

    protected JButton attack;
    protected JButton defend;
    protected JButton spells;
    protected JButton items;

    protected JLabel playerHealth;
    protected JLabel playerMana;
    protected JLabel monster1Health;
    protected JLabel monster1Mana;
    protected JLabel Graphic1;
    protected JLabel monster2Health;
    protected JLabel monster2Mana;
    protected JLabel Graphic2;
    protected JLabel monster3Health;
    protected JLabel monster3Mana;
    protected JLabel Graphic3;

    private int screenWidth;
    private int screenHeight;

    private boolean playerTurn;
    private Character Player;

    private Container panel;
    private Container mainMenuPanel;

    private Enemy Monster1;
    private Enemy Monster2;
    private Enemy Monster3;

    private SpringLayout layout;
    private SpringLayout attackLayout;
    private SpringLayout spellsLayout;
    private SpringLayout spellTargetLayout;

    private Container pathsPanel;
    private SpringLayout pathsLayout;
    private ActionListener pathListener1;
    private ActionListener pathListener2;
    private JButton path1;
    private JButton path2;
    private JButton path3;

    private GeneralEncounter generalEncounter1;
    private GeneralEncounter generalEncounter2;
    private FightEnemy fight;

    protected Empty empty;
    protected int TurnCounter=0;


    public MainFrame() {
        initialize();
    }
    public void initialize() {
        frame = new JFrame();
        frame.setTitle("Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);

        Rectangle r = frame.getBounds();
        screenWidth = r.width;
        screenHeight = r.height;

        mainMenuPanel = frame.getContentPane();

        ImageIcon background = new ImageIcon("../Gra/images/MainMenu.jpg");
        Image img = background.getImage();
        Image temp = img.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        background = new ImageIcon(temp);
        JLabel back = new JLabel(background);
        back.setLayout(null);
        back.setBounds(0, 0, screenWidth, screenHeight);

        JButton start = new JButton();
        start.setPreferredSize(new Dimension(300, 200));
        start.setIcon(new ImageIcon("../Gra/images/attackButton.png"));

        JButton settings = new JButton();
        settings.setPreferredSize(new Dimension(300, 200));
        settings.setIcon(new ImageIcon("../Gra/images/defendButton.png"));

        JButton exit = new JButton();
        exit.setPreferredSize(new Dimension(300, 200));
        exit.setIcon(new ImageIcon("../Gra/images/spellButton.png"));

        SpringLayout mainMenuLayout = new SpringLayout();
        assert mainMenuPanel != null;
        mainMenuPanel.setLayout(mainMenuLayout);


        mainMenuPanel.add(start);
        mainMenuPanel.add(settings);
        mainMenuPanel.add(exit);

        mainMenuPanel.add(back);

        frame.setContentPane(mainMenuPanel);


        mainMenuLayout.putConstraint(SpringLayout.NORTH, start, 150, SpringLayout.NORTH, mainMenuPanel);
        mainMenuLayout.putConstraint(SpringLayout.NORTH, settings, 50, SpringLayout.SOUTH, start);
        mainMenuLayout.putConstraint(SpringLayout.NORTH, exit, 50, SpringLayout.SOUTH, settings);

        mainMenuLayout.putConstraint(SpringLayout.EAST, start, -760, SpringLayout.EAST, mainMenuPanel);
        mainMenuLayout.putConstraint(SpringLayout.EAST, settings, -760, SpringLayout.EAST, mainMenuPanel);
        mainMenuLayout.putConstraint(SpringLayout.EAST, exit, -760, SpringLayout.EAST, mainMenuPanel);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back.setVisible(false);
                mainMenuPanel.setVisible(false);
                start.setVisible(false);
                settings.setVisible(false);
                exit.setVisible(false);

                Player = new Warrior();
                for(int i=0;i<Player.spellsLibrary.length;i++) {
                    Player.AddSpell(empty);
                }
                Fireball fireball = new Fireball();
                Player.AddSpell(fireball);
                Lesser_heal lesser_heal = new Lesser_heal();
                Player.AddSpell(lesser_heal);


                generalEncounter1 = new GeneralEncounter("Fight", "1");
                generalEncounter2 = new GeneralEncounter("Fight", "2");
                renderPaths(generalEncounter1, generalEncounter2);

            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });


    }
    public void updateBars(FightEnemy Fight){
        playerHealth.setText(Double.toString(Player.GetHP()));
        playerMana.setText(Double.toString(Player.GetMana()));
        if(!Objects.equals(Fight.slot1.GetName(), "EmptyEnemy")){
            monster1Health.setText(Double.toString(Fight.slot1.GetHP()));
            monster1Mana.setText(Double.toString(Fight.slot1.GetMana()));
        }
        if(!Objects.equals(Fight.slot2.GetName(), "EmptyEnemy")){
            monster2Health.setText(Double.toString(Fight.slot2.GetHP()));
            monster2Mana.setText(Double.toString(Fight.slot2.GetMana()));
        }
        if(!Objects.equals(Fight.slot3.GetName(), "EmptyEnemy")){
            monster3Health.setText(Double.toString(Fight.slot3.GetHP()));
            monster3Mana.setText(Double.toString(Fight.slot3.GetMana()));
        }
    }


    public void checkTurn(FightEnemy Fight){
        if(playerTurn){
            TurnCounter=+1;
            updateBars(Fight);
            if(Player.GetHP() <=0){
                Player.dead=true;
            }
            pTurn();
        }else{
            updateBars(Fight);
            if(Monster1.GetHP() <=0){
                Monster1.dead=true;
            }
            if (Monster2.GetHP() <= 0) {
                Monster2.dead=true;
            }
            if(Monster3.GetHP() <=0){
                Monster3.dead=true;

            }
            PcTurn();
        }
    }


    public void show(){
        frame.setVisible(true);
    }


    public void renderPaths(GeneralEncounter generalEncounter1,GeneralEncounter generalEncounter2){
        ImageIcon background = new ImageIcon("../Gra/images/BackgroundPaths.png");
        Image img = background.getImage();
        Image temp = img.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        background = new ImageIcon(temp);
        JLabel back = new JLabel(background);
        back.setLayout(null);
        back.setBounds(0, 0, screenWidth, screenHeight);

        pathsPanel = new Container();
        frame.setContentPane(pathsPanel);

        path1 = new JButton();
        path1.setPreferredSize(new Dimension(300, 500));
        path1.setIcon(new ImageIcon("../Gra/images/attackButton.png"));

        path2 = new JButton();
        path2.setPreferredSize(new Dimension(300, 500));
        path2.setIcon(new ImageIcon("../Gra/images/defendButton.png"));

        path3 = new JButton();
        path3.setPreferredSize(new Dimension(300, 500));
        path3.setIcon(new ImageIcon("../Gra/images/spellButton.png"));

        pathsLayout = new SpringLayout();
        assert pathsPanel != null;
        pathsPanel.setLayout(pathsLayout);


        pathsPanel.add(path1);
        pathsPanel.add(path2);
        pathsPanel.add(path3);

        pathsPanel.add(back);

        pathsLayout.putConstraint(SpringLayout.NORTH, path1, 350, SpringLayout.NORTH, pathsPanel);
        pathsLayout.putConstraint(SpringLayout.NORTH, path2, 350, SpringLayout.NORTH, pathsPanel);
        pathsLayout.putConstraint(SpringLayout.NORTH, path3, 350, SpringLayout.NORTH, pathsPanel);

        pathsLayout.putConstraint(SpringLayout.EAST, path1, 550, SpringLayout.WEST, pathsPanel);
        pathsLayout.putConstraint(SpringLayout.EAST, path2, 550, SpringLayout.EAST, path1);
        pathsLayout.putConstraint(SpringLayout.EAST, path3, 550, SpringLayout.EAST, path2);


        pathListener1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(generalEncounter1.Type, "Fight")){
                    fight = new FightEnemy(generalEncounter1.Number);
                    back.setVisible(false);
                    pathsPanel.setVisible(false);
                    path1.setVisible(false);
                    path2.setVisible(false);
                    path3.setVisible(false);
                    renderFight(fight);
                }
                pTurn();
            }
        };
        path1.addActionListener(pathListener1);

        pathListener2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(generalEncounter2.Type, "Fight")){
                    fight = new FightEnemy(generalEncounter2.Number);
                    System.out.println(generalEncounter2.Number);
                    back.setVisible(false);
                    pathsPanel.setVisible(false);
                    path1.setVisible(false);
                    path2.setVisible(false);
                    path3.setVisible(false);
                    renderFight(fight);
                }
                pTurn();
            }
        };
        path2.addActionListener(pathListener2);
    }

    public void renderFight(FightEnemy Fight){
        path1.removeActionListener(pathListener1);
        path2.removeActionListener(pathListener2);
        ImageIcon background = new ImageIcon("../Gra/images/Background.png");
        Image img = background.getImage();
        Image temp = img.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
        background = new ImageIcon(temp);
        JLabel back = new JLabel(background);
        back.setLayout(null);
        back.setBounds(0, 0, screenWidth, screenHeight);

        Monster1 = Fight.slot1;
        Monster2 = Fight.slot2;
        Monster3 = Fight.slot3;

        panel = new Container();
        frame.setContentPane(panel);

        attack = new JButton();
        attack.setPreferredSize(new Dimension(230, 60));
        attack.setIcon(new ImageIcon("../Gra/images/attackButton.png"));

        defend = new JButton();
        defend.setPreferredSize(new Dimension(230, 60));
        defend.setIcon(new ImageIcon("../Gra/images/defendButton.png"));

        spells = new JButton();
        spells.setPreferredSize(new Dimension(230, 60));
        spells.setIcon(new ImageIcon("../Gra/images/spellButton.png"));

        items = new JButton();
        items.setPreferredSize(new Dimension(230, 60));
        items.setIcon(new ImageIcon("../Gra/images/itemButton.png"));

        //

        playerHealth = new JLabel();
        playerHealth.setPreferredSize(new Dimension(100,30));
        playerHealth.setIcon(new ImageIcon("../Gra/images/healthBar.png"));
        playerHealth.setText(Double.toString(Player.GetHP()));
        playerHealth.setVerticalTextPosition(JLabel.CENTER);
        playerHealth.setHorizontalTextPosition(JLabel.CENTER);

        playerMana = new JLabel();
        playerMana.setPreferredSize(new Dimension(100,30));
        playerMana.setIcon(new ImageIcon("../Gra/images/manaBar.png"));
        playerMana.setText(Double.toString(Player.GetMana()));
        playerMana.setVerticalTextPosition(JLabel.CENTER);
        playerMana.setHorizontalTextPosition(JLabel.CENTER);

        Graphic1 = Monster1.Graphic;

        monster1Health = new JLabel();
        monster1Health.setPreferredSize(new Dimension(100,30));
        monster1Health.setIcon(new ImageIcon("../Gra/images/healthBar.png"));
        monster1Health.setText(Double.toString(Fight.slot1.GetHP()));
        monster1Health.setVerticalTextPosition(JLabel.CENTER);
        monster1Health.setHorizontalTextPosition(JLabel.CENTER);

        monster1Mana = new JLabel();
        monster1Mana.setPreferredSize(new Dimension(100,30));
        monster1Mana.setIcon(new ImageIcon("../Gra/images/manaBar.png"));
        monster1Mana.setText(Double.toString(Fight.slot1.GetMana()));
        monster1Mana.setVerticalTextPosition(JLabel.CENTER);
        monster1Mana.setHorizontalTextPosition(JLabel.CENTER);

        Graphic2 = Monster2.Graphic;

        monster2Health = new JLabel();
        monster2Health.setPreferredSize(new Dimension(100,30));
        monster2Health.setIcon(new ImageIcon("../Gra/images/healthBar.png"));
        monster2Health.setText(Double.toString(Fight.slot2.GetHP()));
        monster2Health.setVerticalTextPosition(JLabel.CENTER);
        monster2Health.setHorizontalTextPosition(JLabel.CENTER);

        monster2Mana = new JLabel();
        monster2Mana.setPreferredSize(new Dimension(100,30));
        monster2Mana.setIcon(new ImageIcon("../Gra/images/manaBar.png"));
        monster2Mana.setText(Double.toString(Fight.slot2.GetMana()));
        monster2Mana.setVerticalTextPosition(JLabel.CENTER);
        monster2Mana.setHorizontalTextPosition(JLabel.CENTER);

        Graphic3 = Monster3.Graphic;

        monster3Health = new JLabel();
        monster3Health.setPreferredSize(new Dimension(100,30));
        monster3Health.setIcon(new ImageIcon("../Gra/images/healthBar.png"));
        monster3Health.setText(Double.toString(Fight.slot3.GetHP()));
        monster3Health.setVerticalTextPosition(JLabel.CENTER);
        monster3Health.setHorizontalTextPosition(JLabel.CENTER);

        monster3Mana = new JLabel();
        monster3Mana.setPreferredSize(new Dimension(100,30));
        monster3Mana.setIcon(new ImageIcon("../Gra/images/manaBar.png"));
        monster3Mana.setText(Double.toString(Fight.slot3.GetMana()));
        monster3Mana.setVerticalTextPosition(JLabel.CENTER);
        monster3Mana.setHorizontalTextPosition(JLabel.CENTER);

        //

        chooseAttackMenu = new JLabel();
        chooseAttackMenu.setPreferredSize(new Dimension(1650,325));
        chooseAttackMenu.setIcon(new ImageIcon("../Gra/images/chooseMenu.png"));

        attackMonster1 = new JButton();
        attackMonster1.setPreferredSize(new Dimension(200,60));
        attackMonster1.setText(Fight.slot1.GetName());
        attackMonster1.setVerticalTextPosition(JMenuItem.CENTER);
        attackMonster1.setHorizontalTextPosition(JMenuItem.CENTER);

        attackMonster2 = new JButton();
        attackMonster2.setPreferredSize(new Dimension(200,60));
        attackMonster2.setText(Fight.slot2.GetName());
        attackMonster2.setVerticalTextPosition(JMenuItem.CENTER);
        attackMonster2.setHorizontalTextPosition(JMenuItem.CENTER);

        attackMonster3 = new JButton();
        attackMonster3.setPreferredSize(new Dimension(200,60));
        attackMonster3.setText(Fight.slot3.GetName());
        attackMonster3.setVerticalTextPosition(JMenuItem.CENTER);
        attackMonster3.setHorizontalTextPosition(JMenuItem.CENTER);

        spellMonster1 = new JButton();
        spellMonster1.setPreferredSize(new Dimension(200,60));
        spellMonster1.setText(Fight.slot1.GetName());
        spellMonster1.setVerticalTextPosition(JMenuItem.CENTER);
        spellMonster1.setHorizontalTextPosition(JMenuItem.CENTER);

        spellMonster2 = new JButton();
        spellMonster2.setPreferredSize(new Dimension(200,60));
        spellMonster2.setText(Fight.slot2.GetName());
        spellMonster2.setVerticalTextPosition(JMenuItem.CENTER);
        spellMonster2.setHorizontalTextPosition(JMenuItem.CENTER);

        spellMonster3 = new JButton();
        spellMonster3.setPreferredSize(new Dimension(200,60));
        spellMonster3.setText(Fight.slot3.GetName());
        spellMonster3.setVerticalTextPosition(JMenuItem.CENTER);
        spellMonster3.setHorizontalTextPosition(JMenuItem.CENTER);

        chooseSpellsMenu = new JLabel();
        chooseSpellsMenu.setPreferredSize(new Dimension(1650,325));
        chooseSpellsMenu.setIcon(new ImageIcon("../Gra/images/chooseMenu.png"));

        chooseSpellTargetMenu = new JLabel();
        chooseSpellTargetMenu.setPreferredSize(new Dimension(1650,325));
        chooseSpellTargetMenu.setIcon(new ImageIcon("../Gra/images/chooseMenu.png"));

        Spell0 = new JButton();
        Spell0.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[0]!=empty) {
            Spell0.setText(Player.spellsLibrary[0].GetName());
        }else {
            Spell0.setVisible(false);
        }
        Spell0.setVerticalTextPosition(JMenuItem.CENTER);
        Spell0.setHorizontalTextPosition(JMenuItem.CENTER);

        Spell1 = new JButton();
        Spell1.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[1]!=empty) {
            Spell1.setText(Player.spellsLibrary[1].GetName());
        }else {
            Spell1.setVisible(false);
        }
        Spell1.setVerticalTextPosition(JMenuItem.CENTER);
        Spell1.setHorizontalTextPosition(JMenuItem.CENTER);

        Spell2 = new JButton();
        Spell2.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[2]!=empty) {
            Spell2.setText(Player.spellsLibrary[2].GetName());
        }else {
            Spell2.setVisible(false);
        }
        Spell2.setVerticalTextPosition(JMenuItem.CENTER);
        Spell2.setHorizontalTextPosition(JMenuItem.CENTER);

        Spell3 = new JButton();
        Spell3.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[3]!=empty) {
            Spell3.setText(Player.spellsLibrary[3].GetName());
        }else {
            Spell3.setVisible(false);
        }
        Spell3.setVerticalTextPosition(JMenuItem.CENTER);
        Spell3.setHorizontalTextPosition(JMenuItem.CENTER);

        Spell4 = new JButton();
        Spell4.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[4]!=empty) {
            Spell4.setText(Player.spellsLibrary[4].GetName());
        }else {
            Spell4.setVisible(false);
        }
        Spell4.setVerticalTextPosition(JMenuItem.CENTER);
        Spell4.setHorizontalTextPosition(JMenuItem.CENTER);

        Spell5 = new JButton();
        Spell5.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[5]!=empty) {
            Spell5.setText(Player.spellsLibrary[5].GetName());
        }else {
            Spell5.setVisible(false);
        }
        Spell5.setVerticalTextPosition(JMenuItem.CENTER);
        Spell5.setHorizontalTextPosition(JMenuItem.CENTER);

        Spell6 = new JButton();
        Spell6.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[6]!=empty) {
            Spell6.setText(Player.spellsLibrary[6].GetName());
        }else {
            Spell6.setVisible(false);
        }
        Spell6.setVerticalTextPosition(JMenuItem.CENTER);
        Spell6.setHorizontalTextPosition(JMenuItem.CENTER);

        Spell7 = new JButton();
        Spell7.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[7]!=empty) {
            Spell7.setText(Player.spellsLibrary[7].GetName());
        }else {
            Spell7.setVisible(false);
        }
        Spell7.setVerticalTextPosition(JMenuItem.CENTER);
        Spell7.setHorizontalTextPosition(JMenuItem.CENTER);

        Spell8 = new JButton();
        Spell8.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[8]!=empty) {
            Spell8.setText(Player.spellsLibrary[8].GetName());
        }else {
            Spell8.setVisible(false);
        }
        Spell8.setVerticalTextPosition(JMenuItem.CENTER);
        Spell8.setHorizontalTextPosition(JMenuItem.CENTER);

        Spell9 = new JButton();
        Spell9.setPreferredSize(new Dimension(200,60));
        if(Player.spellsLibrary[9]!=empty) {
            Spell9.setText(Player.spellsLibrary[9].GetName());
        }else {
            Spell9.setVisible(false);
        }
        Spell9.setVerticalTextPosition(JMenuItem.CENTER);
        Spell9.setHorizontalTextPosition(JMenuItem.CENTER);

        //

        attackLayout = new SpringLayout();
        assert chooseAttackMenu != null;
        chooseAttackMenu.setLayout(attackLayout);

        attackLayout.putConstraint(SpringLayout.NORTH, attackMonster2, 10, SpringLayout.NORTH, chooseAttackMenu);
        attackLayout.putConstraint(SpringLayout.NORTH, attackMonster1, 10, SpringLayout.NORTH, chooseAttackMenu);
        attackLayout.putConstraint(SpringLayout.NORTH, attackMonster3, 10, SpringLayout.NORTH, chooseAttackMenu);



        attackLayout.putConstraint(SpringLayout.WEST, attackMonster2, 10, SpringLayout.WEST, chooseAttackMenu);
        attackLayout.putConstraint(SpringLayout.WEST, attackMonster1, 320, SpringLayout.WEST, attackMonster2);
        attackLayout.putConstraint(SpringLayout.WEST, attackMonster3, 320, SpringLayout.WEST, attackMonster1);

        //

        spellsLayout = new SpringLayout();
        assert chooseSpellsMenu != null;
        chooseSpellsMenu.setLayout(spellsLayout);

        spellsLayout.putConstraint(SpringLayout.NORTH,Spell0, 10, SpringLayout.NORTH, chooseSpellsMenu);
        spellsLayout.putConstraint(SpringLayout.NORTH,Spell1, 10, SpringLayout.NORTH, chooseSpellsMenu);
        spellsLayout.putConstraint(SpringLayout.NORTH,Spell2, 10, SpringLayout.NORTH, chooseSpellsMenu);
        spellsLayout.putConstraint(SpringLayout.NORTH,Spell3, 10, SpringLayout.NORTH, chooseSpellsMenu);
        spellsLayout.putConstraint(SpringLayout.NORTH,Spell4, 10, SpringLayout.NORTH, chooseSpellsMenu);


        spellsLayout.putConstraint(SpringLayout.WEST, Spell0, 10, SpringLayout.WEST, chooseSpellsMenu);
        spellsLayout.putConstraint(SpringLayout.WEST, Spell1, 210, SpringLayout.WEST, Spell0);
        spellsLayout.putConstraint(SpringLayout.WEST, Spell2, 210, SpringLayout.WEST, Spell1);
        spellsLayout.putConstraint(SpringLayout.WEST, Spell3, 210, SpringLayout.WEST, Spell2);
        spellsLayout.putConstraint(SpringLayout.WEST, Spell4, 210, SpringLayout.WEST, Spell3);

        //

        spellTargetLayout = new SpringLayout();
        assert spellTargetLayout != null;
        chooseSpellTargetMenu.setLayout(spellTargetLayout);

        spellTargetLayout.putConstraint(SpringLayout.NORTH,spellMonster2, 10, SpringLayout.NORTH, chooseSpellTargetMenu);
        spellTargetLayout.putConstraint(SpringLayout.NORTH,spellMonster1, 10, SpringLayout.NORTH, chooseSpellTargetMenu);
        spellTargetLayout.putConstraint(SpringLayout.NORTH,spellMonster3, 10, SpringLayout.NORTH, chooseSpellTargetMenu);


        spellTargetLayout.putConstraint(SpringLayout.WEST, spellMonster2, 10, SpringLayout.WEST, chooseSpellTargetMenu);
        spellTargetLayout.putConstraint(SpringLayout.WEST, spellMonster1, 320, SpringLayout.WEST, spellMonster2);
        spellTargetLayout.putConstraint(SpringLayout.WEST, spellMonster3, 320, SpringLayout.WEST, spellMonster1);

        //

        chooseAttackMenu.setVisible(false);
        chooseSpellsMenu.setVisible(false);
        chooseSpellTargetMenu.setVisible(false);

        layout = new SpringLayout();
        assert panel != null;
        panel.setLayout(layout);

        panel.add(attack);
        panel.add(defend);
        panel.add(spells);
        panel.add(items);
        panel.add(playerHealth);
        panel.add(playerMana);
        panel.add(Graphic1);
        panel.add(monster1Health);
        panel.add(monster1Mana);
        panel.add(Graphic2);
        panel.add(monster2Health);
        panel.add(monster2Mana);
        panel.add(Graphic3);
        panel.add(monster3Health);
        panel.add(monster3Mana);
        panel.add(chooseAttackMenu);
        panel.add(chooseSpellsMenu);
        panel.add(chooseSpellTargetMenu);

        panel.add(back);

        chooseAttackMenu.add(attackMonster1);
        chooseAttackMenu.add(attackMonster2);
        chooseAttackMenu.add(attackMonster3);

        chooseSpellsMenu.add(Spell0);
        chooseSpellsMenu.add(Spell1);
        chooseSpellsMenu.add(Spell2);
        chooseSpellsMenu.add(Spell3);
        chooseSpellsMenu.add(Spell4);

        chooseSpellTargetMenu.add(spellMonster1);
        chooseSpellTargetMenu.add(spellMonster2);
        chooseSpellTargetMenu.add(spellMonster3);

        //

        layout.putConstraint(SpringLayout.SOUTH, items, -30, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.SOUTH, spells, -20, SpringLayout.NORTH, items);
        layout.putConstraint(SpringLayout.SOUTH, defend, -20, SpringLayout.NORTH, spells);
        layout.putConstraint(SpringLayout.SOUTH, attack, -20, SpringLayout.NORTH, defend);
        layout.putConstraint(SpringLayout.SOUTH, playerHealth, -400, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.NORTH, playerMana, 10, SpringLayout.SOUTH, playerHealth);
        layout.putConstraint(SpringLayout.SOUTH, Graphic1, -310, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.SOUTH, monster1Health, -30, SpringLayout.NORTH, Graphic1);
        layout.putConstraint(SpringLayout.SOUTH, monster1Mana, 5, SpringLayout.NORTH, Graphic1);
        layout.putConstraint(SpringLayout.SOUTH, Graphic2, -340, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.SOUTH, monster2Health, -30, SpringLayout.NORTH, Graphic2);
        layout.putConstraint(SpringLayout.SOUTH, monster2Mana, 5, SpringLayout.NORTH, Graphic2);
        layout.putConstraint(SpringLayout.SOUTH, Graphic3, -370, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.SOUTH, monster3Health, -30, SpringLayout.NORTH, Graphic3);
        layout.putConstraint(SpringLayout.SOUTH, monster3Mana, 5, SpringLayout.NORTH, Graphic3);
        layout.putConstraint(SpringLayout.SOUTH, chooseAttackMenu, -5, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.SOUTH, chooseSpellsMenu, -5, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.SOUTH, chooseSpellTargetMenu, -5, SpringLayout.SOUTH, panel);



        layout.putConstraint(SpringLayout.WEST, defend, 25, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.WEST, attack, 25, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.WEST, spells, 25, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.WEST, items, 25, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, playerHealth, -25, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.EAST, playerMana, -25, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.EAST, Graphic1, -800, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.WEST, monster1Health, -30, SpringLayout.EAST, Graphic1);
        layout.putConstraint(SpringLayout.WEST, monster1Mana, -30, SpringLayout.EAST, Graphic1);
        layout.putConstraint(SpringLayout.EAST, Graphic2, -1180, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.WEST, monster2Health, -30, SpringLayout.EAST, Graphic2);
        layout.putConstraint(SpringLayout.WEST, monster2Mana, -30, SpringLayout.EAST, Graphic2);
        layout.putConstraint(SpringLayout.EAST, Graphic3, -420, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.WEST, monster3Health, -30, SpringLayout.EAST, Graphic3);
        layout.putConstraint(SpringLayout.WEST, monster3Mana, -30, SpringLayout.EAST, Graphic3);
        layout.putConstraint(SpringLayout.WEST, chooseAttackMenu, 10, SpringLayout.EAST, attack);
        layout.putConstraint(SpringLayout.WEST, chooseSpellsMenu, 10, SpringLayout.EAST, attack);
        layout.putConstraint(SpringLayout.WEST, chooseSpellTargetMenu, 10, SpringLayout.EAST, attack);

        //

        playerTurn=true;
        attack.addActionListener(new attackListener() {});
        defend.addActionListener(new defendListener() {});
        spells.addActionListener(new spellsListener());
        items.addActionListener(new itemsListener());
        attackMonster1.addActionListener(new attackMonster1Listener());
        attackMonster2.addActionListener(new attackMonster2Listener());
        attackMonster3.addActionListener(new attackMonster3Listener());
        Spell0.addActionListener(new spell0Listener());
        Spell1.addActionListener(new spell1Listener());
        spellMonster1.addActionListener(new spellMonster1Listener());
        spellMonster2.addActionListener(new spellMonster2Listener());
        spellMonster3.addActionListener(new spellMonster3Listener());

        if(Monster2.GetName() =="EmptyEnemy"){
            monster2Health.setVisible(false);
            monster2Mana.setVisible(false);
            Graphic2.setVisible(false);
            attackMonster2.setVisible(false);
            spellMonster2.setVisible(false);
        }
        if(Monster3.GetName() =="EmptyEnemy"){
            monster3Health.setVisible(false);
            monster3Mana.setVisible(false);
            Graphic3.setVisible(false);
            attackMonster3.setVisible(false);
            spellMonster3.setVisible(false);
        }
    }



    public class attackListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            chooseSpellsMenu.setVisible(false);
            chooseAttackMenu.setVisible(true);
        }
    }

    public class attackMonster1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Player.AttackAction(Player,Monster1);
            chooseAttackMenu.setVisible(false);
            playerTurn = false;
            checkTurn(fight);
        }
    }
    public class attackMonster2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Player.AttackAction(Player,Monster2);
            chooseAttackMenu.setVisible(false);
            playerTurn = false;
            checkTurn(fight);
        }
    }
    public class attackMonster3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Player.AttackAction(Player,Monster3);
            chooseAttackMenu.setVisible(false);
            playerTurn = false;
            checkTurn(fight);
        }
    }



    public class defendListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Player.SetArmor(5);
            Player.SetDefend(true);
            playerTurn = false;
            checkTurn(fight);
        }
    }



    public class spellsListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            chooseSpellsMenu.setVisible(true);
            chooseAttackMenu.setVisible(false);
            }
        }

    public class spell0Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            castedSpell=0;
            if (Player.spellsLibrary[castedSpell].type == "Heal") {
                Player.spellsLibrary[castedSpell].Heal(Player, Player);
                chooseSpellsMenu.setVisible(false);
                playerTurn = false;
                checkTurn(fight);
            } else if (Player.spellsLibrary[castedSpell].type == "Damage") {
                chooseSpellsMenu.setVisible(false);
                chooseSpellTargetMenu.setVisible(true);
            }
        }
}

    public class spell1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            castedSpell=1;
            if(Player.spellsLibrary[castedSpell].type=="Heal") {
                Player.spellsLibrary[castedSpell].Heal(Player, Player);
            }
            else if (Player.spellsLibrary[castedSpell].type=="Damage"){
                chooseSpellsMenu.setVisible(false);
                chooseSpellTargetMenu.setVisible(true);
            }
            chooseSpellsMenu.setVisible(false);
            playerTurn = false;
            checkTurn(fight);
        }
    }

    public class spellMonster1Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Player.spellsLibrary[castedSpell].Damage(Monster1, Player);
            chooseSpellTargetMenu.setVisible(false);
            playerTurn = false;
            checkTurn(fight);
        }
    }

    public class spellMonster2Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Player.spellsLibrary[castedSpell].Damage(Monster2, Player);
            chooseSpellTargetMenu.setVisible(false);
            playerTurn = false;
            checkTurn(fight);
        }
    }

    public class spellMonster3Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Player.spellsLibrary[castedSpell].Damage(Monster3, Player);
            chooseSpellTargetMenu.setVisible(false);
            playerTurn = false;
            checkTurn(fight);
        }
    }



    public class itemsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            playerTurn = false;
            checkTurn(fight);
        }
    }



    public void pTurn(){

        if (Player.dead) {
                    System.out.println("Przegrałes");
                }
                if(Player.defend){
                    Player.SetArmor(-5);
                    Player.SetDefend(false);
                }
    }

    public void PcTurn(){
        if (Monster1.dead && Monster2.dead && Monster3.dead ) {
            TurnCounter=0;
            renderPaths(generalEncounter2,generalEncounter1);
        }
        if(!Monster1.dead){
            Monster1.AttackAction(Monster1,Player);
        }else{
            monster1Health.setVisible(false);
            monster1Mana.setVisible(false);
            Graphic1.setVisible(false);
            attackMonster1.setVisible(false);
            spellMonster1.setVisible(false);
        }
        if(!Monster2.dead) {
            Monster2.AttackAction(Monster2, Player);
        }else{
            monster2Health.setVisible(false);
            monster2Mana.setVisible(false);
            Graphic2.setVisible(false);
            attackMonster2.setVisible(false);
            spellMonster2.setVisible(false);
        }
        if(!Monster3.dead) {
            Monster3.AttackAction(Monster3, Player);
        }else{
            monster3Health.setVisible(false);
            monster3Mana.setVisible(false);
            Graphic3.setVisible(false);
            attackMonster3.setVisible(false);
            spellMonster3.setVisible(false);
        }
        playerTurn=true;
        checkTurn(fight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

