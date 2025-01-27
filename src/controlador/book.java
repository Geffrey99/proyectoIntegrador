package controlador;

import javax.swing.*;

public class book {
    private JPanel mainPanel;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton CANCELARButton;
    private JButton GUARDARButton;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }

    public JTextField getTextField2() {
        return textField2;
    }

    public void setTextField2(JTextField textField2) {
        this.textField2 = textField2;
    }

    public JTextField getTextField3() {
        return textField3;
    }

    public void setTextField3(JTextField textField3) {
        this.textField3 = textField3;
    }

    public JTextField getTextField4() {
        return textField4;
    }

    public void setTextField4(JTextField textField4) {
        this.textField4 = textField4;
    }

    public JTextField getTextField5() {
        return textField5;
    }

    public void setTextField5(JTextField textField5) {
        this.textField5 = textField5;
    }

    public JButton getCancelarButton() {
        return CANCELARButton;
    }

    public void setCANCELARButton(JButton CANCELARButton) {
        this.CANCELARButton = CANCELARButton;
    }

    public JButton getGuardarButton() {
        return GUARDARButton;
    }

    public void setGUARDARButton(JButton GUARDARButton) {
        this.GUARDARButton = GUARDARButton;
    }
}
