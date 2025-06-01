//仿照飞机类，定义子弹类
#include "bomb.h"
MyBomb::MyBomb()
{
    this->setPixmap(QPixmap("://resource/images/bomb.png"));
    this->setPos(250, 350);
}
