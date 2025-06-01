#include <iostream>
using namespace std;

class Animal{
public:
	Animal(int con_weight, int con_age);
	~Animal(){
		cout << "Animal destructor!" << endl;
	}
	int get_age(){
		return m_nAge;
	}
private:
	int m_nWeight, m_nAge;
};

Animal::Animal(int con_weight, int con_age){
	m_nWeight = con_weight;
	m_nAge = con_age;
	cout << "Animal constructor with param!" << endl;
}

class Cat :public Animal{
public:
	Cat(string con_name, int con_weight, int con_age);
	~Cat(){ cout << "Cat destructor!" << endl; }
private:
	string m_strName;
};

Cat::Cat(string con_name, int con_weight, int con_age) :Animal(con_weight, con_age){
	m_strName = con_name;
	cout << "Cat constructor with param!" << endl;
}

int main(){
	Cat cat("Persian", 3, 4);
	cout << "cat age = " << cat.get_age() << endl;
	
	return 0;
}
