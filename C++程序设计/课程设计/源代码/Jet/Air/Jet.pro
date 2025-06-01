QT       += core gui
QT += multimedia

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

CONFIG += c++17

# You can make your code fail to compile if it uses deprecated APIs.
# In order to do so, uncomment the following line.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

SOURCES += \
    MyBullet.cpp \
    bomb.cpp \
    enemy.cpp \
    form.cpp \
    main.cpp \
    myplane.cpp \
    reference.cpp \
    speed.cpp \
    widget.cpp

HEADERS += \
    Bullet.h \
    bomb.h \
    enemy.h \
    form.h \
    myplane.h \
    reference.h \
    speed.h \
    widget.h

FORMS += \
    form.ui \
    reference.ui \
    widget.ui

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target

RESOURCES += \
    resouce.qrc
