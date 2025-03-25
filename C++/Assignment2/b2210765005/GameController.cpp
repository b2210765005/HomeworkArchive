// GameController.cpp

#include "GameController.h"
#include <fstream>
#include <iostream>

bool GameController::play(BlockFall &game, const string &commands_file) {
    std::ifstream inputFile(commands_file);
    if (!inputFile.is_open()) {
        std::cerr << "Error: Unable to open file " << commands_file << std::endl;
        return false;
    }

    std::string command;
    while (std::getline(inputFile, command)) {
        handle_command(game, command);
    }

    inputFile.close();

    return true;
}

void GameController::handle_command(BlockFall &game, const string &command) {
    if (command == "PRINT_GRID") {
        print_game_state(game);
    } else if (command == "ROTATE_RIGHT") {
        game.rotate(*game.active_rotation);
    } else if (command == "ROTATE_LEFT") {
        game.rotate(*(game.active_rotation->left_rotation));
    } else if (command == "MOVE_RIGHT") {
    } else if (command == "MOVE_LEFT") {
    } else if (command == "DROP") {
    } else if (command == "GRAVITY_SWITCH") {
    } else {
        handle_unknown_command(command);
    }
}

void GameController::print_game_state(BlockFall &game) {
}

void GameController::handle_unknown_command(const string &command) {
    std::cerr << "Unknown command: " << command << std::endl;
}
