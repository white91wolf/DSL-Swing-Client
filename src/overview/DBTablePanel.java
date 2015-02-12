package overview;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

public class DBTablePanel extends JPanel {

    JLabel titel;
    Point pressPoint;
    Point releasePoint;
    DragProcessor dragProcessor = new DragProcessor();
    String[][] columns;
    JTable table;
    String[] columnTitel = {"Bezeichnung", "Datentyp"};
    Point location;

    public DBTablePanel(String title, String[][] columns) {
        this.setLayout(new java.awt.BorderLayout());
        this.titel = new JLabel(title);
        this.titel.setFont(new Font("Tahoma", 1, 14));
        this.titel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        
        table = new JTable();
        this.columns = columns;
        setBorder(new CompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(1, 5, 1, 1)));
        addMouseListener(dragProcessor);
        addMouseMotionListener(dragProcessor);
        init();
    }

    private void init() {
        initColumns();
        this.add(this.titel, java.awt.BorderLayout.PAGE_START);
        this.add(this.table, java.awt.BorderLayout.CENTER);
        this.setPreferredSize(this.getPreferredSize());
        
        this.validate();
        this.repaint();
    }
    
    private void initColumns() {
        table.setModel(new DefaultTableModel(columns, columnTitel));
    }

    protected class DragProcessor extends MouseAdapter implements MouseListener, MouseMotionListener {

        Window dragWindow = new JWindow() {
            public void paint(Graphics g) {
                super.paint(g);
                DBTablePanel.this.paint(g);
            }
        };

        public void mouseDragged(MouseEvent e) {
            Point dragPoint = e.getPoint();
            int xDiff = pressPoint.x - dragPoint.x;
            int yDiff = pressPoint.y - dragPoint.y;

            Rectangle b = e.getComponent().getBounds();
            Point p = b.getLocation();
            SwingUtilities.convertPointToScreen(p, e.getComponent().getParent());
            p.x -= xDiff;
            p.y -= yDiff;

            dragWindow.setLocation(p);
        }

        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            pressPoint = e.getPoint();
            Rectangle b = e.getComponent().getBounds();
            Point p = b.getLocation();
            SwingUtilities.convertPointToScreen(p, e.getComponent().getParent());
            dragWindow.setBounds(b);
            dragWindow.setLocation(p);
            dragWindow.setVisible(true);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            releasePoint = e.getPoint();
            dragWindow.setVisible(false);

            int xDiff = pressPoint.x - releasePoint.x;
            int yDiff = pressPoint.y - releasePoint.y;

            Rectangle b = e.getComponent().getBounds();
            Point p = b.getLocation();
            SwingUtilities.convertPointToScreen(p, e.getComponent().getParent());
            p.x -= xDiff;
            p.y -= yDiff;

            SwingUtilities.convertPointFromScreen(p, DBTablePanel.this.getParent());
            if (p.x <= 0) {
                p.x = 1;
            }
            if (p.x > DBTablePanel.this.getParent().getWidth() - b.width) {
                p.x = DBTablePanel.this.getParent().getWidth() - b.width;
            }
            if (p.y <= 0) {
                p.y = 1;
            }
            if (p.y > DBTablePanel.this.getParent().getHeight() - b.height) {
                p.y = DBTablePanel.this.getParent().getHeight() - b.height;
            }
            setLocation(p);
            location = p;
            getParent().repaint();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        if(location != null){
            this.setLocation(location);
        }
        super.paint(g);
        
    }
    
}
