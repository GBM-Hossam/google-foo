package com.hos.foo;

import java.util.*;

public class ChessGraph {

    static Tree searchTree;

    public static int solution(int src, int dest) {
        Graph graph = new Graph(64);
        printAllPathsBFS(graph, src, dest);
        System.out.println(searchTree.desiredNodeSteps);
        return searchTree.desiredNodeSteps;
    }

    public static void printDFS(Graph graph, int start, int end, String path,
                                boolean[] visited) {
        String newPath = path + "->" + start;
        visited[start] = true;
        LinkedList<GraphNode> list = graph.adjacencyList[start];
        System.out.println("list:" + list);
        for (int i = 0; i < list.size(); i++) {
            GraphNode node = list.get(i);
            if (node.destination != end && visited[node.destination] == false) {
                visited[node.destination] = true;
                printDFS(graph, node.destination, end, newPath, visited);
            } else if (node.destination == end) {
                System.out.println(newPath + "->" + node.destination);
            }
        }
        // remove from path
        // visited[start] = false;
    }

    public static void printAllPathsDFS(Graph graph, int start, int end) {
        boolean[] visited = new boolean[graph.vertices];
        visited[start] = true;
        printDFS(graph, start, end, "", visited);
    }

    public static void printAllPathsBFS(Graph graph, int start, int end) {
        boolean visited[] = new boolean[graph.vertices];
        searchTree = new Tree(new TreeNode(start, null), end);
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[start] = true;
        queue.add(start);
        TreeNode currentparentTreeNode = searchTree.root;

        while (queue.size() != 0) {
            start = queue.poll();
            currentparentTreeNode = searchTree.getNode(start);
            Iterator<GraphNode> i = graph.adjacencyList[start].iterator();
            while (i.hasNext()) {
                GraphNode node = i.next();
                if (!visited[node.destination]) {
                    searchTree.insertNode(node.destination,
                            currentparentTreeNode);

                    visited[node.destination] = true;
                    queue.add(node.destination);
                }
            }
        }
        searchTree.traverse(searchTree.root);
    }

    public static void main(String[] args) {
        ChessGraph.solution(19, 36);
        ChessGraph.solution(0, 1);
    }

    static class Graph {
        int vertices;
        int totalEdges;
        LinkedList<GraphNode>[] adjacencyList;

        public Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new LinkedList[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new LinkedList<GraphNode>();
            }
            calculateAllPossibleMoves();
        }

        private void calculateAllPossibleMoves() {
            for (int vertexId = 0; vertexId < vertices; vertexId++) {
                int vertexAdjRowIndex = (vertexId / 8) + 1;
                int vertexMaxBoundary = (vertexAdjRowIndex * 8) - 1;
                int vertexMinBoundary = vertexMaxBoundary - 7;
                calculateVerticalMove(vertexId, vertexAdjRowIndex,
                        vertexMaxBoundary, vertexMinBoundary);

                calculateHorizontalMove(vertexId, vertexAdjRowIndex,
                        vertexMaxBoundary, vertexMinBoundary);

            }
        }

        private void calculateHorizontalMove(int vertexId,
                                             int vertexAdjRowIndex, int vertexMaxBoundary,
                                             int vertexMinBoundary) {
            int rightMove = vertexId + 2;

            if (rightMove <= vertexMaxBoundary) {
                int rightDownMove = rightMove + 8;
                if (rightDownMove <= vertices - 1)
                    addEdge(vertexId, rightDownMove);

                int rightUpMove = rightMove - 8;
                if (rightUpMove >= 0)
                    addEdge(vertexId, rightUpMove);
            }

            int leftMove = vertexId - 2;

            if (leftMove >= vertexMinBoundary) {
                int leftDownMove = leftMove + 8;
                if (leftDownMove <= vertices - 1)
                    addEdge(vertexId, leftDownMove);

                int leftUpMove = leftMove - 8;
                if (leftUpMove >= 0)
                    addEdge(vertexId, leftUpMove);
            }
        }

        private void calculateVerticalMove(int vertexId, int vertexAdjRowIndex,
                                           int vertexMaxBoundary, int vertexMinBoundary) {

            int upMove = vertexId - 16;

            if (upMove >= 0) {
                int upRightMove = upMove + 1;
                if (upRightMove <= vertexMaxBoundary - 16)
                    addEdge(vertexId, upRightMove);

                int upLeftMove = upMove - 1;
                if (upLeftMove >= vertexMinBoundary - 16)
                    addEdge(vertexId, upLeftMove);
            }

            int downMove = vertexId + 16;

            if (downMove <= vertices - 1) {
                int downRightMove = downMove + 1;
                if (downRightMove <= vertexMaxBoundary + 16)
                    addEdge(vertexId, downRightMove);

                int downLeftMove = downMove - 1;
                if (downLeftMove >= vertexMinBoundary + 16)
                    addEdge(vertexId, downLeftMove);
            }
        }

        public void addEdge(int source, int destination) {
            GraphNode node = new GraphNode(source, destination);
            adjacencyList[source].addLast(node);
            totalEdges++;
        }
    }

    static class GraphNode {
        int source;
        int destination;

        public GraphNode(int source, int destination) {
            this.source = source;
            this.destination = destination;
        }

        @Override
        public String toString() {
            return "Node [source=" + source + ", destination=" + destination
                    + "]";
        }
    }

    static class Tree {
        TreeNode root;
        Map<Integer, TreeNode> nodeMap = new HashMap<Integer, TreeNode>();
        int desiredNodeValue = -1;
        int desiredNodeSteps;

        Tree(TreeNode root, int desiredNodeValue) {
            nodeMap.put(root.value, root);
            this.root = root;
            this.desiredNodeValue = desiredNodeValue;
        }

        void traverse(TreeNode node) {
            if (node == null)
                return;
            // /System.out.println("Node ==> " + node.value
            // /+ " : Parent Node  ==> " + node.parentNode);
            for (int i = 0; i < node.childNodes.size(); i++)
                traverse(node.childNodes.get(i));
        }

        public TreeNode getNode(int nodeValue) {
            return nodeMap.get(nodeValue);
        }

        TreeNode insertNode(int value, TreeNode parent) {
            TreeNode node = new TreeNode(value, parent);
            parent.addNode(node);
            nodeMap.put(value, node);
            if (value == desiredNodeValue) {
                TreeNode backTrack = node;
                int currentDesiredNodeSteps = 0;
                while (backTrack != null) {
                    backTrack = backTrack.parentNode;
                    currentDesiredNodeSteps++;
                }
                if (currentDesiredNodeSteps < desiredNodeSteps
                        || currentDesiredNodeSteps > -1)
                    desiredNodeSteps = currentDesiredNodeSteps - 1;
            }
            return node;
        }
    }

    static class TreeNode {
        int value;
        TreeNode parentNode;
        List<TreeNode> childNodes;

        public TreeNode(int value, TreeNode parent) {
            super();
            this.value = value;
            this.parentNode = parent;
            this.childNodes = new ArrayList<ChessGraph.TreeNode>();
        }

        void addNode(TreeNode e) {
            childNodes.add(e);
        }

        @Override
        public int hashCode() {
            return this.value;
        }

        @Override
        public String toString() {
            return "TreeNode [value=" + value + "]";
        }
    }
}