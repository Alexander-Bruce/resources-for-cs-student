#include <iostream>
using namespace std;

class Animal{
public:
	virtual void speak(){
		cout << "animal language!" << endl;
	}
};
class Cat :public Animal{
public:
	virtual void speak(){
		cout << "cat language:!" << endl;
	}
};
int main()
{
	Cat cat;
	Animal *panimal = &cat;
	Animal &ref = cat;
	panimal->speak();
	ref.speak();
	
	return 0;
}
