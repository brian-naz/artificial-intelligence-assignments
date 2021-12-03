import java.io.*;
import java.util.*;

class Edge {
    Node nodeName;
    int cost;

    Edge(Node node, int pathCost) {
        nodeName = node;
        cost = pathCost;
    }
}

class Node {
    static ArrayList<Node> exists = new ArrayList<>();
    private static int objectCounter = 0;
    public String state;
    public int totalCost;
    public LinkedList<Edge> neighbourNodes = new LinkedList<>();
    public Node parent;
    public int heuristicCost;
    public int totalHeuristic;

    Node() {
        neighbourNodes = new LinkedList<>();
    }

    private Node(String value) {
        this.state = value;
    }

    public Node(Node node) {
        state = node.state;
        totalCost = node.totalCost;
        parent = node.parent;
    }

    public static int getCounter() {
        return objectCounter;
    }

    public Node getObj(String value) {
        Node node;
        for (Node a : exists) {
            if (a.getVal().equals(value))
                return a;
        }
        node = new Node(value);
        objectCounter++;
        exists.add(node);
        return node;
    }

    public String getVal() {
        return state;
    }

    //add edges between two nodes
    void addEdge(Node n, int pathCost) {
        Edge edge = new Edge(n, pathCost);
        neighbourNodes.add(edge);
    }

