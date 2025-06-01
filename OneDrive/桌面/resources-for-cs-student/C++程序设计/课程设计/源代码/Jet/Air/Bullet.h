#ifndef BULLET_H
#define BULLET_H

#include <QObject>
#include <QGraphicsPixmapItem>
//仿照飞机类定义子弹类，使得飞机可以发射子弹

class MyBullet : public QGraphicsPixmapItem
{
public:
    explicit MyBullet();

signals:

public slots:


};
class EnemyBullet : public QGraphicsPixmapItem
{
public:
    explicit EnemyBullet();

signals:

public slots:


};
#endif // MYPLANE_H
