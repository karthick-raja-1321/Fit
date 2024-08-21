


public class Workout {
    private int id;
    private String workout;
    private int duration;
    private int calories;

    public Workout(int id, String workout, int duration, int calories) {
        this.id = id;
        this.workout = workout;
        this.duration = duration;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkout() {
        return workout;
    }

    public void setWorkout(String workout) {
        this.workout = workout;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}