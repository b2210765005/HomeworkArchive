// EdgeDetector.cpp

#include "EdgeDetector.h"
#include <cmath>

#include "EdgeDetector.h"
#include <cmath>

// Default constructor
EdgeDetector::EdgeDetector() {

}

// Destructor
EdgeDetector::~EdgeDetector() {

}

// Detect Edges using the given algorithm
std::vector<std::pair<int, int>> EdgeDetector::detectEdges(const ImageMatrix &input_image) {
    std::vector<std::pair<int, int>> process;
    double *kernel_gaussianX[3] = {
            new double[3]{-1, 0, 1},
            new double[3]{-2, 0, 2},
            new double[3]{-1, 0, 1}
    };
    Convolution convolutionx(kernel_gaussianX, 3, 3, 1, true);
    double *kernel_gaussianY[3] = {
            new double[3]{-1, -2, -1},
            new double[3]{0, 0, 0},
            new double[3]{1, 2, 1}
    };
    Convolution convolutiony(kernel_gaussianY, 3, 3, 1, true);
    ImageMatrix input_imageX = convolutionx.convolve(input_image);
    ImageMatrix input_imageY = convolutiony.convolve(input_image);
    ImageMatrix process_image(input_image.get_height(), input_image.get_width());
    double process_total = 0;
    for (int x = 0; x < input_image.get_height(); x++) {
        for (int y = 0; y < input_image.get_width(); y++) {
            process_image.set_data(x, y, sqrt(input_imageX.get_data()[x][y] * input_imageX.get_data()[x][y] +
                                              input_imageY.get_data()[x][y] * input_imageY.get_data()[x][y]));
        }

    }
    for (int i = 0; i < process_image.get_height(); i++) {
        for (int j = 0; j < process_image.get_width(); j++) {
            process_total += process_image.get_data(i, j);

        }
    }
    double process_average = process_total / (process_image.get_height() * process_image.get_width());
    for (int i = 0; i < process_image.get_height(); i++) {
        for (int j = 0; j < process_image.get_width(); j++) {
            if (process_image.get_data()[i][j] > process_average) {
                process.push_back(std::make_pair(i, j));
            }
        }
    }


    return process;
}

