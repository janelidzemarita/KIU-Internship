package assemblyTest;

import assembly.DeBruijnGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

class DeBruijnGraphTest {

    private DeBruijnGraph graphBuilder;

    @BeforeEach
    void setUp() {
        graphBuilder = new DeBruijnGraph();
    }

    @Test
    void testBuildGraph() {
        String sequence = "GATCACA";
        int k = 4;

        graphBuilder.buildGraph(sequence, k);
        Map<String, List<String>> graph = graphBuilder.getGraph();

        // Expected graph:
        // GAT -> ATC
        // ATC -> TCA
        // TCA -> CAC
        // CAC -> ACA
        assertEquals(1, graph.get("GAT").size());
        assertEquals("ATC", graph.get("GAT").get(0));

        assertEquals(1, graph.get("ATC").size());
        assertEquals("TCA", graph.get("ATC").get(0));

        assertEquals(1, graph.get("TCA").size());
        assertEquals("CAC", graph.get("TCA").get(0));

        assertEquals(1, graph.get("CAC").size());
        assertEquals("ACA", graph.get("CAC").get(0));
    }

    @Test
    void testBuildGraphWithEmptySequence() {
        DeBruijnGraph graph = new DeBruijnGraph();
        int k = 4;

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> graph.buildGraph("", k));

        assertEquals("Sequence length must be at least as long as k.", thrown.getMessage());
    }

    @Test
    void testBuildGraphWithSequenceShorterThanK() {
        DeBruijnGraph graph = new DeBruijnGraph();
        String sequence = "GAT";
        int k = 4;

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
                () -> graph.buildGraph(sequence, k));

        assertEquals("Sequence length must be at least as long as k.", thrown.getMessage());
    }
}
