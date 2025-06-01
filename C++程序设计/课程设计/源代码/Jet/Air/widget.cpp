#include "widget.h"
#include "ui_widget.h"
#include <QTimer>
#include <QKeyEvent>
#include <QList>
#include <ctime>
#include <QThread>
//#include <windows.h>
#include "myplane.h"
#include "Bullet.h"
#include "speed.h"
#include "bomb.h"
#include "enemy.h"
#include <QSoundEffect>
#include <QWidget>
#include <QApplication>
#include <qfile.h>
#include <QFile>
#include <QDateTime>
#include <QTextStream>
#include <QDebug>

Widget::Widget(QWidget* parent)
    : QWidget(parent)
    , ui(new Ui::Widget)
{
    ui->setupUi(this);
    this->setFixedSize(530, 700);
    this->setWindowTitle("Aircraft battle");
    this->setStyleSheet("QScrollArea { scrollbar-width: 0px; }");
    mGameView.setSceneRect(QRect(0, 0, 530, 700));
    mScene.setSceneRect(QRect(0, 0, 530, 700));

    mBackGround1.setPixmap(QPixmap("://resource/images/background.png"));
    mBackGround2.setPixmap(QPixmap("://resource/images/background.png"));
    Plane.setPixmap(QPixmap("://resource/images/enemy1_down2.png"));
    mBackGround2.setPos(0, -mBackGround2.pixmap().height());

    Planelife1.setPixmap(QPixmap("://resource/images/life.png"));
    Planelife1.setPos(480, 0);
    mScene.addItem(&Planelife1);
    Planelife2.setPixmap(QPixmap("://resource/images/life.png"));
    Planelife2.setPos(480, 40);
    mScene.addItem(&Planelife2);
    Planelife3.setPixmap(QPixmap("://resource/images/life.png"));
    Planelife3.setPos(480, 80);
    mScene.addItem(&Planelife3);

    mScene.addItem(&mBackGround1);
    mScene.addItem(&mBackGround2);
    //mScene.addItem(&Plane);
    mScene.addItem(&mMyPlane);

    mGameView.setScene(&mScene);

    mGameView.setParent(this);
    mGameView.show();

    mBGMoveTimer = new QTimer(this);
    mBGMoveTimer->start(100);
    connect(mBGMoveTimer, &QTimer::timeout, this, &Widget::BGMOve);


    mPlaneMoveTimer = new QTimer(this);
    mPlaneMoveTimer->start(100);
    connect(mPlaneMoveTimer, &QTimer::timeout, this, &Widget::PlaneMove);

    //定义子弹类， 在飞机前方
    mBulletTimer = new QTimer(this);
    mBulletTimer->start(mSPEED.speed);
    connect(mBulletTimer, &QTimer::timeout, this, &Widget::BulletMove);

    //定义敌机类
    mEnemyTimer = new QTimer(this);
    mEnemyTimer->start(3000);
    connect(mEnemyTimer, &QTimer::timeout, this, &Widget::EnemyMove);

    //敌机子弹类
    mEnemyBulletMoveTimer = new QTimer(this);
    mEnemyBulletMoveTimer->start(mSPEED.speed);
    connect(mEnemyBulletMoveTimer, &QTimer::timeout, this, &Widget::EnemyBulletMove);

    //判断子弹碰撞
    mBulletClashTimer = new QTimer(this);
    mBulletClashTimer->start(100);
    connect(mBulletClashTimer, &QTimer::timeout, this, &Widget::BulletClash);

    // 创建 QMediaPlaylist 对象
//    QSoundEffect * startSound1 = new QSoundEffect;//创建对象
//    startSound1->setSource(QUrl::fromLocalFile(":/resource/sound/bullet.wav"));//添加资源
//    startSound1->setLoopCount(1);//设置循环次数int；  QSoundEffect::Infinite 枚举值 无限循环
//    //startSound1->play();//软件启动自动播放

    //定义背景音乐
    //QMediaPlayer *player = new QMediaPlayer;

    // 设置音乐文件的路径
    //QString musicFilePath = "qrc:/resource/sound/I'll Be There - Walk off the Earth.wav";
//    player->setMedia(QUrl("qrc:/resource/sound/I'll Be There - Walk off the Earth.wav"));

//    // 设置音量为50%
//    player->setVolume(50);

//    // 播放音乐
//    player->play();


    //判断我方生命
    mLifeTimer = new QTimer(this);
    mLifeTimer->start(100);
    connect(mLifeTimer, &QTimer::timeout, this, &Widget::LifeTimer);
}

