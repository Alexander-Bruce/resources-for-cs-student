#include <stdio.h>
#include <stdlib.h>
#include <string> 
#include <iostream>
#include <cctype>
#include <fstream>
using namespace std; 
using std::string;
typedef struct student{                                                   //建立链表 
	string studentmumber;                                                 //创建学生学号 
	string name;                                                          //创建学生姓名 
	string ID;                                                            //创建学生身份证号 
	string phonenumber;                                                   //创建学生电话号码 
	int  io;                                                              //判断学生出入校园类型 
	string peopletype;                                                    //人员身份类型 
	string date;                                                          //人员出入校园日期 
	int judge;                                                            //part9的一个判断条件 
	struct student *next;                                                 //指向下一个结构体的指针 
}student;                                                                 //定义此结构体位student 
student *head=NULL;                                                       //建立头指针，并赋值为空 

bool yearjudge(int year)
{
	if((year%4==0&&year%100!=0)||year%400==0)
	return true;
	else
	return false;
}

bool basejudge1(string a)
{
	if(a.size()!=18)
	return false;
	else
	return true;
}
bool basejudge2(string a)
{
	if(a.size()!=11)
	return false;
	else
	return true;
}
bool basejudge3(string a)
{
	if(a=="teacher"||a=="student"||a=="other")
	return true;
	else
	return false;
}
bool basejudge4(string a)
{
	if(a.size()!=10)
	return false;
	int year=(a[0]-'0')*1000+(a[1]-'0')*100+(a[2]-'0')*10+a[3]-'0',mounth=(a[5]-'0')*10+a[6]-'0',date=(a[8]-'0')*10+a[9]-'0';
	int array[13]={31,28,31,30,31,30,31,31,30,31,30,31,29};
    if(yearjudge(year)==false)
	{
       if((date>array[mounth-1])||mounth>12)
       return false;
       else
       return true;
	}
	else 
	{
		if(mounth<=12)
			return true;
        else if(mounth==2&&date<=array[12])
			return true;
	    else if(date<=array[mounth-1])
    		return true;
    	else 
    	    return false;
	}
}
void print(int x)
{
	while(getchar()!='\n')
		continue; 
} 

void print1(int key)                                                                //建立打印函数 
{
	switch (key)                                                            //如果值为一 
	{
	 case 1: printf("\n*************************欢迎使用校园出入管理系统***************************\n");//输入校园信息管理系统的基本页面 
			 printf("----------------------------------------------------------------------------\n"); 
			 printf("---------------------------1.录入出入校园人员信息---------------------------\n");
			 printf("---------------------------2.保存出入校园人员信息---------------------------\n");
			 printf("---------------------------3.浏览出入校园人员信息---------------------------\n");
			 printf("---------------------------4.查询出入校园人员信息---------------------------\n");
			 printf("---------------------------5.增加出入校园人员信息---------------------------\n");
			 printf("---------------------------6.删除出入校园人员信息---------------------------\n");
			 printf("---------------------------7.修改出入校园人员信息---------------------------\n");
			 printf("---------------------------8.统计出入校园人员信息---------------------------\n");
			 printf("---------------------------9.出入校园人员信息排序---------------------------\n");
			 printf("---------------------------10.退出------------------------------------------\n");
			 printf("----------------------------------------------------------------------------\n");
			 printf("********************************谢谢使用！**********************************\n");
	         printf("\n请选择操作号码(1-10)： ");
	         break; 
     case 2: printf("请问需要修改他\她的什么信息?\n");                         //这是part5中要打印的信息 
		     printf("1.修改学号\n");
		     printf("2.修改姓名\n");
		     printf("3.修改身份证号\n");
		     printf("4.修改学生类型\n");
		     printf("5.修改电话号码\n");
		     printf("6.修改出\入校类型\n");
		     printf("7.修改出\入校日期\n");
		     printf("8.退出\n");
			 printf("请选择以上一个模块(1-7): ");
			 break;
     case 3: printf("1.按编号进行查询\n");                                       //这是part4中要打印的信息
			 printf("2.按电话号码查询\n");
			 printf("3.按身份证号查询\n");
			 printf("4.按姓名查询\n");
			 printf("5.退出\n");
			 printf("请选择查询方式(1-4): ");
			 break;
     case 4: printf("1.以编号查找删除\n");                                       //这是part6中要打印的信息
			 printf("2.以电话查找删除\n");
			 printf("3.以身份查找删除\n");
			 printf("4.以姓名查找删除\n");
			 printf("请选择删除方式(1-4): ");
			 break;
     case 5: printf("1.以编号查找修改\n");                                        //这是part7中要打印的信息
			 printf("2.以电话查找修改\n");
			 printf("3.以身份查找修改\n");
			 printf("4.以姓名查找修改\n");
			 printf("请选择修改方式(1-4): ");
			 break;
     case 6: printf("1.查找某日出校人数\n");                                       //这是part8中要打印的信息
			 printf("2.查找某日入校人数\n");
		     printf("3.查找全部出校人数\n");
			 printf("4.查找全部入校人数\n");
			 printf("请选择查找要求(1-4): ");
			 break;
     case 7: printf("1.按出校日期排序\n");                                          //这是part9中要打印的信息
			 printf("2.按入校日期排序\n");
			 printf("请选择排序要求(1-2)："); 
			 break;
	}
}

