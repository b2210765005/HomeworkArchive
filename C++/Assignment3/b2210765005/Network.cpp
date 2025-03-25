#include "Network.h"
#include <iostream>
Network::Network() {

}

void Network::process_commands(vector<Client> &clients, vector<string> &commands, int message_limit,
                      const string &sender_port, const string &receiver_port) {
    // TODO: Execute the commands given as a vector of strings while utilizing the remaining arguments.
    /* Don't use any static variables, assume this method will be called over and over during testing.
     Don't forget to update the necessary member variables after processing each command. For example,
     after the MESSAGE command, the outgoing queue of the sender must have the expected frames ready to send. */
}

vector<Client> Network::read_clients(const string &filename) {
    vector<Client> clients;
    // TODO: Read clients from the given input file and return a vector of Client instances.
    std::fstream fil_input(filename);
    std::string line;
    int first_line=0;
    while(std::getline(fil_input,line)){
        if(first_line==0){
            first_line++;
            continue;
        }
        std::istringstream iss(line);
        std::string element;
        std::vector<std::string> words;

        do {
            std::string word;
            iss >> word;
            words.push_back(word);
        } while (iss);
        Client new_client(words[0],words[1],words[2]);
        clients.push_back(new_client);
        first_line++;
    }
    return clients;
}

void Network::read_routing_tables(vector <Client> &clients, const string &filename) {
    // TODO: Read the routing tables from the given input file and populate the clients' routing_table member variable.
    ifstream file(filename);
    if (file.is_open()) {
        int num_routing_entries;
        file >> num_routing_entries;

        for (int i = 0; i < num_routing_entries; ++i) {
            string dest_id, neighbor_id;
            file >> dest_id >> neighbor_id;
            // TODO: Populate the routing table for the corresponding client.
        }

        file.close();
    } else {
        cerr << "Unable to open file: " << filename << endl;
    }
}

// Returns a list of token lists for each command
vector<string> Network::read_commands(const string &filename) {
    vector<string> commands;
    // TODO: Read commands from the given input file and return them as a vector of strings.
    std::fstream file_inp(filename);
    std::string line;
    int first_line=0;
    while(std::getline(file_inp,line)){
        if(first_line==0){
            first_line++;
            continue;
        }

        commands.push_back(line);
        first_line++;
    }


    return commands;
}

Network::~Network() {
    // TODO: Free any dynamically allocated memory if necessary.
}
