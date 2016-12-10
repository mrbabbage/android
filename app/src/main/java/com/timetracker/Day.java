package com.timetracker;

// A class which contains data pertaining to user activity on a single day.

class Day {

    private final String date;
    private int totalHours;
    private int dining;
    private int exercise;
    private int lecture;
    private int leisure;
    private int study;
    private int work;

    public Day(String date) {
        this.date = date;
    }

    public String getMax() {
        int max = Math.max(dining, exercise);
        max = Math.max(max, lecture);
        max = Math.max(max, leisure);
        max = Math.max(max, study);
        max = Math.max(max, work);
        if (max == dining) {
            return "dining";
        } else if (max == exercise) {
            return "exercise";
        } else if (max == lecture) {
            return "lecture";
        } else if (max == leisure) {
            return "leisure";
        } else if (max == study) {
            return "study";
        } else if (max == work) {
            return "work";
        } else {
            return "leisure";
        }
    }

    public String getDate() {
        return date;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public int getDining() {
        return dining;
    }

    public int getExercise() {
        return exercise;
    }

    public int getLecture() {
        return lecture;
    }

    public int getLeisure() {
        return leisure;
    }

    public int getStudy() {
        return study;
    }

    public int getWork() {
        return work;
    }

    public void addDining(int hours) {
        dining += hours;
        totalHours += hours;
    }

    public void addExercise(int hours) {
        if ((totalHours+hours)<24) {
            exercise += hours;
            totalHours += hours;
        }
    }

    public void addLecture(int hours) {
        if ((totalHours+hours)<24) {
            lecture += hours;
            totalHours += hours;
        }
    }

    public void addLeisure(int hours) {
        if ((totalHours+hours)<24) {
            leisure += hours;
            totalHours += hours;
        }
    }

    public void addStudy(int hours) {
        if ((totalHours+hours)<24) {
            study += hours;
            totalHours += hours;
        }
    }

    public void addWork(int hours) {
        if ((totalHours+hours)<24) {
            work += hours;
            totalHours += hours;
        }
    }
}
