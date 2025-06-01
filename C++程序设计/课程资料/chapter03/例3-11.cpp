#include <iostream>
using namespace std;

class Bird                                                 //定义鸟类
{
public:
	//定义鸟在天空飞的成员函数
	void fly_in_sky()	{ cout << "bird fly in sky!" << endl; }
	//定义鸟呼吸的成员函数
	void breath() { cout << "bird breath!" << endl; }
};
class Fish                                              //定义鱼类
{
public:
	//定义鱼在水里游的成员函数
	void swim_in_water()	{ cout << "fish swim in water!" << endl; }
	//定义鱼呼吸的成员函数
	void breath() { cout << "fish breath!" << endl; }
};
class WaterBird :public Bird, public Fish         //定义水鸟类
{
public:
	//定义水鸟行为的成员函数
	void fly_swim() { cout << "waterbird cat fly and swim!" << endl; }
};
int main()
{
	WaterBird waterbird;                           //定义水鸟对象
	waterbird.fly_in_sky();                       //调用从鸟类继承来的flyinsky()函数
	waterbird.swim_in_water();                   //调用从鱼类继承来的swiminwater()函数
	system("pause");
	return 0;
}
