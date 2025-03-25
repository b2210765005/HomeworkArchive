// DecodeMessage.cpp

#include "DecodeMessage.h"
#include <iostream>
#include <bitset>

// Default constructor
DecodeMessage::DecodeMessage() {
    // Nothing specific to initialize here
}

// Destructor
DecodeMessage::~DecodeMessage() {
    // Nothing specific to clean up
}


std::string
DecodeMessage::decodeFromImage(const ImageMatrix &image, const std::vector<std::pair<int, int>> &edgePixels) {
    std::string binary;
    for (const auto &pair: edgePixels) {
        int firstValue = pair.first;
        int secondValue = pair.second;
        if (static_cast<int>(image.get_data(firstValue, secondValue)) % 2 == 0) {
            binary += '0';
        } else {
            binary += '1';
        }

    }

    while (binary.length() % 7 != 0) {
        binary.insert(0, 1, '0');
    }

    std::string output;

    for (int i = 0; i < binary.length(); i += 7) {
        int startIndex = i;
        int endIndex = std::min(i + 7, static_cast<int>(binary.size()));
        std::string chunk = binary.substr(startIndex, endIndex - startIndex);

        int decimalValue = std::bitset<7>(chunk).to_ulong();
        if (decimalValue <= 32) {
            decimalValue += 33;
        } else if (decimalValue >= 127) {
            decimalValue = 126;
        }
        output += static_cast<char>(decimalValue);
    }

    return output;
}

