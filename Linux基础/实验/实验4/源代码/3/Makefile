CC = gcc

TARGET = main_test

SRCS = main_test.c add_int.c add_float.c sub_int.c sub_float.c
OBJS = $(SRCS:.c=.o)

$(TARGET): $(OBJS)
	$(CC) -o $@ $(OBJS)

%.o: %.c add.h sub.h
	$(CC) -c $< -o $@

clean:
	rm -f $(OBJS) $(TARGET)
