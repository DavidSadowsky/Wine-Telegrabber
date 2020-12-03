package org.davidsadowsky.zammywinegrabber;

import org.davidsadowsky.zammywinegrabber.data.Location;
import org.davidsadowsky.zammywinegrabber.data.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZammywinegrabberGUI extends JFrame {

    private JComboBox locationComboBox;
    private JComboBox logoutComboBox;
    private JButton initiate;

    public ZammywinegrabberGUI() {
        super("Autofiremaker Configuration");

        setLayout(new FlowLayout());
        initiate = new JButton("Initiate");
        locationComboBox = new JComboBox(Location.values());
        logoutComboBox = new JComboBox(Time.values());
        add(locationComboBox);
        add(logoutComboBox);
        add(initiate);

        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();

        locationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Location location = (Location) locationComboBox.getSelectedItem();
            }
        });

        initiate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Zammywinegrabber.location = (Location) locationComboBox.getSelectedItem();
                Zammywinegrabber.time = (Time) logoutComboBox.getSelectedItem();
                org.rspeer.ui.Log.info("[Location: " + Zammywinegrabber.location.getName() + ", Duration: " + Zammywinegrabber.time.getTime() + "]" );
                setVisible(false);
            }
        });
    }
}