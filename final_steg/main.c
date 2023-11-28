#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define HEADER_SIZE 54
#define MAX_FILE_SIZE 1000

void hideMessage(FILE *image, char *message)
{
    int i, j, k = HEADER_SIZE + 1, message_len = strlen(message);
    char byte, bit;

    unsigned char header[HEADER_SIZE];
    fread(header, sizeof(unsigned char), HEADER_SIZE, image);

    unsigned char *image_data = (unsigned char*) malloc(MAX_FILE_SIZE);
    int file_size = fread(image_data, sizeof(unsigned char), MAX_FILE_SIZE, image);

    FILE *output = fopen("output.bmp", "wb");
    fwrite(header, sizeof(unsigned char), HEADER_SIZE, output);

    /*for (j = 1; j < 33; j++)
        {
            bit = (message_len >> j) & 1;

            //image_data[k] = (image_data[k] & 0xFE) & bit;
            int lsb = image_data[k] & 1;
            if(lsb == 1)
            {
                image_data[k] -= 1;
            }
            else
            {
                image_data[k] += 1;
            }

            k++;

            if (k >= file_size)
            {
                printf("Image file too small to hide the entire message.\n");
                return;
            }
        }*/
    for (i = 0; i < message_len; i++)
    {
        byte = message[i];

        for (j = 0; j < 8; j++)
        {
            bit = (byte >> j) & 1;

            image_data[k] = (image_data[k] & 0xFE) | bit;

            k++;

            if (k >= file_size)
            {
                printf("Image file too small to hide the entire message.\n");
                return;
            }
        }
    }

    fwrite(image_data, sizeof(unsigned char), file_size, output);
    fclose(output);

    printf("Message hidden successfully in encoded.bmp !\n");
}

void retrieveMessage(FILE *image) {
    int i, j, k = 0;
    char byte, bit;

    unsigned char header[HEADER_SIZE];
    fread(header, sizeof(unsigned char), HEADER_SIZE, image);

    unsigned char *image_data = (unsigned char*) malloc(MAX_FILE_SIZE);
    int file_size = fread(image_data, sizeof(unsigned char), MAX_FILE_SIZE, image);

    for (i = HEADER_SIZE + 1; i < file_size; i+=8)
    {
        for(j = i; j < i + 8; j++)
        {
            bit = image_data[i] & 1;
            byte = (byte << j) | bit;
        }

        printf("%c", byte);
        byte = 0;
    }

    printf("\n");
}

int main() {
    FILE *image;
    char message[1000];
    char input_image[1000];

    int option;
    printf("Usage :\n");
    printf("e. Hide message in image\n");
    printf("   -must enter input path(required)\n");
    printf("d. Retrieve message from image\n");
    printf("   -must enter input path(required)\n");
    printf("Choose an option(e or d) : ");
    scanf("%c", &option);

    if (option == 'e')
    {
        printf("Enter input image name/path : \n");
        scanf(" %[^\n]s", input_image);
        image = fopen(input_image, "rb");
        if(image == NULL)
        {
            printf("Failed to open this file!\n");
            return 1;
        }
        printf("Enter message to hide:\n");
        scanf(" %[^\n]s", message);
        hideMessage(image, message);
        fclose(image);
    }
    else if (option == 'd')
    {
        printf("Enter input image name/path : \n");
        scanf(" %[^\n]s", input_image);
        image = fopen(input_image, "rb");
        if(image == NULL)
        {
            printf("Failed to open this file!\n");
            return 1;
        }
        retrieveMessage(image);
        fclose(image);
    }
    else
    {
        printf("Invalid option!\n");
        return 1;
    }

    return 0;
}
