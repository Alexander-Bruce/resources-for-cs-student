#ifndef MYPLANE_H
#define MYPLANE_H

#include <QObject>
#include <QGraphicsPixmapItem>

class MyPlane : public QGraphicsPixmapItem
{
public:
    explicit MyPlane();
    int getlife();
    void setlife(int life);

private:
    int mlife;


signals:

public slots:


};

#endif // MYPLANE_H
