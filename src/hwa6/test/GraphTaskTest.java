package hwa6.test;

import static org.junit.Assert.*;

import hwa6.src.GraphTask;
import org.junit.Test;
import java.util.*;

/** Testklass.
 * @author jaanus
 */
public class GraphTaskTest {

   @Test
   public void testSmallGraphCloning() throws CloneNotSupportedException {
      GraphTask.Graph smallGraph = new GraphTask.Graph("SmallGraph");
      smallGraph.createRandomSimpleGraph(5, 5);
      GraphTask.Graph clonedSmallGraph = (GraphTask.Graph) smallGraph.clone();
      assertNotSame(smallGraph, clonedSmallGraph);
      assertEquals(smallGraph.toString(), clonedSmallGraph.toString());
   }

   @Test
   public void testRandomTreeCloning() throws CloneNotSupportedException {
      GraphTask.Graph randomTree = new GraphTask.Graph("RandomTree");
      randomTree.createRandomTree(10);
      GraphTask.Graph clonedRandomTree = (GraphTask.Graph) randomTree.clone();
      assertNotSame(randomTree, clonedRandomTree);
      assertEquals(randomTree.toString(), clonedRandomTree.toString());
   }

   @Test
   public void testRandomSimpleCloning() throws CloneNotSupportedException {
      GraphTask.Graph randomSimpleGraph = new GraphTask.Graph("RandomSimpleGraph");
      randomSimpleGraph.createRandomSimpleGraph(8, 12);
      GraphTask.Graph clonedRandomSimpleGraph = (GraphTask.Graph) randomSimpleGraph.clone();
      assertNotSame(randomSimpleGraph, clonedRandomSimpleGraph);
      assertEquals(randomSimpleGraph.toString(), clonedRandomSimpleGraph.toString());
   }

   @Test
   public void testEmptyGraphCloning() throws CloneNotSupportedException {
      GraphTask.Graph emptySimpleGraph = new GraphTask.Graph("EmptySimpleGraph");
      emptySimpleGraph.createRandomSimpleGraph(0, 0);
      GraphTask.Graph clonedEmptySimpleGraph = (GraphTask.Graph) emptySimpleGraph.clone();
      assertNotSame(emptySimpleGraph, clonedEmptySimpleGraph);
      assertEquals(emptySimpleGraph.toString(), clonedEmptySimpleGraph.toString());
   }

   @Test
   public void testSingleVertexGraphCloning() throws CloneNotSupportedException {
      GraphTask.Graph singleVertexGraph = new GraphTask.Graph("SingleVertexGraph");
      singleVertexGraph.createRandomSimpleGraph(2, 1);
      GraphTask.Graph clonedSingleVertexGraph = (GraphTask.Graph) singleVertexGraph.clone();
      assertNotSame(singleVertexGraph, clonedSingleVertexGraph);
      assertEquals(singleVertexGraph.toString(), clonedSingleVertexGraph.toString());
   }

   @Test
   public void testLargeGraphCloning() throws CloneNotSupportedException {
      GraphTask.Graph largeGraph = new GraphTask.Graph("LargeGraph");
      largeGraph.createRandomSimpleGraph(2300, 10000);
      long startTime = System.nanoTime();
      GraphTask.Graph clonedLargeGraph = (GraphTask.Graph) largeGraph.clone();
      long endTime = System.nanoTime();
      long duration = (endTime - startTime) / 1000000; // in milliseconds
      System.out.println("\nCloning Large Graph (2300 vertices) took: " + duration + " ms");
      assertNotSame(largeGraph, clonedLargeGraph);
      assertEquals(largeGraph.toString(), clonedLargeGraph.toString());
   }

   @Test
   public void testGraphModification() throws CloneNotSupportedException {
      GraphTask.Graph graph = new GraphTask.Graph("Graph");
      graph.createRandomSimpleGraph(5, 5);
      GraphTask.Graph clonedGraph = (GraphTask.Graph) graph.clone();
      GraphTask.Vertex originalVertex = graph.first;
      originalVertex.id = "ModifiedVertex";
      assertNotEquals(graph.toString(), clonedGraph.toString());
   }

}

