package hwa6.src;

import java.util.*;

public class GraphTask {

   public static void main(String[] args) {
      GraphTask a = new GraphTask();
      a.run();
   }

   public void run() {
      try {
         // Test 1: Small graph
         Graph smallGraph = new Graph("SmallGraph");
         smallGraph.createRandomSimpleGraph(5, 5);
         Graph clonedSmallGraph = (Graph) smallGraph.clone();
         System.out.println("Test 1 - Original Small Graph:");
         System.out.println(smallGraph);
         System.out.println("Test 1 - Cloned Small Graph:");
         System.out.println(clonedSmallGraph);

         // Test 2: Random tree
         Graph randomTree = new Graph("RandomTree");
         randomTree.createRandomTree(10);
         Graph clonedRandomTree = (Graph) randomTree.clone();
         System.out.println("\nTest 2 - Original Random Tree:");
         System.out.println(randomTree);
         System.out.println("\nTest 2 - Cloned Random Tree:");
         System.out.println(clonedRandomTree);

         // Test 3: Random simple graph
         Graph randomSimpleGraph = new Graph("RandomSimpleGraph");
         randomSimpleGraph.createRandomSimpleGraph(8, 12);
         Graph clonedRandomSimpleGraph = (Graph) randomSimpleGraph.clone();
         System.out.println("\nTest 3 - Original Random Simple Graph:");
         System.out.println(randomSimpleGraph);
         System.out.println("\nTest 3 - Cloned Random Simple Graph:");
         System.out.println(clonedRandomSimpleGraph);

         // Test 4: Empty Graph
         Graph emptySimpleGraph = new Graph("EmptySimpleGraph");
         emptySimpleGraph.createRandomSimpleGraph(0, 0);
         Graph clonedemptySimpleGraph = (Graph) emptySimpleGraph.clone();
         System.out.println("\nTest 4 - Original Empty Graph:");
         System.out.println(emptySimpleGraph + "(Here goes nothing because the graph is empty)");
         System.out.println("\nTest 4 - Cloned Empty Graph:");
         System.out.println(clonedemptySimpleGraph + "(Here goes nothing because the graph is empty)");

         // Test 5: Clone graph with one vertex
         Graph singleVertexGraph = new Graph("SingleVertexGraph");
         singleVertexGraph.createRandomSimpleGraph(2, 1);
         Graph clonedSingleVertexGraph = (Graph) singleVertexGraph.clone();
         System.out.println("\nTest 5 - Original Single Vertex Graph:");
         System.out.println(singleVertexGraph);
         System.out.println("\nTest 5 - Cloned Single Vertex Graph:");
         System.out.println(clonedSingleVertexGraph);

         // Test 6: Clone large graph and measure time
         Graph largeGraph = new Graph("LargeGraph");
         largeGraph.createRandomSimpleGraph(2300, 10000);
         long startTime = System.nanoTime();
         Graph clonedLargeGraph = (Graph) largeGraph.clone();
         long endTime = System.nanoTime();
         long duration = (endTime - startTime) / 1000000; // in milliseconds
         System.out.println("\nTest 6 - Cloning Large Graph (2300 vertices):");
         System.out.println("Cloning Time: " + duration + " ms");

         // Test 7: Testing that graph is cloned, not just referenced
         Graph graph = new Graph("Graph");
         graph.createRandomSimpleGraph(5, 5);
         Graph clonedGraph = (Graph) graph.clone();
         System.out.println("\nTest 7 - Original Graph");
         System.out.println(graph);
         System.out.println("\nTest 7 - Cloned Graph");
         System.out.println(clonedGraph);
//         graph.first =

      } catch (Exception e) {
         System.err.println("Error: " + e.getMessage());
      }
   }


   public static class Vertex {

      public String id;
      private Vertex next;
      private Arc first;
      private int info = 0;

      Vertex(String s, Vertex v, Arc e) {
         id = s;
         next = v;
         first = e;
      }

      Vertex(String s) {
         this(s, null, null);
      }

      public Vertex cloneVertex() {
         return new Vertex(id, null, null);
      }

      @Override
      public String toString() {
         return id;
      }

   }


   static class Arc {

      private final String id;
      private Vertex target;
      private Arc next;

      Arc(String s, Vertex v, Arc a) {
         id = s;
         target = v;
         next = a;
      }

      Arc(String s) {
         this(s, null, null);
      }

      public Arc cloneArc(Vertex targetVertex) {
         return new Arc(id, targetVertex, null);
      }

      @Override
      public String toString() {
         return id;
      }
   }

   public static class Graph {

      private final String id;
      public Vertex first;

      Graph (String s, Vertex v) {
         id = s;
         first = v;
      }

      public Graph(String s) {
         this (s, null);
      }


