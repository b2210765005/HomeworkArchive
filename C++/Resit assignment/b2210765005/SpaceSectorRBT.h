#ifndef SPACESECTORRBT_H
#define SPACESECTORRBT_H

#include "Sector.h"
#include <iostream>
#include <fstream>  
#include <sstream>
#include <vector>

class SpaceSectorRBT {

private:
    std::vector<Sector*> sectors;

public:
    Sector* root;
    SpaceSectorRBT();
    ~SpaceSectorRBT();
    void readSectorsFromFile(const std::string& filename);
    void insertSectorByCoordinates(int x, int y, int z);
    void displaySectorsInOrder();
    void displaySectorsPreOrder();
    void displaySectorsPostOrder();
    std::vector<Sector*> getStellarPath(const std::string& sector_code);
    void printStellarPath(const std::vector<Sector*>& path);

    Sector *insert(Sector *sector, int x, int y, int z);

    void balance(Sector *new_sector);

    void inorder(Sector *sector);

    void preorder(Sector *sector);

    void postorder(Sector *sector);

    void left_rotate(Sector *new_sector);

    void right_rotate(Sector *new_sector);

    void left_right_rotate(Sector* new_sector);

    void right_left_rotate(Sector* new_sector);
};

#endif // SPACESECTORRBT_H
