package Entity;

import javax.persistence.Entity;

@Entity
@javax.persistence.Table(name = "product", schema = "cp_6sem", catalog = "")
public class ProductEntity {
    private int id;

    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    private Integer protein;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "protein", nullable = true)
    public Integer getProtein() {
        return protein;
    }

    public void setProtein(Integer protein) {
        this.protein = protein;
    }

    private Integer fat;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "fat", nullable = true)
    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }

    private String carbons;

    @javax.persistence.Basic
    @javax.persistence.Column(name = "carbons", nullable = true, length = 45)
    public String getCarbons() {
        return carbons;
    }

    public void setCarbons(String carbons) {
        this.carbons = carbons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (calories != null ? !calories.equals(that.calories) : that.calories != null) return false;
        if (protein != null ? !protein.equals(that.protein) : that.protein != null) return false;
        if (fat != null ? !fat.equals(that.fat) : that.fat != null) return false;
        if (carbons != null ? !carbons.equals(that.carbons) : that.carbons != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (calories != null ? calories.hashCode() : 0);
        result = 31 * result + (protein != null ? protein.hashCode() : 0);
        result = 31 * result + (fat != null ? fat.hashCode() : 0);
        result = 31 * result + (carbons != null ? carbons.hashCode() : 0);
        return result;
    }
}
