package community;

import java.util.ArrayList;
import java.util.Arrays;
import util.*;

public class Community {
    public int id;
    public int number;
    public ArrayList<String> nameStrings = new ArrayList<String>();
    public People pl = new People();
    public int[] ifname_index = new int[10];

    public void add(String newname) {

        this.nameStrings.add(newname);
        Person person = pl.getPerson(newname);
        this.ifname_index[person.matrix_index] = 1;
        this.number++;
    }

    public Community(int id, String name) {
        this.id = id;
        this.nameStrings.add(name);
        this.number = 1;
        Arrays.fill(ifname_index, 0);

        Person person = pl.getPerson(name);
        ifname_index[person.matrix_index] = 1;
    }
}
