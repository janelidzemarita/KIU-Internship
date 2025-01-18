package assemblyTest;

import assembly.DeBruijnGraph;
import assembly.EulerianPathReconstructor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class EulerianPathReconstructorTest {

    @Test
    void testFindEulerianPathBasic() {
        // Create a DeBruijnGraph
        DeBruijnGraph dbg = new DeBruijnGraph();
        dbg.buildGraph("GATCACA", 4);

        // Find the Eulerian path
        List<String> expectedPath = Arrays.asList("GAT", "ATC", "TCA", "CAC", "ACA");
        List<String> eulerianPath = EulerianPathReconstructor.findEulerianPath(dbg);

        // Validate the result
        assertEquals(expectedPath, eulerianPath);
    }

    @Test
    void testFindEulerianPathEmptyGraph() {
        // Create an empty DeBruijnGraph
        DeBruijnGraph dbg = new DeBruijnGraph();

        // Find the Eulerian path
        List<String> eulerianPath = EulerianPathReconstructor.findEulerianPath(dbg);

        // Validate the result
        assertTrue(eulerianPath.isEmpty());
    }



    @Test
    void testFindEulerianPathSingleNode() {
        // Create a DeBruijnGraph with a single node
        DeBruijnGraph dbg = new DeBruijnGraph();
        dbg.buildGraph("AAAA", 4);

        // Find the Eulerian path
        List<String> expectedPath = Arrays.asList("AAA", "AAA");
        List<String> eulerianPath = EulerianPathReconstructor.findEulerianPath(dbg);

        // Validate the result
        assertEquals(expectedPath, eulerianPath);
    }

    @Test
    void testFindEulerianPathMultipleStartNodes() {
        // Create a DeBruijnGraph with multiple start nodes
        DeBruijnGraph dbg = new DeBruijnGraph();
        dbg.buildGraph("GATCAG", 4);

        // Find the Eulerian path
        List<String> eulerianPath = EulerianPathReconstructor.findEulerianPath(dbg);

        // Validate the result
        assertNotNull(eulerianPath);
        assertFalse(eulerianPath.isEmpty());
    }

    @Test
    void testReconstructGenomeBasic() {
        // Define an Eulerian path
        List<String> path = Arrays.asList("GAT", "ATC", "TCA", "CAC", "ACA");

        // Reconstruct the genome
        String expectedGenome = "GATCACA";
        String genome = EulerianPathReconstructor.reconstructGenome(path);

        // Validate the result
        assertEquals(expectedGenome, genome);
    }

    @Test
    void testReconstructGenomeEmptyPath() {
        // Define an empty Eulerian path
        List<String> path = Collections.emptyList();

        // Reconstruct the genome
        String genome = EulerianPathReconstructor.reconstructGenome(path);

        // Validate the result
        assertEquals("", genome);
    }

    @Test
    void testReconstructGenomeSingleNode() {
        // Define an Eulerian path with a single node
        List<String> path = Collections.singletonList("GAT");

        // Reconstruct the genome
        String expectedGenome = "GAT";
        String genome = EulerianPathReconstructor.reconstructGenome(path);

        // Validate the result
        assertEquals(expectedGenome, genome);
    }
}
