package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Notebook {
    @Id
    @GeneratedValue
    private int id;

    private String type;
    private int numberOfProcessors;

    public Notebook() {
    }

    public Notebook(String type, int numberOfProcessors) {
        this.setType(type);
        this.setNumberOfProcessors(numberOfProcessors);
    }


    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumberOfProcessors() {
        return numberOfProcessors;
    }

    public void setNumberOfProcessors(int numberOfProcessors) {
        this.numberOfProcessors = numberOfProcessors;
    }
}
