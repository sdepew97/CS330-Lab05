#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/time.h>

#ifndef _REENTRANT
#define _REENTRANT
#endif

//method declarations
void *thread_function(void *string);

struct thread_data
{
    pthread_t thread_id;
    char *message;
};

int main(int argc, char *argv[]) {

    //array of pthreads
    pthread_t threads[argc - 1];
    //pthread_t *threads = (pthread_t *) malloc( argc * sizeof(pthread_t) )
    struct thread_data thread_data_array[argc - 1];

    for (int i = 1; i < argc; i++) {
        //create threads
        thread_data_array[i - 1].message = argv[i];
        thread_data_array[i - 1].thread_id = NULL;

        int returnCreate = pthread_create(&(threads[i - 1]), 0, thread_function, (void *) &thread_data_array[i-1]);

        if (returnCreate != 0) {
            exit(EXIT_FAILURE);
        }
    }

    for (int i = 1; i < argc; i++) {
        //link threads
        int returnJoin = pthread_join(threads[i - 1], 0);
        if (returnJoin == 0) {
            //print statement
            printf("Thread %d returns %d\n", (int) thread_data_array[i].thread_id, returnJoin);
        }
            //error
        else {
            exit(EXIT_FAILURE);
        }
    }
    return 0;
}

void *thread_function(void * thread) {
    struct timeval now;
    unsigned int secs;

    gettimeofday(&now, NULL);
    secs = now.tv_sec;

    int random_times = rand_r(&(secs)) % 9 + 2; //number between 2 and 10

    struct thread_data *data = (struct thread_data *) thread;

    printf("%s\tto be printed %d times\n", data->message, random_times);
    for (int i = 0; i < random_times; i++) {
        //sleep(1 / 2); //wait a second, so that the printing is slowed down to show you how to print
        printf("%s ", data->message);
    }

    //insert thread id in struct to use later in printing
    ((struct thread_data *) thread)->thread_id = pthread_self();
    printf("\n");
    return NULL;
}