package app;

import dataAccess.ImprovedJTable;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import overview.OverviewFrame;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import tableModels.DBTableModel;

public class Gui extends JFrame {

    protected JTabbedPane tabbedPane;
    protected OverviewFrame overview;
    protected List<DBTableModel> tableModels;
    private JTable table;
    private boolean jpaError = false;

    public Gui(List<DBTableModel> setupTables) {
        super("Datenbank: Nutzertest");
        this.tableModels = setupTables;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    protected void init() {
        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        overview = new OverviewFrame(tabbedPane);
        setupTappedListener(tabbedPane);
        setupTableViews();
        setupButtons();
        setSize(800, 600);
        
        setLocationRelativeTo(null);
        revalidate();
        repaint();
        pack();
        
        if(jpaError){
            showJpaConnectionError();
        }else{
            addExitDialog();
        }
    }

    private void setupTableViews() {
        for (DBTableModel atm : this.tableModels) {
            ImprovedJTable tbl = new ImprovedJTable(atm);
            tbl.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            tbl.setColumnSelectionAllowed(false);
            tbl.setCellSelectionEnabled(false);

            this.tabbedPane.addTab(atm._tableName, new JScrollPane(tbl));
        }

    }

    private void setupButtons() {
        JButton btnHinzufgen = new JButton("Hinzuf\u00FCgen");
        JButton btnLschen = new JButton("L\u00F6schen");
        JButton btnAktualisieren = new JButton("Aktualisieren");
        JButton btnSpeichern = new JButton("Speichern");
        
        
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 675, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                .addComponent(btnHinzufgen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLschen, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAktualisieren, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSpeichern, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addGap(49)
                        .addComponent(btnHinzufgen)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addComponent(btnLschen)
                        .addGap(93)
                        .addComponent(btnAktualisieren)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(btnSpeichern)
                        .addPreferredGap(ComponentPlacement.RELATED, 276, Short.MAX_VALUE)
                        .addContainerGap())
                .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)
        );
        getContentPane().setLayout(groupLayout);

        btnHinzufgen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTable tbl = getSelectedTableFromTabPane();
                DBTableModel model = getSelectedTableModelFromTabPane();

                model.createRow();
                tbl.revalidate();
            }

        }
        );

        btnLschen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTable tbl = getSelectedTableFromTabPane();
                DBTableModel model = getSelectedTableModelFromTabPane();

                int selectedRow = tbl.getSelectedRow();

                model.remove(selectedRow);
                tbl.revalidate();
            }
        });

        btnAktualisieren.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTable tbl = getSelectedTableFromTabPane();
                DBTableModel model = getSelectedTableModelFromTabPane();

                model.discardAll();
                tbl.revalidate();
                tbl.repaint();
            }
        });

        btnSpeichern.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JTable tbl = getSelectedTableFromTabPane();
                DBTableModel model = getSelectedTableModelFromTabPane();

                model.saveAll();
                tbl.revalidate();
            }

        });
    }

    private void addExitDialog() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                int answer = JOptionPane.showConfirmDialog(tabbedPane, "Haben Sie alle Datenbanken gespeichert?", "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (answer == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public void showJpaConnectionError() {
        JOptionPane.showMessageDialog(this,
                "Fehler bei Verbindungsaufbau zur Datenbank.",
                "Fehler",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void setupTappedListener(JTabbedPane tabbedPane) {
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane ef = (JTabbedPane) e.getSource();
                Component selectedComponent = ef.getSelectedComponent();
                if (selectedComponent instanceof JScrollPane) {
                    JScrollPane b = (JScrollPane) selectedComponent;
                    Component tabl = b.getViewport().getView();
                    if (tabl instanceof JTable) {
                        JTable table = (JTable) tabl;
                        TableModel model = table.getModel();
                        if (model instanceof DBTableModel) {
                            DBTableModel aModel = (DBTableModel) model;
                            aModel.loadEntities();
                            table.revalidate();
                        }
                    }
                }
            }
        });
    }

    private JTable getSelectedTableFromTabPane() {
        Component selected = tabbedPane.getSelectedComponent();
        JTable tbl = null;

        if (selected instanceof JScrollPane) {
            JScrollPane sPane = (JScrollPane) selected;
            selected = sPane.getViewport().getView();
        }

        if (selected instanceof JTable) {
            tbl = (JTable) selected;
        }

        return tbl;
    }

    private DBTableModel getSelectedTableModelFromTabPane() {
        JTable tbl = this.getSelectedTableFromTabPane();
        DBTableModel model = null;
        if (tbl.getModel() instanceof DBTableModel) {
            model = (DBTableModel) tbl.getModel();
        }

        return model;
    }

    void setJpaError(boolean b) {
        this.jpaError = b;
    }
}
