/*   Ported from: src.gui.painting.qpainter.cpp
<snip>
//! [0]
    void SimpleExampleWidget::paintEvent(QPaintEvent *)
    {
        QPainter painter(this);
        painter.setPen(Qt::blue);
        painter.setFont(QFont("Arial", 30));
        painter.drawText(rect(), Qt::AlignCenter, "Qt");
    }
//! [0]


//! [1]
        void MyWidget::paintEvent(QPaintEvent *)
        {
            QPainter p;
            p.begin(this);
            p.drawLine(...);        // drawing code
            p.end();
        }
//! [1]


//! [2]
        void MyWidget::paintEvent(QPaintEvent *)
        {
            QPainter p(this);
            p.drawLine(...);        // drawing code
        }
//! [2]


//! [3]
        painter->begin(0); // impossible - paint device cannot be 0

        QPixmap image(0, 0);
        painter->begin(&image); // impossible - image.isNull() == true;

        painter->begin(myWidget);
        painter2->begin(myWidget); // impossible - only one painter at a time
//! [3]


//! [4]
        void QPainter::rotate(qreal angle)
        {
            QMatrix matrix;
            matrix.rotate(angle);
            setWorldMatrix(matrix, true);
        }
//! [4]


//! [5]
    QPainterPath path;
    path.moveTo(20, 80);
    path.lineTo(20, 30);
    path.cubicTo(80, 0, 50, 50, 80, 80);

    QPainter painter(this);
    painter.drawPath(path);
//! [5]


//! [6]
    QLineF line(10.0, 80.0, 90.0, 20.0);

    QPainter(this);
    painter.drawLine(line);
//! [6]


//! [7]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);

        QPainter painter(this);
        painter.drawRect(rectangle);
//! [7]


//! [8]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);

        QPainter painter(this);
        painter.drawRoundedRect(rectangle, 20.0, 15.0);
//! [8]


//! [9]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);

        QPainter painter(this);
        painter.drawEllipse(rectangle);
//! [9]


//! [10]
    QRectF rectangle(10.0, 20.0, 80.0, 60.0);
    int startAngle = 30 * 16;
    int spanAngle = 120 * 16;

    QPainter painter(this);
    painter.drawArc(rectangle, startAngle, spanAngle);
//! [10]


//! [11]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);
        int startAngle = 30 * 16;
        int spanAngle = 120 * 16;

        QPainter painter(this);
        painter.drawPie(rectangle, startAngle, spanAngle);
//! [11]


//! [12]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);
        int startAngle = 30 * 16;
        int spanAngle = 120 * 16;

        QPainter painter(this);
        painter.drawChord(rect, startAngle, spanAngle);
//! [12]


//! [13]
        static const QPointF points[3] = {
            QPointF(10.0, 80.0),
            QPointF(20.0, 10.0),
            QPointF(80.0, 30.0),
        };

        QPainter painter(this);
        painter.drawPolyline(points, 3);
//! [13]


//! [14]
    static const QPointF points[4] = {
        QPointF(10.0, 80.0),
        QPointF(20.0, 10.0),
        QPointF(80.0, 30.0),
        QPointF(90.0, 70.0)
    };

    QPainter painter(this);
    painter.drawPolygon(points, 4);
//! [14]


//! [15]
    static const QPointF points[4] = {
        QPointF(10.0, 80.0),
        QPointF(20.0, 10.0),
        QPointF(80.0, 30.0),
        QPointF(90.0, 70.0)
    };

    QPainter painter(this);
    painter.drawConvexPolygon(points, 4);
//! [15]


//! [16]
    QRectF target(10.0, 20.0, 80.0, 60.0);
    QRectF source(0.0, 0.0, 70.0, 40.0);
    QPixmap pixmap(":myPixmap.png");

    QPainter(this);
    painter.drawPixmap(target, image, source);
//! [16]


//! [17]
        QPainter painter(this);
        painter.drawText(rect, Qt::AlignCenter, tr("Qt by\nTrolltech"));
//! [17]


//! [18]
        QPicture picture;
        QPointF point(10.0, 20.0)
        picture.load("drawing.pic");

        QPainter painter(this);
        painter.drawPicture(0, 0, picture);
//! [18]


//! [19]
        fillRect(rectangle, background()).
//! [19]


//! [20]
    QRectF target(10.0, 20.0, 80.0, 60.0);
    QRectF source(0.0, 0.0, 70.0, 40.0);
    QImage image(":/images/myImage.png");

    QPainter(this);
    painter.drawImage(target, image, source);
//! [20]


</snip>
*/
import com.trolltech.qt.*;
import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;
import com.trolltech.qt.xml.*;
import com.trolltech.qt.network.*;
import com.trolltech.qt.sql.*;
import com.trolltech.qt.svg.*;


