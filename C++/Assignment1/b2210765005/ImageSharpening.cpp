#include "ImageSharpening.h"

// Default constructor
ImageSharpening::ImageSharpening() {

}

ImageSharpening::~ImageSharpening() {

}

ImageMatrix ImageSharpening::sharpen(const ImageMatrix &input_image, double k) {
    double *kernel_gaussian[3] = {
            new double[3]{1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0},
            new double[3]{1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0},
            new double[3]{1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0}
    };
    Convolution convolution(kernel_gaussian, 3, 3, 1, true);
    ImageMatrix blurred = convolution.convolve(input_image);
    ImageMatrix sharpened(input_image.get_height(), input_image.get_width());
    for (int x = 0; x < sharpened.get_height(); x++) {
        for (int y = 0; y < sharpened.get_width(); y++) {
            double new_pixel_value =
                    input_image.get_data(x, y) + (input_image.get_data(x, y) - blurred.get_data(x, y)) * k;
            if (new_pixel_value < 0) {
                new_pixel_value = 0;
            }
            if (new_pixel_value > 255) {
                new_pixel_value = 255;
            }
            sharpened.set_data(x, y, new_pixel_value);
        }
    }
    return sharpened;
}
