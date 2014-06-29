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
        jToggleButton1 = new javax.swing.JToggleButton();
        jScrollPanePascal = new javax.swing.JScrollPane();
        jTextPascal = new javax.swing.JTextArea();
        jScrollPaneC = new javax.swing.JScrollPane();
        jTextC = new javax.swing.JTextArea();
        Translate = new javax.swing.JButton();
        jButtonClearPascal = new javax.swing.JButton();
        jButtonImportPascal = new javax.swing.JButton();
        jButtonExportC = new javax.swing.JButton();
        jButtonClearC = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaError = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

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

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Компилятор");
        setMinimumSize(new java.awt.Dimension(970, 600));
        setPreferredSize(new java.awt.Dimension(960, 570));

        jTextPascal.setColumns(20);
        jTextPascal.setRows(5);
        jTextPascal.setMinimumSize(new java.awt.Dimension(100, 75));
        jTextPascal.setName(""); // NOI18N
        jScrollPanePascal.setViewportView(jTextPascal);

        jTextC.setColumns(20);
        jTextC.setRows(5);
        jTextC.setMinimumSize(new java.awt.Dimension(100, 75));
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

        jButtonImportPascal.setFont(jButtonImportPascal.getFont().deriveFont(jButtonImportPascal.getFont().getSize()-1f));
        jButtonImportPascal.setText("Импортировать код \"Pascal\" из файла");
        jButtonImportPascal.setToolTipText("");
        jButtonImportPascal.setAutoscrolls(true);
        jButtonImportPascal.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonImportPascal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jButtonImportPascal.setInheritsPopupMenu(true);
        jButtonImportPascal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonImportPascalActionPerformed(evt);
            }
        });

        jButtonExportC.setText("Экспортировать код \"С\" в файл");
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

        jLabel1.setText("Список ошибок");

        jTextAreaError.setColumns(20);
        jTextAreaError.setRows(5);
        jScrollPane2.setViewportView(jTextAreaError);

        jButton1.setText("Очистить список ошибок");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPanePascal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                            .addComponent(jScrollPaneC, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonClearC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonImportPascal, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Translate, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonExportC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonClearPascal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonImportPascal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jButtonClearPascal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Translate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPanePascal, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonExportC, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonClearC, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPaneC, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TranslateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TranslateActionPerformed
        // TODO add your handling code here:
        String text = jTextPascal.getText();
        //Убираем лишние пробелы
        text.trim();
        
        LexicalAnalyzer lexAnalyzer = new LexicalAnalyzer();
        ArrayList<TokenParser> tokenList = lexAnalyzer.Parse(text);
        
        for (TokenParser token : tokenList) {
            System.out.println(token.getText());
        }

        PascalParser parser = new PascalParser( jTextAreaError );

        text = parser.parse(tokenList);
        
        Formater formater = new Formater();
        text = formater.format(text);

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextAreaError.setText(null);
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonClearC;
    private javax.swing.JButton jButtonClearPascal;
    private javax.swing.JButton jButtonExportC;
    private javax.swing.JButton jButtonImportPascal;
    private javax.swing.JFrame jFrameError;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneC;
    private javax.swing.JScrollPane jScrollPanePascal;
    private javax.swing.JTextArea jTextAreaError;
    private javax.swing.JTextArea jTextC;
    private javax.swing.JTextArea jTextError;
    private javax.swing.JTextArea jTextPascal;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
