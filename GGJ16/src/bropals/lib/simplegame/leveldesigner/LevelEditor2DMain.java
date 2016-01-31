/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.lib.simplegame.leveldesigner;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Jonathon
 */
public abstract class LevelEditor2DMain extends JFrame {

    private final PropertyEditor property;
    private final EditorView editor;
    private final JMenuBar menuBar;
    private final JMenuItem saveButton;
    private final JMenuItem saveAsButton;
    private final JMenuItem openButton;
    private final JMenuItem newButton;
    private final JMenuItem cutButton;
    private final JMenuItem copyButton;
    private final JMenuItem pasteButton;
    private final JMenuItem deleteButton;
    private File saveLocation = null;
    private EditableLevel currentLevel = null;
    private final JFileChooser fc = new JFileChooser();
    
    public LevelEditor2DMain(String windowText) {
        super(windowText);
        editor = new EditorView();
        property = new PropertyEditor(editor);
        editor.setPropertyEditor(property);
        
        this.add(editor, BorderLayout.CENTER);
        this.add(property, BorderLayout.EAST);
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        saveButton = new JMenuItem("Save");
        saveAsButton = new JMenuItem("Save As");
        openButton = new JMenuItem("Open");
        newButton = new JMenuItem("New");
        fileMenu.add(newButton);
        newButton.addActionListener(new NewButton());
        newButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        fileMenu.add(saveButton);
        saveButton.addActionListener(new SaveButton());
        saveButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        fileMenu.add(saveAsButton);
        saveAsButton.addActionListener(new SaveAsButton());
        saveAsButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.SHIFT_DOWN_MASK));
        fileMenu.add(openButton);
        openButton.addActionListener(new OpenButton());
        openButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        menuBar.add(fileMenu);
        JMenu editMenu = new JMenu("Edit");
        cutButton = new JMenuItem("Cut");
        cutButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK));
        copyButton = new JMenuItem("Copy");
        copyButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        pasteButton = new JMenuItem("Paste");
        pasteButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        deleteButton = new JMenuItem("Delete");
        deleteButton.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        cutButton.addActionListener(new CutButton());
        editMenu.add(cutButton);
        copyButton.addActionListener(new CopyButton());
        editMenu.add(copyButton);
        pasteButton.addActionListener(new PasteButton());
        editMenu.add(pasteButton);
        deleteButton.addActionListener(new DeleteButton());
        editMenu.add(deleteButton);
        menuBar.add(editMenu);
        
        setJMenuBar(menuBar);
        addWindowListener(new ExitButton());
        setSize(800, 600);
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public abstract void newLevel();

    public void setCurrentLevel(EditableLevel currentLevel) {
        this.currentLevel = currentLevel;
        editor.setLevel(currentLevel);
    }

    public EditableLevel getCurrentLevel() {
        return currentLevel;
    }  
    
    public PropertyEditor getPropertyEditor() {
        return property;
    }

    public EditorView getEditorView() {
        return editor;
    }
        
    public void saveAs() {
        if (currentLevel != null) {
            int response = fc.showSaveDialog(this);
            if (response == JFileChooser.APPROVE_OPTION) {
                saveLocation = fc.getSelectedFile();
                save();
            }
        }
    }
    
    public void save() {
        if (currentLevel != null) {
            if (saveLocation == null) {
                saveAs();
            } else {
                try {
                    OutputStream out = null;
                    if (saveLocation.exists()) {
                        Files.delete(saveLocation.toPath());
                    }
                    currentLevel.saveLevel(out = Files.newOutputStream(saveLocation.toPath(), StandardOpenOption.CREATE));
                    out.close();
                } catch(IOException e) {
                    System.err.println("Could not save level: " + e);
                }
            }
        }
    }
    
    public void open() {
        int response = fc.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            saveLocation = fc.getSelectedFile();
            try {
                if (currentLevel != null) {
                    currentLevel.clearLevel();
                } else {
                    newLevel();
                }
                InputStream in = null;
                currentLevel.loadLevel(in = Files.newInputStream(saveLocation.toPath(), StandardOpenOption.READ));
                in.close();
            } catch(IOException e) {
                System.err.println("Could not open level: " + e);
            }
        }
    }
    
    public int askForSaveChanges() {
        return JOptionPane.showConfirmDialog(this, "Save level data? Any unsaved data may be lost.", "Save changes?", JOptionPane.YES_NO_CANCEL_OPTION);
    }
    
    class NewButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (saveLocation != null) {
                int response = askForSaveChanges();
                if (response == JOptionPane.YES_OPTION) {
                    save();
                } else if (response == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            newLevel();
        }
    }
    
    class SaveButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            save();
        }
    }
    
    class SaveAsButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveAs();
        }
    }
    
    class OpenButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.YES_OPTION;
            if (currentLevel != null) {
                response = askForSaveChanges();
            }
            if (response == JOptionPane.YES_OPTION) {
                save();
            } else if (response == JOptionPane.CANCEL_OPTION) {
                return;
            }
            open();
        }
    }
    
    class ExitButton extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            if (currentLevel != null) {
                int response = askForSaveChanges();
                if (response == JOptionPane.YES_OPTION) {
                    save();
                    dispose();
                } else if (response == JOptionPane.NO_OPTION) {
                    dispose();
                }
            } else {
                dispose();
            }
        }
        
    }
    
    class CutButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentLevel != null) {
                editor.cutSelected();
            }
        }
    }
    
    class CopyButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentLevel != null) {
                editor.copySelected();
            }
        }
    }
    
    class PasteButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentLevel != null) {
                editor.pasteSelected();
            }
        }
    }
    
    class DeleteButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentLevel != null) {
                editor.deleteSelected();
            }
        }
    }
}
