#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
    char id[20];      // 学号
    char name[50];    // 姓名
    float *scores;    // 各科成绩
    float avg;        // 平均分
} Student;

void calculate_average(Student *students, int num_students, int num_courses) {
    for (int i = 0; i < num_students; i++) {
        float sum = 0;
        for (int j = 0; j < num_courses; j++) {
            sum += students[i].scores[j];
        }
        students[i].avg = sum / num_courses;
    }
}

int compare_students(const void *a, const void *b) {
    Student *studentA = (Student *)a;
    Student *studentB = (Student *)b;
    return (studentB->avg - studentA->avg > 0) ? 1 : -1;
}

int main() {
    int num_students, num_courses;

    // 输入学生人数和课程门数
    printf("请输入学生人数: ");
    scanf("%d", &num_students);
    printf("请输入课程门数: ");
    scanf("%d", &num_courses);

    // 动态分配学生数组
    Student *students = (Student *)malloc(num_students * sizeof(Student));
    for (int i = 0; i < num_students; i++) {
        students[i].scores = (float *)malloc(num_courses * sizeof(float));
    }

    // 输入学生信息
    for (int i = 0; i < num_students; i++) {
        printf("\n请输入学生 %d 的学号: ", i + 1);
        scanf("%s", students[i].id);
        printf("请输入学生 %d 的姓名: ", i + 1);
        scanf("%s", students[i].name);
        printf("请输入学生 %d 的所有课程成绩（用空格隔开）: ", i + 1);
        for (int j = 0; j < num_courses; j++) {
            scanf("%f", &students[i].scores[j]);
        }
    }

    // 计算平均分
    calculate_average(students, num_students, num_courses);

    // 按平均分排序
    qsort(students, num_students, sizeof(Student), compare_students);

    // 输出排序后的学生信息
    printf("\n排序后的学生信息:\n");
    printf("学号\t姓名\t");
    for (int j = 0; j < num_courses; j++) {
        printf("课程%d\t", j + 1);
    }
    printf("平均分\n");

    for (int i = 0; i < num_students; i++) {
        printf("%s\t%s\t", students[i].id, students[i].name);
        for (int j = 0; j < num_courses; j++) {
            printf("%.2f\t", students[i].scores[j]);
        }
        printf("%.2f\n", students[i].avg);
    }

    // 释放内存
    for (int i = 0; i < num_students; i++) {
        free(students[i].scores);
    }
    free(students);

    return 0;
}

