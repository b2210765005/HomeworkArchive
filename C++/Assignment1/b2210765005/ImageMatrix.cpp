#include "ImageMatrix.h"
#include <iostream>


// Default constructor
ImageMatrix::ImageMatrix() {

}


ImageMatrix::ImageMatrix(int imgHeight, int imgWidth) : height(imgHeight), width(imgWidth) {
    data = new double *[height];
    for (int i = 0; i < height; i++) {
        data[i] = new double[width];
    }
}

ImageMatrix::ImageMatrix(const std::string &filepath) {
    ImageLoader imageLoader(filepath);

    height = imageLoader.getHeight();
    width = imageLoader.getWidth();

    data = new double *[height];
    for (int i = 0; i < height; ++i) {
        data[i] = new double[width];
    }

    double **imageData = imageLoader.getImageData();
    for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; j++) {
            data[i][j] = imageData[i][j];
        }
    }
}

ImageMatrix::~ImageMatrix() = default;

ImageMatrix::ImageMatrix(const double **inputMatrix, int imgHeight, int imgWidth) {
    height = imgHeight;
    width = imgWidth;
    data = new double *[height];
    for (int i = 0; i < height; i++) {
        data[i] = new double[width];
    }
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            data[i][j] = inputMatrix[i][j];

        }
    }

}


ImageMatrix ImageMatrix::operator+(const ImageMatrix &other) const {
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            this->get_data()[i][j] += other.get_data()[i][j];
        }
    }
    return *this;
}

ImageMatrix ImageMatrix::operator-(const ImageMatrix &other) const {
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            this->get_data()[i][j] -= other.get_data()[i][j];
        }
    }
    return *this;
}

ImageMatrix ImageMatrix::operator*(const double &scalar) const {
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            this->get_data()[i][j] *= scalar;
        }
    }
    return *this;
}


double **ImageMatrix::get_data() const {
    return data;
}

double ImageMatrix::get_data(int i, int j) const {
    return data[i][j];
}

int ImageMatrix::get_height() const {
    return height;
}

int ImageMatrix::get_width() const {
    return width;
}

void ImageMatrix::set_data(int i, int j, double k) {
    data[i][j] = k;
}




