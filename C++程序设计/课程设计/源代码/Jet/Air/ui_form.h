/********************************************************************************
** Form generated from reading UI file 'form.ui'
**
** Created by: Qt User Interface Compiler version 6.2.4
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_FORM_H
#define UI_FORM_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QTextBrowser>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Form
{
public:
    QPushButton *start;
    QPushButton *pushButton;
    QPushButton *To_reference;
    QLabel *label;
    QTextBrowser *textBrowser;

    void setupUi(QWidget *Form)
    {
        if (Form->objectName().isEmpty())
            Form->setObjectName(QString::fromUtf8("Form"));
        Form->resize(530, 700);
        QSizePolicy sizePolicy(QSizePolicy::Expanding, QSizePolicy::Expanding);
        sizePolicy.setHorizontalStretch(13);
        sizePolicy.setVerticalStretch(0);
        sizePolicy.setHeightForWidth(Form->sizePolicy().hasHeightForWidth());
        Form->setSizePolicy(sizePolicy);
        start = new QPushButton(Form);
        start->setObjectName(QString::fromUtf8("start"));
        start->setGeometry(QRect(155, 150, 220, 80));
        QFont font;
        font.setPointSize(22);
        start->setFont(font);
        pushButton = new QPushButton(Form);
        pushButton->setObjectName(QString::fromUtf8("pushButton"));
        pushButton->setGeometry(QRect(155, 270, 220, 80));
        pushButton->setFont(font);
        To_reference = new QPushButton(Form);
        To_reference->setObjectName(QString::fromUtf8("To_reference"));
        To_reference->setGeometry(QRect(155, 400, 220, 80));
        To_reference->setFont(font);
        label = new QLabel(Form);
        label->setObjectName(QString::fromUtf8("label"));
        label->setGeometry(QRect(150, 40, 225, 85));
        QFont font1;
        font1.setPointSize(26);
        label->setFont(font1);
        label->setLayoutDirection(Qt::RightToLeft);
        label->setAlignment(Qt::AlignCenter);
        textBrowser = new QTextBrowser(Form);
        textBrowser->setObjectName(QString::fromUtf8("textBrowser"));
        textBrowser->setGeometry(QRect(25, 0, 480, 700));
        textBrowser->raise();
        label->raise();
        start->raise();
        pushButton->raise();
        To_reference->raise();

        retranslateUi(Form);

        QMetaObject::connectSlotsByName(Form);
    } // setupUi

    void retranslateUi(QWidget *Form)
    {
        Form->setWindowTitle(QCoreApplication::translate("Form", "Form", nullptr));
        start->setText(QCoreApplication::translate("Form", "\345\274\200\345\247\213\346\270\270\346\210\217", nullptr));
        pushButton->setText(QCoreApplication::translate("Form", "\345\216\206\345\217\262\350\256\260\345\275\225", nullptr));
        To_reference->setText(QCoreApplication::translate("Form", "\346\223\215\344\275\234\346\214\207\345\215\227", nullptr));
        label->setText(QCoreApplication::translate("Form", "\351\243\236\346\234\272\345\244\247\346\210\230", nullptr));
        textBrowser->setHtml(QCoreApplication::translate("Form", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0//EN\" \"http://www.w3.org/TR/REC-html40/strict.dtd\">\n"
"<html><head><meta name=\"qrichtext\" content=\"1\" /><meta charset=\"utf-8\" /><style type=\"text/css\">\n"
"p, li { white-space: pre-wrap; }\n"
"hr { height: 1px; border-width: 0; }\n"
"li.unchecked::marker { content: \"\\2610\"; }\n"
"li.checked::marker { content: \"\\2612\"; }\n"
"</style></head><body style=\" font-family:'Microsoft YaHei UI'; font-size:9pt; font-weight:400; font-style:normal;\">\n"
"<p style=\" margin-top:0px; margin-bottom:0px; margin-left:0px; margin-right:0px; -qt-block-indent:0; text-indent:0px;\"><img src=\":/resource/images/background.png\" /></p></body></html>", nullptr));
    } // retranslateUi

};

namespace Ui {
    class Form: public Ui_Form {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_FORM_H