void Widget::LifeTimer()
{
    for (int i = 0; i < mEnemyBulletList.size(); i++)
    {
        if (mEnemyBulletList.at(i)->collidesWithItem(&mMyPlane))
        {
            mScene.removeItem(mEnemyBulletList.at(i));
            delete mEnemyBulletList.at(i);
            mEnemyBulletList.removeAt(i);
            mMyPlane.setlife(mMyPlane.getlife()-1);
            if(mMyPlane.getlife()!=0)
                mSPEED.score--;
        }
    }

    for (int i = 0; i <mEnemy1List.size(); i++)
    {
        if (mEnemy1List.at(i)->collidesWithItem(&mMyPlane))
        {
            mScene.removeItem(mEnemy1List.at(i));
            delete mEnemy1List.at(i);
            mEnemy1List.removeAt(i);
            mMyPlane.setlife(mMyPlane.getlife()-10);
            if(mMyPlane.getlife()!=0)
                mSPEED.score-=10;
        }
    }
    for (int i = 0; i <mEnemy2List.size(); i++)
    {
        if (mEnemy2List.at(i)->collidesWithItem(&mMyPlane))
        {
            mScene.removeItem(mEnemy2List.at(i));
            delete mEnemy2List.at(i);
            mEnemy2List.removeAt(i);
            mMyPlane.setlife(mMyPlane.getlife()-10);
            if(mMyPlane.getlife()!=0)
                mSPEED.score-=10;
        }
    }


    mScene.removeItem(&Planelife1);
    mScene.removeItem(&Planelife2);
    mScene.removeItem(&Planelife3);
    if(mMyPlane.getlife()>=80)
    {
        mScene.addItem(&Planelife1);
        mScene.addItem(&Planelife2);
        mScene.addItem(&Planelife3);
    }
    else if(mMyPlane.getlife()>=40)
    {
        mScene.addItem(&Planelife1);
        mScene.addItem(&Planelife2);
    }
    else if(mMyPlane.getlife()>=0)
    {
         mScene.addItem(&Planelife1);
    }
    else
    {

    }

    QSoundEffect * startSound1 = new QSoundEffect;//创建对象
    startSound1->setSource(QUrl::fromLocalFile(":/resource/sound/bullet.wav"));//添加资源
    startSound1->setLoopCount(1);
    startSound1->setVolume(1);
    startSound1->play();

    QSoundEffect * startSound2 = new QSoundEffect;//创建对象
    startSound2->setSource(QUrl::fromLocalFile(":/resource/sound/enemy3_flying.wav"));//添加资源
    startSound2->setLoopCount(1);
    startSound2->setVolume(0.51);
    startSound2->play();
}


