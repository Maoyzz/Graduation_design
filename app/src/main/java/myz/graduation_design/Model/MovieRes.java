package myz.graduation_design.Model;

import android.media.Rating;

import java.util.List;

/**
 * Created by 10246 on 2018/4/12.
 */

public class MovieRes {
    public String id;
    public String title;
    public String year;
    public Rating rating;
    public List<String> genres;
    private MovieImage images;
    public class MovieImage {
        private String small;
        private String medium;
        private String large;

        public String getSmall() {
            return small == null ? "" : small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium == null ? "" : medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large == null ? "" : large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }

    public class Rating {
        private int min;
        private int max;
        private float average;
        private String stars;

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
            this.average = average;
        }

        public String getStars() {
            return stars == null ? "" : stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }
    }
}
