package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.speax.ContactActivity;
import com.example.speax.R;
import com.example.speax.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context uContext;
    private List<User> uUsers;

    public UserAdapter(Context uContext, List<User> uUsers) {
        this.uUsers = uUsers;
        this.uContext = uContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(uContext).inflate(R.layout.contact_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = uUsers.get(position);
        holder.userName.setText(user.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(uContext, ContactActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("user-id", user.getUserId());
//                Toast.makeText(uContext, "User Email: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                uContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return uUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
        }
    }
}
