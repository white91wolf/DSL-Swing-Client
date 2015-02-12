package connector;

import javax.swing.*;
import java.awt.*;
import line.ConnectLine;

/**
 * The class represents pair of components with a connecting line.
 *
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 *
 * @author Stanislav Lapitsky
 * @version 1.0
 */
public class JConnector extends JPanel {

    public static final int CONNECT_LINE_TYPE_SIMPLE = 0;
    public static final int CONNECT_LINE_TYPE_RECTANGULAR = 1;
    protected JComponent source;
    protected JComponent dest;
    protected ConnectLine line;
    protected int lineArrow = ConnectLine.LINE_ARROW_NONE;
    protected int lineType = CONNECT_LINE_TYPE_RECTANGULAR;
    protected Color lineColor;
    protected int yOffsetSource;
    protected int yOffsetDest;

    /**
     * Constructs default connector.
     *
     * @param source JComponent
     * @param dest JComponent
     */
    public JConnector(JComponent source, int yOffsetSource, JComponent dest,
            int yOffsetDest) {
        this(source, yOffsetSource, dest, yOffsetSource,
                ConnectLine.LINE_ARROW_DEST, Color.BLACK);
    }

    /**
     * Constructs a connector with specified arrow and color.
     *
     * @param source JComponent
     * @param dest JComponent
     * @param lineArrow int
     * @param lineColor Color
     */
    public JConnector(JComponent source, int yOffsetSource, JComponent dest,
            int yOffsetDest, int lineArrow, Color lineColor) {
        this(source, yOffsetSource, dest, yOffsetDest, lineArrow,
                CONNECT_LINE_TYPE_RECTANGULAR, lineColor);
    }

    /**
     * Constructs a connector with specified arrow, line type and color.
     *
     * @param source JComponent
     * @param dest JComponent
     * @param lineArrow int
     * @param lineType int
     * @param lineColor Color
     */
    public JConnector(JComponent source, int yOffsetSource, JComponent dest,
            int yOffsetDest, int lineArrow, int lineType, Color lineColor) {
        this.source = source;
        this.yOffsetSource = yOffsetSource;
        this.dest = dest;
        this.yOffsetDest = yOffsetDest;
        this.lineArrow = lineArrow;
        this.lineType = lineType;
        this.lineColor = lineColor;
    }

