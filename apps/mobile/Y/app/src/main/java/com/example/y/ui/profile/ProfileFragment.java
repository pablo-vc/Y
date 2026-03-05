package com.example.y.ui.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.y.Post;
import com.example.y.R;
import com.example.y.Session;
import com.example.y.User;
import com.example.y.adapters.PostAdapter;
import com.example.y.data.Api;
import com.example.y.databinding.FragmentProfileBinding;
import com.example.y.ui.MainActivity;
import com.example.y.ui.login.LogInActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    private RecyclerView rv;
    private PostAdapter adapter;
    private List<Post> postList = new ArrayList<>();
    private TabLayout tabLayout;
    private Button btnEditProfile;
    TextView tvUsername, tvBio, tvFollowers, tvFollowing;
    Boolean update;
    int currentUserId = Session.getInstance().getUserId();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        initUI();
        setUpListeners();
        loadUserProfile();
        loadPosts();
        return binding.getRoot();
    }

    private void initUI() {
        tabLayout = binding.tabLayout;
        tabLayout.addTab(tabLayout.newTab().setText("Posts"));

        rv = binding.rvOwnPosts;
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PostAdapter(getContext(), postList, true);
        rv.setAdapter(adapter);
        btnEditProfile = binding.btnEditProfile;
        tvUsername = binding.tvUsername;
        tvBio = binding.tvBio;
        tvFollowers = binding.tvFollowers;
        tvFollowing = binding.tvFollowing;
    }

    private void setUpListeners() {
        btnEditProfile.setOnClickListener(v -> {
            showEditProfileDialog();
        });
    }

    private void loadUserProfile() {
        new Thread(() -> {
            User user = Api.getUserById(currentUserId);

            getActivity().runOnUiThread(() -> {
                if (user != null) {
                    tvUsername.setText(user.getUsername());
                    tvBio.setText(user.getBio());
                    loadNumbers();
                }
            });
        }).start();

    }

    private void loadNumbers() {
        new Thread(() -> {
            int Following = Api.getFollowingCount(currentUserId);
            int Followers = Api.getFollowerCount(currentUserId);
            getActivity().runOnUiThread(() -> {
                tvFollowing.setText(Html.fromHtml("<b>" + getString(R.string.following) + ": </b>" + Following));
                tvFollowers.setText(Html.fromHtml("<b>" + getString(R.string.followers) + ": </b>" + Followers));
            });
        }).start();
    }

    private void loadPosts() {
        new Thread(() -> {
            List<Post> posts = Api.getUserPosts(currentUserId);
            getActivity().runOnUiThread(() -> {
                postList.clear();
                postList.addAll(posts);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void showEditProfileDialog() {

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_edit_profile);

        EditText etUsername = dialog.findViewById(R.id.etUsername);
        EditText etBio = dialog.findViewById(R.id.etBio);
        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnDeleteAccount = dialog.findViewById(R.id.btnDeleteAccount);
        String oldUsername = tvUsername.getText().toString();
        String oldBio = tvBio.getText().toString();
        update = true;
        etUsername.setText(oldUsername);
        etBio.setText(oldBio);

        btnSave.setOnClickListener(v -> {

            String username = etUsername.getText().toString().trim();
            String bio = etBio.getText().toString().trim();

            if (username.isEmpty()) {
                etUsername.setError("No puede estar vacío");
                return;
            }

            if (username.equals(tvUsername.getText().toString()) && bio.equals(tvBio.getText().toString())) {
                dialog.dismiss();
                return;
            }

            new Thread(() -> {

                boolean success = Api.updateUser(currentUserId, username, bio);

                requireActivity().runOnUiThread(() -> {

                    if (!isAdded()) return;

                    if (success) {
                        tvUsername.setText(username);
                        tvBio.setText(bio);
                        Toast.makeText(getContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Error al actualizar", Toast.LENGTH_SHORT).show();
                    }

                    dialog.dismiss();
                });

            }).start();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        btnDeleteAccount.setOnClickListener(v -> {

            new AlertDialog.Builder(getContext()).setTitle("Eliminar cuenta").setMessage("Esta acción es permanente. ¿Seguro que quieres eliminar tu cuenta?").setPositiveButton("Eliminar", (dialog2, which) -> {
                deleteAccount();
            }).setNegativeButton("Cancelar", null).show();
        });


    }

    private void deleteAccount() {
        new Thread(() -> {

            boolean success = Api.deleteUser(currentUserId);

            requireActivity().runOnUiThread(() -> {

                if (!isAdded()) return;

                if (success) {

                    Toast.makeText(getContext(), "Cuenta eliminada", Toast.LENGTH_SHORT).show();

                    // Limpiar sesión
                    Session.getInstance().logout();

                    Intent intent = new Intent(requireActivity(), LogInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(getContext(), "Error al eliminar cuenta", Toast.LENGTH_SHORT).show();
                }

            });

        }).start();
    }


}