//BulletClash, 判断子弹是否打到飞机
void Widget::BulletClash()
{
    mScene.removeItem(&mPlane);

    //遍历子弹列表
    for (int i = 0; i < mBulletList.size(); i++)
    {
        //遍历敌机列表
        for (int j = 0; j < mEnemy1List.size(); j++)
        {
            //判断子弹是否打到敌机
            if (mBulletList.at(i)->collidesWithItem(mEnemy1List.at(j)))
            {
                //删除子弹
                mScene.removeItem(mBulletList.at(i));
                delete mBulletList.at(i);
                mBulletList.removeAt(i);
                //如果敌机mEnemylife!=0,则减1
                 mEnemy1List.at(j)->setlife(mEnemy1List.at(j)->getlife()-1);
                if(mMyPlane.getlife()!=0)
                     mSPEED.score++;
            }
        }

        for (int j = 0; j < mEnemy2List.size(); j++)
        {
            //判断子弹是否打到敌机
            if (mBulletList.at(i)->collidesWithItem(mEnemy2List.at(j)))
            {
                //删除子弹
                mScene.removeItem(mBulletList.at(i));
                delete mBulletList.at(i);
                mBulletList.removeAt(i);
                //删除敌机
             mEnemy2List.at(j)->setlife(mEnemy2List.at(j)->getlife()-1);
                if(mMyPlane.getlife()!=0)
                    mSPEED.score++;
            }
        }
    }

    //判断炸弹是否打到敌机
    for (int i = 0; i < mBombList.size(); i++)
    {
        //遍历敌机列表
        for (int j = 0; j < mEnemy1List.size(); j++)
        {
            //判断炸弹是否打到敌机
            if (mBombList.at(i)->collidesWithItem(mEnemy1List.at(j)))
            {
                //删除炸弹
                mScene.removeItem(mBombList.at(i));
                delete mBombList.at(i);
                mBombList.removeAt(i);
                mPlane.setPixmap(QPixmap("://resource/images/enemy1_down3.png"));
                mPlane.setPos(mEnemy1List.at(j)->x(), mEnemy1List.at(j)->y());
                mScene.removeItem(mEnemy1List.at(j));
                delete mEnemy1List.at(j);
                mEnemy1List.removeAt(j);
                mScene.addItem(&mPlane);
                if(mMyPlane.getlife()!=0)
                     mSPEED.score++;
            }
        }
        for (int j = 0; j < mEnemy2List.size(); j++)
        {
            //判断炸弹是否打到敌机
            if (mBombList.at(i)->collidesWithItem(mEnemy2List.at(j)))
            {
                //删除炸弹
                mScene.removeItem(mBombList.at(i));
                delete mBombList.at(i);
                mBombList.removeAt(i);

                mPlane.setPixmap(QPixmap("://resource/images/enemy2_down3.png"));
                mPlane.setPos(mEnemy2List.at(j)->x(), mEnemy2List.at(j)->y());
                mScene.removeItem(mEnemy2List.at(j));
                delete mEnemy2List.at(j);
                mEnemy2List.removeAt(j);
                mScene.addItem(&mPlane);
                if(mMyPlane.getlife()!=0)
                     mSPEED.score++;
            }
        }
    }

    for (int j = 0; j < mEnemy1List.size(); j++)
    {
        if (mEnemy1List.at(j)->getlife() > 0 && mEnemy1List.at(j)->getlife()<=40 )
        {
            //更换图片
            mEnemy1List.at(j)->setPixmap(QPixmap("://resource/images/enemy1_down2.png"));
            //敌机生命值减1
            mScene.addItem(mEnemy1List.at(j));
        }
        else if(mEnemy1List.at(j)->getlife() > 40 && mEnemy1List.at(j)->getlife()<=80 )
        {
            //更换图片
            mEnemy1List.at(j)->setPixmap(QPixmap("://resource/images/enemy1_down1.png"));
            //敌机生命值减1
            mScene.addItem(mEnemy1List.at(j));
        }
        else if(mEnemy1List.at(j)->getlife() <= 0)
        {
            mScene.removeItem(mEnemy1List.at(j));
            delete mEnemy1List.at(j);
            mEnemy1List.removeAt(j);
        }

    }
    for (int j = 0; j < mEnemy2List.size(); j++)
    {
       if(mEnemy2List.at(j)->getlife() <= 0)
        {
            QSoundEffect * startSound1 = new QSoundEffect;//创建对象
            startSound1->setSource(QUrl::fromLocalFile(":/resource/sound/enemy3_flying.wav"));//添加资源
            startSound1->setLoopCount(1);
            startSound1->play();
            mPlane.setPixmap(QPixmap("://resource/images/enemy2_down3.png"));
            mPlane.setPos(mEnemy2List.at(j)->x(), mEnemy2List.at(j)->y());
            mScene.removeItem(mEnemy2List.at(j));
            delete mEnemy2List.at(j);
            mEnemy2List.removeAt(j);
            mScene.addItem(&mPlane);

        }
    }

}


