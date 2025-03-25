#include <fstream>
#include <utility>
#include <sstream>
#include "BlockFall.h"
#include <iostream>

BlockFall::BlockFall(string grid_file_name, string blocks_file_name, bool gravity_mode_on, const string &leaderboard_file_name,
                     const string &player_name) : gravity_mode_on(gravity_mode_on),
                                                  leaderboard_file_name(
                                                          leaderboard_file_name),
                                                  player_name(std::move(player_name)) {
    read_blocks(blocks_file_name);
    initialize_grid(grid_file_name);
    leaderboard.read_from_file(leaderboard_file_name);

}

Block BlockFall::blockk(const vector<std::vector<std::string>> &vectorBlocks) {
    Block block1;
    Block *current = &block1;

    for (int i = 0; i < vectorBlocks.size(); i++) {
        for (const basic_string<char>& j: vectorBlocks[i]) {

            std::vector<bool> vector;
            for (char k: j) {
                if (k == '1') {
                    vector.push_back(true);
                }
                if (k == '0') {
                    vector.push_back(false);
                }
            }
            if (i == vectorBlocks.size() - 1) {
                power_up.push_back(vector);
            } else {
                current->shape.push_back(vector);
            }
        }
        if (i < vectorBlocks.size() - 2) {
            current->next_block = new Block();
            current = current->next_block;
        }
    }
    return block1;
}

void BlockFall::rotate(Block &block) {
    Block *currentBlock = &block;
    std::vector<std::vector<bool>> control;
    int rotateNum = 0;

    while (currentBlock != nullptr) {
        control = (rotateNum == 0) ? currentBlock->shape : control;
        int row = static_cast<int>(currentBlock->shape.size());
        int col = static_cast<int>(currentBlock->shape[0].size());

        std::vector<std::vector<bool>> vBlock(col, std::vector<bool>(row, false));
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                vBlock[j][row - 1 - i] = currentBlock->shape[i][j];
            }
        }

        if (control == vBlock) {
            if (rotateNum == 0) {
                currentBlock->right_rotation = currentBlock;
                currentBlock->left_rotation = currentBlock;
            } else if (rotateNum == 1) {
                currentBlock->right_rotation = currentBlock->left_rotation;
                currentBlock->left_rotation->left_rotation = currentBlock;
            } else if (rotateNum == 3) {
                currentBlock->right_rotation = currentBlock->left_rotation->left_rotation->left_rotation;
                currentBlock->left_rotation->left_rotation->left_rotation->left_rotation = currentBlock;
            }
            currentBlock = currentBlock->next_block;
            rotateNum = 0;
        } else {
            currentBlock->right_rotation = new Block;
            currentBlock->right_rotation->shape = vBlock;
            currentBlock->right_rotation->next_block = currentBlock->next_block;
            currentBlock->right_rotation->left_rotation = currentBlock;
            currentBlock = currentBlock->right_rotation;
            rotateNum++;
        }
    }
}


void BlockFall::read_blocks(const string &input_file) {
    std::ifstream inputFile(input_file);
    if (!inputFile.is_open()) {
        cerr << "Error: Unable to open file " << input_file << endl;
        exit(EXIT_FAILURE);
    }

    std::vector<std::vector<std::string>> vectorBlocks;
    std::vector<std::string> vectorBlock;
    std::string string1;

    while (std::getline(inputFile, string1)) {
        if (string1.empty()) {
            continue;
        } else if (string1[0] == '[' && string1[string1.size() - 1] == ']') {
            vectorBlock.push_back(string1.substr(1, string1.size() - 2));
            vectorBlocks.push_back(vectorBlock);
            vectorBlock.clear();
        } else if (string1[0] == '[') {
            vectorBlock.push_back(string1.substr(1));
        } else if (string1[string1.size() - 1] == ']') {
            vectorBlock.push_back(string1.substr(0, string1.size() - 1));
            vectorBlocks.push_back(vectorBlock);
            vectorBlock.clear();
        } else {
            vectorBlock.push_back(string1);
        }
    }

    inputFile.close();

    if (vectorBlocks.empty()) {
        cerr << "Error: Empty file " << input_file << endl;
        exit(EXIT_FAILURE);
    }

    auto *block = new Block(blockk(vectorBlocks));
    initial_block = block;
    active_rotation = initial_block;

    rotate(*block);
}


void BlockFall::initialize_grid(const string &input_file) {
    std::ifstream inputFile(input_file);
    std::string line;

    while (std::getline(inputFile, line)) {
        if (line.empty()) {
            continue;
        }

        std::stringstream ss(line);
        std::vector<int> gridRow;
        int box;

        while (ss >> box) {
            gridRow.push_back(box);
        }
        grid.push_back(gridRow);

    }
    inputFile.close();

    rows = grid.size();
    cols = grid[0].size();
}

BlockFall::~BlockFall() = default;