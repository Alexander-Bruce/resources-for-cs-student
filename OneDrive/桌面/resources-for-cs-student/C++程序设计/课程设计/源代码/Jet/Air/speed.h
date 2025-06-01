#ifndef SPEED_H
#define SPEED_H

#include <QObject>
#include <QGraphicsPixmapItem>
#include <iostream>
#include <ctime>
#include <cstdlib>
#define S 200
class SPEED
{
public:
    int judge = 1;
    int speed = S;
    int Enemy1speed = 15;
    int Enemy2speed = 10;
    int EnemyBulletSpeed = 25;
    int score = 0;
    //随机数
    int random_x;
    int random_y;
    int getRand(int start, int end);
    void setSpeed(int speed);
};

#endif // SPEED_H

