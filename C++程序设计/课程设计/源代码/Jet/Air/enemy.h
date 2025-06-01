//仿照飞机类，定义敌机类，类下有三种敌机
#ifndef ENEMY_H
#define ENEMY_H

#include <QObject>
#include <QGraphicsPixmapItem>

class Enemy1 : public QGraphicsPixmapItem
{
public:
    //定义敌机类，类下有三种敌机


    explicit Enemy1();
    //坐标
    int mX1;
    int mY1;
    int getlife();
    void setlife(int life);

private:
    int mEnemylife1;

signals:

public slots:


};

class Enemy2 : public QGraphicsPixmapItem
{
public:
    //定义敌机类，类下有三种敌机


    explicit Enemy2();
    int mX2;
    int mY2;
    int getlife();
    void setlife(int life);
signals:

private:
    int mEnemylife2;

public slots:


};

class Enemy3 :public QGraphicsPixmapItem
{
public:
    //定义敌机类，类下有三种敌机


    explicit Enemy3();
    int mEnemylife3 ;
    int mX3;
    int mY3;
signals:

public slots:


};



#endif // MYPLANE_H
