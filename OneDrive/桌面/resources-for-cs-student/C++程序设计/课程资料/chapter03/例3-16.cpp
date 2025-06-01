#include <iostream>
using namespace std;

class Animal{
public:
	Animal(int age) :m_nAge(age) { cout << "Animal constructor!" << endl; }
protected:
	int m_nAge;
};

class Bird :virtual public Animal                     //定义类Bird继承自虚基类Animal
{
public:
	//定义类Bird的构造函数
	Bird(int age, int fh) :Animal(age)
	{
		cout << "Bird constructor!" << endl;
		m_nFlightAltitude = fh;
	}
	//定义获取鸟飞行高度的函数
	int get_flightaltitude()  { return m_nFlightAltitude; }
private:
	int m_nFlightAltitude;
};

class Fish : virtual public Animal                     //定义类Fish继承自虚基类Animal
{
public:
	Fish(int age, int speed) :Animal(age)           //定义类Fish的构造函数
	{
		cout << "Fish constructor!" << endl;
		m_nSwimSpeed = speed;
	}
	//定义获取鱼游速的函数
	int get_swimspeed()  { return m_nSwimSpeed; }
private:
	int m_nSwimSpeed;;
};

class WaterBird :public Bird, public Fish         //定义水鸟类
{
public:
	WaterBird(int b_age, int f_age, int fh, int speed) :Bird(b_age, fh),
		Fish(f_age, speed), Animal(b_age)   //定义水鸟类带参数的构造函数
	{
		cout << "WaterBird constructor!" << endl;
	}
	//定义打印动物年龄的成员函数
	void print_animalage() { cout << "age = " << m_nAge << endl; }
};

int main()
{
	WaterBird waterbird(5, 6, 20, 30);             //定义水鸟对象
	cout << "waterbird flight altitude: " << waterbird.get_flightaltitude()
		<< ", swimming speed:" << waterbird.get_swimspeed() << endl;
	waterbird.print_animalage();
	
	return 0;
}
