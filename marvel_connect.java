import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map;

public class A4_2019CS10490 {
    private static HashMap<String, LinkedList<String[]>> characterGraphConnectivty = new HashMap<String, LinkedList<String[]>>();
    private static HashMap<String, Integer> totalCoOccurences = new HashMap<String, Integer>();
    private static ArrayList<String> storyComponent = new ArrayList<String>();
    private static HashMap<String, Boolean> visitedChar = new HashMap<String, Boolean>();
    private static ArrayList<ArrayList<String>> allStories = new ArrayList<ArrayList<String>>();

    public static void mergeSort(ArrayList<String> characters) {
        if (characters.size() < 2) {
            return;
        } else {
            ArrayList<String> leftHalf = new ArrayList<String>(characters.subList(0, characters.size() / 2));
            ArrayList<String> rightHalf = new ArrayList<String>(
                    characters.subList(characters.size() / 2, characters.size()));
            mergeSort(leftHalf);
            mergeSort(rightHalf);
            merge(leftHalf, rightHalf, characters);
        }
    }

    public static void merge(ArrayList<String> leftHalf, ArrayList<String> rightHalf, ArrayList<String> characters) {
        int i = 0;
        int j = 0;
        int p = 0;
        while (i < leftHalf.size() && j < rightHalf.size()) {
            if (!(totalCoOccurences.containsKey(leftHalf.get(i)))) {
                if (!(totalCoOccurences.containsKey(rightHalf.get(j)))) {
                    if (leftHalf.get(i).compareTo(rightHalf.get(j)) > 0) {
                        characters.set(p, leftHalf.get(i));
                        i++;
                    } else {
                        characters.set(p, rightHalf.get(j));
                        j++;
                    }
                } else {
                    characters.set(p, rightHalf.get(j));
                    j++;
                }
            } else {
                if (!(totalCoOccurences.containsKey(rightHalf.get(j)))) {
                    characters.set(p, leftHalf.get(i));
                    i++;
                } else {
                    if (totalCoOccurences.get(leftHalf.get(i)) > totalCoOccurences.get(rightHalf.get(j))) {
                        characters.set(p, leftHalf.get(i));
                        i++;
                    } else if (totalCoOccurences.get(leftHalf.get(i)) < totalCoOccurences.get(rightHalf.get(j))) {
                        characters.set(p, rightHalf.get(j));
                        j++;
                    } else {
                        if (leftHalf.get(i).compareTo(rightHalf.get(j)) > 0) {
                            characters.set(p, leftHalf.get(i));
                            i++;
                        } else {
                            characters.set(p, rightHalf.get(j));
                            j++;
                        }
                    }
                }
            }
            p++;
        }
        while (i < leftHalf.size()) {
            characters.set(p, leftHalf.get(i));
            i++;
            p++;
        }
        while (j < rightHalf.size()) {
            characters.set(p, rightHalf.get(j));
            j++;
            p++;
        }

    }

    private static String rank() throws Exception {
        ArrayList<String> characters = new ArrayList<String>();
        for (String characterName : characterGraphConnectivty.keySet()) {
            characters.add(characterName);
        }
        mergeSort(characters);
        String res;
        return String.join(",", characters);
    }

    private static String average() throws Exception {
        int countEdge = 0;
        for (String characterKey : characterGraphConnectivty.keySet()) {
            countEdge += characterGraphConnectivty.get(characterKey).size();
        }
        int numCharacters = characterGraphConnectivty.keySet().size();
        return String.format("%.2f", (float) countEdge / numCharacters);
    }

    private static void sortStory(ArrayList<String> storyComp) {
        if (storyComp.size() < 2) {
            return;
        } else {
            ArrayList<String> leftHalf = new ArrayList<String>(storyComp.subList(0, storyComp.size() / 2));
            ArrayList<String> rightHalf = new ArrayList<String>(
                    storyComp.subList(storyComp.size() / 2, storyComp.size()));
            sortStory(leftHalf);
            sortStory(rightHalf);
            mergeStory(leftHalf, rightHalf, storyComp);
        }
    }

