#include <Reference.h>
#include "ui_reference.h"
#include "form.h"
#include <ui_form.h>
#include <Widget.h>
#include <ui_widget.h>
#include <QTimer>
#include <QKeyEvent>
#include <QList>
#include <ctime>
#include <QThread>
//#include <windows.h>
#include <myplane.h>
#include <Bullet.h>
#include <speed.h>
#include <bomb.h>
#include <enemy.h>
#include <QSoundEffect>
#include <QGraphicsPixmapItem>
#include <QGraphicsView>
#include <QGraphicsScene>

Reference::Reference(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Reference)
{
    ui->setupUi(this);
    this->setFixedSize(345, 385);
    this->setWindowTitle("Reference");
    this->setStyleSheet("QScrollArea { scrollbar-width: 0px; }");
//    mGameView.setSceneRect(QRect(0, 0, 530, 700));
//    mScene.setSceneRect(QRect(0, 0, 530, 700));
    //f.show();
}

Reference::~Reference()
{
    delete ui;
}

//void Reference::on_back_clicked(Form *f)
//{
//    connect(Reference::ui->back,&QPushButton::clicked,this,[=](){
//        this->hide();
//        f->show();
//    });
//}

