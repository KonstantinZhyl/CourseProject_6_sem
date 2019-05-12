package Models;



public class ExerciseInfoEntity {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    private Integer caloriesForOne;

    public Integer getCaloriesForOne() {
        return caloriesForOne;
    }

    public void setCaloriesForOne(Integer caloriesForOne) {
        this.caloriesForOne = caloriesForOne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseInfoEntity that = (ExerciseInfoEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (caloriesForOne != null ? !caloriesForOne.equals(that.caloriesForOne) : that.caloriesForOne != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (caloriesForOne != null ? caloriesForOne.hashCode() : 0);
        return result;
    }
}
