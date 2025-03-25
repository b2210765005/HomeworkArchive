#include <valarray>
#include "SpaceSectorRBT.h"

using namespace std;

SpaceSectorRBT::SpaceSectorRBT() : root(nullptr) {}

void SpaceSectorRBT::readSectorsFromFile(const std::string& filename) {
    // TODO: read the sectors from the input file and insert them into the RBT sector map
    // according to the given comparison critera based on the sector coordinates.
    ifstream file(filename);

    if (file.is_open()) {
        string line;
        bool first_line = true;

        while (getline(file, line)) {
            if (!first_line) {
                vector<int> numbers;
                stringstream iss(line);

                string number;
                while (getline(iss, number, ',')) {
                    numbers.push_back(stoi(number));
                }
                insertSectorByCoordinates(numbers[0], numbers[1], numbers[2]);
            }
            first_line = false;
        }
        file.close();
    }
}

// Remember to handle memory deallocation properly in the destructor.
SpaceSectorRBT::~SpaceSectorRBT() {
    // TODO: Free any dynamically allocated memory in this class.

    for (Sector* sector : sectors) {
        delete sector;
    }
}

void SpaceSectorRBT::insertSectorByCoordinates(int x, int y, int z) {
    // TODO: Instantiate and insert a new sector into the space sector RBT map
    // according to the coordinates-based comparison criteria.

    root = insert(root, x, y, z);
    if (root != nullptr) balance(sectors.back());
}

void SpaceSectorRBT::balance(Sector* new_sector) {
    if (new_sector->parent == nullptr) {
        return;
    }

    if (new_sector->parent->color) {
        if (new_sector->parent->parent != nullptr) {
            Sector* aunt;

            aunt = new_sector->parent->parent->right == new_sector->parent ? new_sector->parent->parent->left : new_sector->parent->parent->right;

            if (aunt == nullptr || !aunt->color) {
                bool is_left_child = new_sector->parent->left == new_sector;
                bool is_left_descendant = new_sector->parent->parent->left == new_sector->parent;

                if (is_left_child && is_left_descendant) {
                    right_rotate(new_sector);
                } else if (!is_left_child && !is_left_descendant) {
                    left_rotate(new_sector);
                } else if (is_left_child && !is_left_descendant) {
                    right_left_rotate(new_sector);
                } else {
                    left_right_rotate(new_sector);
                }

            } else {
                aunt->color = BLACK;
                new_sector->parent->color = BLACK;
                new_sector->parent->parent->color = RED;
                root->color = BLACK;
                balance(new_sector->parent->parent);
            }
        }
    }

}

void SpaceSectorRBT::right_left_rotate(Sector* new_sector) {
    Sector* parent = new_sector->parent;
    Sector* grandparent = new_sector->parent->parent;
    Sector* grand_grandparent = new_sector->parent->parent->parent;
    Sector* left_child = new_sector->left;
    Sector* right_child = new_sector->right;

    new_sector->color = BLACK;
    grandparent->color = RED;

    new_sector->parent = grand_grandparent;
    new_sector->left = grandparent;
    new_sector->right = parent;
    parent->parent = new_sector;
    parent->left = right_child;
    grandparent->parent = new_sector;
    grandparent->right = left_child;
    if (grand_grandparent != nullptr) {
        if(grand_grandparent->left == grandparent) {
            grand_grandparent->left = new_sector;
        } else {
            grand_grandparent->right = new_sector;
        }
    }
    if (left_child != nullptr) {
        left_child->parent = grandparent;
    }
    if (right_child != nullptr) {
        right_child->parent = parent;
    }

    if(root == grandparent) {
        root = new_sector;
    }

}

