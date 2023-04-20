package com.example.ase_project.analyticReport.model.data;

import com.example.ase_project.feedback.model.data.Feedback;

import java.util.ArrayList;
import java.util.List;

public record AnalyticReportAttendee(String eventID, List<Feedback> feedbacks) {
    public AnalyticReportAttendee {
        if (eventID == null) {
            throw new IllegalArgumentException("eventID can not be null");
        }
        if (feedbacks == null) {
            throw new IllegalArgumentException("feedbacks can not be null");
        }

    }

    /**
     * get the mean of overall Rating
     *
     * @return float mean
     */
    public float getMeanOverallRating() {
        float total = 0;
        for (Feedback feedback : this.feedbacks) {
            total += feedback.getRatingOverall();
        }
        return total / this.feedbacks.size();
    }

    /**
     * get the number of feedbacks that contain a comment
     *
     * @return int numberOFComments
     */
    public int getNumberOfComments() {
        int numberOfComments = 0;
        for (Feedback feedback : this.feedbacks) {
            if (!feedback.getComment().equals("")) {
                numberOfComments++;
            }
        }
        return numberOfComments;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(this.eventID);
        ret.append(": ");
        for (Feedback feedback : this.feedbacks) {
            ret.append(feedback.toString());
            ret.append(", ");
        }
        return ret.toString();
    }
}
