/****************************************************************************
**
** Copyright (C) 1992-2009 Nokia. All rights reserved.
**
** This file is part of Qt Jambi.
**
** $BEGIN_LICENSE$
** GNU Lesser General Public License Usage
** This file may be used under the terms of the GNU Lesser
** General Public License version 2.1 as published by the Free Software
** Foundation and appearing in the file LICENSE.LGPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU Lesser General Public License version 2.1 requirements
** will be met: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html.
**
** In addition, as a special exception, Nokia gives you certain
** additional rights. These rights are described in the Nokia Qt LGPL
** Exception version 1.0, included in the file LGPL_EXCEPTION.txt in this
** package.
**
** GNU General Public License Usage
** Alternatively, this file may be used under the terms of the GNU
** General Public License version 3.0 as published by the Free Software
** Foundation and appearing in the file LICENSE.GPL included in the
** packaging of this file.  Please review the following information to
** ensure the GNU General Public License version 3.0 requirements will be
** met: http://www.gnu.org/copyleft/gpl.html.
** $END_LICENSE$

**
** This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
** WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
**
****************************************************************************/

package com.trolltech.demos.imageviewer;

import com.trolltech.launcher.Worker;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.qreal.QReal;

public class View extends QWidget
{
    public Signal1<Boolean> valid = new Signal1<Boolean>();

    public View(QWidget parent) {
        super(parent);

        int size = 40;
        QPixmap bg = new QPixmap(size, size);
        bg.fill(new QColor(com.trolltech.qt.core.Qt.GlobalColor.white));
        QPainter p = new QPainter();
        p.begin(bg);
        p.fillRect(0, 0, size/2, size/2, new QBrush(new QColor(com.trolltech.qt.core.Qt.GlobalColor.lightGray)));
        p.fillRect(size/2, size/2, size/2, size/2, new QBrush(new QColor(com.trolltech.qt.core.Qt.GlobalColor.lightGray)));
        p.end();

        QPalette pal = palette();
        pal.setBrush(backgroundRole(), new QBrush(bg));
        setPalette(pal);

        setAutoFillBackground(true);

        delayedUpdate.setDelay(10);
    }

    public QImage image() {
        return original;
    }

    public void setImage(QImage original) {
        this.original = original != null ? original.convertToFormat(QImage.Format.Format_ARGB32_Premultiplied) : null;
        resetImage();

        valid.emit(original != null);
    }

    public QImage modifiedImage() {
        return modified;
    }

    public void setColorBalance(int c) {
        colorBalance = c;
        resetImage();
    }

    public void setRedCyan(int c) {
        redCyan = c;
        resetImage();
    }

    public void setGreenMagenta(int c) {
        greenMagenta = c;
        resetImage();
    }

    public void setBlueYellow(int c) {
        blueYellow = c;
        resetImage();
    }

    public void setInvert(boolean b) {
        invert = b;
        resetImage();
    }

    public QSize sizeHint() {
        return new QSize(500, 500);
    }

    protected void paintEvent(QPaintEvent e) {
        if (background == null) {
            background = new QPixmap(size());
            QPainter p = new QPainter(background);
            QLinearGradient lg = new QLinearGradient(0, 0, 0, height());
            lg.setColorAt(QReal.valueOf(0.5).platformValue(), new QColor(com.trolltech.qt.core.Qt.GlobalColor.black));
            lg.setColorAt(QReal.valueOf(0.7).platformValue(), QColor.fromRgbF(QReal.valueOf(0.5).platformValue(), QReal.valueOf(0.5).platformValue(), QReal.valueOf(0.6).platformValue()));
            lg.setColorAt(1, QColor.fromRgbF(QReal.valueOf(0.8).platformValue(), QReal.valueOf(0.8).platformValue(), QReal.valueOf(0.9).platformValue()));
            p.fillRect(background.rect(), new QBrush(lg));
            p.end();
        }

        QPainter p = new QPainter(this);
        p.drawPixmap(0, 0, background);

        if (modified == null)
            updateImage();

        if (modified != null && !modified.isNull()) {
            p.setViewport(rect().adjusted(10, 10, -10, -10));
            QRect rect = rectForImage(modified);

            p.setRenderHint(QPainter.RenderHint.SmoothPixmapTransform);
            p.drawImage(rect, modified);

            p.drawImage(0, height() - reflection.height() + 10, reflection);

        }

        p.end();
    }

    protected void resizeEvent(QResizeEvent e) {
        if (background != null) {
            background.dispose();
            background = null;
        }

        resetImage();
    }

    private final void resetImage() {
        if (modified != null)
            modified.dispose();
        modified = null;
        delayedUpdate.start();
    }

    private static final QColor decideColor(int value, QColor c1, QColor c2) {
        QColor c = value < 0 ? c1 : c2;
        double sign = value < 0 ? -1.0 : 1.0;
        return QColor.fromRgbF(QReal.valueOf(c.redF()).platformValue(), QReal.valueOf(c.greenF()).platformValue(), QReal.valueOf(c.blueF()).platformValue(), QReal.valueOf(sign * value * 0.5 / 100).platformValue());
    }