void print2(student *head)                                                          //建立保存函数，作为part2的调用函数 
{
	student *head1=head;                                                            //将head1指向头指针，以免头指针的值被改变 
    fstream fout; //可输入输出
	fout.open("test_A.txt", ios::out);
	while(head1!=NULL)                                                              //如果head1不指向空指针 
	{
	     fout<<head1->peopletype<<" "<<head1->studentmumber<<" "<<head1->name<<" "<<head1->ID<<" "<<head1->phonenumber<<" "<<head1->io<<" "<<head1->date<<"\n";                                            
		 head1=head1->next;                                                          //并指向下一个指针 
    }	
      fout.close();
      printf("数据保存成功\n");
}

void print3(student *head)                                                           //建立打印函数，作为以后打印整个数据函数的调用函数 
{
	while(head!=NULL)                                                                //如果head1不指向空指针
	{
		 cout<<head->peopletype<<" "<<head->name<<" "<<head->ID<<" "<<head->phonenumber<<" "<<head->io<<" "<<head->date<<endl;
		 head=head->next;                                                            //并指向下一个指针 
    }   
}

void print4(student *head,int key)                                                   //建立打印函数，作为以后打印单个结构体信息函数的调用 
{ 
		 cout<<head->peopletype<<" "<<head->name<<" "<<head->ID<<" "<<head->phonenumber<<" "<<head->io<<" "<<head->date<<endl;
		 if(key==1)                                                                  //如果主函数还需要将指针指向下一个 
		 head=head->next;    
}

void print5(student *head,int j)                                                     //建立打印函数，作为part4函数的调用
{
	    string mumber;                                                               //建立一个新的字符串，并把它赋值为空字符 
	    cin>>mumber;                                                                  //输入字符串 
	    print(1);
		while(head!=NULL)                                                            //遍历链表，查找 
		{
           if(j==1&&head->studentmumber==mumber)
           break;
           else if(j==2&&head->phonenumber==mumber)
           break;
           else if(j==3&&head->ID==mumber)
           break;
           else if(j==4&&head->name==mumber)
 	       break;
 	       else 
           head=head->next;
	   }
	    if(head!=NULL)                                                              //如果这个函数头指针不为空 
	   	print4(head,0);                                                             //调用打印单个结构体的函数print4，并且不用指针后指 
		else                                                                        //如果这个函数头指针为空 
		{
		  printf("没有这个人的相关信息\n");
		  return ; 
	    }
}
 
