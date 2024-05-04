package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Song {
    private String title;
    private String artist;

    public Song(String title, String artist) {
        this.title = title;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Artist: " + artist;
    }
}


interface Iterator {
    boolean hasNext();
    Song next();
}

interface Playlist {
    Iterator createIterator();
    void addSong(Song song);
    void removeSong(Song song);
}

class PlaylistIterator implements Iterator {
    private List<Song> songs;
    private int position = 0;

    public PlaylistIterator(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean hasNext() {
        return songs.size() > 0; 
    }

    @Override
    public Song next() {
        if (position >= songs.size()) {
            position = 0;
            System.out.println(">> End of playlist. Starting from the beginning."); 
        }
        Song song = songs.get(position);
        position++;
        return song;
    }
}

class PlaylistImpl implements Playlist {
    private List<Song> songs;

    public PlaylistImpl() {
        this.songs = new ArrayList<>();
    }

    @Override
    public Iterator createIterator() {
        return new PlaylistIterator(songs);
    }

    @Override
    public void addSong(Song song) {
        songs.add(song);
    }

    @Override
    public void removeSong(Song song) {
        songs.remove(song);
    }
}

public class MusicPlayerApp {
    public static void main(String[] args) {
 
        Playlist playlist = new PlaylistImpl();

        Scanner scanner = new Scanner(System.in);
        System.out.println("========== Welcome to the Music Player App ==========");
        System.out.println("     (Enter 'done' when finished adding songs)       ");
        System.out.println("=======================================================");

        while (true) {
            System.out.print("Enter song title: ");
            String title = scanner.nextLine();
            if (title.equalsIgnoreCase("done")) {
                break;
            }
            System.out.print("Enter artist: ");
            String artist = scanner.nextLine();
            playlist.addSong(new Song(title, artist));
            System.out.println("\n\033[0;32mSong added: " + title + " by " + artist + " \033[0m\n");
        }

        Iterator iterator = playlist.createIterator(); 
        System.out.println("\n========== Music Player Controls ==========");
        System.out.println("     (Enter 'next' to play the next song)      ");
        System.out.println("       (Enter 'exit' to quit the app)         ");
        System.out.println("=============================================");

        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("exit")) {
            if (input.equalsIgnoreCase("next")) {
                if (iterator.hasNext()) {
                    Song song = iterator.next();
                    System.out.println("\n\033[0;36mNow playing: " + song.getTitle() + " by " + song.getArtist() + "\033[0m\n");
                }
            } else {
                System.out.println("\n\033[0;31mInvalid input. Please enter 'next' or 'exit'.\033[0m\n");
            }
            System.out.println();
        }

        System.out.println("\n\033[0;33mThank you for using the Music Player App!\033[0m\n");
        scanner.close();
    }
}
