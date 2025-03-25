// GameController.h

#ifndef GAMECONTROLLER_H
#define GAMECONTROLLER_H

#include "BlockFall.h"

class GameController {
public:
    // Oyunu oynamak için ana fonksiyon
    bool play(BlockFall& game, const string& commands_file);

private:
    // Diğer yardımcı fonksiyonlar
    void print_game_state(BlockFall& game);
    void handle_command(BlockFall& game, const string& command);
    void handle_unknown_command(const string& command);
};

#endif // GAMECONTROLLER_H
