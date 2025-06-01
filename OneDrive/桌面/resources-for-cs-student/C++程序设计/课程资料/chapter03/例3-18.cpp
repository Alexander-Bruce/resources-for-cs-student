#include <iostream>
using namespace std;

class Animal{
public:
	void speak(){ cout << "animal language!" << endl; }
};

class Cat :public Animal{
public:
	void speak(){ cout << "cat language: miaomiao!" << endl; }
};
int main()
{
	Cat cat;
	Animal *panimal = &cat;
	panimal->speak();
	
	return 0;
}
