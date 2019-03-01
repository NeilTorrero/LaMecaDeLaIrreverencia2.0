package Greedy_BackTracking.Dist;

import Greedy.GreedyDist.Greedy;
import Greedy.GreedyDist.GreedyDist;
import Json.Nodes;
import Json.Server;
import Json.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

// TODO: 2019-03-01 No devuelve bien la informacion, el path, ni la mejor solucion

public class BackDist {
    public static void main(String[] args) throws FileNotFoundException {
        Gson gson = new GsonBuilder().create();
        User[] users = gson.fromJson(new FileReader("Datasets/users.json"), User[].class);
        Nodes[] nodes = gson.fromJson(new FileReader("Datasets/nodes.json"), Nodes[].class);
        Server[] servers = gson.fromJson(new FileReader("Datasets/servers.json"), Server[].class);
        for (int c = 0; c < users.length; c++) {
            users[c].setId(c);
            users[c].setUbication();
        }

        ArrayList<User> usersList= new ArrayList<User>(Arrays.asList(users));
        int from = 1;
        int to = 4;
        GreedyDist greedy = new GreedyDist(nodes, from, to);
        Greedy.greedy(from -1, to, greedy);
        for (int i = 0; i < nodes.length; i++){
            nodes[i].clearSelected();
        }
        BackDist bd = new BackDist(nodes,from,to, greedy);
        BacktrackingDist.backTracking(from-1,0,bd);
        System.out.println(bd.getBest());
        System.out.println(bd.winPath);
    }
    private Nodes[] nodes;
    private int from;
    private int to;
    private int best;
    public ArrayList<Integer> pathTemp = new ArrayList<Integer>();
    public ArrayList winPath;
    public BackDist(Nodes[] nodes, int from, int to, GreedyDist greedy) {
        this.nodes = nodes;
        this.from = from;
        this.to = to;
        nodes[from-1].setSelected();
        pathTemp.add(from);
        this.best = greedy.getBest();
    }


    public boolean bt(int i,int sum) {
        return nodes[i].getId() == to;
    }

    public void handleSolution(int i,int best) {
        nodes[i].setSelected();
        winPath = (ArrayList)pathTemp.clone();
        this.best = best;
    }

    public int getEndOptions(int i) {
        return nodes[i].getLenghtOfConexions();
    }

    public boolean promising(int sum,int i, int x) {
        return sum + nodes[i].getConnectsTo(x).getCost() < best && nodes[nodes[i].getConnectsTo(x).getTo() - 1].getSelected() != 1;
    }

    public void set(int i,int x) {
        nodes[nodes[i].getConnectsTo(x).getTo() - 1].setSelected();
        pathTemp.add(nodes[i].getConnectsTo(x).getTo());
    }

    public void unSet(int i,int x) {
        nodes[nodes[i].getConnectsTo(x).getTo() - 1].clearSelected();
        pathTemp.remove(pathTemp.size()-1);
    }

    public int next(int i,int x) {
        return nodes[i].getConnectsTo(x).getTo() - 1;
    }

    public int agregation(int i,int x) {
        int cost =  nodes[i].getConnectsTo(x).getCost();
        return cost;
    }

    public int getBest() {
        return best;
    }
}