    public static void mergeStory(ArrayList<String> leftHalf, ArrayList<String> rightHalf,
            ArrayList<String> storyComp) {
        int i = 0;
        int j = 0;
        int p = 0;
        while (i < leftHalf.size() && j < rightHalf.size()) {
            if (leftHalf.get(i).compareTo(rightHalf.get(j)) > 0) {
                storyComp.set(p, leftHalf.get(i));
                i++;
            } else {
                storyComp.set(p, rightHalf.get(j));
                j++;
            }
            p++;
        }
        while (i < leftHalf.size()) {
            storyComp.set(p, leftHalf.get(i));
            i++;
            p++;
        }
        while (j < rightHalf.size()) {
            storyComp.set(p, rightHalf.get(j));
            j++;
            p++;
        }
    }

    private static void sortCompnents(ArrayList<ArrayList<String>> allStories) {
        if (allStories.size() < 2) {
            return;
        } else {
            ArrayList<ArrayList<String>> leftHalf = new ArrayList<ArrayList<String>>(
                    allStories.subList(0, allStories.size() / 2));
            ArrayList<ArrayList<String>> rightHalf = new ArrayList<ArrayList<String>>(
                    allStories.subList(allStories.size() / 2, allStories.size()));
            sortCompnents(leftHalf);
            sortCompnents(rightHalf);
            mergeComponents(leftHalf, rightHalf, allStories);
        }
    }

    private static void mergeComponents(ArrayList<ArrayList<String>> leftHalf, ArrayList<ArrayList<String>> rightHalf,
            ArrayList<ArrayList<String>> allStories) {

        int i = 0;
        int j = 0;
        int p = 0;
        while (i < leftHalf.size() && j < rightHalf.size()) {
            if (leftHalf.get(i).size() > rightHalf.get(j).size()) {
                allStories.set(p, leftHalf.get(i));
                i++;
            } else if (leftHalf.get(i).size() < rightHalf.get(j).size()) {
                allStories.set(p, rightHalf.get(j));
                j++;
            } else {
                String char1 = leftHalf.get(i).get(0);
                String char2 = rightHalf.get(j).get(0);
                if (char1.compareTo(char2) > 0) {
                    allStories.set(p, leftHalf.get(i));
                    i++;
                } else {
                    allStories.set(p, rightHalf.get(j));
                    j++;
                }
            }
            p++;
        }
        while (i < leftHalf.size()) {
            allStories.set(p, leftHalf.get(i));
            i++;
            p++;
        }
        while (j < rightHalf.size()) {
            allStories.set(p, rightHalf.get(j));
            j++;
            p++;
        }
    }

    private static void DFS(String myChar) throws Exception {
        try {
            visitedChar.put(myChar, true);
        } catch (Exception e) {
        }
        storyComponent.add(myChar);
        
        for (String[] data : characterGraphConnectivty.get(myChar)) {
            String charNeighbour = data[0];

            if (!visitedChar.get(charNeighbour)) {                
                DFS(charNeighbour);
            }
        }
    }

