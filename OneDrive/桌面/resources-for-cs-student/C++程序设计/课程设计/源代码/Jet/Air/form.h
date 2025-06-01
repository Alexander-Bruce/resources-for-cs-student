#ifndef FORM_H
#define FORM_H

#include <QWidget>
#include <QGraphicsPixmapItem>
#include <QGraphicsView>
#include <QGraphicsScene>
#include <QList>
#include <myplane.h>
#include <Bullet.h>
#include <speed.h>
#include <bomb.h>
#include <enemy.h>
#include <Widget.h>
#include <Reference.h>
#include <ui_reference.h>
namespace Ui {
class Form;
}

class Form : public QWidget
{
    Q_OBJECT

public:
    explicit Form(QWidget *parent = nullptr);
    Ui::Form *ui;
    QGraphicsView GameView;
    QGraphicsScene Scene;

    QGraphicsPixmapItem Plane;
    QGraphicsPixmapItem BackGround1;
    QGraphicsPixmapItem BackGround2;
    QPushButton *openButton;
    ~Form();
    void on_start_clicked();
    void on_To_reference_clicked();

private slots:




    void on_pushButton_clicked();

private:

};

#endif // FORM_H
