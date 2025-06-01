#include "form.h"
#include "ui_form.h"
#include <Widget.h>
#include <ui_widget.h>
#include <reference.h>
#include <ui_reference.h>
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
#include <QFileDialog>
#include <QDesktopServices>

Form::Form(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Form)
{
    ui->setupUi(this);
    this->setFixedSize(530, 700);
    GameView.setSceneRect(QRect(0, 0, 530, 700));
    Scene.setSceneRect(QRect(0, 0, 530, 700));

    BackGround1.setPixmap(QPixmap("://resource/images/background.png"));
    BackGround1.setPos(0, 0);
    Scene.addItem(&BackGround1);
    BackGround2.setPixmap(QPixmap(":/resource/images/pause_pressed.png"));
    BackGround2.setPos(0, 300);
    Scene.addItem(&BackGround2);
    openButton = new QPushButton("打开文档", this);
    connect(openButton, &QPushButton::clicked, this, &Form::on_pushButton_clicked);

}

Form::~Form()
{
    delete ui;
}

void Form::on_start_clicked()
{
    connect(Form::ui->start,&QPushButton::clicked,this,[=](){
        this->hide();
        Widget *w =  new Widget;
        w->show();
    });
}


void Form::on_To_reference_clicked()
{
    connect(Form::ui->To_reference,&QPushButton::clicked,this,[=](){
        Reference *r =  new Reference;
        r->show();
    });
}




void Form::on_pushButton_clicked()
{
    QString filePath = "data.txt";
    QDesktopServices::openUrl(QUrl::fromLocalFile(filePath));
}

