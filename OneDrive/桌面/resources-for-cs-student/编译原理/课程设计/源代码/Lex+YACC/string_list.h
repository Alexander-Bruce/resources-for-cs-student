#ifndef STRING_LIST_H
#define STRING_LIST_H

#include <stdio.h>  // For FILE in parser, perror
#include <stdlib.h> // For malloc, realloc, free, exit
#include <string.h> // For strdup, strlen, strncmp
#include <errno.h>  // For errno

// Definition for a list of strings
typedef struct StringList {
    char** items;
    int count;
    int capacity;
} StringList;

// Function prototypes that use StringList
// Implementations can be in fss_parser.y's C section or a separate string_list.c
StringList* create_string_list();
void add_to_string_list_copy(StringList* list, const char* item_to_copy);
void free_string_list(StringList* list);
// int str_ends_with(const char *str, const char *suffix); // Prototype if moving implementation

#endif // STRING_LIST_H