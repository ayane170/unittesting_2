package test2.opdracht1;

import java.util.Objects;

public class Team {
    //Geef Atributten
    private String name;
    private Integer skillLevel;


    public Team(String name, Integer skillLevel) {
        this.name = name;
        this.skillLevel = skillLevel;
    }
    //Implementee equals en haschCode


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(Integer skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;

        return Double.compare(team.skillLevel, skillLevel) == 0
                && Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, skillLevel);
    }

    @Override
    public String toString() {
        return String.format("Team{name='%s', skillLevel='%s'}", name, skillLevel);
    }


}
