/*
 * Copyright (c) HALive 2016
 * See LICENCE For Licence information.
 */

package halive.visualsort.test.export;

import halive.visualsort.core.DataEntry;
import halive.visualsort.core.SortingHandler;
import halive.visualsort.core.export.SortingExporter;
import halive.visualsort.datageneration.triangle.TriangleGenerator;
import halive.visualsort.sortingalgorithms.others.BinaryTreeSort;
import halive.visualsort.sortingalgorithms.quicksort.heuristics.LastElementHeuristic;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ExporterTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private SortingHandler handler;
    private SortingExporter exporter;

    private DataEntry[] data = new DataEntry[1200];

    @Before
    public void setUp() throws Exception {
        System.out.println("Initializing...");
        handler = new SortingHandler(null);
        exporter = new SortingExporter(handler, folder.newFile("test.png"));
        init();
        System.out.println("Initialized.");
    }

    private void init() {
        handler.setSortingAlgorithm(new LastElementHeuristic.QuickSortR2Last());
        handler.setDataGenerator(new TriangleGenerator());
        handler.setSortingExporter(exporter);
        System.out.println("Instantiating Data Array...");
        for (int i = 0; i < data.length; i++) {
            data[i] = new DataEntry(1, handler);
        }
        System.out.println("Generating Data...");
        handler.getDataGenerator().generateData(data, 1000);
        handler.setEntries(data);
    }

    /**
     * Tests The Behaviour for non Exportable Algorithms
     *
     * @throws Exception
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidAlgorithm() throws Exception {
        System.out.println("Running Invalid Argument Test");
        handler.setSortingAlgorithm(new BinaryTreeSort());
        System.out.println("Sorting...");
        handler.getCurrentAlgorithm().doSort(data, handler, -1, -1);
    }

    /**
     * Tests whether or not the Exporter is deterministic
     *
     * @throws Exception
     */
    @Test
    public void testRendering() throws Exception {
        System.out.println("Running Render Test");
        System.out.println("Sorting For File 1...");
        handler.getCurrentAlgorithm().doSort(data, handler, 0, data.length);
        System.out.println("Rendering File 1...");
        exporter.export();
        System.out.println("Reinitializing for File 2...");
        SortingExporter old = exporter;
        init();
        System.out.println("Sorting File 2...");
        handler.getCurrentAlgorithm().doSort(data, handler, 0, data.length);
        System.out.println("Rendering File 2...");
        exporter.export();
        System.out.println("Validating...");
        validate(old);
        System.out.println("Done");
    }

    private void validate(SortingExporter old) throws Exception {
        System.out.println("Loading Generated Data...");
        byte[] data = Files.readAllBytes(exporter.getOutputFile().toPath());
        byte[] validation = Files.readAllBytes(old.getOutputFile().toPath());
        Assert.assertTrue("Array Lengths are Unequal", data.length == validation.length);
        System.out.println("Calcualting Hashes...");
        byte[] generatedHash = getSHA256Hash(data);
        byte[] readHash = getSHA256Hash(validation);
        Assert.assertArrayEquals("The Hashes do not match", readHash, generatedHash);
    }

    private byte[] getSHA256Hash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest d = MessageDigest.getInstance("SHA-256");
        return d.digest(data);
    }
}