void SpaceSectorRBT::left_right_rotate(Sector* new_sector) {
    Sector* parent = new_sector->parent;
    Sector* grandparent = new_sector->parent->parent;
    Sector* grand_grandparent = new_sector->parent->parent->parent;
    Sector* left_child = new_sector->left;
    Sector* right_child = new_sector->right;

    new_sector->color = BLACK;
    grandparent->color = RED;

    new_sector->parent = grand_grandparent;
    new_sector->left = parent;
    new_sector->right = grandparent;
    parent->parent = new_sector;
    parent->right = left_child;
    grandparent->parent = new_sector;
    grandparent->left = right_child;
    if (grand_grandparent != nullptr) {
        if(grand_grandparent->left == grandparent) {
            grand_grandparent->left = new_sector;
        } else {
            grand_grandparent->right = new_sector;
        }
    }
    if (left_child != nullptr) {
        left_child->parent = parent;
    }
    if (right_child != nullptr) {
        right_child->parent = grandparent;
    }

    if(root == grandparent) {
        root = new_sector;
    }

}

void SpaceSectorRBT::left_rotate(Sector* new_sector) {
    new_sector->parent->color = BLACK;
    new_sector->parent->parent->color = RED;

    if(new_sector->parent->parent->parent != nullptr) {
        if (new_sector->parent->parent->parent->right == new_sector->parent->parent) {
            new_sector->parent->parent->parent->right = new_sector->parent;
        } else {
            new_sector->parent->parent->parent->left = new_sector->parent;
        }
    }

    if (new_sector->parent->left != nullptr) {
        new_sector->parent->left->parent = new_sector->parent->parent;
    }

    new_sector->parent->parent->right = new_sector->parent->left;
    new_sector->parent->left = new_sector->parent->parent;
    new_sector->parent->parent = new_sector->parent->parent->parent;
    new_sector->parent->left->parent = new_sector->parent;

    if (new_sector->parent->parent == nullptr) root = new_sector->parent;
}

void SpaceSectorRBT::right_rotate(Sector* new_sector) {
    new_sector->parent->color = BLACK;
    new_sector->parent->parent->color = RED;

    if(new_sector->parent->parent->parent != nullptr) {
        if (new_sector->parent->parent->parent->right == new_sector->parent->parent) {
            new_sector->parent->parent->parent->right = new_sector->parent;
        } else {
            new_sector->parent->parent->parent->left = new_sector->parent;
        }
    }

    if (new_sector->parent->right != nullptr) {
        new_sector->parent->right->parent = new_sector->parent->parent;
    }

    new_sector->parent->parent->left = new_sector->parent->right;
    new_sector->parent->right = new_sector->parent->parent;
    new_sector->parent->parent = new_sector->parent->parent->parent;
    new_sector->parent->right->parent = new_sector->parent;

    if (new_sector->parent->parent == nullptr) root = new_sector->parent;
}

Sector *SpaceSectorRBT::insert(Sector *parent, int x, int y, int z) {
    if (parent == nullptr) {
        auto* new_sector = new Sector(x,y,z);
        if(root == nullptr) {
            new_sector->color = BLACK;
        }
        new_sector->distance_from_earth = sqrt(pow(x, 2) + pow(y, 2) + pow(z, 2));
        new_sector->sector_code += to_string(static_cast<int>(std::floor(new_sector->distance_from_earth)));

        new_sector->sector_code += new_sector->x == 0 ? "S" : new_sector->x > 0 ? "R" : "L";
        new_sector->sector_code += new_sector->y == 0 ? "S" : new_sector->y > 0 ? "U" : "D";
        new_sector->sector_code += new_sector->z == 0 ? "S" : new_sector->z > 0 ? "F" : "B";

        sectors.push_back(new_sector);
        return new_sector;
    } else if (x > parent->x) {
        parent->right = insert(parent->right, x, y, z);
        parent->right->parent = parent;
    } else if (x < parent->x) {
        parent->left = insert(parent->left, x, y, z);
        parent->left->parent = parent;
    } else if (y > parent->y) {
        parent->right = insert(parent->right, x, y, z);
        parent->right->parent = parent;
    } else if (y < parent->y) {
        parent->left = insert(parent->left, x, y, z);
        parent->left->parent = parent;
    } else if (z > parent->z) {
        parent->right = insert(parent->right, x, y, z);
        parent->right->parent = parent;
    } else if (z < parent->z) {
        parent->left = insert(parent->left, x, y, z);
        parent->left->parent = parent;
    }

    return parent;
}

