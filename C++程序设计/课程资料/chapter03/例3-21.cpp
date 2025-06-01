#include <iostream>
using namespace std;

class Animal                                            //定义基类Animal
{
public:
	Animal(char *name);                               //声明基类构造函数
	void print_name();                                //声明print_name()函数
	virtual void print_color() = 0;                //声明纯虚函数print_color()
	virtual ~Animal();                                //声明虚析构函数
private:
	char *m_pAnimalName;                             //存放动物名称的数据成员
};

Animal::Animal(char *name)                          //Animal类构造函数的定义
{
	int len = strlen(name) + 1;
	m_pAnimalName = new char[len];                 //为m_pAnimalName指针开辟空间
	strcpy_s(m_pAnimalName, len, name);           //存入动物名称
}
Animal::~Animal()                                     //Animal类析构函数
{
	cout << "Animal destructor!" << endl;
	if (m_pAnimalName){
		delete[]m_pAnimalName;                     //释放空间
	}
}
void Animal::print_name()                           //定义print_name()函数
{
	cout << "name:" << m_pAnimalName << endl;
}
class Cat :public Animal                             //定义派生类Cat
{
public:
	Cat(char* name, char *catcolor);
	virtual void print_color();                   //声明虚函数print_color()
	virtual ~Cat();                                  //声明虚析构函数
private:
	char *m_pCatColor;                               //存放猫颜色的数据成员
};
Cat::Cat(char* name, char *color) :Animal(name) //Cat类构造函数的定义
{
	cout << "Cat constructor!" << endl;
	m_pCatColor = new char[strlen(color) + 1];    //为m_pCatColor指针开辟空间
	strcpy_s(m_pCatColor, strlen(color) + 1, color);  //存入描述猫颜色的字符串
}
Cat::~Cat()                                             //Cat类析构函数的定义
{
	cout << "Cat destructor!" << endl;
	if (m_pCatColor){
		delete[]m_pCatColor;                         //释放m_pCatColor指向的空间
	}
}
void Cat::print_color()                              //print_color()虚函数的实现
{
	cout << "cat color :" << m_pCatColor << endl;
}
int main()
{
	Animal *p;                                         //定义基类Animal指针数组

	p = new Cat("short_haired_cat", "white");   //通过new生成派生类Cat对象
	p->print_name();
	p->print_color();
	cout << "--------------------" << endl;
	delete p;                                         //通过delete释放派生类对象
	system("pause");
	return 0;
}