    void addHeuristicCost(int hCost) {
        this.heuristicCost = hCost;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        String algo;
        int numberOfTrafficLines;
        int heuristicCost;
        try {
            Node node = new Node();
            Node start = new Node();
            Node dest = new Node();
            Scanner scan = new Scanner(new File("input.txt"));
            algo = scan.next();
            start = node.getObj(scan.next());
            dest = node.getObj(scan.next());
            numberOfTrafficLines = scan.nextInt();
            scan.nextLine();
            String[] trafficInfo = new String[numberOfTrafficLines];
            while (numberOfTrafficLines > 0) {
                node = new Node();
                trafficInfo = scan.nextLine().split(" ");
                node.getObj(trafficInfo[0]).addEdge(node.getObj(trafficInfo[1]), Integer.valueOf(trafficInfo[2]));
                numberOfTrafficLines--;
            }
            if (algo.equals("A*")) {
                heuristicCost = scan.nextInt();
                scan.nextLine();
                while (heuristicCost > 0) {
                    node = new Node();
                    trafficInfo = scan.nextLine().split(" ");
                    char[] check = trafficInfo[1].toCharArray();
                    if (Character.isLetter(check[0])) {
                        node.getObj(trafficInfo[0]).addHeuristicCost(0);
                    } else {
                        node.getObj(trafficInfo[0]).addHeuristicCost(Integer.valueOf(trafficInfo[1]));
                    }
                    heuristicCost--;
                }
            }

            if (algo.equals("BFS")) {
                System.out.println("\nBFS");
                BFS(start, dest);
            } else if (algo.equals("DFS")) {
                System.out.println("\nDFS");
                DFS(start, dest);
            } else if (algo.equals("UCS")) {
                System.out.println("\nUCS");
                UCS(start, dest);
            } else if (algo.equals("A*")) {
                System.out.println("\nA*");
                Astar(start, dest);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Breadth First Search
    public static void BFS(Node source, Node goal) throws IOException {

        Node poppedNode;
        Node nodeNext = new Node();
        if (source.state.equals(goal.state)) {
            printWithoutPath(goal);
        }
        Set<String> visited = new HashSet<>();
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(source);

        while (queue.size() != 0) {
            poppedNode = queue.poll();
            visited.add(poppedNode.state);
            ListIterator<Edge> hasNeighbour = poppedNode.neighbourNodes.listIterator();
            while (hasNeighbour.hasNext()) {
                Edge nextNode = hasNeighbour.next();
                if (nextNode.nodeName.state.equals(goal.state)) {
                    goal.parent = poppedNode;
                    printWithoutPath(goal);
                    return;
                }
                if (!visited.contains(nextNode.nodeName.state)) {
                    visited.add(nextNode.nodeName.state);
                    nodeNext = nodeNext.getObj(nextNode.nodeName.state);
                    nodeNext.parent = poppedNode;
                    queue.add(nodeNext);
                }
            }
        }
    }

    //Depth First Search
    public static void DFS(Node source, Node goal) throws IOException {
        Node poppedNode;
        Node nodeNext = new Node();
        source.parent = null;
        if (source.state.equals(goal.state)) {
            printWithoutPath(goal);
            return;
        }
        Set<String> visited = new HashSet<>();
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(source);

        while (queue.size() != 0) {
            poppedNode = queue.poll();
            if (poppedNode.state.equals(goal.state)) {
                printWithoutPath(goal);
                return;
            }
            visited.add(poppedNode.state);
            Iterator<Edge> hasNeighbour = poppedNode.neighbourNodes.descendingIterator();
            while (hasNeighbour.hasNext()) {
                Edge nextNode = hasNeighbour.next();
                if (visited.contains(nextNode.nodeName.state)) {

                } else if (queue.contains(nextNode.nodeName)) {

                } else {
                    nodeNext = nodeNext.getObj(nextNode.nodeName.state);
                    nodeNext.parent = poppedNode;
                    queue.addFirst(nodeNext);
                }
            }
        }
    }

    //Uniform Cost Search
    public static void UCS(Node source, Node goal) throws IOException {
        source.totalCost = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(source);
        Set<Node> visited = new HashSet<>();
        do {
            Node current = queue.poll();
            visited.add(current);
            if (current.state.equals(goal.state)) {
                goal.parent = current.parent;
                goal.totalCost = current.totalCost;
                break;
            }
            for (Edge edges : current.neighbourNodes) {
                Node child = edges.nodeName;
                int cost = edges.cost;
                if (!visited.contains(child) && !queue.contains(child)) {
                    child.totalCost = current.totalCost + cost;
                    child.parent = current;
                    queue.add(child);
                } else if (queue.contains(child) || visited.contains(child)) {
                    if (child.totalCost > cost + current.totalCost) {
                        child.parent = current;
                        child.totalCost = current.totalCost + cost;
                    }
                }
            }
            queue = sortQueue(queue, "UCS");
        } while (!queue.isEmpty());
        printPath(goal);
    }

    public static Queue<Node> sortQueue(Queue queue, String algo) {

        Queue<Node> queue1 = new LinkedList<>();
        Queue<Node> queue2 = new LinkedList<>();

        while (!queue.isEmpty())
            queue1.add((Node) queue.remove());

        while (!queue1.isEmpty()) {
            Node q = queue1.remove();
            if (algo.equals("A*")) {
                while (!queue2.isEmpty() && q.totalHeuristic < queue2.peek().totalHeuristic)
                    if (q.totalHeuristic < queue2.peek().totalHeuristic) {
                        queue1.add(queue2.remove());
                    }
                queue2.add(q);
            } else if (algo.equals("UCS")) {
                while (!queue2.isEmpty() && q.totalCost < queue2.peek().totalCost)
                    if (q.totalCost < queue2.peek().totalCost) {
                        queue1.add(queue2.remove());
                    }
                queue2.add(q);
            }
        }
        return queue2;
    }

    //A* search
    public static void Astar(Node source, Node goal) throws IOException {
        source.totalCost = 0;

        Queue<Node> queue = new LinkedList<>();

        queue.add(source);
        Set<Node> visited = new HashSet<>();
        do {
            Node current = queue.poll();
            visited.add(current);
            if (current.state.equals(goal.state)) {
                if (current.parent == null) {
                    goal.parent = current.parent;
                    goal.totalCost = current.totalCost;
                } else {
                    for (Edge edges : current.parent.neighbourNodes) {
                        if (edges.nodeName.state.equals(current.state)) {
                            goal.totalCost = edges.cost + current.parent.totalCost;
                        }
                    }
                    goal.parent = current.parent;
                }
                break;
            }
            for (Edge edges : current.neighbourNodes) {
                Node child = edges.nodeName;
                int cost = edges.cost;
                if (queue.contains(child) || visited.contains(child)) {
                    if (child.totalHeuristic > cost + current.totalCost + child.heuristicCost) {
                        child.parent = current;
                        child.totalCost = current.totalCost + cost;
                        child.totalHeuristic = current.totalCost + cost + child.heuristicCost;
                    }
                } else {
                    child.totalCost = current.totalCost + cost;
                    child.totalHeuristic = current.totalCost + cost + child.heuristicCost;
                    child.parent = current;
                    queue.add(child);
                }
            }
            queue = sortQueue(queue, "A*");
        } while (!queue.isEmpty());
        printPath(goal);
    }

    public static void printPath(Node target) throws IOException {
        List<Node> path = new ArrayList<>();
        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path);
        File writeFile = new File("output.txt");
        FileWriter fw = new FileWriter(writeFile);
        for (Node node : path) {
            fw.write(node.state + " " + node.totalCost + "\n");
            System.out.println(node.state + " " + node.totalCost);
        }
        fw.close();
    }

    public static void printWithoutPath(Node target) {
        List<Node> path = new ArrayList<>();
        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path);
        int i = 0;
        for (Node node : path) {
            System.out.println(node.state + " " + i++);
        }
    }
}