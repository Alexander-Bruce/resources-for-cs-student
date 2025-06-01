#ifndef SCORE_H
#define SCORE_H

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
class score;
}

class score : public QWidget
{
    Q_OBJECT

public:
    explicit score(QWidget *parent = nullptr);
    QGraphicsView mGameView;
    QGraphicsScene mScene;

    QGraphicsPixmapItem mPlane;
    QGraphicsPixmapItem mBackGround1;
    QGraphicsPixmapItem mBackGround2;
    //Form f;
    //void on_back_clicked(Form *f);
    ~score();

private:
    Ui::score *ui;
};

#endif // SCORE_H
