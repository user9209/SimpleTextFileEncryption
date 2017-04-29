package de.gs_sys.FileTextCryption.gui;

import de.gs_sys.basics.data.CharArrayToByteArray;
import de.gs_sys.basics.gui.About;
import de.gs_sys.kp2016.crypto.DecryptionException;
import de.gs_sys.kp2016.crypto.SecureRandom;
import de.gs_sys.kp2016.crypto.SymmetricCipher;
import de.gs_sys.kp2016.hash.Base64;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * Encrypts text and files with SymmetricCipher 256 Bit
 *
 * @author Georg Schmidt <gs-develop@gs-sys.de>
 * @version 1.0 vom 28.04.2017
 */

public class Main extends JFrame {

    private final float version = 0.1f;

    // Anfang Attribute
    private JTextArea ta_input = new JTextArea("");
    private JScrollPane ta_inputScrollPane = new JScrollPane(ta_input);
    private JTextArea ta_output = new JTextArea("");
    private JScrollPane ta_outputScrollPane = new JScrollPane(ta_output);
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JButton b_file_in = new JButton();
    private JButton b_file_out = new JButton();
    private JLabel jLabel3 = new JLabel();
    private JPasswordField tf_password = new JPasswordField();
    //private JTextField tf_password = new JTextField();
    private JButton b_gen = new JButton();
    private JButton b_show = new JButton();
    private JButton b_encrypt = new JButton();
    private JButton b_decrypt = new JButton();
    private JButton b_about = new JButton();
    private JLabel l_version = new JLabel();
    private JButton b_exit = new JButton();
    private JFileChooser jFileChooser1 = new JFileChooser();
    private JFileChooser jFileChooser2 = new JFileChooser();
    // Ende Attribute

