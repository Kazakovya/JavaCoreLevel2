package ru.geekbrains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private JTextField field;
    private JTextArea jta;
    private JButton button;

    public App() {
        setBounds(300, 300, 300, 300);
        setTitle("Easy Chat");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jta = new JTextArea();
        jta.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jta);
        jp.add(jsp, BorderLayout.CENTER);

        JPanel bottonJp = new JPanel();
        bottonJp.setLayout(new BorderLayout());

        addTextField();
        bottonJp.add(field, BorderLayout.CENTER);

        addButton();
        bottonJp.add(button, BorderLayout.SOUTH);
        jp.add(bottonJp, BorderLayout.SOUTH);
        add(jp);

        setVisible(true);
        field.grabFocus();
    }

    private void sendMessage(){
        if(!field.getText().isEmpty()){
            jta.append(field.getText()+"\n");
            field.setText("");
            field.grabFocus();
        }
    }

    private void addTextField(){
        field = new JTextField();
        field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void addButton(){
        button = new JButton("Отправить");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }
}
