package halive.visualsort.nativeloader;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class NativeLoaderTest {

    private static String[] TEST_CONTENTS = {"random.dll",
            "rnd.dylib", "test.dll", "test-dy.dylib",
            "test-so.so", "xyz.so"};

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private String inputJarFile = "nativeloader/nltest.zip";
    private String hashFile = "nativeloader/fileHashes.txt";
    private File workingDir = null;
    private File nativesOutputFolder = null;
    private NativeLoader loader;

    private Map<String, String> fileHashes = new HashMap<>();

    private String foldername;

    public NativeLoaderTest(String foldername) {
        this.foldername = foldername;
    }

    @Before
    public void setUp() throws Exception {
        workingDir = new File(folder.newFolder(), foldername);
        nativesOutputFolder = new File(workingDir, "natives");
        if (workingDir.exists() || nativesOutputFolder.exists()) {
            throw new IOException("The NativeLoader Test Directory already Exists");
        }
        workingDir.mkdir();
        nativesOutputFolder.mkdir();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = getClass().getClassLoader().getResourceAsStream(inputJarFile);
        int d = 0;
        while ((d = in.read()) != -1) {
            out.write(d);
        }
        File jar = new File(workingDir, "test.jar");
        Files.write(jar.toPath(), out.toByteArray());
        loader = new NativeLoader(jar);
        List<String> hashLines = new ArrayList<>();
        Scanner scn = new Scanner(getClass().getClassLoader().getResourceAsStream(hashFile));
        while (scn.hasNextLine()) {
            hashLines.add(scn.nextLine());
        }
        for (String line : hashLines) {
            String[] vals = line.split(":");
            if (vals.length != 2) {
                throw new IllegalArgumentException();
            }
            fileHashes.put(vals[0], vals[1]);
        }
    }

    @Test
    public void testNativeLoader() throws Exception {
        //TODO Fix nativeLoader test. it is failing in CI
        loader.copyNatives(nativesOutputFolder);
        int cntValidFiles = 0;
        for (File file : nativesOutputFolder.listFiles()) {
            for (String name : TEST_CONTENTS) {
                if (file.getName().contains(name)) {
                    cntValidFiles++;
                }
            }
        }
        assertTrue("Not all Valid Items have Been Copied", cntValidFiles == TEST_CONTENTS.length);
        for (File f : nativesOutputFolder.listFiles()) {
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

    @Parameterized.Parameters(name = "{index}: Folder: \"{0}\"")
    public static Collection<String> getFolderNamesToTest() {
        return Arrays.asList("TestFolder", "ÄTestFolder",
                "ÜTestFolder", "ÜTestFolder", "Test Folder");
    }
}