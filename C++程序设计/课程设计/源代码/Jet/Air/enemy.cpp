//仿照飞机类，定义敌机类，类下有三种飞机
#include "enemy.h"
Enemy2::Enemy2()
{
    //仿照飞机类，定义敌机类，类下有三种飞机
    this->setPixmap(QPixmap("://resource/images/enemy2.png"));
    this->setPos(250, 350);
//    this->mEnemylife2 = 200 ;
    this->mEnemylife2 = 2 ;
}

int Enemy2::getlife()
{
    return this->mEnemylife2;
}

void Enemy2::setlife(int life)
{
    this->mEnemylife2 = life;
}


Enemy1::Enemy1()
{
    //仿照飞机类，定义敌机类，类下有三种飞机
    this->setPixmap(QPixmap("://resource/images/enemy1.png"));
    this->setPos(250, 350);

}

int Enemy1::getlife()
{
    return this->mEnemylife1;
}

void Enemy1::setlife(int life)
{
    this->mEnemylife1 = life;
}

Enemy3::Enemy3()
{
    //仿照飞机类，定义敌机类，类下有三种飞机
    this->setPixmap(QPixmap("://resource/images/enemy3_n1.png"));
    this->setPos(250, 350);
}
