// 3-1
#include <iostream>
#include <string>
using namespace std;

class Animal{
public:
	void speak(){
		cout << "animal language!" << endl;
	}
};

class Cat :public Animal{
public:
	Cat(string con_name) :m_strName(con_name){}
	void print_name(){
		cout << "cat name: " << m_strName << endl;
	}
private:
	string m_strName;
};

int main(){
	Cat cat("Persian"); 
	cat.print_name();
	cat.speak();
	
	return 0;
}