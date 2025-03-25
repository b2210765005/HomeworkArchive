#include <iostream>

#include "Convolution.h"

// Default constructor 
Convolution::Convolution() {
}

Convolution::Convolution(double **customKernel, int kh, int kw, int stride_val, bool pad) {
    this->kernel = customKernel;
    this->kh = kh;
    this->kw = kw;
    this->stride_val = stride_val;
    this->pad = pad;
}

// Destructor
Convolution::~Convolution() {
}


ImageMatrix Convolution::convolve(const ImageMatrix& input_image) const {
    int input_height = input_image.get_height();
    int input_width = input_image.get_width();
    int output_height = ((input_height - kh + 2 * pad) / stride_val) + 1;
    int output_width = ((input_width - kw + 2 * pad) / stride_val) + 1;
    ImageMatrix output_image(output_height, output_width);
    ImageMatrix process;

    if (pad) {
        process = ImageMatrix(input_height + 2, input_width + 2);
        for (int x = 0; x < process.get_height(); x++) {
            for (int y = 0; y < process.get_width(); y++) {
                process.get_data()[x][y] = 0;
            }
        }
        for (int x = 0; x < input_height; x++) {
            for (int y = 0; y < input_width; y++) {
                double pixel_value = input_image.get_data(x, y);
                process.get_data()[x + 1][y + 1] = pixel_value;
            }
        }
    } else {
        process = ImageMatrix(input_height, input_width);
        for (int x = 0; x < process.get_height(); x++) {
            for (int y = 0; y < process.get_width(); y++) {
                process.get_data()[x][y] = 0;
            }
        }
        for (int x = 0; x < input_height; x++) {
            for (int y = 0; y < input_width; y++) {
                double pixel_value = input_image.get_data(x, y);
                process.get_data()[x][y] = pixel_value;
            }
        }

    }

    for (int y = 0; y < process.get_height(); y += stride_val) {
        for (int x = 0; x < process.get_width(); x += stride_val) {
            for (int i = 0; i < kh; i++) {
                for (int j = 0; j < kw; j++) {
                    if (y > process.get_height() - kh || x > process.get_width() - kw) {
                        continue;
                    } else {
                        output_image.get_data()[y / stride_val][x / stride_val] +=
                                process.get_data(y + i, x + j) * kernel[i][j];
                    }
                }
            }
        }
    }

    return output_image;
}