//EnemyBulletMove
void Widget::EnemyBulletMove()
{
    //遍历敌机列表
    for (int i = 0; i < mEnemy1List.size(); i++)
    {
        //创建子弹
        EnemyBullet* enemyBullet = new EnemyBullet;
        //设置子弹位置
        enemyBullet->setPos(mEnemy1List.at(i)->x() + 27, mEnemy1List.at(i)->y() + 40);
        //添加到场景
        mScene.addItem(enemyBullet);
        //添加到子弹列表
        mEnemyBulletList.append(enemyBullet);
    }
    //遍历敌机列表
    for (int i = 0; i < mEnemy2List.size(); i++)
    {
        //创建子弹
        EnemyBullet* enemyBullet = new EnemyBullet;
        //设置子弹位置
        enemyBullet->setPos(mEnemy2List.at(i)->x() + 31, mEnemy2List.at(i)->y() + 90);
        //添加到场景
        mScene.addItem(enemyBullet);
        //添加到子弹列表
        mEnemyBulletList.append(enemyBullet);
    }
    //遍历敌机列表

    //遍历子弹列表
    for (int i = 0; i < mEnemyBulletList.size(); i++)
    {
        //子弹向下移动
        mEnemyBulletList.at(i)->moveBy(0, mSPEED.EnemyBulletSpeed);

        //如果子弹超出屏幕，删除子弹
        if (mEnemyBulletList.at(i)->y() > 700)
        {
            mScene.removeItem(mEnemyBulletList.at(i));
            mEnemyBulletList.removeAt(i);
        }
    }

    for (auto it = mEnemy1List.begin(); it != mEnemy1List.end(); it++)
    {
        int a = mMyPlane.x()-(*it)->x();
        if(a>=0)
        {
            (*it)->moveBy(GetRand.getRand(0, 10), mSPEED.Enemy1speed);
        }
        else
        {
            (*it)->moveBy(-GetRand.getRand(0,10), mSPEED.Enemy1speed);
        }
                //(*it)->moveBy(0, mSPEED.Enemy1speed);
        mScene.addItem(*it);
    }
    for (auto it = mEnemy2List.begin(); it != mEnemy2List.end(); it++)
    {
         int a = mMyPlane.x()-(*it)->x();
        if(a>=0)
        {
            (*it)->moveBy(GetRand.getRand(0, 10), mSPEED.Enemy2speed);
        }
        else
        {
            (*it)->moveBy(-GetRand.getRand(0,10), mSPEED.Enemy2speed);
        }
        //(*it)->moveBy(0, mSPEED.Enemy2speed);
        mScene.addItem(*it);
    }

    //敌机超出屏幕删除
    for (int i = 0; i < mEnemy1List.size(); i++)
    {
        if (mEnemy1List.at(i)->y() > 700)
        {
            mScene.removeItem(mEnemy1List.at(i));
            mEnemy1List.removeAt(i);
            if(mMyPlane.getlife()!=0)
                mSPEED.score-=10;
        }
    }

    //敌机超出屏幕删除
    for (int i = 0; i < mEnemy2List.size(); i++)
    {
        if (mEnemy2List.at(i)->y() > 700)
        {
            mScene.removeItem(mEnemy2List.at(i));
            mEnemy2List.removeAt(i);
            if(mMyPlane.getlife()!=0)
                mSPEED.score-=10;
        }
    }


}

void Widget::EnemyMove()
{
    srand(time(0));
    for (int i = 0; i < 30; i++)
    {
        GetRand.getRand(0, 480);
    }
    for (int i = 0; i < 1; i++)
    {
        Enemy1* enemy1 = new Enemy1;
        //利用随机坐标添加敌机
        enemy1->setPos(GetRand.getRand(10, 440), 0);
        //定义敌机生命
        enemy1->setlife(100);
        mEnemy1List.append(enemy1);
        mScene.addItem(enemy1);
    }
    for (int i = 0; i < 30; i++){
        GetRand.getRand(0, 480);
    }

    for (int i = 0; i < 1; i++){
        Enemy2* enemy2 = new Enemy2;
        //利用随机坐标添加敌机
        enemy2->setPos(GetRand.getRand(30, 440), 0);
        //enemy2->setlife(10);
        mEnemy2List.append(enemy2);
        mScene.addItem(enemy2);
    }
}


//定义BombMove
void Widget::BombMove()
{
    //依次添加30个炸弹
    for (int i = 0; i < 1; i++)
    {
        MyBomb* bomb = new MyBomb;
        bomb->setPos(mMyPlane.x() + mMyPlane.pixmap().width() / 2 - mMyBomb.pixmap().width() / 2,
                     mMyPlane.y() + mMyPlane.pixmap().height() / 2 - mMyBomb.pixmap().height() / 2);
        mBombList.append(bomb);
        mScene.addItem(bomb);
    }


}


