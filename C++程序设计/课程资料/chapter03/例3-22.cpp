#include<iostream>
using namespace std;

const double PI = 3.14159;

class Shapes{
public:
	Shapes(int x, int y = 0);
	virtual ~Shapes();
	virtual void disp() = 0;
protected:
	int m_nX, m_nY;
};

Shapes::Shapes(int x, int y){
	m_nX = x;
	m_nY = y;
}
Shapes::~Shapes(){
	cout << "Shapes destructor!" << endl;
}

class Square :public Shapes{
public:
	Square(int square_x, int square_y);
	~Square();
	void disp();
};

Square::Square(int square_x, int square_y) :Shapes(square_x, square_y){
}
Square::~Square(){
	cout << "Square destructor!" << endl;
}
void Square::disp(){
	cout << "square area:" << m_nX * m_nY << endl;
}

class Circle :public Shapes{
public:
	Circle(int radius);
	~Circle();
	void disp();
};

Circle::Circle(int radius) :Shapes(radius){
}
Circle::~Circle(){
	cout << "Circle destructor!" << endl;
}
void Circle::disp(){
	cout << "circle area:" << PI * m_nX * m_nX << endl;
}

int main(){
	Shapes *ptr[2];

	ptr[0] = new Square(10, 5);
	ptr[0]->disp();
	ptr[1] = new Circle(10);
	ptr[1]->disp();
	for (int i = 0; i < 2; i++)
		delete ptr[i];
	
	return 0;
}