    public static void independent_storylines_dfs() throws Exception {
        for (String characterName : characterGraphConnectivty.keySet()) {
            visitedChar.put(characterName, false);
        }
        int i = 0;
        for (String myChar : visitedChar.keySet()) {
            storyComponent = new ArrayList<String>();
            if (!visitedChar.get(myChar)) {

                DFS(myChar);
                sortStory(storyComponent);
                allStories.add(storyComponent);
            }

        }

        sortCompnents(allStories);
        for (ArrayList<String> componet : allStories) {
            int k = 0;
            for (String character : componet) {
                if (k == componet.size() - 1) {
                    System.out.print(character);
                } else {
                    System.out.print(character + ",");
                }
                k++;
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws Exception {
        String NodesFile = args[0];
        String EdgeFile = args[1];
        BufferedReader br = new BufferedReader(new FileReader(EdgeFile));
        br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            String[] csvData = new String[3];
            if (line.charAt(0) == '\"') {
                int index0 = -1;
                int index1 = -1;
                int index2 = -1;
                int index3 = -1;
                int indexCount = 0;
                for (char c : line.toCharArray()) {
                    if (c == '\"') {
                        if (index0 == -1) {
                            index0 = indexCount;
                        } else if (index1 == -1) {
                            index1 = indexCount;
                        } else if (index2 == -1) {
                            index2 = indexCount;
                        } else {
                            index3 = indexCount;
                            break;
                        }
                    }
                    indexCount++;
                }
                if (index2 == -1 && index3 == -1) {
                    csvData[0] = line.substring(1, index1);
                    csvData[1] = line.substring(index1 + 2, line.length()).split(",")[0];
                    csvData[2] = line.substring(index1 + 2, line.length()).split(",")[1];
                } else {
                    csvData[0] = line.substring(1, index1);
                    csvData[1] = line.substring(index2 + 1, index3);
                    csvData[2] = line.substring(index3 + 2, line.length());
                }
            } else {
                int index1 = -1;
                int index2 = -1;
                int indexCount = 0;
                for (char c : line.toCharArray()) {
                    if (c == '\"') {
                        if (index1 == -1) {
                            index1 = indexCount;
                        } else {
                            index2 = indexCount;
                            break;
                        }
                    }
                    indexCount++;
                }
                if (index1 == -1 && index2 == -1) {
                    csvData = line.split(",");
                } else {
                    csvData[0] = line.substring(0, index1 - 1);
                    csvData[1] = line.substring(index1 + 1, index2);
                    csvData[2] = line.substring(index2 + 2, line.length());
                }
            }
            if (characterGraphConnectivty.containsKey(csvData[0])) {
                String[] edge = { csvData[1], csvData[2] };
                LinkedList<String[]> ll = characterGraphConnectivty.get(csvData[0]);
                ll.add(edge);
                characterGraphConnectivty.put(csvData[0], ll);
                totalCoOccurences.put(csvData[0], totalCoOccurences.get(csvData[0]) + Integer.valueOf(csvData[2]));
            } else {
                String[] edge = { csvData[1], csvData[2] };
                LinkedList<String[]> ll = new LinkedList<String[]>();
                ll.add(edge);
                characterGraphConnectivty.put(csvData[0], ll);
                totalCoOccurences.put(csvData[0], Integer.valueOf(csvData[2]));
            }
            if (characterGraphConnectivty.containsKey(csvData[1])) {
                String[] edge = { csvData[0], csvData[2] };
                LinkedList<String[]> ll = characterGraphConnectivty.get(csvData[1]);
                ll.add(edge);
                characterGraphConnectivty.put(csvData[1], ll);
                totalCoOccurences.put(csvData[1], totalCoOccurences.get(csvData[1]) + Integer.valueOf(csvData[2]));
            } else {
                String[] edge = { csvData[0], csvData[2] };
                LinkedList<String[]> ll = new LinkedList<String[]>();
                ll.add(edge);
                characterGraphConnectivty.put(csvData[1], ll);
                totalCoOccurences.put(csvData[1], Integer.valueOf(csvData[2]));
            }
        }
        br.close();

        BufferedReader br2 = new BufferedReader(new FileReader(NodesFile));
        br2.readLine();
        line = "";
        while ((line = br2.readLine()) != null) {
            String[] csvData = new String[2];
            if (line.charAt(0) == '\"') {
                int index0 = -1;
                int index1 = -1;
                int indexCount = 0;
                for (char c : line.toCharArray()) {
                    if (c == '\"') {
                        if (index0 == -1) {
                            index0 = indexCount;
                        } else if (index1 == -1) {
                            index1 = indexCount;
                            break;
                        }
                    }
                    indexCount++;
                }
                csvData[0] = line.substring(1, index1);
            } else {
                csvData = line.split(",");
            }
            if (!(characterGraphConnectivty.containsKey(csvData[0]))) {
                LinkedList<String[]> ll = new LinkedList<String[]>();
                characterGraphConnectivty.put(csvData[0], ll);
            }
        }
        br2.close();
        if (args[2].equals("average")) {
            System.out.println(average());
        } else if (args[2].equals("rank")) {
            System.out.println(rank());
        } else if (args[2].equals("independent_storylines_dfs")) {
            independent_storylines_dfs();
        }
    }
}
