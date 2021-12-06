package com.justinalmassi.backend.util;

import com.justinalmassi.backend.model.Post;

import java.util.Comparator;

public enum PostComparator implements Comparator<Post> {
    ID {
        public int compare(Post o1, Post o2) {
            return o1.getId() < o2.getId() ? 1 : -1;
        }
    },
    LIKES {
        public int compare(Post o1, Post o2) {
            return o1.getLikes() < o2.getLikes() ? 1 : -1;
        }
    },
    READS{
        public int compare(Post o1, Post o2) {
            return o1.getReads() < o2.getReads() ? 1 : -1;
        }
    },
    POPULARITY_SORT {
        public int compare(Post o1, Post o2) {
            return o1.getPopularity() < o2.getPopularity() ? 1 : -1;
        }
    };

    public static Comparator<Post> getComparator(final Comparator<Post> option) {
        return new Comparator<Post>() {
            public int compare(Post o1, Post o2) {
                return option.compare(o1, o2);
            }
        };
    }

    public static Comparator<Post> descending(Comparator<Post> comparator) {
        return new Comparator<Post>() {
            public int compare(Post o1, Post o2) {
                return -1 * comparator.compare(o1, o2);
            }
        };
    }
}