public class src_gui_painting_qpainter {
    public static void main(String args[]) {
        QApplication.initialize(args);
//! [0]
    void SimpleExampleWidget.paintEvent(QPaintEvent *)
    {
        QPainter painter(this);
        painter.setPen(Qt.blue);
        painter.setFont(QFont("Arial", 30));
        painter.drawText(rect(), Qt.AlignCenter, "Qt");
    }
//! [0]


//! [1]
        void MyWidget.paintEvent(QPaintEvent *)
        {
            QPainter p;
            p.begin(this);
            p.drawLine(...);        // drawing code
            p.end();
        }
//! [1]


//! [2]
        void MyWidget.paintEvent(QPaintEvent *)
        {
            QPainter p(this);
            p.drawLine(...);        // drawing code
        }
//! [2]


//! [3]
        painter.begin(0); // impossible - paint device cannot be 0

        QPixmap image(0, 0);
        painter.begin(mage); // impossible - image.isNull() == true;

        painter.begin(myWidget);
        painter2.begin(myWidget); // impossible - only one painter at a time
//! [3]


//! [4]
        void QPainter.rotate(double angle)
        {
            QMatrix matrix;
            matrix.rotate(angle);
            setWorldMatrix(matrix, true);
        }
//! [4]


//! [5]
    QPainterPath path;
    path.moveTo(20, 80);
    path.lineTo(20, 30);
    path.cubicTo(80, 0, 50, 50, 80, 80);

    QPainter painter(this);
    painter.drawPath(path);
//! [5]


//! [6]
    QLineF line(10.0, 80.0, 90.0, 20.0);

    QPainter(this);
    painter.drawLine(line);
//! [6]


//! [7]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);

        QPainter painter(this);
        painter.drawRect(rectangle);
//! [7]


//! [8]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);

        QPainter painter(this);
        painter.drawRoundedRect(rectangle, 20.0, 15.0);
//! [8]


//! [9]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);

        QPainter painter(this);
        painter.drawEllipse(rectangle);
//! [9]


//! [10]
    QRectF rectangle(10.0, 20.0, 80.0, 60.0);
    int startAngle = 30 * 16;
    int spanAngle = 120 * 16;

    QPainter painter(this);
    painter.drawArc(rectangle, startAngle, spanAngle);
//! [10]


//! [11]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);
        int startAngle = 30 * 16;
        int spanAngle = 120 * 16;

        QPainter painter(this);
        painter.drawPie(rectangle, startAngle, spanAngle);
//! [11]


//! [12]
        QRectF rectangle(10.0, 20.0, 80.0, 60.0);
        int startAngle = 30 * 16;
        int spanAngle = 120 * 16;

        QPainter painter(this);
        painter.drawChord(rect, startAngle, spanAngle);
//! [12]


//! [13]
        static QPointF points[3] = {
            QPointF(10.0, 80.0),
            QPointF(20.0, 10.0),
            QPointF(80.0, 30.0),
        };

        QPainter painter(this);
        painter.drawPolyline(points, 3);
//! [13]


//! [14]
    static QPointF points[4] = {
        QPointF(10.0, 80.0),
        QPointF(20.0, 10.0),
        QPointF(80.0, 30.0),
        QPointF(90.0, 70.0)
    };

    QPainter painter(this);
    painter.drawPolygon(points, 4);
//! [14]


//! [15]
    static QPointF points[4] = {
        QPointF(10.0, 80.0),
        QPointF(20.0, 10.0),
        QPointF(80.0, 30.0),
        QPointF(90.0, 70.0)
    };

    QPainter painter(this);
    painter.drawConvexPolygon(points, 4);
//! [15]


//! [16]
    QRectF target(10.0, 20.0, 80.0, 60.0);
    QRectF source(0.0, 0.0, 70.0, 40.0);
    QPixmap pixmap(":myPixmap.png");

    QPainter(this);
    painter.drawPixmap(target, image, source);
//! [16]


//! [17]
        QPainter painter(this);
        painter.drawText(rect, Qt.AlignCenter, tr("Qt by\nTrolltech"));
//! [17]


//! [18]
        QPicture picture;
        QPointF point(10.0, 20.0)
        picture.load("drawing.pic");

        QPainter painter(this);
        painter.drawPicture(0, 0, picture);
//! [18]


//! [19]
        fillRect(rectangle, background()).
//! [19]


//! [20]
    QRectF target(10.0, 20.0, 80.0, 60.0);
    QRectF source(0.0, 0.0, 70.0, 40.0);
    QImage image(":/images/myImage.png");

    QPainter(this);
    painter.drawImage(target, image, source);
//! [20]


    }
}