void SpaceSectorRBT::displaySectorsInOrder() {
    // TODO: Traverse the space sector RBT map in-order and print the sectors
    // to STDOUT in the given format.

    cout << "Space sectors inorder traversal:" << endl;
    inorder(root);
    cout << endl;
}

void SpaceSectorRBT::inorder(Sector* sector){
    if (sector!= nullptr){
        inorder(sector->left);
        string color = sector->color ? "RED" : "BLACK";
        cout<<color<<" sector: ";
        cout<<sector->sector_code<<endl;
        inorder(sector->right);
    }
}

void SpaceSectorRBT::displaySectorsPreOrder() {
    // TODO: Traverse the space sector RBT map in pre-order traversal and print
    // the sectors to STDOUT in the given format.

    cout << "Space sectors preorder traversal:" << endl;
    preorder(root);
    cout << endl;
}

void SpaceSectorRBT::preorder(Sector *sector) {
    if (sector == nullptr) return;
    cout << (sector->color ? "RED" : "BLACK") << " sector: " << sector->sector_code << endl;
    preorder(sector->left);
    preorder(sector->right);
}

void SpaceSectorRBT::displaySectorsPostOrder() {
    // TODO: Traverse the space sector RBT map in post-order traversal and print
    // the sectors to STDOUT in the given format.

    cout << "Space sectors postorder traversal:" << endl;
    postorder(root);
    cout << endl;
}

void SpaceSectorRBT::postorder(Sector *sector) {
    if (sector == nullptr) return;
    postorder(sector->left);
    postorder(sector->right);
    cout << (sector->color ? "RED" : "BLACK") << " sector: " << sector->sector_code << endl;
}

std::vector<Sector*> SpaceSectorRBT::getStellarPath(const std::string& sector_code) {
    std::vector<Sector*> path;
    // TODO: Find the path from the Earth to the destination sector given by its
    // sector_code, and return a vector of pointers to the Sector nodes that are on
    // the path. Make sure that there are no duplicate Sector nodes in the path!

    vector<Sector *> path_of_world;
    vector<Sector *> path_of_destination;

    Sector* world = nullptr;

    for (Sector *sector: sectors) {
        if (sector->sector_code == "0SSS") {
            world = sector;
        }
    }


    while (world != nullptr) {
        path_of_world.push_back(world);
        world = world->parent;
    }

    Sector* destination = nullptr;

    for (Sector *sector: sectors) {
        if (sector->sector_code == sector_code) {
            destination = sector;
        }
    }

    while (destination != nullptr) {
        path_of_destination.push_back(destination);
        destination = destination->parent;
    }

    if (path_of_destination.empty() || path_of_world.empty()) {
        return path;
    }
    reverse(path_of_destination.begin(), path_of_destination.end());

    for (int i = 0; i < path_of_world.size(); i++) {
        for (int j = 0; j < path_of_destination.size(); j++) {
            if (path_of_world[i]->sector_code == path_of_destination[j]->sector_code) {
                path = vector<Sector *>(path_of_world.begin(), path_of_world.begin() + i);
                vector<Sector *> temp(path_of_destination.begin() + j, path_of_destination.end());
                path.insert(path.end(), temp.begin(), temp.end());
                return path;
            }
        }
    }

    return path;
}

void SpaceSectorRBT::printStellarPath(const std::vector<Sector*>& path) {
    // TODO: Print the stellar path obtained from the getStellarPath() function
    // to STDOUT in the given format.

    if (path.empty()) {
        cout << "A path to Dr. Elara could not be found." << endl;
    } else {
        cout << "The stellar path to Dr. Elara: ";
        for (int i = 0; i < path.size(); i++) {
            cout << path[i]->sector_code << ((i == path.size() - 1) ? "\n" : "->");
        }
    }
}