student *print6 (student *head,int j)                                                   //建立打印函数，并在传入指针的同时，传入一个字符串 
{
	student *head5=head,*p=head;                                                    //将head5指向头指针，以免头指针的值被改变 
	char mumber[25]={'\0'};                                                         //建立一个新的字符串，并把它赋值为空字符
	scanf("%s",mumber);                                                             //输入字符串 
	print(1);
	    while(head5!=NULL)                                                          //如果head5不指向空指针
		{
	         if(j==1&&head5->studentmumber==mumber)                         //比较字符串和mumber是否相等 ，如果相等，表示找到相应的元素，就退出 
	         break;
	         else if(j==2&&head5->phonenumber==mumber)
	         break;
	         else if(j==3&&head5->ID==mumber)
	         break;
	         else if(j==4&&head5->name==mumber)
	         break;                                                                 
	         else                                                                   //如果不相等， 将p指向head5，head5指向下一个结构体 
	         {                                             
	         	p=head5;                                                            //p用来储存head5指针指向的地址 
			    head5=head5->next;                                       
			 }
	    }
	    if(head5!=NULL)                                                             //如果head5不指向空指针，
	    {
	    	if(head5==head)                                                         //如果删除的是第一个节点 
	    		head=head->next;                                                    //头指针直接后指就好 
			else                                                                    //如果删除的不是第一个节点 
	    	p->next=head5->next;                                                    //p->next指针，指向head5->next指针的地址 
	    	free(head5);                                                            //释放head5地址的空间 
		}
		else 
		printf("没有找到这个人\n");
		return head;
}

void print7 (student *head,int x)                                                   //建立打印函数，并在传入指针的同时，传入一个字符串
{
	    student *head6=head;                                                        //将head6指向头指针，以免头指针的值被改变
	    char mumber[25]={'\0'};                                                     //建立一个新的字符串，并把它赋值为空字符
	    scanf("%s",mumber);                                                         //输入字符串
	    print(1);
		while(head6!=NULL)                                                          //如果head6不指向空指针 
		{
	         if(x==1&&head6->studentmumber==mumber)                        //比较字符串a和mumber是否相等 ，如果相等，表示找到相应的元素，就退出
	         break;
	         else if(x==2&&head6->phonenumber==mumber) 
	         break;
	         else if(x==3&&head6->ID==mumber)
	         break;
	         else if(x==4&&head6->name==mumber)
	         break;
	         else                                                                   //如果不相等，head6指向下一个结构体
			    head6=head6->next;                       
	    }
	    if(head6!=NULL)                                                             //如果head6不是空指针，打印这个人的信息 
	    	print4(head6,0);
		else                                                                        //如果是空指针，表示没有找到这个人的相关信息，退出函数 
		{                                                                    
		  printf("没有这个人的相关信息\n");
		  return ;
	    }
        print1(2);                                                                  //打印修改信息选项表 
        int select4;                                                                //定义一个选择变量，传入switch函数 
	    scanf("%d",&select4);
	    print(1);
    if(select4<=7&&select4>=1)
    {
	    switch(select4)
		{
			case 1: printf("请输入要修改的编号：");                                 //这是表示要修改学号 
			        scanf("%s",&head6->studentmumber);                              //录入链表 
			        print(1);
			        break;
			case 2: printf("请输入要修改的姓名：");                                 //这是表示要修改姓名
			        scanf("%s",&head6->name);                                       //录入链表 
			        print(1);
			        break;
	        case 3: printf("请输入要修改的身份证号：");                             //这是表示要修改学号
	                scanf("%s",&head6->ID);                                         //录入链表
	                print(1);
	                break;
	        case 4: printf("请输入要修改的人员类型：");                             //这是表示要修改人员类型
			        scanf("%s",&head6->studentmumber);                              //录入链表
			        print(1);
			        break;
			case 5: printf("请输入要修改的电话号码：");                             //这是表示要修改电话号码
			        scanf("%s",&head6->phonenumber);                                //录入链表
			        print(1);
			        break;
			case 6: printf("请输入要修改的出\入校类型：");                          //这是表示要修改出\入校类型
			        scanf("%d",&head6->io);                                         //录入链表
			        print(1);
			        break;
			case 7: printf("请输入要修改的出\入校日期：");                          //这是表示要修改出\入校日期
			        scanf("%s",&head6->date);                                       //录入链表
			        print(1);
			        break;
		}
	}
	else                                                                            //如果输入的选项不满足范围 
	printf("您输入的条件不满足查找需求\n"); 
}

