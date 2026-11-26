package com.riyaz.ecom.paymentservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentServiceConfigurationTests {
    private static final String CONFIGURED_KEY_MARKER = "CONFIGURED_STRIPE_API_KEY=[";

    @TempDir
    Path tempDirectory;

    @Test
    void startsWhenNoEnvFileExists() throws Exception {
        createModuleDirectory();

        assertConfiguredKey(tempDirectory, "");
    }

    @Test
    void loadsEnvFileFromPaymentServiceWorkingDirectory() throws Exception {
        Path moduleDirectory = createModuleDirectory();
        writeEnvFile(moduleDirectory.resolve(".env"), "module-working-directory-key");

        assertConfiguredKey(moduleDirectory, "module-working-directory-key");
    }

    @Test
    void moduleEnvFileTakesPrecedenceOverRootEnvFile() throws Exception {
        Path moduleDirectory = createModuleDirectory();
        writeEnvFile(tempDirectory.resolve(".env"), "root-file-key");
        writeEnvFile(moduleDirectory.resolve(".env"), "module-file-key");

        assertConfiguredKey(tempDirectory, "module-file-key");
    }

    @Test
    void operatingSystemEnvironmentVariableTakesPrecedenceOverEnvFiles() throws Exception {
        Path moduleDirectory = createModuleDirectory();
        writeEnvFile(tempDirectory.resolve(".env"), "root-file-key");
        writeEnvFile(moduleDirectory.resolve(".env"), "module-file-key");

        assertConfiguredKey(tempDirectory, "environment-key", "environment-key");
    }

    private Path createModuleDirectory() throws IOException {
        Path moduleDirectory = tempDirectory.resolve("PaymentService");
        Files.createDirectories(moduleDirectory);
        return moduleDirectory;
    }

    private void writeEnvFile(Path file, String stripeKey) throws IOException {
        Files.writeString(file, "STRIPE_API_KEY=" + stripeKey + System.lineSeparator());
    }

    private void assertConfiguredKey(Path workingDirectory, String expectedKey) throws Exception {
        assertConfiguredKey(workingDirectory, expectedKey, null);
    }

    private void assertConfiguredKey(Path workingDirectory, String expectedKey, String environmentKey) throws Exception {
        Path javaExecutable = Path.of(System.getProperty("java.home"), "bin",
                System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java");
        Path outputFile = tempDirectory.resolve("configuration-probe.log");
        ProcessBuilder processBuilder = new ProcessBuilder(
                javaExecutable.toString(),
                "-cp", System.getProperty("java.class.path"),
                PaymentServiceConfigurationProbe.class.getName(),
                "--spring.main.banner-mode=off",
                "--logging.level.root=OFF");
        processBuilder.directory(workingDirectory.toFile());
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(outputFile.toFile());
        processBuilder.environment().remove("STRIPE_API_KEY");
        if (environmentKey != null) {
            processBuilder.environment().put("STRIPE_API_KEY", environmentKey);
        }

        Process process = processBuilder.start();
        boolean finished = process.waitFor(Duration.ofSeconds(30).toMillis(), TimeUnit.MILLISECONDS);
        if (!finished) {
            process.destroyForcibly();
        }
        assertTrue(finished, "Spring configuration probe timed out");

        String output = Files.readString(outputFile);
        assertEquals(0, process.exitValue(), output);
        assertTrue(output.contains(CONFIGURED_KEY_MARKER + expectedKey + "]"), output);
    }
}