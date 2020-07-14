package AStar;

import java.util.LinkedList;
import java.util.List;
import Map.*;


    import java.util.*;

    public class AStarSearch {

        public static class PriorityList extends LinkedList {

            public void add(Comparable object) {
                for (int i=0; i<size(); i++) {
                    if (object.compareTo(get(i)) <= 0) {
                        add(i, object);
                        return;
                    }
                }
                addLast(object);
            }
        }

        protected static List constructPath(AStarNode node) {
            LinkedList path = new LinkedList();
            while (node.pathParent != null) {
                path.addFirst(node);
                node = node.pathParent;
            }
            return path;
        }

        public static List findPath(AStarNode startNode, AStarNode goalNode, Tile[][] enemyRange,int range ) {

            PriorityList openList = new PriorityList();
            LinkedList closedList = new LinkedList();


            startNode.costFromStart = 0;
            startNode.estimatedCostToGoal =
                    startNode.getEstimatedCost(goalNode);
            startNode.pathParent = null;
            openList.add(startNode);

            while (!openList.isEmpty()) {
                AStarNode node = (AStarNode)openList.removeFirst();
                if (node == goalNode) {
                    // construct the path from start to goal

                    return constructPath(goalNode);
                }

                List neighbors = node.getNeighbors(enemyRange,range);

                for (int i=0; i<neighbors.size(); i++) {
                    AStarNode neighborNode =
                            (AStarNode)neighbors.get(i);
                    boolean isOpen = openList.contains(neighborNode);
                    boolean isClosed =
                            closedList.contains(neighborNode);
                    float costFromStart = node.costFromStart +
                            node.getCost(neighborNode);

                    // check if the neighbor node has not been
                    // traversed or if a shorter path to this
                    // neighbor node is found.
                    if ((!isOpen && !isClosed) ||
                            costFromStart < neighborNode.costFromStart)
                    {
                        neighborNode.pathParent = node;
                        neighborNode.costFromStart = costFromStart;
                        neighborNode.estimatedCostToGoal =
                                neighborNode.getEstimatedCost(goalNode);

                        if (isClosed) {
                            closedList.remove(neighborNode);
                        }
                        if (!isOpen) {
                            openList.add(neighborNode);
                        }
                    }
                }
                closedList.add(node);

            }


            return null;
        }

    }
