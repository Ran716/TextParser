package util;

import java.util.ArrayList;
import java.util.List;

public class Person {
    public String name;
    public List<String> aliases;
    public int times;
    public double firstindex;
    public double lastindex;
    public double span;
    public List<Integer> indexList = new ArrayList<>();
    public double[][] scatterArray;
    public int matrix_index;

    public String getName() {
        return name;
    }

    public double getSpan() {
        return lastindex - firstindex;
    }

    public Person(String name, List<String> aliases, int matrix_index) {
        this.name = name;
        this.aliases = aliases;
        this.times = 0;
        this.firstindex = -1;
        this.lastindex = -1;
        this.span = -1;
        this.matrix_index = matrix_index;
    }
}
