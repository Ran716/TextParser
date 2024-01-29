package community;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CommunityInfo {

    public String[][] relationshipTable = new String[10][10];

    public Map<Integer, Community> communities = new TreeMap<>();

    public int communitiesNumber = 0;

    public ArrayList<ArrayList<String>> finalCommunities = new ArrayList<>();

    public void algo(Community community) {

        int pre_number = -1;

        while (community.number > pre_number && community.number < 4) {

            pre_number = community.number;

            for (int i = 0; i < 10; i++) {

                if (community.ifname_index[i] == 0) {
                    int current_number = community.number;
                    int count1 = 0;
                    for (int k = 0; k < current_number; k++) {
                        for (int j = 1; j < 5 && count1 < current_number; j++) {
                            if (relationshipTable[i][j] == community.nameStrings.get(count1)) {
                                count1++;
                            }
                        }
                    }
                    int count2 = 0;
                    if (current_number == count1) {
                        for (int j = 0; j < community.number; j++) {
                            String name = community.nameStrings.get(j);
                            for (int k = 0; k < 10; k++) {
                                if (relationshipTable[k][0] == name) {
                                    for (int u = 1; u < 5; u++) {
                                        if (relationshipTable[k][u] == relationshipTable[i][0]) {
                                            count2++;
                                        }
                                    }
                                }
                            }
                        }
                        if (count2 == current_number) {
                            community.add(relationshipTable[i][0]);
                        }

                    }
                }

                if (community.number == 4) {
                    break;
                }
            }

        }

    }

    public Community getCommunity(int id) {
        for (Map.Entry<Integer, Community> entry : communities.entrySet()) {
            Community community = entry.getValue();
            if (community.id == id) {
                return community;
            }
        }
        return new Community(-1, null);
    }

    public void sort() {
        for (Map.Entry<Integer, Community> entry : communities.entrySet()) {
            Community community = entry.getValue();
            algo(community);

            if (community.number < 3) {
                community.id = -1;
                this.communitiesNumber--;
            }
            if (community.id >= 0) {
                finalCommunities.add(community.nameStrings);
            }
        }

    }

    public CommunityInfo(Object[][] data) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                relationshipTable[i][j] = (String) data[i][j];
            }
        }
        Community community0 = new Community(0, relationshipTable[0][0]);
        Community community1 = new Community(1, relationshipTable[1][0]);
        Community community2 = new Community(2, relationshipTable[2][0]);
        Community community3 = new Community(3, relationshipTable[3][0]);
        Community community4 = new Community(4, relationshipTable[4][0]);
        Community community5 = new Community(5, relationshipTable[5][0]);
        Community community6 = new Community(6, relationshipTable[6][0]);
        Community community7 = new Community(7, relationshipTable[7][0]);
        Community community8 = new Community(8, relationshipTable[8][0]);
        Community community9 = new Community(9, relationshipTable[9][0]);
        communities.put(community0.id, community0);
        communities.put(community1.id, community1);
        communities.put(community2.id, community2);
        communities.put(community3.id, community3);
        communities.put(community4.id, community4);
        communities.put(community5.id, community5);
        communities.put(community6.id, community6);
        communities.put(community7.id, community7);
        communities.put(community8.id, community8);
        communities.put(community9.id, community9);
        communitiesNumber = 10;
        this.sort();
    }
}