void print8 (int n , student *head , string a)                                       //建立打印函数，并在传入指针的同时，传入一个字符串，还传入一个选项值 
{
	student *head7=head;                                                            //将head6指向头指针，以免头指针的值被改变
		int count1=0,teacher=0,student=0,other=0;                                  //建立四个统计变量 ，用来分别统计个个类型的人的数量                                                         
		while(head7!=NULL)
		{
	         if(n==0&&a==head7->date&&head7->io==0)                           //比较函数，看是否符合要求 
	         {
	         	if(head7->peopletype=="teacher")                              //是老师，老师人数加一 
	         	teacher++;
	         	else if(head7->peopletype=="student")                         //是学生，学生人数加一 
	         	student++;
	         	else                                                                 //是其他人员，其他人员加一 
				other++; 
	         	count1++;                                                             //总人数加一 
	         	print4(head7,0);                                                         //打印结构体信息，并不用将指针指向下一个结构体 
	         	head7=head7->next;
			 }
			 else if(n==1&&a==head7->date&&head7->io==1)                        //比较函数，看是否符合要求 
			{
				if(head7->peopletype=="teacher")                               //是老师，老师人数加一
	         	teacher++;
	         	else if(head7->peopletype=="student")                          //是学生，学生人数加一
	         	student++;
	         	else                                                                  //是其他人员，其他人员加一
				other++;                      
	         	count1++;                                                             //总人数加一
	         	print4(head7,0);                                                         //打印结构体信息，并不用将指针指向下一个结构体 
	         	head7=head7->next;
			}
			 else if(n==2&&head7->io==0)                                              //比较函数，看是否符合要求
			{
				if(head7->peopletype=="teacher")                               //是老师，老师人数加一
	         	teacher++;
	         	else if(head7->peopletype=="student")                          //是学生，学生人数加一
	         	student++;
	         	else                                                                  //是其他人员，其他人员加一
				other++;  
	         	count1++;                                                             //总人数加一
	         	print4(head7,0);                                                         //打印结构体信息，并不用将指针指向下一个结构体 
	         	head7=head7->next;
			}
			 else if(n==3&&head7->io==1)                                              //比较函数，看是否符合要求
			{
				if(head7->peopletype=="teacher")                               //是老师，老师人数加一
	         	teacher++;
	         	else if(head7->peopletype=="student")                         //是学生，学生人数加一
	         	student++;
	         	else                                                                  //是其他人员，其他人员加一
				other++; 
	         	count1++;                                                             //总人数加一
	    		print4(head7,0);                                                         //打印结构体信息，并不用将指针指向下一个结构体      	
	         	head7=head7->next;
			}
	         else 
			    head7=head7->next;
	    } 
	    if(count1==0)                                                                 //如果总人数为零，表示查无此人 
	    	printf("没有此类相关信息\n");
	    else                                                                          //如果总人数不为零，输出各个类型的人数 
	        printf("\n共有%d人，其中老师%d人，学生%d人，其他人员%d人\n",count1,teacher,student,other);
}

student *part1(student *head)                                                         //part1表示录入信息函数 
{
	int x;                                                                            //建立录入人数变量x 
	printf("请输入录入人数: "); 
	scanf("%d",&x);                                                                   //输入录入人数 
    print(1);
	student *p;                                                                       //建立用于录入信息的指针 
	for(int i=0;i<x;i++)                                                              //用for循环来录入x个信息 
	{
	    student *part=new student ;                                                  //动态分配内存，为新结构体申请空间 
		if(head==NULL)                                                                //如果头指针为空 
			head=part;                                                                //将头指针指向新的空间 
		else                                                                          //如果头指针不为空 
		    p->next=part;                                                             //将p->next指针指向新的空间 
		    part->next=NULL;                                                          //将p的下一个指针指向空指针 
		    p=part;                                                                   //p指向新的空间 
		  a:printf("请按以下顺序输入第 %d 学生信息\n",i+1);                           //录入学生信息 
			printf("人员类型 编号 人员姓名 身份证号 电话号码 出\入校类型 出\入校日期\n"); 
			cin>>part->peopletype>>part->studentmumber>>part->name>>part->ID>>part->phonenumber>>part->io>>part->date;
			if(basejudge1(part->ID)==false||basejudge2(part->phonenumber)==false||basejudge3(part->peopletype)==false||basejudge4(part->date)==false)
			{
				if(basejudge1(part->ID)==false)          printf("身份证格式有误\n");
				if(basejudge2(part->phonenumber)==false) printf("电话号码格式有误\n");
				if(basejudge3(part->peopletype)==false)  printf("身份类型格式有误\n");
				if(basejudge4(part->date)==false)        printf("日期格式有误\n");
				goto a;
			}
		    p->judge=1;                                                               //将判断变量赋值为1 
	}
	print2(head);                                                                    //调用保存函数对数据保存 
	return head;                                                                      //返回一个头指针 
}

