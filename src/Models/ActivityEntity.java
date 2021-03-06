package Models;


public class ActivityEntity {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Integer steps;

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    private Integer trainingCalories;

    public Integer getTrainingCalories() {
        return trainingCalories;
    }

    public void setTrainingCalories(Integer trainingCalories) {
        this.trainingCalories = trainingCalories;
    }

    private Integer sleep;

    public Integer getSleep() {
        return sleep;
    }

    public void setSleep(Integer sleep) {
        this.sleep = sleep;
    }

    private Integer activityCalories;

    public Integer getActivityCalories() {
        return activityCalories;
    }

    public void setActivityCalories(Integer activityCalories) {
        this.activityCalories = activityCalories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityEntity that = (ActivityEntity) o;

        if (id != that.id) return false;
        if (steps != null ? !steps.equals(that.steps) : that.steps != null) return false;
        if (trainingCalories != null ? !trainingCalories.equals(that.trainingCalories) : that.trainingCalories != null)
            return false;
        if (sleep != null ? !sleep.equals(that.sleep) : that.sleep != null) return false;
        if (activityCalories != null ? !activityCalories.equals(that.activityCalories) : that.activityCalories != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (steps != null ? steps.hashCode() : 0);
        result = 31 * result + (trainingCalories != null ? trainingCalories.hashCode() : 0);
        result = 31 * result + (sleep != null ? sleep.hashCode() : 0);
        result = 31 * result + (activityCalories != null ? activityCalories.hashCode() : 0);
        return result;
    }
}
