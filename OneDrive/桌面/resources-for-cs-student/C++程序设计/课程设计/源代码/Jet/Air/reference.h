#ifndef REFERENCE_H
#define REFERENCE_H

#include <QWidget>
#include <Widget.h>
#include <ui_widget.h>
#include <QGraphicsPixmapItem>
#include <QGraphicsView>
#include <QGraphicsScene>
#include <QList>
#include <Form.h>
#include <ui_form.h>
#include <myplane.h>
#include <Bullet.h>
#include <speed.h>
#include <bomb.h>
#include <enemy.h>
#include <QIcon>

namespace Ui {
class Reference;
}

class Reference : public QWidget
{
    Q_OBJECT

public:
    explicit Reference(QWidget *parent = nullptr);
    Ui::Reference *ui;
    QGraphicsView mGameView;
    QGraphicsScene mScene;

    QGraphicsPixmapItem mPlane;
    QGraphicsPixmapItem mBackGround1;
    QGraphicsPixmapItem mBackGround2;
    //Form f;
    //void on_back_clicked(Form *f);
    ~Reference();

signals:

private slots:

private:
};

#endif // REFERENCE_H
