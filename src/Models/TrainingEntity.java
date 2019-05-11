package Entity;

import javax.persistence.Entity;

@Entity
@javax.persistence.Table(name = "training", schema = "cp_6sem", catalog = "")
public class TrainingEntity {
    private int id;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Integer duration;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "duration", nullable = true)
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    private Integer calories;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "calories", nullable = true)
    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingEntity that = (TrainingEntity) o;

        if (id != that.id) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (calories != null ? !calories.equals(that.calories) : that.calories != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (calories != null ? calories.hashCode() : 0);
        return result;
    }
}
