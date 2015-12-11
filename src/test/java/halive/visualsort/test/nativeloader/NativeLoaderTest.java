package halive.visualsort.test.nativeloader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class NativeLoaderTest {

    private static String[] TEST_CONTENTS = {"random.dll",
            "rnd.dylib", "test.dll", "test-dy.dylib",
            "test-so.so", "xyz.so"};

    private File inputJarFile = new File("src/test/resources/nativeloader/nltest.zip");
    private File hashFile = new File("src/test/resources/nativeloader/fileHashes.txt");
    private File outputFolder = new File("nltest");
    private NativeLoader loader;

    private Map<String, String> fileHashes = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        File file = new File("test");
        String s = file.getAbsolutePath();
        if (outputFolder.exists()) {
            throw new IOException("The NativeLoader Test Directory already Exists");
        }
        outputFolder.mkdir();
        loader = new NativeLoader(inputJarFile);
        List<String> hashLines = Files.readAllLines(hashFile.toPath());
        for (String line : hashLines) {
            String[] vals = line.split(":");
            if (vals.length != 2) {
                throw new IllegalArgumentException();
            }
            fileHashes.put(vals[0], vals[1]);
        }
    }

    @After
    public void tearDown() throws Exception {
        NativeLoaderUtils.deleteFolder(outputFolder);
    }

    @Test
    public void testNativeLoader() throws Exception {
        loader.copyNatives(outputFolder);
        int cntValidFiles = 0;
        for (File file : outputFolder.listFiles()) {
            for (String name : TEST_CONTENTS) {
                if (file.getName().contains(name)) {
                    cntValidFiles++;
                }
            }
        }
        assertTrue("Not all Valid Items have Been Copied", cntValidFiles == TEST_CONTENTS.length);
        for (File f : outputFolder.listFiles()) {
            byte[] data = Files.readAllBytes(f.toPath());
            String hash = getSHAHash(data);
            String h2 = fileHashes.get(f.getName());
            assertTrue("Hashes are not Equal.", h2 != null && hash.equals(h2));
        }
    }

    private String getSHAHash(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return String.format("%064x", new java.math.BigInteger(1, md.digest(data)));
    }
}