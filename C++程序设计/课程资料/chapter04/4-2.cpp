#include <iostream>
using namespace std;
class A{
private:
	int x;
	int y;
public:
	A(int x1 = 0, int y1 = 0) :x(x1), y(y1){}
	void show() const;
	A operator++();    // Prefix
	A operator++(int); // Postfix
};

void A::show() const{
	cout << "(x,y) = " << "(" << x << "," << y << ")" << endl;
}

A A::operator++(){
	++x;
	++y;
	return *this;
}

A A::operator++(int){
	A a = *this;
	++(*this);
	return a;
}

int main(){
	A a1(1, 2), a2(3, 4);
	(a1++).show();
	(++a2).show();

	A an = a1++;
	an.show();
	
	return 0;
}

// Mindful of the analogy here with the cout statement
// Note that we write (a1++).show() in line 32, which is worth a little deeper insight.
// One obvious thing is that in the overloading function for operator ++(both prefix and postfix), we return an object of type A, which explains why we can call show(), a member function in class A.
// Now check the following statement cout << "a" << "b" << endl;
// After the initial output of "a", what's actually done is that the operator(overloaded already) will return ostream (refer to the istream function we've covered) so that cout will be "updated" for the next output of "b".