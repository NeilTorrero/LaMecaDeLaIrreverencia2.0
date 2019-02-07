package BackTracking;

import BackTracking.InterficieBacktraking;
import Json.Nodes;

import java.util.ArrayList;


public class BackDist implements InterficieBacktraking {
    private Nodes[] nodes;
    private int from;
    private int to;
    private int best = 999999;
    public ArrayList<Integer> pathTemp = new ArrayList<Integer>();
    public ArrayList winPath;
    public BackDist(Nodes[] nodes, int from, int to) {
        this.nodes = nodes;
        this.from = from;
        this.to = to;
        nodes[from-1].setSelected();
        pathTemp.add(from);
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