student *part2(student *head)                                                         //part2表示保存文件函数 
{
	student *head1=head;                                                              //定义一个head1指向head，以防改变头指针 
	print2(head1);                                                                    //引用函数将链表写入文件 
    return head;
}

student *part3(student *head)                                                         //part3表示浏览函数 
{
	student *head2=head;                                                             //定义一个head1指向head，以防改变头指针
	if(head==NULL)                                                                   //如果头指针为空，表示没有信息 
	printf("此表中还没有信息\n"); 
	else                                                                              //如果头指针不为空，打印链表 
	print3(head2);                                                                    //引用函数，输出完整链表 
	return head;                                                                      //返回一个头指针 
}

student *part4(student *head)                                                        //part4表示查询函数 
{
	int select;                                                                      //建立一个选择变量 
    print1(3);                                                                       //打印查询基本表格 
	scanf("%d",&select);                                                             //输入查询选项 
	print(1);
	student *head3=head;
	if(select==1)
	{
		printf("请输入编号: ");                                                      //输入学号查找 
	    print5(head3,1);                                                             //调用查找函数 
	}
	else if(select==2)
	{
	    printf("请输入电话号码: ");                                                 //输入电话号码查找 
	    print5(head3,2);                                                            //调用查找函数 
	}
	else if(select==3)
	{
	    printf("请输入身份证号: ");                                                  //输入身份证号查找 
	    print5(head3,3);                                                             //调用查找函数 
	}
    else if(select==4)            
    {
	    printf("请输入姓名: ");                                                      //输入姓名查找 
	    print5(head3,4);                                                             //调用查找函数 
	}
	else                                                                             //输入的值不在此范围内 
	printf("您输入的条件不满足查找需求\n");  
	return head;                                                                     //返回头指针 
}

student *part5(student *head)                                                        //part5表示增加信息函数 
{ 
	student *head4=head,*p;                                                          //建立head4指向头指针，以防头指针被改变 
	int count;                                                                       //建立增加人数变量 
	printf("请输入需要增加的人数:"); 
	scanf("%d",&count);                                                              //输入增加人数 
	print(1);
	if(head==NULL)                                                                   //如果头指针是空 ，表示没有录入数据，返回 
	{
		printf("您还没有录入数据,请返回1录入数据\n");
		return 0;
	}
	while(head4->next!=NULL)                                                         //找到最后一个节点 
		 head4=head4->next;
	for(int i=0;i<count;i++)                                                         //用for循环，录入count个人的数据 
	{
	    student *part5=new student ;                                                    //为新数据申请空间 
		head4->next=part5;                                                           //最后一个节点指向新空间 
		part5->next=NULL;                                                            //新空间的节点是空指针 
		head4=part5;                                                                 //head4指向新空间 
	  a:printf("请按以下格式输入第 %d 个学生的数据\n",i+1);                          //录入人员基本数据 
	    printf("人员类型 编号 人员姓名 身份证号 电话号码 出/入校类型 出/入校日期\n"); 
        cin>>part5->peopletype>>part5->studentmumber>>part5->name>>part5->ID>>part5->phonenumber>>part5->io>>part5->date;
        if(basejudge1(part5->ID)==false||basejudge2(part5->phonenumber)==false||basejudge3(part5->peopletype)==false||basejudge4(part5->date)==false)
			{
				if(basejudge1(part5->ID)==false)          printf("身份证格式有误\n");
				if(basejudge2(part5->phonenumber)==false) printf("电话号码格式有误\n");
				if(basejudge3(part5->peopletype)==false)  printf("身份类型格式有误\n");
				if(basejudge4(part5->date)==false)        printf("日期格式有误\n");
				goto a;
			}
        part5->judge=1;                                                              //将判断变量赋值为1 
	}           
    print2(head);                                                                    //调用保存函数对数据保存 
	return head;                                                                     //返回头指针 
}

