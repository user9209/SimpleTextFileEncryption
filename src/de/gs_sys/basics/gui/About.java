/*
    Copyright (C) 2017  Georg Schmidt <gs-develop@gs-sys.de>
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package de.gs_sys.basics.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Georg Schmidt
 * @version 1.0 vom 16.12.2016
 */

public class About extends JFrame {

    private JTextArea taAbout = new JTextArea("");
    private JScrollPane taAboutScrollPane = new JScrollPane(taAbout);
    private JButton bOK = new JButton();

    String README = "#####################################################################\n" +
                    "#      Authors                                                      #\n" +
                    "#####################################################################\n" +
                    "#                                                                   #\n" +
                    "# -  Georg Schmidt  <gs-develop@gs-sys.de>                          #\n" +
                    "#                                                                   #\n" +
                    "#                                                                   #\n" +
                    "#####################################################################\n" +
                    "#      License                                                      #\n" +
                    "#####################################################################\n" +
                    "# Copyright (C) 2017  Georg Schmidt <gs-develop@gs-sys.de>          #\n" +
                    "#  This program is free software: you can redistribute it and/or    #\n" +
                    "#  modify it under the terms of the GNU General Public License as   #\n" +
                    "#  published by the Free Software Foundation, either version 3 of   #\n" +
                    "#  the License, or (at your option) any later version.              #\n" +
                    "#  This program is distributed in the hope that it will be useful,  #\n" +
                    "#  but WITHOUT ANY WARRANTY; without even the implied warranty of   #\n" +
                    "#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the    #\n" +
                    "#  GNU General Public License for more details.                     #\n" +
                    "#  You should have received a copy of the GNU General Public License#\n" +
                    "#  along with this program.                                         #\n" +
					"#  If not, see <http://www.gnu.org/licenses/>.                      #\n" +
                    "#####################################################################";


    public About() {
        // Frame-Initialisierung
        super("About");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 545;
        int frameHeight = 418;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten

        taAboutScrollPane.setBounds(16, 16, 505, 313);
        taAbout.setEditable(false);
        taAboutScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        cp.add(taAboutScrollPane);
        bOK.setBounds(176, 344, 161, 33);
        bOK.setText("OK");
        bOK.setMargin(new Insets(2, 2, 2, 2));
        bOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                bOK_ActionPerformed(evt);
            }
        });
        cp.add(bOK);
        // Ende Komponenten

        // All characters some size
        taAbout.setFont(new Font("monospaced", Font.PLAIN, 12));

        try {
            README = new String(Files.readAllBytes(Paths.get("about.txt")), StandardCharsets.UTF_8);
        } catch (IOException ignore) {}

        taAbout.append(README);
        taAbout.setCaretPosition(0);


        setVisible(true);
    } // end of public About

    // Anfang Methoden
    public void bOK_ActionPerformed(ActionEvent evt) {
        dispose();
    } // end of bOK_ActionPerformed

} // end of class About
