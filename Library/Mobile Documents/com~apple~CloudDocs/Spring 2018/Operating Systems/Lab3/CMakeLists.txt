#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#ifndef _REENTRANT
#define _REENTRANT
#endif

//method declarations
void *thread_function(void *string);

int main(int argc, char *argv[]) {

//array of pthreads
pthread_t threads[argc-1];
//pthread_t *threads = (pthread_t *) malloc( argc * sizeof(pthread_t) )

for(int i=1; i<argc; i++) {
//create thread
int returnCreate = pthread_create(&(threads[i-1]), 0, thread_function, argv[i]);
if(returnCreate != 0) {
exit(1);
}
}

for(int i=1; i<argc; i++) {
//link thread
int returnJoin = pthread_join(threads[i-1], 0);

if(returnJoin == 0) {
//print statement
printf("Thread %d returns %d\n", , returnJoin);
}
//error                                                                                    \

else {
exit(1);
}

for(int i=1; i<argc; i++) {
//link thread
int returnJoin = pthread_join(threads[i-1], 0);

if(returnJoin == 0) {
//print statement
printf("Thread %d returns %d\n", , returnJoin);
}
//error                                                                                    \

else {
exit(1);
}
}
//link thread
/*
int returnJoin = pthread_join(threads[i-1], 0);

if(returnJoin == 0) {
//print statement
printf("Thread %d returns %d\n", pthread_self(), returnJoin);
}
*/
//error

//print statement
//printf("Thread %d returns %d\n", returnCreate, returnJoin);

return 0;
}

void *thread_function(void *string){
int seed_value = 0;
int random_times = rand_r(&seed_value)%10 + 1; //number between 1 and 10

printf("%s\tto be printed %d times\n", string, random_times);
for(int i=0; i<random_times; i++) {
//sleep(1); //wait a second, so that the printing is slowed down to show you how to print
printf("%s ", string);
}
printf("\n");
}

