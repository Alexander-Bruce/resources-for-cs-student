#ifndef JET_H
#define JET_H

#include <QWidget>
#include <QGraphicsPixmapItem>
#include <QGraphicsView>
#include <QGraphicsScene>
#include <QList>
#include "myplane.h"
#include "Bullet.h"
#include "speed.h"
#include "bomb.h"
#include "enemy.h"
#include <QIcon>
#include <QTimer>
#include <QCloseEvent>
//文件头文件

#include <qfile.h>






QT_BEGIN_NAMESPACE
namespace Ui {
class Widget;
}
//QT_END_NAMESPACE

class Widget : public QWidget
{
    Q_OBJECT

public:
    explicit Widget(QWidget *parent = nullptr);
    ~Widget();

    void BGMOve();

    void PlaneMove();
    void BulletMove();
    void BombMove();
    void EnemyMove();
    void EnemyBulletMove();
    void BulletClash();
    void LifeTimer();

    void keyPressEvent(QKeyEvent* event);
    void keyReleaseEvent(QKeyEvent *event);

    void closeEvent(QCloseEvent *event);

private:
    Ui::Widget *ui;
    QGraphicsView mGameView;
    QGraphicsScene mScene;

    QGraphicsPixmapItem mPlane;
    QGraphicsPixmapItem mBackGround1;
    QGraphicsPixmapItem mBackGround2;

    MyPlane mMyPlane;
    MyPlane Plane;
    MyPlane Planelife1;
    MyPlane Planelife2;
    MyPlane Planelife3;
    MyBullet mMyBullet;
    MyBomb mMyBomb;
    EnemyBullet mEnemyBullet;
    Enemy1 mEnemy1;
    Enemy2 mEnemy2;
    Enemy3 mEnemy3;
    SPEED mSPEED;
    SPEED GetRand;

    QTimer* mBGMoveTimer;
    QTimer* mPlaneMoveTimer;
    QTimer* mBulletTimer;
    QTimer* mBulletMoveTimer;
    QTimer* mBombTimer;
    QTimer* mEnemyTimer;
    QTimer* mEnemyBulletMoveTimer;
    QTimer* mBulletClashTimer;
    QTimer* mLifeTimer;

    QList<int>mKeyList;
    QList<MyBullet*>mBulletList;
    QList<MyBomb*>mBombList;
    QList<Enemy1*>mEnemy1List;
    QList<Enemy2*>mEnemy2List;
    QList<Enemy3*>mEnemy3List;
    QList<EnemyBullet*>mEnemyBulletList;


    //QMediaPlayer *player；

public slots:

};
#endif // JET_H

