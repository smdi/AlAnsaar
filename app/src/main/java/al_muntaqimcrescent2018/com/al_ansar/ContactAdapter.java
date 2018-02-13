package al_muntaqimcrescent2018.com.al_ansar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Imran on 06-02-2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {


    private Context context;
    private List<ContactInitialiser> listView;

    public ContactAdapter(Context context ,List<ContactInitialiser> listView) {
        this.context = context;
        this.listView=listView;
    }

    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list,parent ,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ContactInitialiser contactInitialiser = listView.get(position);

        Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();


        Toast.makeText(context,"",Toast.LENGTH_SHORT).show();

        holder.contactName.setText(""+contactInitialiser.getName());
        holder.profession.setText(""+contactInitialiser.getProfession());
        holder.mail.setText(""+contactInitialiser.getMail());

    }

    @Override
    public int getItemCount() {
        return listView.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView contactName;
        public TextView  profession;
        public TextView  mail;

        public ViewHolder(View itemView) {
            super(itemView);

            contactName = (TextView) itemView.findViewById(R.id.contactName);
            profession  = (TextView) itemView.findViewById(R.id.profession);
            mail        = (TextView) itemView.findViewById(R.id.mail);


        }
    }
}