    private void updateImage() {
        if (original == null || original.isNull())
            return;

        if (modified != null)
            modified.dispose();

        modified = original.copy();

        QPainter p = new QPainter();
        p.begin(modified);
        p.setCompositionMode(QPainter.CompositionMode.CompositionMode_SourceAtop);
        if (redCyan != 0) {
            QColor c = decideColor(redCyan, new QColor(com.trolltech.qt.core.Qt.GlobalColor.cyan), new QColor(com.trolltech.qt.core.Qt.GlobalColor.red));
            p.fillRect(0, 0, modified.width(), modified.height(), new QBrush(c));
        }
        if (greenMagenta != 0) {
            QColor c = decideColor(greenMagenta, new QColor(com.trolltech.qt.core.Qt.GlobalColor.magenta), new QColor(com.trolltech.qt.core.Qt.GlobalColor.green));
            p.fillRect(0, 0, modified.width(), modified.height(), new QBrush(c));
        }
        if (blueYellow != 0) {
            QColor c = decideColor(blueYellow, new QColor(com.trolltech.qt.core.Qt.GlobalColor.yellow), new QColor(com.trolltech.qt.core.Qt.GlobalColor.blue));
            p.fillRect(0, 0, modified.width(), modified.height(), new QBrush(c));
        }
        if (colorBalance != 0) {
            QColor c = decideColor(colorBalance, new QColor(com.trolltech.qt.core.Qt.GlobalColor.white), new QColor(com.trolltech.qt.core.Qt.GlobalColor.black));
            p.fillRect(0, 0, modified.width(), modified.height(), new QBrush(c));
        }

        if (invert) {
            p.setCompositionMode(QPainter.CompositionMode.CompositionMode_Difference);
            p.fillRect(modified.rect(), new QBrush(new QColor(com.trolltech.qt.core.Qt.GlobalColor.white)));
        }

        p.end();

        reflection = createReflection(modified);
    }

    private QRect rectForImage(QImage image) {
        QSize isize = image.size();
        QSize size = size();

        size.setHeight(size.height() * 3 / 4);
        size.setWidth(size.width() * 3 / 4);

        isize.scale(size, Qt.AspectRatioMode.KeepAspectRatio);

        return new QRect(width() / 2 - isize.width() / 2,
                         size.height() / 2 - isize.height() / 2,
                         isize.width(), isize.height());
    }

    private QImage createReflection(QImage source) {
        if (source == null || source.isNull())
            return null;

        QRect r = rectForImage(source);

        QImage image = new QImage(width(),
                                  height() - r.height() - r.y(),
                                  QImage.Format.Format_ARGB32_Premultiplied);
        image.fill(0);

        double iw = image.width();
        double ih = image.height();

        QPainter pt = new QPainter(image);

        pt.setRenderHint(QPainter.RenderHint.SmoothPixmapTransform);
        pt.setRenderHint(QPainter.RenderHint.Antialiasing);

        pt.save(); {
            QPolygonF imageQuad = new QPolygonF();
            imageQuad.add(0, 0);
            imageQuad.add(0, source.height());
            imageQuad.add(source.width(), source.height());
            imageQuad.add(source.width(), 0);
            QPolygonF targetQuad = new QPolygonF();
            targetQuad.add(0, QReal.valueOf(ih).platformValue());
            targetQuad.add(QReal.valueOf(iw / 2 - r.width() / 2).platformValue(), 0);
            targetQuad.add(QReal.valueOf(iw / 2 + r.width() / 2).platformValue(), 0);
            targetQuad.add(QReal.valueOf(iw).platformValue(), QReal.valueOf(ih).platformValue());
            try {
                pt.setTransform(QTransform.quadToQuad(imageQuad, targetQuad));
                pt.drawImage(imageQuad.boundingRect(), source);
            } catch (IllegalArgumentException e) {
                // user has resized the view too small
            }
        } pt.restore();

        QLinearGradient lg = new QLinearGradient(0, 0, 0, image.height());
        lg.setColorAt(QReal.valueOf(0.1).platformValue(), QColor.fromRgbF(0, 0, 0, QReal.valueOf(0.4).platformValue()));
        lg.setColorAt(QReal.valueOf(0.6).platformValue(), new QColor(com.trolltech.qt.core.Qt.GlobalColor.transparent));
        pt.setCompositionMode(QPainter.CompositionMode.CompositionMode_DestinationIn);
        pt.fillRect(image.rect(), new QBrush(lg));
        pt.end();

        return image;
    }



    private int colorBalance;
    private int redCyan;
    private int greenMagenta;
    private int blueYellow;

    private boolean invert;

    private QImage original;
    private QImage modified;
    private QImage reflection;
    private QPixmap background;

    private Worker delayedUpdate = new Worker(this) {
        public void execute() {
            update();
        }
    };
}
