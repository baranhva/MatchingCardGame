package com.example.blackhorse.matchingcardgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class GameContent {

    /**
     * An array of sample (game) items.
     */
    public static final List<GameItem> ITEMS = new ArrayList<GameItem>();

    /**
     * A map of sample (game) items, by ID.
     */
    public static final Map<String, GameItem> ITEM_MAP = new HashMap<String, GameItem>();

    private static final int COUNT = 50;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createGameItem(i));
        }
    }

    private static void addItem(GameItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static GameItem createGameItem(int position) {
        return new GameItem(String.valueOf(position), "Score " + (101 - position), makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Rank: ").append(position).append(":\nName:\nImage:");
        return builder.toString();
    }

    /**
     * A game item representing a piece of content.
     */
    public static class GameItem {
        public final String id;
        public final String content;
        public final String details;

        public GameItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
