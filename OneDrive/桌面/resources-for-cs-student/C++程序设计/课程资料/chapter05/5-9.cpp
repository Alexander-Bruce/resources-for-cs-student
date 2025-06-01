#include <iostream>
#include <string.h>
#include <cstring>
#include <vector>
#include <algorithm>
#include <queue>
using namespace std;

class Contestant{
   public:
   string name;
   int time ,number ,submit,Alltime;
   Contestant (){}
   Contestant(string name,int submit,int time,int number):name(name),submit(submit),time(time),number(number){}
   void add_penalty()
   {
       this->Alltime = this->time+20*number;
   }
   friend istream& operator>> (istream& stream, Contestant &c)
   {
      return  stream>>c.name>>c.submit>>c.time>>c.number;
   }
   friend ostream& operator<< (ostream& stream , const Contestant & c)
   {
      return stream<<c.name<<" "<<c.submit<<" "<<c.time<<" "<<c.Alltime;
   }

};

bool compare(Contestant &a , Contestant &b)
{
   if(a.submit>b.submit)
   {
      return true;
   }
   else if (a.Alltime<b.Alltime&&a.submit==b.submit)
   {
      return true;
   }
   else
   return false;
}

int main()
{
    int n;
    cin >> n;
    Contestant c;
    vector<Contestant> v;
    for (int i = 0; i < n; i++) 
    {
        cin >> c;         //输入选手名、解题数、解题用时、错误提交次数 
        c.add_penalty(); //根据错误提交次数计算罚时，并加到总用时上 
        v.push_back(c);
    }

    sort(v.begin(), v.end(), compare);

    for (int i = 0; i < v.size(); i++)
        cout << v[i] << '\n'; //输出选手名、解题数、解题用时、错误提交次数、加上罚时的总用时 

    return 0;
}
