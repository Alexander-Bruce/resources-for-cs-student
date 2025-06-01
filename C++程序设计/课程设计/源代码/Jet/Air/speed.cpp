#include "speed.h"
#include <QObject>
#include <QGraphicsPixmapItem>
#include <iostream>
#include <ctime>
#include <cstdlib>
using namespace std;
int SPEED::getRand(int start, int end)
{
    return (rand() % (end - start + 1) ) + start;
}
