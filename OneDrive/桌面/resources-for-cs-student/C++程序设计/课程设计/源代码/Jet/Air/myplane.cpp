#include "myplane.h"

MyPlane::MyPlane()
{
    this->setPixmap(QPixmap("://resource/images/me1.png"));
    this->setPos(180, 550);
    this->mlife=120;
}

int MyPlane::getlife()
{
    return this->mlife;
}

void MyPlane::setlife(int life)
{
    this->mlife = life;
}
