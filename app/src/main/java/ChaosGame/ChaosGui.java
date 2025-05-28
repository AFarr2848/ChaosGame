package ChaosGame;
/*
 * Aaron Farrar
 * APCSA Pd 1
 * Chaos Game
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * The main GUI frame
 */
public class ChaosGui extends JFrame implements ActionListener {
  private OptionsPanel optionsPanel;
  private ChaosShape shape;
  private GamePanel gamePanel;
  private JButton startButton;
  private Color color;
  private int iterations;
  private double rotations, skew = 1;
  private JMenuBar menuBar;
  private JMenu menuFile, menuHelp;
  private JMenuItem itemReset, itemSave, itemExit, itemAbout;
  private static int panelX = 800, panelY = 800;

  public ChaosGui() {
    setTitle("hello");
    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    optionsPanel = new OptionsPanel();
    gamePanel = new GamePanel(panelX, panelY);
    shape = new ChaosShape(3, panelX / 2, new Point(panelX / 2, panelY / 2), skew);
    startButton = new JButton("Run");

    startButton.addActionListener(this);

    menuBar = new JMenuBar();
    menuFile = new JMenu("File");
    menuHelp = new JMenu("Help");
    itemReset = new JMenuItem("Reset");
    itemSave = new JMenuItem("Save");
    itemExit = new JMenuItem("Exit");
    itemAbout = new JMenuItem("About");

    itemReset.addActionListener(this);
    itemSave.addActionListener(this);
    itemExit.addActionListener(this);
    itemAbout.addActionListener(this);

    menuFile.add(itemReset);
    menuFile.add(itemSave);
    menuFile.add(itemExit);
    menuHelp.add(itemAbout);
    menuBar.add(menuFile);
    menuBar.add(menuHelp);

    add(menuBar, BorderLayout.NORTH);

    add(optionsPanel, BorderLayout.EAST);
    add(gamePanel, BorderLayout.CENTER);
    add(startButton, BorderLayout.SOUTH);
    pack();
    setVisible(true);
  }

  public static void main(String[] args) {
    new ChaosGui();
  }

  private void saveFile() {
    try {
      Rectangle screenRect = new Rectangle(gamePanel.getBounds());
      BufferedImage capture = new Robot().createScreenCapture(screenRect);
      JFileChooser fileChooser = new JFileChooser();
      if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        String path = fileChooser.getSelectedFile().getAbsolutePath();
        if (!path.substring(path.length() - 4).equals(".png"))
          path += ".png";
        ImageIO.write(capture, "png", new File(path));
      }
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  /**
   * Grabs appropriate values from the OptionsPanel and calls paintShape when the
   * run button is pressed
   *
   * @param e Action
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == startButton) {
      iterations = Integer.parseInt(optionsPanel.fieldIterations.getText());
      rotations = optionsPanel.getRotation();
      skew = optionsPanel.getSkew();
      shape = new ChaosShape(optionsPanel.selectedSides, panelX / 2, new Point(panelX / 2, panelY / 2), skew);
      color = optionsPanel.colorChosen;
      String selectedRule = optionsPanel.selectedRule;
      Function<Point, Point> func = p -> shape.getRandomCorner();

      switch (selectedRule) {
        case "Random vertex":
          func = p -> shape.getRandomCorner();
          break;
        case "Not closest":
          func = p -> shape.getRandomCornerNotClosest(p);
          break;

        case "Not furthest":
          func = p -> shape.getRandomCornerNotFurthest(p);
          break;

        default:
          System.err.println("Bad rule!");
          break;
      }
      if (optionsPanel.customColor)
        gamePanel.paintShape(shape, iterations, func, optionsPanel.btnPressed, color, rotations);
      else
        gamePanel.paintShape(shape, iterations, func, optionsPanel.btnPressed, null, rotations);
    }

    // Menu Items

    if (e.getSource() == itemExit)
      System.exit(0);
    if (e.getSource() == itemReset) {
      remove(optionsPanel);
      remove(gamePanel);
      optionsPanel = new OptionsPanel();
      gamePanel = new GamePanel(panelX, panelY);
      add(optionsPanel, BorderLayout.EAST);
      add(gamePanel, BorderLayout.CENTER);
      pack();
    }

    if (e.getSource() == itemAbout) {
      JOptionPane.showMessageDialog(this, "ChaosGame made by Aaron F\nhttps://github.com/AFarr2848/ChaosGame#", "About",
          JOptionPane.NO_OPTION);
    }
    if (e.getSource() == itemSave) {
      saveFile();
    }
  }

}