student *part6(student *head)                                                         //part6表示删除函数 
{
	int select3;                                                                      //建立选择变量 
    print1(4);                                                                        //打印删除的基本选项表 
	scanf("%d",&select3);                                                             //输入选择变量
	print(1);
	student *head5=head;                                                              //建立head5指针，指向头指针一以免头指针被改变
	if(select3==1)                                                                    //按学号搜索删除 
	{
	    printf("请输入编号: ");                                                       //输入学号 
	    head=print6(head,1);                                                            //调用print6函数进行进一步操作 
	}
	else if(select3==2)                                                                //按电话号码搜索删除 
	{
	    printf("请输入电话号码: ");                                                    //输入电话号码 
	    head=print6(head,2);                                                              //调用print6函数进行进一步操作 
	}
	else if(select3==3)                                                                 //按身份证号码搜索删除  
	{
	    printf("请输入身份证号: ");                                                     //输入身份证号码 
	    head=print6(head,3);                                                                 //调用print6函数进行进一步操作 
	}
	else if(select3==4)                                                                 //按姓名搜索删除 
	{
	    printf("请输入姓名: ");                                                         //输入姓名 
	    head=print6(head,4);                                                            //调用print6函数进行进一步操作 
	}
	else 
	printf("您输入的条件不满足查找需求\n");                                             //不满足查找需求，返回 
	print2(head5);                                                                      //调用保存函数，对数据进行保存 
	return head;                                                                        //返回头指针 
}

student *part7(student *head)                                                           //part7是修改函数 
{
	int select4;                                                                        //建立选择变量 
    print1(5);                                                                          //打印删除选择信息表 
	scanf("%d",&select4);                                                               //输入删除选项 
	print(1);
	student *head6=head;                                                                //建立head6指针，将头指针指向head6，防止头指针被改变 
	if(select4==1)                                                                      //按学号搜索修改 
	{
	    printf("请输入编号: ");                                                         //输入学号 
	    print7 (head6,1);                                                               //调用print7函数进行进一步操作 
	}
	else if(select4==2)                                                                 //按学号搜索修改
	{
	    printf("请输入电话号码: ");                                                     //输入电话号码 
	    print7 (head6,2);                                                               //调用print7函数进行进一步操作 
	}
	else if(select4==3)                                                                 //按身份证号搜索修改
	{
	    printf("请输入身份证号: ");                                                      //输入身份证号 
	    print7 (head6,3);                                                               //调用print7函数进行进一步操作
    }
    else if(select4==4)                                                                 //按姓名搜索修改
    {
	    printf("请输入姓名: ");                                                         //输入姓名 
	    print7 (head6,4);                                                               //调用print7函数进行进一步操作
    }
	else
	printf("您输入的条件不满足查找需求\n");                                              //不满足查找需求，返回 
	print2(head6);                                                                       //调用保存函数对数据保存 
	return head;                                                                         //返回头指针 
}

student *part8(student *head)                                                            //part8是统计函数 
{
	student *head7=head;                                                                 //将head7指向头指针，以免头指针被改变 
	string outDate ;                                                           //建立一个新的字符串，并把它赋值为空字符 
	int select;	                                                                         //建立统计变量 
	print1(6);                                                                           //打印基本的统计信息表 
	scanf("%d",&select);                                                                 //输入要完成的步骤 
	print(1);
	if(select==1)                                                                        //按照日期查找 
	{ 
	    printf("请输入日期: ");                                                          //输入日期 
	    cin>>outDate;
	    print(1);
	    print8(0,head7,outDate);                                                         //调用print8函数进行日期查找 
	} 
	else if(select==2)                                                                   //按照日期查找 
	{
	    printf("请输入日期: ");                                                          //输入日期 
	    cin>>outDate;
	    print(1);
	    print8(1,head7,outDate);                                                          //调用print8函数进行日期查找
	}
	else if(select==3)                                      
		print8(2,head7,head7->date);                                                      //调用print8函数进行日期查找
	else if(select==4)
	    print8(3,head7,head7->date);                                                      //调用print8函数进行日期查找
	else                                                                                  //不满足查找需求，返回
	    printf("您输入的条件不满足查找需求\n"); 
	return head;                                                                          //返回头指针 
}

