#include <iostream>
using namespace std;

class Animal{
public:
	Animal(int con_weight, int con_age);
	Animal();
private:
	int m_nWeight;
	int m_nAge;
};

Animal::Animal(int con_weight, int con_age){
	m_nWeight = con_weight;
	m_nAge = con_age;
	cout << "Animal constructor with param!" << endl;
}

Animal::Animal(){
	m_nWeight = 6;
	m_nAge = 9;
	cout << "Animal constructor with param!" << endl;
}

class Cat :public Animal{
public:
	Cat(string con_name, int con_weight, int con_age);
private:
	string m_strName;
};

Cat::Cat(string con_name, int con_weight, int con_age) :Animal(con_weight, con_age){
	m_strName = con_name;
	cout << "Cat constructor with param!" << endl;
}

int main(){
	Cat cat("Persian", 5, 7);
	return 0;
}