//定义BulletMove，使得飞机不断在飞机正前面发射子弹
void Widget::BulletMove()
{
    //依次添加30个子弹
    for (int i = 0; i < 30; i++)
    {
        MyBullet* bullet = new MyBullet;
        bullet->setPos(mMyPlane.x() + mMyPlane.pixmap().width() / 2 - bullet->pixmap().width() / 2, mMyPlane.y() - bullet->pixmap().height());
        mBulletList.append(bullet);
        mScene.addItem(bullet);
    }
    //依次显示mMyBullet
    for (auto it = mBulletList.begin(); it != mBulletList.end(); it++)
    {
        (*it)->moveBy(0, -20);
        //将子弹添加到场景中
        mScene.addItem(*it);
        //(*it)->show();
    }

    //判断子弹是否超出边界，超出则删除
    for (auto it = mBulletList.begin(); it != mBulletList.end();)
    {
        if ((*it)->y() < 0)
        {
            //将子弹从场景中删除
            mScene.removeItem(*it);
            //将子弹从容器中删除
            it = mBulletList.erase(it);
        }
        else
        {
            it++;
        }
    }
    for (auto it = mBombList.begin(); it != mBombList.end(); it++)
    {
        (*it)->moveBy(0, -30);
        //将炸弹添加到场景中
        mScene.addItem(*it);
        //(*it)->show();
    }
    //判断炸弹是否超出边界，超出则删除
    for (auto it = mBombList.begin(); it != mBombList.end();)
    {
        if ((*it)->y() < 0)
        {
            //将炸弹从场景中删除
            mScene.removeItem(*it);
            //将炸弹从容器中删除
            it = mBombList.erase(it);
        }
        else
        {
            it++;
        }
    }

}





Widget::~Widget()
{
    delete ui;
}

void Widget::closeEvent(QCloseEvent *event) {
    QDateTime dateTime = QDateTime::currentDateTime(); // 获取系统当前的时间
    QString str = dateTime.toString("yyyy-MM-dd hh:mm:ss").trimmed(); // 格式化日期时间并去掉空格
    QString filePath = "data.txt"; // 文件路径
    QFile file(filePath);
    if (file.open(QIODevice::Append | QIODevice::Text)) { // 添加 QIODevice::Text 模式
        QTextStream out(&file); // 使用 QTextStream
        QString nextline = "\n";
        QString data = str;
        out <<"Date:"<<data<<nextline<<"Score:"<<mSPEED.score<<nextline<<nextline; // 写入数据

        file.close();
        qDebug() << "文件追加写入成功！";
    } else {
        qDebug() << "无法打开文件：" << file.errorString();
    }

}



void Widget::BGMOve()
{
    mBackGround2.moveBy(0, 5);
    mBackGround1.moveBy(0, 5);

    if (mBackGround1.y() >= mBackGround1.pixmap().height())
    {
        mBackGround1.setY(-mBackGround1.pixmap().height());
    }
    else if (mBackGround2.y() >= mBackGround2.pixmap().height())
    {
        mBackGround2.setY(-mBackGround2.pixmap().height());
    }

}

void Widget::PlaneMove()
{
    if (mMyPlane.x() <= 0)
    {
        mMyPlane.setX(0);
    }
    else if (mMyPlane.x() >= this->width() - mMyPlane.pixmap().width())
    {
        mMyPlane.setX(this->width() - mMyPlane.pixmap().width());
    }
    else if (mMyPlane.y() <= 0)
    {
        mMyPlane.setY(0);
    }
    else if (mMyPlane.y() >= this->height() - mMyPlane.pixmap().height())
    {
        mMyPlane.setY(this->height() - mMyPlane.pixmap().height());
    }
    for (int keyCode : mKeyList)
    {
        switch (keyCode)
        {
        case Qt::Key_W:mMyPlane.moveBy(0, -15); break;
        case Qt::Key_S:mMyPlane.moveBy(0, 15); break;
        case Qt::Key_A:mMyPlane.moveBy(-15, 0); break;
        case Qt::Key_D:mMyPlane.moveBy(15, 0); break;
        }
    }

}

void Widget::keyPressEvent(QKeyEvent* event)
{
    switch (event->key())
    {
    case Qt::Key_P:mMyPlane.setlife(120);break;
    case Qt::Key_K:mSPEED.speed = 200;  mBulletTimer->start(mSPEED.speed); break;
    case Qt::Key_J:mSPEED.speed = 100;  mBulletTimer->start(mSPEED.speed); break;
    case Qt::Key_L:mSPEED.speed = 300;  mBulletTimer->start(mSPEED.speed); break;
    case Qt::Key_B:mBombTimer = new QTimer(this); BombMove(); break;
    case Qt::Key_W:
    case Qt::Key_S:
    case Qt::Key_A:
    case Qt::Key_D:
        mKeyList.append(event->key());
        break;
    }
}

void Widget::keyReleaseEvent(QKeyEvent* event)
{
    if (mKeyList.contains(event->key()))
    {
        mKeyList.removeOne(event->key());
        //        mKeyList.remov
    }

}


