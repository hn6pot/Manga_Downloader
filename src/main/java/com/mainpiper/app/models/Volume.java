package com.mainpiper.app.models;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Christian Kula
 * @date 22/07/2016.
 */
public class Volume {
    private String name;

    private String number;
    private Set<Chapter> chapters;

    public Volume(String name, String number) {
        this.name = name;
        this.number = number;
        this.chapters = new TreeSet<Chapter>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Volume volume = (Volume) o;

        if (name != null ? !name.equals(volume.name) : volume.name != null) {
            return false;
        }

        return number != null ? number.equals(volume.number) : volume.number == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}
