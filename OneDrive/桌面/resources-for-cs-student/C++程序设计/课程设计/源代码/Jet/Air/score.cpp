#include <score.h>
#include "ui_score.h"

score::score(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::score)
{
    ui->setupUi(this);
    this->setFixedSize(345, 385);
    this->setWindowTitle("Reference");
    this->setStyleSheet("QScrollArea { scrollbar-width: 0px; }");
}

score::~score()
{
    delete ui;
}
