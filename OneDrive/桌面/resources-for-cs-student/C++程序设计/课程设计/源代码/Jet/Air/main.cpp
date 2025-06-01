#include <Widget.h>
#include "form.h"
#include "QPushButton"
#include "reference.h"
#include "ui_reference.h"

#include <QApplication>
#include <QMediaPlayer>
#include <QCoreApplication>
#include <QtPlugin>
#include <QUrl>
#include <QSoundEffect>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    Form f;
    Reference r;
    f.show();

    f.on_start_clicked();
    f.on_To_reference_clicked();

    return a.exec();
}



