package ChaosGame;
/*
 * Aaron Farrar
 * APCSA Pd 1
 * Chaos Game
 */

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class OptionsPanel extends JPanel implements ActionListener {
  private JComboBox<String> menuPolygon;
  private JComboBox<String> menuRule;
  private JComboBox<String> menuColor;
  public JTextField fieldIterations;
  private JRadioButton btnShape;
  public String selectedRule = "Random vertex", selectedColor;
  public int selectedSides = 3;
  public boolean btnPressed = true, customColor = false;
  public Color colorChosen;
  private NumberFormatter formatterInt, formatterDouble;
  public JSlider sliderRotation, sliderSkew;

  public OptionsPanel() {
    setLayout(new GridLayout(0, 2));
    setBorder(BorderFactory.createTitledBorder("Settings"));

    // I looked this up
    formatterInt = new NumberFormatter(new DecimalFormat("0"));
    formatterInt.setMinimum(0);
    formatterInt.setAllowsInvalid(false);
    formatterInt.setCommitsOnValidEdit(true);

    String[] optsPolygon = { "Triangle", "Square", "Pentagon", "Hexagon" };
    String[] optsRule = { "Random vertex", "Not closest", "Not furthest" };
    String[] optsColor = { "By point", "Custom" };

    menuPolygon = new JComboBox<>(optsPolygon);
    menuRule = new JComboBox<>(optsRule);
    menuColor = new JComboBox<>(optsColor);

    fieldIterations = new JFormattedTextField(formatterInt);

    sliderRotation = new JSlider(0, 360, 0);
    sliderSkew = new JSlider(0, 200, 100);

    sliderRotation.setPaintTicks(true);
    sliderRotation.setMajorTickSpacing(45);
    sliderRotation.setMinorTickSpacing(15);

    fieldIterations.setText("100000");

    btnShape = new JRadioButton("Draw Polygon");
    btnShape.setSelected(true);

    menuPolygon.addActionListener(this);
    menuRule.addActionListener(this);
    menuColor.addActionListener(this);

    fieldIterations.addActionListener(this);

    btnShape.addActionListener(this);

    add(new JLabel("Polygon"));
    add(menuPolygon);
    add(new JLabel("Generation Rule"));
    add(menuRule);
    add(new JLabel("Dot Color"));
    add(menuColor);
    add(new JLabel("Iterations"));
    add(fieldIterations);
    add(new JLabel("Rotation"));
    add(sliderRotation);
    add(new JLabel("Skew"));
    add(sliderSkew);
    add(btnShape);
  }

  public int getRotation() {
    return sliderRotation.getValue();
  }

  public double getSkew() {
    return sliderSkew.getValue();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == menuPolygon) {
      selectedSides = menuPolygon.getSelectedIndex() + 3;
    }
    if (e.getSource() == menuRule)
      selectedRule = (String) menuRule.getSelectedItem();
    if (e.getSource() == menuColor) {
      selectedColor = (String) menuColor.getSelectedItem();
      if (menuColor.getSelectedItem().equals((Object) "Custom")) {
        colorChosen = JColorChooser.showDialog(this, "Choose a color", Color.RED);
        customColor = true;
      } else
        customColor = false;

    }

    if (e.getSource() == btnShape)
      btnPressed = btnShape.isSelected();

  }

}