      @Override
      public String toString() {
         String nl = System.getProperty ("line.separator");
         StringBuffer sb = new StringBuffer (nl);
         sb.append (id);
         sb.append (nl);
         Vertex v = first;
         while (v != null) {
            sb.append (v.toString());
            sb.append (" -->");
            Arc a = v.first;
            while (a != null) {
               sb.append (" ");
               sb.append (a.toString());
               sb.append (" (");
               sb.append (v.toString());
               sb.append ("->");
               sb.append (a.target.toString());
               sb.append (")");
               a = a.next;
            }
            sb.append (nl);
            v = v.next;
         }
         return sb.toString();
      }

      public Vertex createVertex (String vid) {
         Vertex res = new Vertex (vid);
         res.next = first;
         first = res;
         return res;
      }

      public Arc createArc (String aid, Vertex from, Vertex to) {
         Arc res = new Arc (aid);
         res.next = from.first;
         from.first = res;
         res.target = to;
         return res;
      }

      public void createRandomTree (int n) {
         if (n <= 0)
            return;
         Vertex[] varray = new Vertex [n];
         for (int i = 0; i < n; i++) {
            varray [i] = createVertex ("v" + String.valueOf(n-i));
            if (i > 0) {
               int vnr = (int)(Math.random()*i);
               createArc ("a" + varray [vnr].toString() + "_"
                       + varray [i].toString(), varray [vnr], varray [i]);
               createArc ("a" + varray [i].toString() + "_"
                       + varray [vnr].toString(), varray [i], varray [vnr]);
            } else {}
         }
      }

      public int[][] createAdjMatrix() {
         int info = 0;
         Vertex v = first;
         while (v != null) {
            v.info = info++;
            v = v.next;
         }
         int[][] res = new int[info][info];
         v = first;
         while (v != null) {
            int i = v.info;
            Arc a = v.first;
            while (a != null) {
               int j = a.target.info;
               res[i][j]++;
               a = a.next;
            }
            v = v.next;
         }
         return res;
      }

      public void createRandomSimpleGraph (int n, int m) {
         if (n <= 0)
            return;
         if (n > 2500)
            throw new IllegalArgumentException ("Too many vertices: " + n);
         if (m < n-1 || m > n*(n-1)/2)
            throw new IllegalArgumentException
                    ("Impossible number of edges: " + m);
         first = null;
         createRandomTree (n);       // n-1 edges created here
         Vertex[] vert = new Vertex [n];
         Vertex v = first;
         int c = 0;
         while (v != null) {
            vert[c++] = v;
            v = v.next;
         }
         int[][] connected = createAdjMatrix();
         int edgeCount = m - n + 1;  // remaining edges
         while (edgeCount > 0) {
            int i = (int)(Math.random()*n);  // random source
            int j = (int)(Math.random()*n);  // random target
            if (i==j)
               continue;  // no loops
            if (connected [i][j] != 0 || connected [j][i] != 0)
               continue;  // no multiple edges
            Vertex vi = vert [i];
            Vertex vj = vert [j];
            createArc ("a" + vi.toString() + "_" + vj.toString(), vi, vj);
            connected [i][j] = 1;
            createArc ("a" + vj.toString() + "_" + vi.toString(), vj, vi);
            connected [j][i] = 1;
            edgeCount--;  // a new edge happily created
         }
      }


      /**
       * Deep clone a graph
       *
       * @return Object deep cloned graph
       * @throws CloneNotSupportedException if cloning is not supported
       */
      public Object clone() throws CloneNotSupportedException {
         Map<Vertex, Vertex> vertexMap = new HashMap<>();
         return new Graph(id, cloneVertexAndArcs(first, vertexMap));
      }


      /**
       * Deep clone vertex
       *
       * @param originalVertex root vertex
       * @param vertexMap map consisting of pairs of non-cloned vertex, and it's deep clone
       * @return Vertex deep cloned vertex
       */
      private Vertex cloneVertexAndArcs(Vertex originalVertex, Map<Vertex, Vertex> vertexMap) {
         if (originalVertex == null) {
            return null;
         }

         Vertex clonedVertex = new Vertex(originalVertex.id);
         vertexMap.put(originalVertex, clonedVertex);

         clonedVertex.next = cloneVertexAndArcs(originalVertex.next, vertexMap);
         clonedVertex.first = cloneArcs(originalVertex.first, vertexMap);

         return clonedVertex;
      }


      /**
       * Deep clone arc
       *
       * @param originalArc root arc
       * @param vertexMap map consisting of pairs of non-cloned vertex, and it's deep clone
       * @return Arc deep cloned arc
       */
      private Arc cloneArcs(Arc originalArc, Map<Vertex, Vertex> vertexMap) {
         if (originalArc == null) {
            return null;
         }

         Arc clonedArc = new Arc(originalArc.id);
         clonedArc.target = vertexMap.get(originalArc.target);

         clonedArc.next = cloneArcs(originalArc.next, vertexMap);

         return clonedArc;
      }
   }
}