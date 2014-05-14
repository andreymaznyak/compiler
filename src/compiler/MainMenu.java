/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.awt.Component;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;

/**
 *
 * @author Andry
 */
public class MainMenu extends javax.swing.JFrame {

    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        fileChooserSave = new javax.swing.JFileChooser();
        jFrameError = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextError = new javax.swing.JTextArea();
        jScrollPanePascal = new javax.swing.JScrollPane();
        jTextPascal = new javax.swing.JTextArea();
        jScrollPaneC = new javax.swing.JScrollPane();
        jTextC = new javax.swing.JTextArea();
        Translate = new javax.swing.JButton();
        jButtonClearPascal = new javax.swing.JButton();
        jButtonImportPascal = new javax.swing.JButton();
        jButtonExportC = new javax.swing.JButton();
        jButtonClearC = new javax.swing.JButton();
        jButtonErorrlog = new javax.swing.JButton();

        fileChooser.setDialogTitle("Выберите файл на языке pascal");
        fileChooser.setFileFilter(new MyCustomFilterPascal());
        fileChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }
        });

        fileChooserSave.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        fileChooserSave.setFileFilter(new MyCustomFilterC());
        fileChooserSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserSaveActionPerformed(evt);
            }
        });

        jFrameError.setTitle("Список ошибок");
        jFrameError.setPreferredSize(new java.awt.Dimension(800, 300));

        jTextError.setColumns(20);
        jTextError.setRows(5);
        jScrollPane1.setViewportView(jTextError);

        javax.swing.GroupLayout jFrameErrorLayout = new javax.swing.GroupLayout(jFrameError.getContentPane());
        jFrameError.getContentPane().setLayout(jFrameErrorLayout);
        jFrameErrorLayout.setHorizontalGroup(
            jFrameErrorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameErrorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
                .addContainerGap())
        );
        jFrameErrorLayout.setVerticalGroup(
            jFrameErrorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrameErrorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Компилятор");

        jTextPascal.setColumns(20);
        jTextPascal.setRows(5);
        jScrollPanePascal.setViewportView(jTextPascal);

        jTextC.setColumns(20);
        jTextC.setRows(5);
        jScrollPaneC.setViewportView(jTextC);

        Translate.setText("Компилировать");
        Translate.setActionCommand("");
        Translate.setName("btmTranslate"); // NOI18N
        Translate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TranslateActionPerformed(evt);
            }
        });

        jButtonClearPascal.setText("Очистить поле ввода");
        jButtonClearPascal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearPascalActionPerformed(evt);
            }
        });

        jButtonImportPascal.setFont(jButtonImportPascal.getFont());
        jButtonImportPascal.setText("Импортировать код \"Pascal\" из файла");
        jButtonImportPascal.setToolTipText("");
        jButtonImportPascal.setAutoscrolls(true);
        jButtonImportPascal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jButtonImportPascal.setInheritsPopupMenu(true);
        jButtonImportPascal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImportPascalActionPerformed(evt);
            }
        });

        jButtonExportC.setText("Экспортировать код \"С\" в фаил");
        jButtonExportC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportCActionPerformed(evt);
            }
        });

        jButtonClearC.setText("Очистить поле вывода");
        jButtonClearC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearCActionPerformed(evt);
            }
        });

        jButtonErorrlog.setText("Показать список ошибок");
        jButtonErorrlog.setToolTipText("");
        jButtonErorrlog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonErorrlogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonExportC, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClearC, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonErorrlog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonImportPascal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonClearPascal, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Translate, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE))
                            .addComponent(jScrollPanePascal)
                            .addComponent(jScrollPaneC))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonClearPascal, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(Translate, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonImportPascal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPanePascal, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonExportC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonClearC, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jButtonErorrlog, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneC, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TranslateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TranslateActionPerformed
        // TODO add your handling code here:
        String text = jTextPascal.getText();
        //Убираем лишние пробелы
        text.trim();
        ArrayList<TokenParser> tokenList = new ArrayList<TokenParser>();

        StringTokenizer strTokenizer = new StringTokenizer(text, " \t\n\r\f(),.\'\"[]=+:*/;-", true);
        int count = strTokenizer.countTokens();
        int j = 0; //Номер символа символа
        int k = 0; //Номер строки
        for (int i = 0; i < count; i++) {
            String token = strTokenizer.nextToken();
            token.trim();

            if (!token.equals(" ") && !token.equals("\t") && !token.equals("\n") && !token.equals("\r") && !token.equals("\f")) {
                // System.out.println(token);
                text.lastIndexOf(text);
                tokenList.add(new TokenParser(token, k, j));
                j += token.length();
            } else {
                if (token.equals("\n")) {
                    j = 0;
                    k++;
                } else {
                    j += token.length();
                }

            }
        }
        int tokenListSize = tokenList.size() - 1;
        for (int i = 0; i < tokenListSize; i++) {
            if (tokenList.get(i).equals(":") && tokenList.get(i + 1).equals("=")
                    || tokenList.get(i).equals(".") && tokenList.get(i + 1).equals(".")) {
                tokenList.set(i, tokenList.get(i).concat(tokenList.get(i + 1)));
                tokenList.remove(i + 1);
                tokenListSize--;
            }
        }

        for (TokenParser token : tokenList) {
            System.out.println(token.getText());
        }

        PascalParser parser = new PascalParser();

        text = parser.parseNew(tokenList);

        jTextC.setText(text);

    }//GEN-LAST:event_TranslateActionPerformed

    private void jButtonImportPascalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonImportPascalActionPerformed
        // TODO add your handling code here:
        fileChooserActionPerformed(evt);
    }//GEN-LAST:event_jButtonImportPascalActionPerformed

    private void jButtonClearPascalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearPascalActionPerformed
        // TODO add your handling code here:
        jTextPascal.setText(null);
    }//GEN-LAST:event_jButtonClearPascalActionPerformed

    private void jButtonClearCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearCActionPerformed
        // TODO add your handling code here:
        jTextC.setText(null);
    }//GEN-LAST:event_jButtonClearCActionPerformed

    private void fileChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserActionPerformed
        // TODO add your handling code here:

        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                // What to do with the file, e.g. display it in a TextArea
                jTextPascal.read(new FileReader(file.getAbsolutePath()), null);
            } catch (IOException ex) {
                System.out.println("problem accessing file" + file.getAbsolutePath());
            }
        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event_fileChooserActionPerformed

    private void jButtonExportCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportCActionPerformed
        // TODO add your handling code here:
        fileChooserSaveActionPerformed(evt);
    }//GEN-LAST:event_jButtonExportCActionPerformed

    private void fileChooserSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserSaveActionPerformed
        // TODO add your handling code here:

        if (fileChooserSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fw = new FileWriter(fileChooserSave.getSelectedFile())) {
                fw.write(jTextC.getText());
            } catch (IOException e) {
                System.out.println("Ошибка записи");
            }
        }
    }//GEN-LAST:event_fileChooserSaveActionPerformed

    private void jButtonErorrlogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonErorrlogActionPerformed
        // TODO add your handling code here:

       // jFrameError.setVisible(true);
        // jFrameError.setSize(WIDTH, HEIGHT);
        jFrameError.pack();
        jFrameError.setVisible(true);
        jTextError.setText("Hello world");
    }//GEN-LAST:event_jButtonErorrlogActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Translate;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JFileChooser fileChooserSave;
    private javax.swing.JButton jButtonClearC;
    private javax.swing.JButton jButtonClearPascal;
    private javax.swing.JButton jButtonErorrlog;
    private javax.swing.JButton jButtonExportC;
    private javax.swing.JButton jButtonImportPascal;
    private javax.swing.JFrame jFrameError;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneC;
    private javax.swing.JScrollPane jScrollPanePascal;
    private javax.swing.JTextArea jTextC;
    private javax.swing.JTextArea jTextError;
    private javax.swing.JTextArea jTextPascal;
    // End of variables declaration//GEN-END:variables
}
