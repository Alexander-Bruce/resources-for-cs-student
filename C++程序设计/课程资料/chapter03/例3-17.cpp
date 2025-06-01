#include <iostream>
using namespace std;

class Animal                                             //定义类Animal
{
public:
	//定义类Animal的构造函数
	Animal(int age) :m_nAge(age) { }
	int m_nAge;                                          //成员m_nAge记录动物年龄
};
class Bird :virtual public Animal                    //定义类Bird继承自虚基类Animal
{
public:
	//定义类Bird的构造函数
	Bird(int age, int fh) :Animal(age), m_nFlightAltitude(fh){	}
	int m_nFlightAltitude;
};
class Fish :virtual public Animal                    //定义类Fish继承自虚基类Animal
{
public:
	//定义类Fish的构造函数
	Fish(int age, int speed) :Animal(age), m_nSwimSpeed(speed){	}

	int m_nSwimSpeed;;
};
class WaterBird :public Bird, public Fish        //定义水鸟类
{
public:
	//定义水鸟类带参数的构造函数
	WaterBird(int age, int fh, int speed, int weight) :Bird(age, fh),
		Fish(age, speed), Animal(age), m_nWeight(weight)
	{
	}
	int m_nWeight;
};

int main()
{
	WaterBird waterbird(5, 20, 30, 10);             //定义水鸟对象

	cout << "sizeof(waterbird) = " << sizeof(waterbird) << endl;
	cout << "waterbird addr:" << &waterbird << endl;
	cout << "Bird::m_nFlightAltitude addr:"
		<< &(waterbird.m_nFlightAltitude) << endl;
	cout << "Fish::m_nSwimSpeed addr:" << &(waterbird.m_nSwimSpeed) << endl;
	cout << "WaterBird::m_nWeight addr:" << &(waterbird.m_nWeight) << endl;
	cout << "Animal::m_nAge addr:" << &(waterbird.m_nAge) << endl;

	system("pause");
	return 0;
}
