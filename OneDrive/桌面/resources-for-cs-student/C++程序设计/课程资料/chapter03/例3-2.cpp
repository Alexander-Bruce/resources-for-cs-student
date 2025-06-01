#include <iostream>
#include <string>
using namespace std;

class Animal{
public:
	void set_weight(int weight){
		m_nWeightBase = weight; 
	}
	int get_weight(){ return m_nWeightBase; }
	void set_age(int age){ m_nAgeBase = age; }
protected:
	int m_nAgeBase;
private:
	int m_nWeightBase;
};

class Cat :public Animal{
public:
	Cat(string con_name) :m_strName(con_name){}
	void print_age(){ cout << m_strName << ", age = " << m_nAgeBase << endl; }
private:
	string m_strName;
};

int main()
{
	Cat cat("Persian");
	cat.set_age(5);
	cat.set_weight(6);
	cat.print_age();
	cout << "cat weight = " << cat.get_weight() << endl;
	
	return 0;
}
