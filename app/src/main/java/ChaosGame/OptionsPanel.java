package ChaosGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

public class OptionsPanel extends JPanel implements ActionListener {
  private JComboBox<String> menuPolygon;
  private JComboBox<String> menuRule;
  private JComboBox<String> menuColor;
  public JTextField fieldIterations, fieldRotation, fieldSkew;
  private JRadioButton btnShape;
  public String selectedRule, selectedColor;
  public int selectedSides = 3;
  public boolean btnPressed = true;
  private NumberFormatter formatterInt, formatterDouble;

  public OptionsPanel() {
    setLayout(new GridLayout(0, 2));
    setBorder(BorderFactory.createTitledBorder("Settings"));

    // I looked this up
    formatterInt = new NumberFormatter(new DecimalFormat("0"));
    formatterInt.setMinimum(0);
    formatterInt.setAllowsInvalid(false);
    formatterInt.setCommitsOnValidEdit(true);
    formatterDouble = new NumberFormatter(new DecimalFormat("0.0"));
    formatterDouble.setMinimum(0);
    formatterDouble.setAllowsInvalid(false);
    formatterDouble.setCommitsOnValidEdit(true);

    String[] optsPolygon = { "Triangle", "Square", "Pentagon", "Hexagon" };
    String[] optsRule = { "temp", "temp", "temp", "temp" };
    String[] optsColor = { "By vertex", "Custom" };

    menuPolygon = new JComboBox<>(optsPolygon);
    menuRule = new JComboBox<>(optsRule);
    menuColor = new JComboBox<>(optsColor);

    fieldIterations = new JFormattedTextField(formatterInt);
    fieldRotation = new JFormattedTextField(formatterDouble);
    fieldSkew = new JFormattedTextField(formatterDouble);

    fieldIterations.setText("0");
    fieldRotation.setText("0.0");
    fieldSkew.setText("0.0");

    btnShape = new JRadioButton("Draw Polygon");
    btnShape.setSelected(true);

    menuPolygon.addActionListener(this);
    menuRule.addActionListener(this);
    menuColor.addActionListener(this);

    fieldIterations.addActionListener(this);
    fieldRotation.addActionListener(this);
    fieldSkew.addActionListener(this);

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
    add(fieldRotation);
    add(new JLabel("Skew"));
    add(fieldSkew);
    add(btnShape);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == menuPolygon) {
      selectedSides = menuPolygon.getSelectedIndex() + 3;
    }
    if (e.getSource() == menuRule)
      selectedRule = (String) menuRule.getSelectedItem();
    if (e.getSource() == menuColor)
      selectedColor = (String) menuColor.getSelectedItem();

    if (e.getSource() == btnShape)
      btnPressed = btnShape.isSelected();
  }

}