    /**
     * Overrides parent's paint(). It resets clip to draw connecting line
     * between components and set the clip back.
     *
     * @param g Graphics
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        calculateLine();
        if (line != null) {
            Shape oldClip = g2d.getClip();
            g2d.setClip(getLineBounds());
            g2d.setColor(lineColor);
            line.paint(g2d);
            g2d.setClip(oldClip);
        }
    }

    protected void calculateLine() {
        Rectangle rSource = source.getBounds();
        Rectangle rDest = dest.getBounds();
        int yS = rSource.y + this.yOffsetSource;
        int yD = rDest.y + this.yOffsetDest;
        int xS, xD;

        if (rSource.intersects(rDest)) {
            line = null;
            return;
        }

		// S liegt links von D und schneidet D nicht mit seiner breite bzw
        // anders herum
        boolean xIntersect = (rSource.x <= rDest.x && rSource.x + rSource.width
                + 14/* für extra abstand für die pfeile */ >= rDest.x)
                || (rDest.x <= rSource.x && rDest.x + rDest.width + 14 >= rSource.x);

        // S liegt links von D und schneidet D nicht mit seiner breite
        if (!xIntersect) {
            if (rSource.x < rDest.x) {
                xS = rSource.x + rSource.width;
                xD = rDest.x;
            } else {
                xS = rSource.x;
                xD = rDest.x + rDest.width;
            }
            if (yS == yD) {
                line = new ConnectLine(new Point(xS, yS), new Point(xD, yD),
                        ConnectLine.LINE_TYPE_SIMPLE,
                        ConnectLine.LINE_START_HORIZONTAL, lineArrow);
            } else {
                line = new ConnectLine(new Point(xS, yS), new Point(xD, yD),
                        ConnectLine.LINE_TYPE_RECT_2BREAK,
                        ConnectLine.LINE_START_HORIZONTAL, lineArrow);
            }

        } else {
            if (Math.abs(rSource.x - rDest.x) < Math.abs((rSource.x + rSource.width) - (rDest.x + rDest.width))) {
                xS = rSource.x;
                xD = rDest.x;
                line = new ConnectLine(new Point(xS, yS), new Point(xD, yD),
                        ConnectLine.LINE_TYPE_RECT_2BREAK_EXTRA_L,
                        ConnectLine.LINE_START_HORIZONTAL, lineArrow);

            } else {
                xS = rSource.x + rSource.width;
                xD = rDest.x + rDest.width;
                line = new ConnectLine(new Point(xS, yS), new Point(xD, yD),
                        ConnectLine.LINE_TYPE_RECT_2BREAK_EXTRA_R,
                        ConnectLine.LINE_START_HORIZONTAL, lineArrow);
            }

			// Erst abstand von S bzw. D schaffen und dann wieder zur�ck, da
            // anderes Objekt sich im selben Bereich befindet
			/*if (rSource.x < rDest.x) {

             line = new ConnectLine(new Point(xS, yS), new Point(xD, yD),
             ConnectLine.LINE_TYPE_RECT_2BREAK_EXTRA_L,
             ConnectLine.LINE_START_HORIZONTAL, lineArrow);
             } else {

             }*/
        }

        /*
         * S liegt links von D und schneidet D nicht mit seiner breite bzw
         * anders herum boolean xIntersect = (rSource.x <= rDest.x && rSource.x
         * + rSource.width >= rDest.x) || (rDest.x <= rSource.x && rDest.x +
         * rDest.width >= rSource.x);
         * 
         * S liegt links von D und schneidet D nicht mit seiner breite boolean
         * yIntersect = rSource.y <= rDest.y && rSource.y + rSource.height >=
         * rDest.y || (rDest.y <= rSource.y && rDest.y + rDest.height >=
         * rSource.y);
         * 
         * if (xIntersect) { int y1; int y2; int x1 = rSource.x + rSource.width
         * / 2; int x2 = rDest.x + rDest.width / 2; if (rSource.y +
         * rSource.height <= rDest.y) { //source higher y1 = rSource.y +
         * rSource.height; y2 = rDest.y; } else { y1 = rSource.y; y2 = rDest.y +
         * rDest.height; } line = new ConnectLine(new Point(x1, y1), new
         * Point(x2, y2), ConnectLine.LINE_TYPE_RECT_2BREAK,
         * ConnectLine.LINE_START_VERTICAL, lineArrow); if (lineType ==
         * CONNECT_LINE_TYPE_SIMPLE) {
         * line.setLineType(ConnectLine.LINE_TYPE_SIMPLE); } } else if
         * (yIntersect) { int y1 = rSource.y + rSource.height / 2; ; int y2 =
         * rDest.y + rDest.height / 2; ; int x1; int x2; if (rSource.x +
         * rSource.width <= rDest.x) { x1 = rSource.x + rSource.width; x2 =
         * rDest.x; } else { x1 = rSource.x; x2 = rDest.x + rDest.width; } line
         * = new ConnectLine(new Point(x1, y1), new Point(x2, y2),
         * ConnectLine.LINE_TYPE_RECT_2BREAK, ConnectLine.LINE_START_HORIZONTAL,
         * lineArrow); if (lineType == CONNECT_LINE_TYPE_SIMPLE) {
         * line.setLineType(ConnectLine.LINE_TYPE_SIMPLE); } } else { int y1;
         * int y2; int x1; int x2; if (rSource.y + rSource.height <= rDest.y) {
         * //source higher y1 = rSource.y + rSource.height / 2; y2 = rDest.y; if
         * (rSource.x + rSource.width <= rDest.x) { x1 = rSource.x +
         * rSource.width; } else { x1 = rSource.x; } x2 = rDest.x;// +
         * rDest.width / 2; } else { y1 = rSource.y + rSource.height / 2; y2 =
         * rDest.y + rDest.height; if (rSource.x + rSource.width <= rDest.x) {
         * x1 = rSource.x + rSource.width; } else { x1 = rSource.x; } x2 =
         * rDest.x + rDest.width / 2; } line = new ConnectLine(new Point(x1,
         * y1), new Point(x2, y2), ConnectLine.LINE_TYPE_RECT_1BREAK,
         * ConnectLine.LINE_START_HORIZONTAL, lineArrow); if (lineType ==
         * CONNECT_LINE_TYPE_SIMPLE) {
         * line.setLineType(ConnectLine.LINE_TYPE_SIMPLE); } }
         */
    }

    protected Rectangle getLineBounds() {
        int add = 10;
        int maxX = Math.max(line.getP1().x, line.getP2().x);
        int minX = Math.min(line.getP1().x, line.getP2().x);
        int maxY = Math.max(line.getP1().y, line.getP2().y);
        int minY = Math.min(line.getP1().y, line.getP2().y);

        Rectangle res = new Rectangle(minX - add, minY - add, maxX - minX + 2
                * add, maxY - minY + 2 * add);
        return res;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color c) {
        lineColor = c;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int type) {
        lineType = type;
    }

    public int getLineArrow() {
        return lineArrow;
    }

    public void setLineArrow(int arrow) {
        lineArrow = arrow;
    }
}
