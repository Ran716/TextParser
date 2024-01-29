package util;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class People {
    public Map<String, Person> people = new TreeMap<>();
    public int novel_length;
    public int[][] matrix = new int[10][10];
    public int[][] matrix_SortIndex = new int[10][10];

    public Person getPerson(int matrix_index) {
        for (Map.Entry<String, Person> entry : people.entrySet()) {
            Person person = entry.getValue();
            if (person.matrix_index == matrix_index) {
                return person;
            }
        }
        Person nullPerson = new Person(null, null, -1);
        return nullPerson;
    }

    public Person getPerson(String nameString) {
        for (Map.Entry<String, Person> entry : people.entrySet()) {
            Person person = entry.getValue();
            if (person.getName() == nameString) {
                return person;
            }
        }
        Person nullPerson = new Person(null, null, -1);
        return nullPerson;
    }

    public void closedRelationshipCount() {
        for (int i = 0; i < 10; i++) {
            Person person1 = this.getPerson(i);
            for (int j = i + 1; j < 10; j++) {
                Person person2 = this.getPerson(j);
                int p1 = 0;
                int p2 = 0;
                int count = 0;

                while (p1 < person1.indexList.size() && p2 < person2.indexList.size()) {
                    int distance = Math.abs(person1.indexList.get(p1) - person2.indexList.get(p2));
                    if (distance <= 100) {
                        count++;
                        p1++;
                        p2++;
                    } else if (person1.indexList.get(p1) < person2.indexList.get(p2)) {
                        p1++;
                    } else {
                        p2++;
                    }
                }
                this.matrix[i][j] = count;
                this.matrix[j][i] = count;

            }
            this.matrix[i][i] = 0;
        }

    }

    public void getClosedRelationship() {
        this.closedRelationshipCount();
        for (int i = 0; i < matrix.length; i++) {
            // 保存每个值对应的原始下标
            int[] tempIndexes = new int[matrix[i].length];
            for (int j = 0; j < matrix[i].length; j++) {
                tempIndexes[j] = j;
            }
            for (int j = 0; j < matrix[i].length - 1; j++) {
                for (int k = 0; k < matrix[i].length - j - 1; k++) {
                    if (matrix[i][k] < matrix[i][k + 1]) {
                        int temp = matrix[i][k];
                        matrix[i][k] = matrix[i][k + 1];
                        matrix[i][k + 1] = temp;
                        // 交换下标
                        int tempIndex = tempIndexes[k];
                        tempIndexes[k] = tempIndexes[k + 1];
                        tempIndexes[k + 1] = tempIndex;
                    }
                }
            }
            // 排序后的结果对应的原始下标
            for (int j = 0; j < matrix[i].length; j++) {
                matrix_SortIndex[i][j] = tempIndexes[j];
            }

        }

    }

    public People() {
        List<String> zhangfeiAliases = new ArrayList<>();
        List<String> liubeiAliases = new ArrayList<>();
        List<String> guanyuAliases = new ArrayList<>();
        List<String> caocaoAliases = new ArrayList<>();
        List<String> zhugeliangAliases = new ArrayList<>();
        List<String> zhouyuAliases = new ArrayList<>();
        List<String> sunquanAliases = new ArrayList<>();
        List<String> simayiAliases = new ArrayList<>();
        List<String> zhaoyunAliases = new ArrayList<>();
        List<String> lvbuAliases = new ArrayList<>();

        zhangfeiAliases.add("翼德");
        Person zhangfei = new Person("张飞", zhangfeiAliases, 4);
        people.put(zhangfei.getName(), zhangfei);

        liubeiAliases.add("玄德");
        liubeiAliases.add("刘皇叔");
        liubeiAliases.add("汉中王");
        liubeiAliases.add("大耳贼");
        liubeiAliases.add("刘豫州");
        liubeiAliases.add("昭烈皇帝");
        Person liubei = new Person("刘备", liubeiAliases, 0);
        people.put(liubei.getName(), liubei);

        guanyuAliases.add("云长");
        guanyuAliases.add("美髯公");
        guanyuAliases.add("关公");
        guanyuAliases.add("汉寿亭侯");

        Person guanyu = new Person("关羽", guanyuAliases, 3);
        people.put(guanyu.getName(), guanyu);

        caocaoAliases.add("孟德");
        caocaoAliases.add("阿瞒");
        caocaoAliases.add("吉利");
        caocaoAliases.add("魏王");
        Person caocao = new Person("曹操", caocaoAliases, 1);
        people.put(caocao.getName(), caocao);

        zhugeliangAliases.add("孔明");
        zhugeliangAliases.add("卧龙");
        zhugeliangAliases.add("忠武侯");
        zhugeliangAliases.add("武乡侯");
        Person zhugeliang = new Person("诸葛亮", zhugeliangAliases, 2);
        people.put(zhugeliang.getName(), zhugeliang);

        zhouyuAliases.add("公瑾");
        zhouyuAliases.add("周郎");
        Person zhouyu = new Person("周瑜", zhouyuAliases, 6);

        people.put(zhouyu.getName(), zhouyu);

        sunquanAliases.add("仲谋");
        Person sunquan = new Person("孙权", sunquanAliases, 5);
        people.put(sunquan.getName(), sunquan);

        simayiAliases.add("仲达");
        Person simayi = new Person("司马懿", simayiAliases, 7);

        people.put(simayi.getName(), simayi);

        zhaoyunAliases.add("子龙");
        zhaoyunAliases.add("长胜将军");
        Person zhaoyun = new Person("赵云", zhaoyunAliases, 8);
        people.put(zhaoyun.getName(), zhaoyun);

        lvbuAliases.add("奉先");
        lvbuAliases.add("飞将");
        Person lvbu = new Person("吕布", lvbuAliases, 9);
        people.put(lvbu.getName(), lvbu);
    }
}