    public Main() {
        // Frame-Initialisierung
        super();

        //SymmetricCipher.setEngine(SymmetricCipher.ENGINE.Threefish);
        SymmetricCipher.setEngine(SymmetricCipher.ENGINE.Twofish);
        //SymmetricCipher.setEngine(SymmetricCipher.ENGINE.AES);
        //SymmetricCipher.setEngine(SymmetricCipher.ENGINE.Camellia);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        int frameWidth = 692;
        int frameHeight = 384;
        setSize(frameWidth, frameHeight);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2;
        setLocation(x, y);
        setTitle(SymmetricCipher.engine() + " - Cipher");
        setResizable(false);
        Container cp = getContentPane();
        cp.setLayout(null);
        // Anfang Komponenten

        ta_inputScrollPane.setBounds(16, 32, 641, 89);
        cp.add(ta_inputScrollPane);
        ta_outputScrollPane.setBounds(16, 152, 641, 89);
        cp.add(ta_outputScrollPane);
        jLabel1.setBounds(16, 8, 83, 19);
        jLabel1.setText("Input");
        cp.add(jLabel1);
        jLabel2.setBounds(16, 128, 83, 19);
        jLabel2.setText("Output");
        cp.add(jLabel2);
        b_file_in.setBounds(16, 248, 73, 25);
        b_file_in.setText("File In");
        b_file_in.setMargin(new Insets(2, 2, 2, 2));
        b_file_in.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_file_in_ActionPerformed(evt);
            }
        });
        cp.add(b_file_in);
        b_file_out.setBounds(96, 248, 73, 25);
        b_file_out.setText("File Out");
        b_file_out.setMargin(new Insets(2, 2, 2, 2));
        b_file_out.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_file_out_ActionPerformed(evt);
            }
        });
        cp.add(b_file_out);
        jLabel3.setBounds(176, 248, 67, 25);
        jLabel3.setText("Password");
        cp.add(jLabel3);
        tf_password.setBounds(248, 248, 329, 25);
        cp.add(tf_password);
        b_gen.setBounds(584, 248, 33, 25);
        b_gen.setText("Gen");
        b_gen.setMargin(new Insets(2, 2, 2, 2));
        b_gen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_gen_ActionPerformed(evt);
            }
        });
        cp.add(b_gen);
        b_show.setBounds(624, 248, 33, 25);
        b_show.setText("Show");
        b_show.setMargin(new Insets(2, 2, 2, 2));
        b_show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_show_ActionPerformed(evt);
            }
        });
        b_show.setFont(new Font("Dialog", Font.BOLD, 8));
        cp.add(b_show);
        b_encrypt.setBounds(256, 296, 153, 41);
        b_encrypt.setText("Encrypt");
        b_encrypt.setMargin(new Insets(2, 2, 2, 2));
        b_encrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_encrypt_ActionPerformed(evt);
            }
        });
        cp.add(b_encrypt);
        b_decrypt.setBounds(432, 296, 153, 41);
        b_decrypt.setText("Decrypt");
        b_decrypt.setMargin(new Insets(2, 2, 2, 2));
        b_decrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_decrypt_ActionPerformed(evt);
            }
        });
        cp.add(b_decrypt);
        b_about.setBounds(16, 312, 73, 25);
        b_about.setText("About");
        b_about.setMargin(new Insets(2, 2, 2, 2));
        b_about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_about_ActionPerformed(evt);
            }
        });
        cp.add(b_about);
        l_version.setBounds(96, 312, 130, 25);
        l_version.setText("Version: " + version);
        cp.add(l_version);
        b_exit.setBounds(624, 312, 33, 25);
        b_exit.setText("Exit");
        b_exit.setMargin(new Insets(2, 2, 2, 2));
        b_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                b_exit_ActionPerformed(evt);
            }
        });
        cp.add(b_exit);
        // Ende Komponenten

        System.out.println("Version " + version + " with " + SymmetricCipher.info());

        setVisible(true);
    } // end of public Main

    // Anfang Methoden

    public static void main(String[] args) {
        new Main();
    } // end of main

    public void b_file_in_ActionPerformed(ActionEvent evt) {
        File in = jFileChooser1_openFile();
        if (in != null)
            ta_input.setText(in.getAbsolutePath());
    } // end of b_file_in_ActionPerformed

    public void b_file_out_ActionPerformed(ActionEvent evt) {
        File in = jFileChooser2_saveFile();
        if (in != null)
            ta_output.setText(in.getAbsolutePath());
    } // end of b_file_out_ActionPerformed

    public void b_gen_ActionPerformed(ActionEvent evt) {
        if (tf_password.getEchoChar() != (char) 0) {
            tf_password.setEchoChar((char) 0);
        }
        tf_password.setText(SecureRandom.getSecurePasswordString(20));
    } // end of b_gen_ActionPerformed

    public void b_show_ActionPerformed(ActionEvent evt) {
        if (tf_password.getEchoChar() == (char) 0) {
            tf_password.setEchoChar('*');
        } else {
            // (char) 0 == '\u0000'
            tf_password.setEchoChar((char) 0);
        }

    } // end of b_show_ActionPerformed

    public void b_encrypt_ActionPerformed(ActionEvent evt) {
        String in = ta_input.getText();
        if (in.isEmpty() || tf_password.getPassword().length == 0)
            return;

        // check, if it is a file
        if (new File(in).exists()) {
            String out = ta_output.getText();
            if (out.isEmpty() || new File(out).getParentFile() == null) {
                ta_output.setText(ta_output.getText() + "\nEncryption failed! Please try again!");
                return;
            }

            if (SymmetricCipher.encryptFile(CharArrayToByteArray.toBytes(tf_password.getPassword()), in, out))
                ta_output.setText("Encryption successfully!");
            else
                ta_output.setText("Encryption failed! Please try again!");
        } else {
            in = Base64.encode(SymmetricCipher.encrypt(CharArrayToByteArray.toBytes(tf_password.getPassword()), ta_input.getText().getBytes(StandardCharsets.UTF_8)));
            ta_output.setText(in);
        }
    } // end of b_encrypt_ActionPerformed

    public void b_decrypt_ActionPerformed(ActionEvent evt) {
        String in = ta_input.getText();
        if (in.isEmpty())
            return;

        // check, if it is a file
        if (new File(in).exists()) {
            String out = ta_output.getText();
            if (out.isEmpty() || new File(out).getParentFile() == null) {
                ta_output.setText(ta_output.getText() + "\nDecryption failed! Please try again!");
                return;
            }

            try {
                if (SymmetricCipher.decryptFile(CharArrayToByteArray.toBytes(tf_password.getPassword()), in, out))
                    ta_output.setText("Decryption successfully!");
                else
                    ta_output.setText("Decryption failed! Please try again!");
            } catch (DecryptionException e) {
                ta_output.setText("Decryption failed! Please try again!");
            }
        } else {
            try {
                in = new String(SymmetricCipher.decrypt(CharArrayToByteArray.toBytes(tf_password.getPassword()), Base64.decode(ta_input.getText())), StandardCharsets.UTF_8);
            } catch (DecryptionException e) {
                in = "Decryption failed! Please try again!";
            }

            ta_output.setText(in);
        }
    } // end of b_decrypt_ActionPerformed

    public void b_about_ActionPerformed(ActionEvent evt) {
        new About();
    } // end of b_about_ActionPerformed

    public void b_exit_ActionPerformed(ActionEvent evt) {
        System.exit(0);
    } // end of b_exit_ActionPerformed

    public File jFileChooser1_openFile() {
        if (jFileChooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            return jFileChooser1.getSelectedFile();
        } else {
            return null;
        }
    }

    public File jFileChooser2_saveFile() {
        if (jFileChooser2.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            return jFileChooser2.getSelectedFile();
        } else {
            return null;
        }
    }

    // Ende Methoden
} // end of class Main
