package com.kame.prismlines.icon;

import com.intellij.ui.JBColor;
import com.intellij.util.ui.ImageUtil;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public final class PrismIconFactory {

    private PrismIconFactory() {}

    public static Icon createColorIcon(Color color) {

        int size = 16;

        BufferedImage image = ImageUtil.createImage(
                size,
                size,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        double cx = size / 2.0;
        double cy = size / 2.0;
        double radius = size / 2.2;

        // puntos del pentágono
        Point2D[] points = new Point2D[5];

        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(-90 + i * 72);

            points[i] = new Point2D.Double(
                    cx + radius * Math.cos(angle),
                    cy + radius * Math.sin(angle)
            );
        }

        // path con esquinas suavizadas
        Path2D path = new Path2D.Double();

        double round = 1.5; // radio de suavizado

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

            // puntos de entrada/salida suavizados
            double p1x = curr.getX() - ux1 * round;
            double p1y = curr.getY() - uy1 * round;

            double p2x = curr.getX() + ux2 * round;
            double p2y = curr.getY() + uy2 * round;

            if (i == 0) {
                path.moveTo(p1x, p1y);
            } else {
                path.lineTo(p1x, p1y);
            }

            path.quadTo(
                    curr.getX(),
                    curr.getY(),
                    p2x,
                    p2y
            );
        }

        path.closePath();

        g.setColor(color);
        g.fill(path);

        g.dispose();

        return new ImageIcon(image);
    }

    public static Icon createOutlinePrismIcon() {

        int size = 16;

        BufferedImage image = ImageUtil.createImage(
                size,
                size,
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = image.createGraphics();

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        double cx = size / 2.0;
        double cy = size / 2.0;
        double radius = size / 2.2;

        Polygon pentagon = new Polygon();

        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(-90 + i * 72);

            int x = (int) (cx + radius * Math.cos(angle));
            int y = (int) (cy + radius * Math.sin(angle));

            pentagon.addPoint(x, y);
        }

        // SOLO OUTLINE
        g.setColor(new JBColor(new Color(180, 180, 180, 255), new Color(100, 100, 100, 255)));
        g.setStroke(new BasicStroke(1.5f));

        g.drawPolygon(pentagon);

        g.dispose();

        return new ImageIcon(image);
    }
}
