#include <iostream>
using namespace std;

class Animal                                                 //定义基类
{
public:
	//定义speak()函数
	void speak() { cout << "animal language!" << endl; }
};
class Cat :public Animal                                  //定义派生类
{
public:
	//定义speak()函数
	void speak()  { cout << "cat language: miaomiao!" << endl; }
};
int main()
{
	Cat cat;                                              //定义派生类对象
	//定义基类指针，指针初始化为派生类对象地址
	Animal *p = new Cat;
	p->speak();                                          //通过指针调用speak()函数
	cat.speak();                                         //通过派生类调用speak()函数
	cat.Animal::speak();                               //调用基类同名函数
	
	return 0;
}