void part9(student *head)                                                                 //part9是日期排序函数 
{
	student *head8=head,*p=head;                                                          //建立head8和p指针指向头指针，以免头指针被改变 
	int select4,count2=0;                                                                 //建立选择变量有，和计算节点个数的函数 
	while(p!=NULL)                                                                        //遍历链表，数节点个数 
	{
		p=p->next;
		count2++;
	}
	print1(7);                                                                             //打印part9的基本信息表 
	scanf("%d",&select4);                                                                  //输入要选的选项 
	print(1);
	if(select4==1)                                                                         //假如要统计出校人数 
	{
		for(int i=0;i<count2;i++)                                                          //遍历节点的次数 
		{
			string outday;                                                               //建立一个新的字符串，用来比较日期 
			while(head8!=NULL)                                                             //遍历节点 
			{
			  if(head8->judge==1&&head8->io==0)                                            //如果这个节点已经输出了，就不用比较 
			  {
			  	for(int t=0;t<25;t++)                                                      //遍历25个字符 
			  	{
			  		int x1=head8->date[t]-'0',x2=outday[t]-'0';
			  		if(x1>x2)                                                              //如果原本的日期比较看哪个最大 
			  		{
			  			outday=head8->date;                                              //如果大于，直接赋值，并退出 
			  			break;                                                             
					}
					else if(x1<x2)                                                         //如果小于，不赋值，就退出 
					break;
				}
			  }
			  head8=head8->next;                                                           //节点指向下一个 
			}
			head8=head;                                                                    //head8再次指向头节点 
			
			while(head8!=NULL)                                                             //遍历链表 
			{
				if(head8->judge==1&&head8->date==outday&&head8->io==0)           //看是否是最大的日期 
				{
                        print4(head8,1);                                                   //调用print4函数输出，节点指向下一个 
						head8->judge=0;                                                    //标记judge为零，表示已经输出过了 
				}
				else
					head8=head8->next;                                                      //节点指向下一个 
			}
			head8=head;                                                                      //将head8指向头节点，进入下一轮循环 
		}
		head8=head;                                                                           //将head8指向头节点
		while(head8!=NULL)                                                                    //遍历head8,将judge全部赋值为1 
		{
			head8->judge=1;
			head8=head8->next;                                                                 //指向下一个节点 
		}
	}
	else if(select4==2)                                                                        //与前面道理相同 
	{
		for(int i=0;i<count2;i++)
		{
		    string inday;  
			while(head8!=NULL)
			{
			  if(head8->judge==1&&head8->io==1)
			  {
			  	for(int t=0;t<25;t++)
			  	{
			  		int x1=head8->date[t]-'0',x2=inday[t]-'0';
			  		if(x1>x2)
			  		{
			  			inday=head8->date; 
			  			break;
					}
					else if(x1<x2)
					break;
				}
			  }
			  head8=head8->next;
			}
			head8=head;
			while(head8!=NULL)
			{
				if(head8->judge==1&&head8->date==inday&&head8->io==1)
				{
						 print4(head8,1);
						 head8->judge=0;
				}
				else
					head8=head8->next;
			}
			head8=head;
		}
		head8=head;
		while(head8!=NULL)
		{
			head8->judge=1;
			head8=head8->next;
		}
	} 
	else                                                                                          //输入不满足要求，退出 
	printf("您输入的条件不满足查找需求\n"); 
}

void part10(student *head)                                                                 //表示退出函数 
{
	while(head!=NULL)                                                                             //遍历节点，释放空间 
	{
		free(head);
		head=head->next;
	}
}

