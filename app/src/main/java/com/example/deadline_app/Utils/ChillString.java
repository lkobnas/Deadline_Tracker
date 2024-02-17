//#COMP 4521   Name: LAM, San Bok   SID:20597932       email:sblam
package com.example.deadline_app.Utils;

import com.example.deadline_app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChillString {

    String[] chillStringArray = new String[]{
            "Listen to music",
            "Practice some yoga",
            "Meditate",
            "Paint your imagination",
            "Go for a walk",
            "Watch the sunrise",
            "Take a hot shower",
            "Play a board game",
            "Watch an anime",
            "Watch a movie",
            "Go for a swim",
            "Get a massage",
            "Spend time with a pet",
            "Take a bath",
            "Learn to play music",
            "Read a book",
            "Hug a Tree",
            "Sit on a swing",
            "Go shopping",
            "Try a new cuisine",
            "Go camping",
            "Sit around a campfire",
            "Go fishing",
            "Go hiking",
            "Take a nap",
            "Go to a beach",
            "Go swimming",
            "Visit a temple",
            "Visit an aquarium",
            "Listen to the birds",
            "Clean your room",
            "Try cycling",
            "Rent a comedy movie",
            "Watch trees",
            "Go to bed",
            "Drink a cup of tea",
            "Read a comic"

    };

    public String getChillString(){
        int random = new Random().nextInt(chillStringArray.length);
        return chillStringArray[random];
    }
    public int getNumberOfString(){
        return chillStringArray.length;
    }
}
