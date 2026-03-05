package com.example.y.data;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.example.y.Notification;
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
    static String ip1 = "192.168.1.20";
    static String ip2 = "10.0.2.2";
    private static final String BASE_URL = "http://10.0.2.2:8080/yapi/rest/";

    public static User login(String email, String password) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(BASE_URL + "users/login");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoOutput(true);

            JSONObject body = new JSONObject();
            body.put("email", email);
            body.put("password", password);

            try (OutputStream os = con.getOutputStream()) {
                os.write(body.toString().getBytes(StandardCharsets.UTF_8));
            }

            int code = con.getResponseCode();
            InputStream stream = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line.trim());

            Log.d("API", "Login HTTP code: " + code + ", response: " + response);

            if (code == 200) {
                JSONObject obj = new JSONObject(response.toString());
                return new User(
                        obj.getInt("id"),
                        obj.getString("username"),
                        obj.getString("email"),
                        obj.getString("bio"),
                        obj.getString("created_at")
                );
            } else {
                Log.e("API", "Login failed: HTTP " + code + " - " + response);
                return null;
            }

        } catch (Exception e) {
            Log.e("API", "Login exception", e);
            return null;
        } finally {
            if (con != null) con.disconnect();
        }
    }

    public static String register(String username, String email, String password) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(BASE_URL + "users/register");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoOutput(true);

            JSONObject body = new JSONObject();
            body.put("username", username);
            body.put("email", email);
            body.put("password", password);

            try (OutputStream os = con.getOutputStream()) {
                os.write(body.toString().getBytes(StandardCharsets.UTF_8));
            }

            int code = con.getResponseCode();
            InputStream stream = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line.trim());

            Log.d("API", "Register HTTP code: " + code + ", response: " + response);

            if (code == 201) {
                return "success";
            } else if (code == 409) {
                return response.toString();
            } else {
                return "Error: " + response.toString();
            }

        } catch (Exception e) {
            Log.e("API", "Register exception", e);
            return "Exception: " + e.getMessage();
        } finally {
            if (con != null) con.disconnect();
        }
    }

    public static List<Post> getPosts() {
        List<Post> postList = new ArrayList<>();
        HttpURLConnection con = null;
        try {
            URL url = new URL(BASE_URL + "posts");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int code = con.getResponseCode();
            InputStream stream = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line.trim());

            Log.d("API", "GetPosts HTTP code: " + code + ", response: " + response);

            if (code == 200) {
                JSONArray postsArray = new JSONArray(response.toString());
                for (int i = 0; i < postsArray.length(); i++) {
                    JSONObject postObj = postsArray.getJSONObject(i);
                    int postId = postObj.getInt("id");
                    int userId = postObj.getInt("id_user");
                    String username = postObj.getString("username");
                    String content = postObj.getString("content");
                    String createdAt = postObj.getString("created_at");

                    Post post = new Post(postId, userId, username, content, createdAt);
                    postList.add(post);
                }
            } else {
                Log.e("API", "Failed to load posts. HTTP code: " + code);
            }
        } catch (Exception e) {
            Log.e("API", "GetPosts exception", e);
        } finally {
            if (con != null) con.disconnect();
        }
        return postList;
    }

    public static List<Post> getFollowingPosts(int id) {
        List<Post> postList = new ArrayList<>();
        HttpURLConnection con = null;
        try {
            URL url = new URL(BASE_URL + "posts/following/"+id);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int code = con.getResponseCode();
            InputStream stream = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line.trim());

            Log.d("API", "GetPosts HTTP code: " + code + ", response: " + response);

            if (code == 200) {
                JSONArray postsArray = new JSONArray(response.toString());
                for (int i = 0; i < postsArray.length(); i++) {
                    JSONObject postObj = postsArray.getJSONObject(i);
                    int postId = postObj.getInt("id");
                    int userId = postObj.getInt("id_user");
                    String username = postObj.getString("username");
                    String content = postObj.getString("content");
                    String createdAt = postObj.getString("created_at");

                    Post post = new Post(postId, userId, username, content, createdAt);
                    postList.add(post);
                }
            } else {
                Log.e("API", "Failed to load posts. HTTP code: " + code);
            }
        } catch (Exception e) {
            Log.e("API", "GetPosts exception", e);
        } finally {
            if (con != null) con.disconnect();
        }
        return postList;
    }

    public static User getUserById(int userId) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(BASE_URL + "users/" + userId);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int code = con.getResponseCode();
            InputStream stream = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }

            Log.d("API", "GetUserById HTTP code: " + code + ", response: " + response);

            if (code == 200) {
                JSONObject obj = new JSONObject(response.toString());
                return new User(
                        obj.getInt("id"),
                        obj.getString("username"),
                        obj.getString("email"),
                        obj.getString("bio"),
                        obj.getString("created_at")
                );
            } else {
                Log.e("API", "Failed to load user. HTTP code: " + code);
                return null;
            }

        } catch (Exception e) {
            Log.e("API", "GetUserById exception", e);
            return null;
        } finally {
            if (con != null) con.disconnect();
        }
    }

    public static List<Post> getUserPosts(int userId) {
        List<Post> postList = new ArrayList<>();
        HttpURLConnection con = null;
        try {
            URL url = new URL(BASE_URL + "posts/" + userId);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int code = con.getResponseCode();
            InputStream stream = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }

            Log.d("API", "GetUserPosts HTTP code: " + code + ", response: " + response);

            if (code == 200) {
                JSONArray postsArray = new JSONArray(response.toString());
                for (int i = 0; i < postsArray.length(); i++) {
                    JSONObject postObj = postsArray.getJSONObject(i);
                    int postId = postObj.getInt("id");
                    int postUserId = postObj.getInt("id_user");
                    String username = postObj.getString("username");
                    String content = postObj.getString("content");
                    String createdAt = postObj.getString("created_at");

                    Post post = new Post(postId, postUserId, username, content, createdAt);
                    postList.add(post);
                }
            } else {
                Log.e("API", "Failed to load user posts. HTTP code: " + code);
            }
        } catch (Exception e) {
            Log.e("API", "GetUserPosts exception", e);
        } finally {
            if (con != null) con.disconnect();
        }
        return postList;
    }




    public static boolean isFollowing(int currentUserId, int targetUserId) {
        boolean isFollowing = false;
        try {
            String urlString = BASE_URL + "followers/isFollowing/" + currentUserId + "/" + targetUserId;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            isFollowing = Boolean.parseBoolean(response.toString());
        } catch (Exception e) {
            Log.e("Api", "Error checking follow status", e);
        }
        Log.i("API",isFollowing+"");
        return isFollowing;
    }

    public static String followUser(int currentUserId, int targetUserId) {
        try {
            String urlString = BASE_URL + "followers/" + currentUserId + "/follow/" + targetUserId;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write("{}".getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = connection.getResponseCode();
            Log.d("FOLLOW_CODE", "Code: " + responseCode);
            if (responseCode == 200) {
                return "success";
            } else {
                return "error";
            }
        } catch (Exception e) {
            Log.e("Api", "Error following user", e);
            return "error";
        }
    }

    public static String unfollowUser(int currentUserId, int targetUserId) {
        try {
            String urlString = BASE_URL + "followers/" + currentUserId + "/unfollow/" + targetUserId;
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return "success";
            } else {
                return "error";
            }
        } catch (Exception e) {
            Log.e("Api", "Error unfollowing user", e);
            return "error";
        }
    }


    public static List<Notification> getNotifications(int id_user) {
        List<Notification> notifications = new ArrayList<>();
        HttpURLConnection con = null;
        try {
            URL url = new URL(BASE_URL + "notifications/"+id_user);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int code = con.getResponseCode();
            InputStream stream = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) response.append(line.trim());

            Log.d("API", "GetPosts HTTP code: " + code + ", response: " + response);

            if (code == 200) {
                JSONArray postsArray = new JSONArray(response.toString());
                for (int i = 0; i < postsArray.length(); i++) {
                    JSONObject postObj = postsArray.getJSONObject(i);
                    int notificationId = postObj.getInt("id");
                    int userId = postObj.getInt("id_user");
                    int followerId = postObj.getInt("id_follower");
                    String username = postObj.getString("username");
                    String createdAt = postObj.getString("created_at");

                    Notification notification = new Notification(notificationId, userId,followerId, username, createdAt);
                    notifications.add(notification);
                }
            } else {
                Log.e("API", "Failed to load notifications. HTTP code: " + code);
            }
        } catch (Exception e) {
            Log.e("API", "GetNotifications exception", e);
        } finally {
            if (con != null) con.disconnect();
        }
        return notifications;
    }

    public static boolean createPost(int userId, String content) {
        try {
            URL url = new URL(BASE_URL + "posts/create");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("id_user", userId);
            json.put("content", content);

            OutputStream os = connection.getOutputStream();
            os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            int responseCode = connection.getResponseCode();
            Log.d("Api", "CreatePost response: " + responseCode);

            return responseCode >= 200 && responseCode < 300;

        } catch (Exception e) {
            Log.e("Api", "Error creating post", e);
            return false;
        }
    }

    public static boolean deletePost(int postId) {
        try {
            URL url = new URL(BASE_URL + "posts/delete/" + postId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");

            int responseCode = connection.getResponseCode();
            Log.d("Api", "DeletePost response: " + responseCode);

            return responseCode >= 200 && responseCode < 300;

        } catch (Exception e) {
            Log.e("Api", "Error deleting post", e);
            return false;
        }
    }

    public static boolean deleteUser(int userId) {
        try {
            URL url = new URL(BASE_URL + "users/delete/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int responseCode = connection.getResponseCode();
            Log.d("Api", "DeleteUser response: " + responseCode);

            return responseCode >= 200 && responseCode < 300;

        } catch (Exception e) {
            Log.e("Api", "Error deleting user", e);
            return false;
        }
    }


    public static boolean updateUser(int userId, String username, String bio) {
        try {
            URL url = new URL(BASE_URL + "users/update/" + userId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("bio", bio);

            OutputStream os = connection.getOutputStream();
            os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            int responseCode = connection.getResponseCode();
            Log.d("Api", "UpdateUser response: " + responseCode);

            return responseCode >= 200 && responseCode < 300;

        } catch (Exception e) {
            Log.e("Api", "Error updating user", e);
            return false;
        }
    }


    public static int getFollowerCount(int userId){
        HttpURLConnection con = null;
        int followers=0;
        try {
            URL url = new URL(BASE_URL + "followers/" + userId+"/followersCount");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int code = con.getResponseCode();
            InputStream stream = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }
            reader.close();
            followers=Integer.parseInt(response.toString());

        } catch (Exception e) {
            Log.e("API", "GetFollowingCount exception", e);
            return 0;
        } finally {
            if (con != null) con.disconnect();
        }
        return followers;
    }
    public static int getFollowingCount(int userId){
        HttpURLConnection con = null;
        int following=0;
        try {
            URL url = new URL(BASE_URL + "followers/" + userId+"/followingCount");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            int code = con.getResponseCode();
            InputStream stream = (code >= 200 && code < 300) ? con.getInputStream() : con.getErrorStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line.trim());
            }
            reader.close();
            following=Integer.parseInt(response.toString());

        } catch (Exception e) {
            Log.e("API", "GetFollowingCount exception", e);
            return 0;
        } finally {
            if (con != null) con.disconnect();
        }
        return following;
    }



}
