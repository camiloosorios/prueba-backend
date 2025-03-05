package com.bosorio.order_management_service;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ConcurrencyTest {

    private static final String apiUrl = "http://localhost:8080/api/orders/1"; // Ajusta la URL a la que necesites

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Usamos ExecutorService para manejar los hilos
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Sincronizamos las tareas con CountDownLatch
        CountDownLatch latch = new CountDownLatch(2);

        // Tarea que realizará la petición HTTP
        Callable<Void> task = () -> {
            try {
                // Configuración de la conexión HTTP
                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("PUT");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("If-Match", "\"1\""); // Asumimos que la versión inicial es 1
                connection.setDoOutput(true);

                // Cuerpo de la petición (cambio de estado de la orden)
                String jsonBody = "{\n" +
                        "    \"userId\": 1,\n" +
                        "    \"orderState\": \"SHIPPED\",\n" +
                        "    \"orderItems\": [\n" +
                        "        {\n" +
                        "            \"id\": 1,\n" +
                        "            \"productId\": 1,\n" +
                        "            \"quantity\": 10\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}";

                // Enviar la solicitud PUT
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonBody.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Obtener la respuesta
                int statusCode = connection.getResponseCode();
                if (statusCode == 200) {
                    System.out.println("Order updated successfully.");
                } else if (statusCode == 409) {
                    System.out.println("Version conflict: The order has already been updated by another process.");
                } else {
                    System.out.println("Error updating order. Code: " + statusCode);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
            return null;
        };

        // Lanzamos las tareas concurrentes
        Future<Void> future1 = executor.submit(task);
        Future<Void> future2 = executor.submit(task);

        // Esperamos que ambas tareas terminen
        latch.await();

        // Esperamos que ambas tareas se completen
        future1.get();
        future2.get();

        // Cerramos el ExecutorService
        executor.shutdown();
    }
}

