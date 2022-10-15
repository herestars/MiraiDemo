package com.stars.constant;

public interface Constants {

    interface MessageType {
        int GROUP_MESSAGE = 1;
        int USER_MESSAGE = 2;
    }

    interface ResponseType {
        int Room_Existed = -1;
        int No_Reply = 0;
        int Create_Room = 1;
        int Join_Room = 2;
        int Already_Join = 3;
        int StartGame = 4;
        int Room_NoExisted = 5;
        int Room_Unenough = 6;
        int Room_Enough = 7;
    }

    interface GameVal {
        int RAND_RANGE = 100;
    }
}
