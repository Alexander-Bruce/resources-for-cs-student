#include <iostream>
using namespace std;

class Animal{
public:
	int get_age(){ return m_nAge; }
	int get_weight(){ return m_nWeight; }
	void set_age(int param_age){ m_nAge = param_age; }
	void set_weight(int param_weight){ m_nWeight = param_weight; }
	void speak(){ cout << "animal language!" << endl; }
private:
	int m_nWeight;
	int m_nAge;
};

class Cat :public Animal{
public:
	void set_name(string param_name);
	void speak(){ cout << "cat language: miaomiao!" << endl; }
private:
	string m_strName;
};

int main(){
	Cat cat;
	Animal *panimal = &cat;

	panimal->set_age(5);
	cout << "base type: age = "
		<< panimal->get_age() << endl;
	cat.speak();
	// Output will be the speak function declared inside class Cat
	// This is called function override in which the function declared inside a inheritance sharing the same name with its sibling from the base class will override its sibling when a object of this inherited class is created and this function is called.
	panimal->speak();
	// In this case, a pointer is created. As a pointer works directly and closely with the actual memory, it will, by the rule of inheritance, point to the speak function inside class animal.

	return 0;
}