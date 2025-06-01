//仿照飞机类，定义子弹类
#include "Bullet.h"
MyBullet::MyBullet()
{
	this->setPixmap(QPixmap("://resource/images/bullet1.png"));
	this->setPos(250, 350);
}