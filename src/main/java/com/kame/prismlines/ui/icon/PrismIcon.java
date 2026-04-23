package com.kame.prismlines.ui.icon;

import com.intellij.util.ui.JBUI;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * Custom vector icon used by PrismLines to render gutter markers.
 *
 * <p>Draws a rounded pentagon shape that represents a colored line marker.
 * Supports filled and outline rendering styles.</p>
 */
public class PrismIcon implements Icon {

    private final Color color;
    private final IconStyle style;
    private static final int SCALE = 16;

    /**
     * Creates a Prism icon.
     *
     * @param color icon color
     * @param style rendering style (filled or outline)
     */
    public PrismIcon(Color color, IconStyle style) {
        this.color = color;
        this.style = style;
    }

    /**
     * Paints the icon using vector graphics.
     *
     * <p>Applies anti-aliasing and renders either a filled or stroked
     * rounded pentagon depending on the selected style.</p>
     */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
        );

        Path2D path = createPentagonPath(x, y);

        g2.setColor(color);

        if (style == IconStyle.FILLED) {
            g2.fill(path);
        } else {
            g2.setStroke(new BasicStroke(
                    JBUI.scale(1.8f),
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));
            g2.draw(path);
        }

        g2.dispose();
    }

    /**
     * Builds a rounded pentagon shape centered in the icon bounds.
     *
     * @param x horizontal offset
     * @param y vertical offset
     * @return constructed path
     */
    private Path2D createPentagonPath(int x, int y) {
        int size = JBUI.scale(SCALE);

        double cx = x + size / 2.0;
        double cy = y + size / 2.0;
        double radius = size / 2.2;

        Point2D[] points = new Point2D[5];

        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(-90 + i * 72);

            points[i] = new Point2D.Double(
                    cx + radius * Math.cos(angle),
                    cy + radius * Math.sin(angle)
            );
        }

        Path2D path = new Path2D.Double();
        double round = JBUI.scale(2);

        for (int i = 0; i < 5; i++) {

            Point2D prev = points[(i + 4) % 5];
            Point2D curr = points[i];
            Point2D next = points[(i + 1) % 5];

            double vx1 = curr.getX() - prev.getX();
            double vy1 = curr.getY() - prev.getY();
            double len1 = Math.hypot(vx1, vy1);

            double vx2 = next.getX() - curr.getX();
            double vy2 = next.getY() - curr.getY();
            double len2 = Math.hypot(vx2, vy2);

            double ux1 = vx1 / len1;
            double uy1 = vy1 / len1;

            double ux2 = vx2 / len2;
            double uy2 = vy2 / len2;

            double p1x = curr.getX() - ux1 * round;
            double p1y = curr.getY() - uy1 * round;

            double p2x = curr.getX() + ux2 * round;
            double p2y = curr.getY() + uy2 * round;

            if (i == 0) {
                path.moveTo(p1x, p1y);
            } else {
                path.lineTo(p1x, p1y);
            }

            path.quadTo(curr.getX(), curr.getY(), p2x, p2y);
        }

        path.closePath();

        return path;
    }

    /**
     * Returns icon width in IDE-scaled pixels.
     */
    @Override
    public int getIconWidth() {
        return JBUI.scale(SCALE);
    }

    /**
     * Returns icon height in IDE-scaled pixels.
     */
    @Override
    public int getIconHeight() {
        return JBUI.scale(SCALE);
    }
}