int main(void)
{
	int key;                                                                                      //建立选择变量 
	system("title 校园出入管理系统");
	system("mode con cols=76 lines=30");
	system("color 0B") ;
    print1(1);                                                                                    //打印基本选择信息表 
	scanf("%d",&key);                                                                             //输入选择信息 
	print(1);
	do{
		switch(key)
		{
			case 1: head=part1(head);                                                              //调用part1函数 
			        system("pause");
			        system("cls");
			        print1(1);                                                                      //打印基本选择信息表
			        scanf("%d",&key);                                                               //输入选择信息 
			        print(1);
			        break;                                                                          //退出 
		    case 2: head=part2(head);                                                               //调用part2函数
		    		system("pause");
		    	    system("cls");
                    print1(1);                                                                      //打印基本选择信息表
		    	    scanf("%d",&key);                                                               //输入选择信息 
		    	    print(1);
		    	    break;                                                                          //退出 
		    case 3: head=part3(head);                                                              //调用part3函数
		            system("pause");
		    	    system("cls");
		    	    print1(1);                                                                     //打印基本选择信息表
		    	    scanf("%d",&key);                                                              //输入选择信息 
		    	    print(1);
		    	    break;                                                                         //退出 
		    case 4: head=part4(head);                                                               //调用part4函数
		    	    system("pause");
		    	    system("cls");
		    	    print1(1);                                                                     //打印基本选择信息表                                                                 //打印基本选择信息表
		    	    scanf("%d",&key);                                                               //输入选择信息 
		    	    print(1);
		    	    break;                                                                           //退出 
		    case 5: head=part5(head);                                                                 //调用part5函数
		    	    system("pause");
		    	    system("cls");
		    	    print1(1);                                                                     //打印基本选择信息表
		    	    scanf("%d",&key);                                                                //输入选择信息
		    	    print(1);
		    	    break;	                                                                          //退出 
		    case 6: head=part6(head);                                                                //调用part6函数
		    	    system("pause");
		    	    system("cls");
		    	    print1(1);                                                                     //打印基本选择信息表
		    	    scanf("%d",&key);                                                                //输入选择信息
		    	    print(1);
		    	    break;                                                                           //退出 
		    case 7: head=part7(head);                                                                //调用part7函数
		    	    system("pause");
		    	    system("cls");
		    	    print1(1);                                                                     //打印基本选择信息表
		    	    scanf("%d",&key);                                                                 //输入选择信息
		    	    print(1);
		    	    break;                                                                            //退出 
	        case 8: head=part8(head);                                                                  //调用part8函数
	                system("pause");
		    	    system("cls");
		    	    print1(1);                                                                     //打印基本选择信息表
	                scanf("%d",&key);                                                                  //输入选择信息
	                print(1);
		    	    break;		    	                                                                //退出 
		    case 9: part9(head);                                                                         //调用par91函数
		    	    system("pause");
		    	    system("cls");
		    	    print1(1);                                                                     //打印基本选择信息表
		    	    scanf("%d",&key);                                                                    //输入选择信息
		    	    print(1);
		    	    break;		    	                                                                 //退出
		    case 10:part10(head);                                                                        //调用part10函数
		    	    //system("shutdown -s -t 1");
		    	    break; 	                                                                             //退出
		    default:system("cls");
		    	    print1(1); 
			        scanf("%d",&key);                                                                    //输入选择信息
		    	    print(1);
		    	    break; 	     
		}
	}while(key!=10);                                                                                  //选择10时，退出 
	system("cls");
	  printf("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	  printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	  printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	  printf("************  *                             *               \n");
	  printf("     *        *                             *               \n");
	  printf("     *        *                             *               \n");
	  printf("     *        *                             *               \n");
	  printf("     *        ********  *******    *******  *    *  ******* \n");
	  printf("     *        *      *  *     *    *     *  *  *    *       \n");
	  printf("     *        *      *  *     *    *     *  **      ******* \n");
	  printf("     *        *      *  *     *    *     *  * *           * \n");
	  printf("     *        *      *  *     **   *     *  *  *          * \n");
	  printf("     *        *      *  ******* *  *     *  *    *  ******* \n");
	  printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	  printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	  printf("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
	return 0;
}
