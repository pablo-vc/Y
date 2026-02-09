package com.example.y.data;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.example.y.Post;
import com.example.y.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Api {
    public static User getUserData(String username) {
        try {
            URL url = new URL("http://10.0.2.2:8080/" + username);  // TODO Revisar ruta

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            int code = con.getResponseCode();
            System.out.println("Código HTTP: " + code);

            InputStream stream = con.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }

            if (code == 200) {
                JSONObject obj = new JSONObject(response.toString());

                int id = obj.getInt("id");
                String name = obj.getString("username");
                String email = obj.getString("email");

                return new User(id, name, email);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // si hay error
    }

    public static void uploadUser(String username, String email, String password) {
        new Thread(() -> {  //Creamos un nuevo hilo para que se ejecute en segundo plano
            try {
                URL url = new URL("http://10.0.2.2:8080/"); //Para emulador 10.0.2.2 sino ipordenador 192.168.1.138  // TODO Revisar ruta
                HttpURLConnection con = (HttpURLConnection) url.openConnection();  //Abrir Conexion
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true); //"Voy a escribir el body"

                JSONObject json = new JSONObject(); //Creamos el Json del body
                json.put("username", username);
                json.put("email", email);
                json.put("password", password);
                try (OutputStream os = con.getOutputStream()) {  //Enviar body
                    os.write(json.toString().getBytes(StandardCharsets.UTF_8));
                }
                int code = con.getResponseCode();
                Log.i("API", "Código respuesta: " + code);
            } catch (Exception e) {
                Log.e("Error", "Error al subir deportista");
            }

        }).start();
    }

    public static List<String> getAllUsernames() {

        List<String> usernames = new ArrayList<>();

        try {
            URL url = new URL("http://10.0.2.2:8080/" // TODO Revisar ruta
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);

            if (code == 200) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                JSONArray array = new JSONArray(response.toString());

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    String username = obj.getString("username");

                    usernames.add(username);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usernames; // lista vacía si hay error
    }

    public static List<String> getAllEmails() {

        List<String> emails = new ArrayList<>();

        try {
            URL url = new URL("http://10.0.2.2:8080/" // TODO Revisar ruta
            );

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);

            if (code == 200) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                JSONArray array = new JSONArray(response.toString());

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    String email = obj.getString("email");

                    emails.add(email);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return emails; // lista vacía si hay error
    }

    /**
     * Gathers Posts from a database
     *
     * @param n         Number of posts gathered
     * @param following If true only
     * @param id        User logged id
     * @return An arraylist with all the posts gathered
     */
    public static List<Post> getPosts(int n, Boolean following, int id) {

        List<Post> posts = new ArrayList<>();

        try {
            String route = following ? "/posts/following" : "/posts"; // TODO Revisar ruta
            URL url = new URL("http://10.0.2.2:8080/" + route);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Código HTTP: " + code);

            if (code == 200) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line.trim());
                }

                JSONArray array = new JSONArray(response.toString());

                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);

                    Post post = new Post(obj.getInt("id"), obj.getInt("userId"), obj.getString("username"), obj.getString("content"), obj.getString("createdAt"));

                    posts.add(post);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts; // lista vacía si hay error
    }


    public static void subirImagen(Uri imageUri, Context context, String nombre) {
        new Thread(() -> {
            try {
                // Leer la imagen
                InputStream is = context.getContentResolver().openInputStream(imageUri);
                if (is == null) {
                    Log.e("UPLOAD", "InputStream nulo");
                    return;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                is.close();
                String base64Imagen = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
                // Crear JSON
                JSONObject json = new JSONObject();
                json.put("nombre", nombre);
                json.put("imagen", base64Imagen);
                // Conexión HTTP
                URL url = new URL("http://172.16.0.79:8080/"); // TODO Revisar ruta
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setConnectTimeout(15000);
                con.setReadTimeout(15000);
                con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                con.connect();
                System.out.println("1");

// Enviar JSON
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input);
                    os.flush();
                }
                System.out.println("2");

                // Leer respuesta
                int code = con.getResponseCode();
                Log.i("API", "Código respuesta: " + code);
            } catch (Exception e) {
                Log.e("UPLOAD", "Error al subir imagen", e);
            }
        }).start();
    }
}
